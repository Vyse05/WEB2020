package Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import Model.Apartman;
import Model.Korisnik;
import Model.Rezervacija;
import Model.Sadrzaj;
import Util.DAL;
import ViewModel.ApartmanRequest;
import ViewModel.ApartmanResponse;
import ViewModel.DostupniRequest;
import ViewModel.ErrorResponse;
import ViewModel.KomentarResponse;

@Path("apartman")
public class ApartmanController {
	@Context
	HttpServletRequest servletRequest;
	@Context
	ServletContext application;
	@Context
	HttpServletResponse servletResponse;

	private DAL<Apartman> apartmani(ServletContext application) {
		return new DAL<Apartman>(Apartman.class, application.getRealPath("") + "apartmani.txt");
	}

	private DAL<Korisnik> korisnici(ServletContext application) {
		return new DAL<Korisnik>(Korisnik.class, application.getRealPath("") + "korisnici.txt");
	}

	private DAL<Rezervacija> rezervacije(ServletContext application) {

		return new DAL<Rezervacija>(Rezervacija.class, application.getRealPath("") + "rezervacije.txt");
	}
	
	private DAL<Sadrzaj> sadrzaji(ServletContext application) {
		return new DAL<Sadrzaj>(Sadrzaj.class, application.getRealPath("") + "sadrzaji.txt");
	}

	@GET
	@Path("/nov")
	public void novPage() {
		Korisnik k = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		if (k == null) {
			try {
				servletResponse.sendRedirect("/WebProj/rest/korisnik/login");
				return;
			} catch (IOException e) {
			}
		}

		if (!k.getUloga().equals("Domaćin")) {
			try {
				servletResponse.sendRedirect("/WebProj");
				return;
			} catch (IOException e) {
			}
		}

		try {
			servletRequest.getRequestDispatcher("/WEB-INF/novApartman.html").forward(servletRequest, servletResponse);
		} catch (Exception e1) {
		}
	}

	@GET
	@Path("/komentari/{id}")
	public void komentarPage(@PathParam("id") int id) {
		try {
			servletRequest.getRequestDispatcher("/WEB-INF/komentari.html").forward(servletRequest, servletResponse);
		} catch (Exception e1) {
		}
	}

	@POST
	@Path("/nov")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response nov(ApartmanRequest request) {
		Korisnik k = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		if (k == null || !k.getUloga().equals("Domaćin")) {
			return Response.status(Status.FORBIDDEN).build();
		}
		DAL<Apartman> apartmani = apartmani(application);

		Apartman novApartman = new Apartman(request.getTip(), k.getId(), request.getGeografskaSirina(),
				request.getGeografskaDuzina(), request.getUlica(), request.getBroj(), request.getNaseljenoMesto(),
				request.getPostanskiBroj(), request.getBrojSoba(), request.getBrojGostiju(), request.getCena(),

				request.getVremeZaPrijavu(), request.getVremeZaOdjavu(), request.getSadrzajIds(), false);
		apartmani.add(novApartman);


		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	public void getApartmanPage(@PathParam("id") int id) {
		try {
			servletRequest.getRequestDispatcher("/WEB-INF/apartman.html").forward(servletRequest, servletResponse);
		} catch (Exception e1) {
		}
	}

	@GET
	@Path("/{id}/info")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartmanInfo(@PathParam("id") int id) {

		DAL<Apartman> apartmani = apartmani(application);
		DAL<Sadrzaj> sadrzajiRepo = sadrzaji(application);
		Apartman apartman;
		try {
			apartman = apartmani.get().get(id);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		if (apartman == null || apartman.getRemoved()) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		DAL<Korisnik> korisnici = korisnici(application);
		Korisnik domacin = korisnici.get().get(apartman.getDomacinId());
		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		Boolean canEdit = korisnik != null
				&& (korisnik.getId() == domacin.getId() || korisnik.getUloga().equals("Administrator"));
		
		List<Sadrzaj> sadrzaji = new ArrayList<>();
		for (Integer sadrzajId : apartman.getSadrzajIds()) {
			try {
				sadrzaji.add(sadrzajiRepo.get().get(sadrzajId));
			} catch (Exception e) {
			}
		}
		
		ApartmanResponse response = new ApartmanResponse(apartman, domacin, canEdit, sadrzaji);
		DAL<Rezervacija> rezervacije = rezervacije(application);
		for (Rezervacija rezervacija : rezervacije.get()) {
			if (rezervacija.getApartmanId() != id || rezervacija.getStatus().equals("ODBIJENA")) {
				continue;
			}
			response.getUnavailable()
					.add(new SimpleDateFormat("dd-MM-yyyy").format(rezervacija.getPocetniDatumRezervacije()));
			for (int i = 1; i < rezervacija.getBrojNocenja(); i++) {
				Calendar c = Calendar.getInstance();
				c.setTime(rezervacija.getPocetniDatumRezervacije());
				c.add(Calendar.DATE, i);
				Date newDate = c.getTime();
				response.getUnavailable().add(new SimpleDateFormat("dd-MM-yyyy").format(newDate));
			}
		}

		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response izmeniApartman(@PathParam("id") int id, ApartmanRequest request) {
		DAL<Apartman> apartmani = apartmani(application);

		Apartman apartman;
		try {
			apartman = apartmani.get().get(id);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		if (apartman == null || apartman.getRemoved()) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		Boolean canEdit = korisnik != null
				&& (korisnik.getId() == apartman.getDomacinId() || korisnik.getUloga().equals("Administrator"));
		if (!canEdit) {
			return Response.status(Status.FORBIDDEN).build();
		}

		apartman.setBroj(request.getBroj());
		apartman.setBrojGostiju(request.getBrojGostiju());
		apartman.setBrojSoba(request.getBrojSoba());
		apartman.setCena(request.getCena());
		apartman.setGeografskaDuzina(request.getGeografskaDuzina());
		apartman.setGeografskaSirina(request.getGeografskaSirina());
		apartman.setNaseljenoMesto(request.getNaseljenoMesto());
		apartman.setPostanskiBroj(request.getPostanskiBroj());
		apartman.setTip(request.getTip());
		apartman.setUlica(request.getUlica());
		apartman.setVremeZaOdjavu(request.getVremeZaOdjavu());
		apartman.setVremeZaPrijavu(request.getVremeZaPrijavu());

		apartmani.refresh();
		return Response.ok().build();
	}

	@GET
	@Path("/{id}/komentariKorisnik")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartmanKomentariKorisnik(@PathParam("id") int id) {

		DAL<Apartman> apartmani = apartmani(application);

		Apartman apartman;
		try {
			apartman = apartmani.get().get(id);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		if (apartman == null || apartman.getRemoved()) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		List<KomentarResponse> response = new ArrayList<>();

		DAL<Korisnik> korisnici = korisnici(application);

		DAL<Rezervacija> rezervacije = rezervacije(application);
		for (Rezervacija rezervacija : rezervacije.get()) {
			if (rezervacija.getApartmanId() != id
					|| (!rezervacija.getStatus().equals("ZAVRŠENA") && !rezervacija.getStatus().equals("ODBIJENA"))
					|| rezervacija.getOcena() == null || rezervacija.getKomentar() == null
					|| (rezervacija.getPrikazatiKomentar() != null && !rezervacija.getPrikazatiKomentar())) {
				continue;
			}
			Korisnik gost = korisnici.get().get(rezervacija.getGostId());

			response.add(new KomentarResponse(rezervacija, gost, apartman));
		}

		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/{id}/komentariAdmin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartmanKomentariAdmin(@PathParam("id") int id) {

		DAL<Apartman> apartmani = apartmani(application);

		Apartman apartman;
		try {
			apartman = apartmani.get().get(id);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		if (apartman == null || apartman.getRemoved()) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		List<KomentarResponse> response = new ArrayList<>();

		DAL<Korisnik> korisnici = korisnici(application);

		DAL<Rezervacija> rezervacije = rezervacije(application);
		for (Rezervacija rezervacija : rezervacije.get()) {
			if (rezervacija.getApartmanId() != id || rezervacija.getApartmanId() != id
					|| (!rezervacija.getStatus().equals("ZAVRŠENA") && !rezervacija.getStatus().equals("ODBIJENA"))
					|| rezervacija.getOcena() == null || rezervacija.getKomentar() == null) {
				continue;
			}
			Korisnik gost = korisnici.get().get(rezervacija.getGostId());

			response.add(new KomentarResponse(rezervacija, gost, apartman));
		}

		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/svi")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartmani() {
		List<ApartmanResponse> response = new ArrayList<>();

		DAL<Apartman> apartmani = apartmani(application);
		DAL<Korisnik> korisnici = korisnici(application);
		DAL<Sadrzaj> sadrzajiRepo = sadrzaji(application);
		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		DAL<Rezervacija> rezervacije = rezervacije(application);

		for (Apartman apartman : apartmani.get()) {
			if (apartman.getRemoved()) {
				continue;
			}

			Korisnik domacin = korisnici.get().get(apartman.getDomacinId());
			Boolean canEdit = korisnik != null
					&& (korisnik.getId() == domacin.getId() || korisnik.getUloga().equals("Administrator"));
			
			List<Sadrzaj> sadrzaji = new ArrayList<>();
			for (Integer sadrzajId : apartman.getSadrzajIds()) {
				try {
					sadrzaji.add(sadrzajiRepo.get().get(sadrzajId));
				} catch (Exception e) {
				}
			}
			
			ApartmanResponse apartmanResponse = new ApartmanResponse(apartman, domacin, canEdit, sadrzaji);

			for (Rezervacija rezervacija : rezervacije.get()) {
				if (rezervacija.getApartmanId() != apartman.getId() || rezervacija.getStatus().equals("ODBIJENA")) {
					continue;
				}
				apartmanResponse.getUnavailable()
						.add(new SimpleDateFormat("dd-MM-yyyy").format(rezervacija.getPocetniDatumRezervacije()));
				for (int i = 1; i < rezervacija.getBrojNocenja(); i++) {
					Calendar c = Calendar.getInstance();
					c.setTime(rezervacija.getPocetniDatumRezervacije());
					c.add(Calendar.DATE, i);
					Date newDate = c.getTime();
					apartmanResponse.getUnavailable().add(new SimpleDateFormat("dd-MM-yyyy").format(newDate));
				}
			}

			response.add(apartmanResponse);
		}

		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/dostupni")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDostupni(DostupniRequest request) {
		List<ApartmanResponse> response = new ArrayList<>();

		DAL<Apartman> apartmani = apartmani(application);
		DAL<Korisnik> korisnici = korisnici(application);
		DAL<Sadrzaj> sadrzajiRepo = sadrzaji(application);
		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		DAL<Rezervacija> rezervacijeRepo = rezervacije(application);

		for (Apartman apartman : apartmani.get()) {
			if (apartman.getRemoved()) {
				continue;
			}

			List<Rezervacija> rezervacije = new ArrayList<>();
			for (Rezervacija rezervacija : rezervacijeRepo.get()) {
				if (rezervacija.getApartmanId() == apartman.getId() && !rezervacija.getStatus().equals("ODBIJENA")) {
					rezervacije.add(rezervacija);
				}
			}

			Boolean slobodan = slobodan(rezervacije, request.getStartDate(), request.getEndDate());
			
			if(!slobodan) {
				continue;
			}
			
			Korisnik domacin = korisnici.get().get(apartman.getDomacinId());
			Boolean canEdit = korisnik != null
					&& (korisnik.getId() == domacin.getId() || korisnik.getUloga().equals("Administrator"));
			
			List<Sadrzaj> sadrzaji = new ArrayList<>();
			for (Integer sadrzajId : apartman.getSadrzajIds()) {
				try {
					sadrzaji.add(sadrzajiRepo.get().get(sadrzajId));
				} catch (Exception e) {
				}
			}
			
			ApartmanResponse apartmanResponse = new ApartmanResponse(apartman, domacin, canEdit, sadrzaji);

			for (Rezervacija rezervacija : rezervacijeRepo.get()) {
				if (rezervacija.getApartmanId() != apartman.getId() || rezervacija.getStatus().equals("ODBIJENA")) {
					continue;
				}
				apartmanResponse.getUnavailable()
						.add(new SimpleDateFormat("dd-MM-yyyy").format(rezervacija.getPocetniDatumRezervacije()));
				for (int i = 1; i < rezervacija.getBrojNocenja(); i++) {
					Calendar c = Calendar.getInstance();
					c.setTime(rezervacija.getPocetniDatumRezervacije());
					c.add(Calendar.DATE, i);
					Date newDate = c.getTime();
					apartmanResponse.getUnavailable().add(new SimpleDateFormat("dd-MM-yyyy").format(newDate));
				}
			}

			response.add(apartmanResponse);
		}

		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response brisanje(@PathParam("id") int id) {
		DAL<Apartman> apartmani = apartmani(application);

		Apartman apartman;
		try {
			apartman = apartmani.get().get(id);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		if (apartman == null || apartman.getRemoved()) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		Boolean canEdit = korisnik != null
				&& (korisnik.getId() == apartman.getDomacinId() || korisnik.getUloga().equals("Administrator"));
		if (!canEdit) {
			return Response.status(Status.FORBIDDEN).build();
		}

		apartman.setRemoved(true);
		apartmani.refresh();

		return Response.ok().build();
	}

	@PUT
	@Path("/{id}/deaktiviraj")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deaktiviraj(@PathParam("id") int id) {
		DAL<Apartman> apartmani = apartmani(application);
		Apartman apartman;
		try {
			apartman = apartmani.get().get(id);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		if (apartman == null || apartman.getRemoved()) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		Boolean canEdit = korisnik != null
				&& (korisnik.getId() == apartman.getDomacinId() || korisnik.getUloga().equals("Administrator"));
		if (!canEdit) {
			return Response.status(Status.FORBIDDEN).build();
		}

		apartman.setAktivno(false);
		apartmani.refresh();

		return Response.ok().build();
	}

	@PUT
	@Path("/{id}/aktiviraj")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response aktiviraj(@PathParam("id") int id) {
		DAL<Apartman> apartmani = apartmani(application);
		Apartman apartman;
		try {
			apartman = apartmani.get().get(id);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		if (apartman == null || apartman.getRemoved()) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		Boolean canEdit = korisnik != null
				&& (korisnik.getId() == apartman.getDomacinId() || korisnik.getUloga().equals("Administrator"));
		if (!canEdit) {
			return Response.status(Status.FORBIDDEN).build();
		}

		apartman.setAktivno(true);
		apartmani.refresh();

		return Response.ok().build();
	}

	private Boolean slobodan(List<Rezervacija> rezervacije, Date startDate, Date endDate) {

		for (Rezervacija rezervacija : rezervacije) {
			for (int i = 0; i < rezervacija.getBrojNocenja(); i++) {
				Calendar c = Calendar.getInstance();
				c.setTime(rezervacija.getPocetniDatumRezervacije());
				c.add(Calendar.DATE, i);
				Date newDate = c.getTime();

				if (newDate.compareTo(startDate) >= 0 && newDate.compareTo(endDate) <= 0) {
					return false;
				}
			}
		}
		return true;
	}
}
