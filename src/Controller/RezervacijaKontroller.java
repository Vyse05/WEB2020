package Controller;

import java.util.ArrayList;
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

import Model.Apartman;
import Model.Korisnik;
import Model.Rezervacija;
import Util.DAL;
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
			servletRequest.getRequestDispatcher("/WEB-INF/gostRezervacije.html").forward(servletRequest,
					servletResponse);
		} catch (Exception e1) {
		}
	}

	
	
	@GET
	@Path("/adminRezervacije")
	public void adminRezervacije() {
		try {
			servletRequest.getRequestDispatcher("/WEB-INF/adminRezervacije.html").forward(servletRequest,
					servletResponse);
		} catch (Exception e1) {
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
	public Response nov(RezervacijaRequest request) {
		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		DAL<Rezervacija> rezervacije = rezervacije(application);
		DAL<Apartman> apartmani = apartmani(application);

		if (korisnik == null) {
			return Response.status(403).build();
		}
		
		Apartman apartman;	
		
		try {
			apartman = apartmani.get().get(request.getApartmanId());
		}
		catch (Exception exception) {
			return Response.status(400).build();
		}
		
		Rezervacija novaLokacija = new Rezervacija(request, apartman, korisnik);
		rezervacije.add(novaLokacija);

		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	public void getRezervacijaPage(@PathParam("id") int id) {
		try {
			servletRequest.getRequestDispatcher("/WEB-INF/rezervacija.html").forward(servletRequest, servletResponse);
		} catch (Exception e1) {
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

		RezervacijaResponse response = new RezervacijaResponse(rezervacija, gost, apartman);
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/svi")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRezervacije() {
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
			RezervacijaResponse rezervacijaReposne = new RezervacijaResponse(rezervacija, gost, apartman);
			response.add(rezervacijaReposne);
		}

		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
}
