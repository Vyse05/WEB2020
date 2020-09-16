package Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
		@SuppressWarnings("unchecked")
		DAL<Rezervacija> rezervacije = (DAL<Rezervacija>) application.getAttribute("rezervacije");
		if (rezervacije == null) {
			rezervacije = new DAL<Rezervacija>(Rezervacija.class, application.getRealPath("") + "rezervacije.txt");
			application.setAttribute("rezervacije", rezervacije);
		}

		return rezervacije;
	}

	private DAL<Korisnik> korisnici(ServletContext application) {
		@SuppressWarnings("unchecked")
		DAL<Korisnik> korisnici = (DAL<Korisnik>) application.getAttribute("korisnici");
		if (korisnici == null) {
			korisnici = new DAL<Korisnik>(Korisnik.class, application.getRealPath("") + "korisnici.txt");
			application.setAttribute("korisnici", korisnici);
		}

		return korisnici;
	}

	private DAL<Apartman> apartmani(ServletContext application) {
		@SuppressWarnings("unchecked")
		DAL<Apartman> apartmani = (DAL<Apartman>) application.getAttribute("apartmani");
		if (apartmani == null) {
			apartmani = new DAL<Apartman>(Apartman.class, application.getRealPath("") + "apartmani.txt");
			application.setAttribute("apartmani", apartmani);
		}

		return apartmani;
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
	public void getApartmanPage(@PathParam("id") int id) {
		try {
			servletRequest.getRequestDispatcher("/WEB-INF/rezervacija.html").forward(servletRequest, servletResponse);
		} catch (Exception e1) {
		}
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
