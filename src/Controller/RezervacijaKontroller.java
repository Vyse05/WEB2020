package Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
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
import Util.DAL;
import ViewModel.ErrorResponse;
import ViewModel.KomentarRequest;
import ViewModel.RezervacijaRequest;
import ViewModel.RezervacijaResponse;

@Path("rezervacija")
public class RezervacijaKontroller {
	@Context
	HttpServletRequest servletRequest;
	@Context
	ServletContext application;
	@Context
	HttpServletResponse servletResponse;

	private DAL<Rezervacija> rezervacije(ServletContext application) {
		return new DAL<Rezervacija>(Rezervacija.class, application.getRealPath("") + "rezervacije.txt");
	}

	private DAL<Korisnik> korisnici(ServletContext application) {
		return new DAL<Korisnik>(Korisnik.class, application.getRealPath("") + "korisnici.txt");
	}

	private DAL<Apartman> apartmani(ServletContext application) {
		return new DAL<Apartman>(Apartman.class, application.getRealPath("") + "apartmani.txt");
	}

	@GET
	@Path("/gostRezervacije")
	public void gostRezervacije() {
		try {
			Korisnik k = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
			if (k == null || !k.getUloga().equals("Korisnik")) {
				servletResponse.sendRedirect("/WebProj");
			} else {
				servletRequest.getRequestDispatcher("/WEB-INF/gostRezervacije.html").forward(servletRequest,
						servletResponse);
			}
		} catch (Exception e1) {
			try {
				servletResponse.sendRedirect("/WebProj");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/adminRezervacije")
	public void adminRezervacije() {
		try {
			Korisnik k = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
			if (k == null || !k.getUloga().equals("Administrator")) {
				servletResponse.sendRedirect("/WebProj");
			} else {
				servletRequest.getRequestDispatcher("/WEB-INF/adminRezervacije.html").forward(servletRequest,
						servletResponse);
			}
		} catch (Exception e1) {
			try {
				servletResponse.sendRedirect("/WebProj");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/nov/{id}")
	public void novPage() {
		try {
			servletRequest.getRequestDispatcher("/WEB-INF/novRezervacija.html").forward(servletRequest,
					servletResponse);
		} catch (Exception e1) {
		}
	}

	@POST
	@Path("/nov")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response nov(RezervacijaRequest request) {
		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		DAL<Rezervacija> rezervacijeRepo = rezervacije(application);
		DAL<Apartman> apartmani = apartmani(application);

		if (korisnik == null) {
			return Response.status(403).build();
		}

		Apartman apartman;

		try {
			apartman = apartmani.get().get(request.getApartmanId());
		} catch (Exception exception) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman ne postoji.")).build();
		}

		List<Rezervacija> rezervacije = new ArrayList<>();
		for (Rezervacija rezervacija : rezervacijeRepo.get()) {
			if (rezervacija.getApartmanId() == apartman.getId() && !rezervacija.getStatus().equals("ODBIJENA")) {
				rezervacije.add(rezervacija);
			}
		}

		Calendar c = Calendar.getInstance();

		c.setTime(request.getPocetniDatumRezervacije());
		c.add(Calendar.DATE, request.getBrojNocenja() - 1);
		Date endDate = c.getTime();
		Boolean slobodan = slobodan(rezervacije, request.getPocetniDatumRezervacije(), endDate);
		
		if(!slobodan) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Apartman nije slobodan za zadati broj noćenja.")).build();
		}
		
		Rezervacija novaLokacija = new Rezervacija(request, apartman, korisnik);
		rezervacijeRepo.add(novaLokacija);

		return Response.ok().build();
	}

	@GET
	@Path("/domacinRezervacije")
	public void domacinRezervacije() {
		try {
			Korisnik k = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
			if (k == null || !k.getUloga().equals("Domaćin")) {
				servletResponse.sendRedirect("/WebProj");
			} else {
				servletRequest.getRequestDispatcher("/WEB-INF/domacinRezervacije.html").forward(servletRequest,
						servletResponse);
			}
		} catch (Exception e1) {
			try {
				servletResponse.sendRedirect("/WebProj");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/ostaviKomentar/{id}")
	public void ostaviKomentar() {
		try {
			servletRequest.getRequestDispatcher("/WEB-INF/ostavljanjeKomentara.html").forward(servletRequest,
					servletResponse);
		} catch (Exception e1) {
		}
	}

	@PUT
	@Path("/{id}/komentar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response dodajKomentar(@PathParam("id") int id, KomentarRequest request) {
		DAL<Rezervacija> rezervacije = rezervacije(application);
		Rezervacija rezervacija = rezervacije.get().get(id);

		rezervacija.setKomentar(request.getKomentar());
		rezervacija.setOcena(request.getOcena());
		rezervacija.setPrikazatiKomentar(false);

		rezervacije.refresh();

		return Response.ok().build();
	}

	@PUT
	@Path("/{id}/odobriKomentar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response odobriKomentar(@PathParam("id") int id) {
		DAL<Rezervacija> rezervacije = rezervacije(application);
		Rezervacija rezervacija = rezervacije.get().get(id);

		rezervacija.setPrikazatiKomentar(true);
		rezervacije.refresh();

		return Response.ok().build();
	}

	@PUT
	@Path("/{id}/sakrijKomentar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sakrijKomentar(@PathParam("id") int id) {
		DAL<Rezervacija> rezervacije = rezervacije(application);
		Rezervacija rezervacija = rezervacije.get().get(id);

		rezervacija.setPrikazatiKomentar(false);
		rezervacije.refresh();

		return Response.ok().build();
	}

	@GET
	@Path("/{id}/info")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRezervacijaInfo(@PathParam("id") int id) {

		DAL<Rezervacija> rezervacije = rezervacije(application);
		DAL<Korisnik> korisnici = korisnici(application);
		DAL<Apartman> apartmani = apartmani(application);

		Rezervacija rezervacija = rezervacije.get().get(id);
		Korisnik gost = korisnici.get().get(rezervacija.getGostId());
		Apartman apartman = apartmani.get().get(rezervacija.getApartmanId());
		Korisnik domacin = korisnici.get().get(apartman.getDomacinId());

		RezervacijaResponse response = new RezervacijaResponse(rezervacija, gost, domacin, apartman);
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/svi")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRezervacije() {
		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		if (korisnik == null) {
			return Response.status(403).build();
		}

		List<RezervacijaResponse> response = new ArrayList<>();

		DAL<Rezervacija> rezervacije = rezervacije(application);
		DAL<Korisnik> korisnici = korisnici(application);
		DAL<Apartman> apartmani = apartmani(application);

		for (Rezervacija rezervacija : rezervacije.get()) {
			if (rezervacija.getRemoved()) {
				continue;
			}

			Korisnik gost = korisnici.get().get(rezervacija.getGostId());
			Apartman apartman = apartmani.get().get(rezervacija.getApartmanId());
			Korisnik domacin = korisnici.get().get(apartman.getDomacinId());
			RezervacijaResponse rezervacijaReposne = new RezervacijaResponse(rezervacija, gost, domacin, apartman);
			if ((korisnik.getUloga().equals("Korisnik") && korisnik.getId() == gost.getId())
					|| (korisnik.getUloga().equals("Domaćin") && korisnik.getId() == domacin.getId())
					|| korisnik.getUloga().equals("Administrator")) {
				response.add(rezervacijaReposne);
			}
		}

		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("/{id}/zavrsi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response zavrsi(@PathParam("id") int id) {
		DAL<Rezervacija> rezervacije = rezervacije(application);
		Rezervacija rezervacija = rezervacije.get().get(id);
		if (rezervacija == null) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Rezervacija ne postoji.")).build();
		}

		rezervacija.setStatus("ZAVRŠENA");
		rezervacije.refresh();

		return Response.ok().build();
	}

	@PUT
	@Path("/{id}/odbij")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response odbij(@PathParam("id") int id) {
		DAL<Rezervacija> rezervacije = rezervacije(application);
		Rezervacija rezervacija = rezervacije.get().get(id);
		if (rezervacija == null) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Rezervacija ne postoji.")).build();
		}

		rezervacija.setStatus("ODBIJENA");
		rezervacije.refresh();

		return Response.ok().build();
	}

	@PUT
	@Path("/{id}/prihvati")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response prihvati(@PathParam("id") int id) {
		DAL<Rezervacija> rezervacije = rezervacije(application);
		Rezervacija rezervacija = rezervacije.get().get(id);
		if (rezervacija == null) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Rezervacija ne postoji.")).build();
		}

		rezervacija.setStatus("PRIHVAĆENA");
		rezervacije.refresh();

		return Response.ok().build();
	}

	@PUT
	@Path("/{id}/odustani")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response odustani(@PathParam("id") int id) {
		DAL<Rezervacija> rezervacije = rezervacije(application);
		Rezervacija rezervacija = rezervacije.get().get(id);
		if (rezervacija == null) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Rezervacija ne postoji.")).build();
		}

		rezervacija.setStatus("ODUSTANAK");
		rezervacije.refresh();

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
