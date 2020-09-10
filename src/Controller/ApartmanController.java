package Controller;

import java.io.IOException;
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
import Util.DAL;
import ViewModel.ApartmanRequest;
import ViewModel.ApartmanResponse;

@Path("apartman")
public class ApartmanController {
	@Context
	HttpServletRequest servletRequest;
	@Context
	ServletContext application;
	@Context
	HttpServletResponse servletResponse;

	private DAL<Apartman> apartmani(ServletContext application) {
		@SuppressWarnings("unchecked")
		DAL<Apartman> apartmani = (DAL<Apartman>) application.getAttribute("apartmani");
		if (apartmani == null) {
			apartmani = new DAL<Apartman>(Apartman.class, application.getRealPath(""));
			application.setAttribute("apartmani", apartmani);
		}

		return apartmani;
	}

	private DAL<Korisnik> korisnici(ServletContext application) {
		@SuppressWarnings("unchecked")
		DAL<Korisnik> korisnici = (DAL<Korisnik>) application.getAttribute("korisnici");
		if (korisnici == null) {
			korisnici = new DAL<Korisnik>(Korisnik.class, application.getRealPath(""));
			application.setAttribute("korisnici", korisnici);
		}

		return korisnici;
	}

	@GET
	@Path("/nov")
	public void novPage() {
		try {
			servletRequest.getRequestDispatcher("/WEB-INF/novApartman.html").forward(servletRequest, servletResponse);
		} catch (Exception e1) {
		}
	}

	@POST
	@Path("/nov")
	@Consumes(MediaType.APPLICATION_JSON)
	public void nov(ApartmanRequest request) {
		Korisnik k = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		if (k == null) {
			try {
				servletResponse.sendRedirect("/WebProj/rest/korisnik/login");
				return;
			} catch (IOException e) {
			}
		}
		DAL<Apartman> apartmani = apartmani(application);

		Apartman novaLokacija = new Apartman(request.getTip(), k.getId(), request.getGeografskaSirina(),
				request.getGeografskaDuzina(), request.getUlica(), request.getBroj(), request.getNaseljenoMesto(),
				request.getPostanskiBroj(), request.getBrojSoba(), request.getBrojGostiju(), request.getCena(),
				request.getVremeZaPrijavu(), request.getVremeZaOdjavu(), true);
		apartmani.add(novaLokacija);
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
		Apartman apartman = apartmani.get().get(id);
		DAL<Korisnik> korisnici = korisnici(application);
		Korisnik domacin = korisnici.get().get(apartman.getDomacinId());
		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		Boolean canEdit = korisnik!=null && korisnik.getId() == domacin.getId();
		ApartmanResponse response = new ApartmanResponse(apartman, domacin, canEdit);
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response izmeniApartman(@PathParam("id") int id, ApartmanRequest request) {

		DAL<Apartman> apartmani = apartmani(application);

		Apartman apartman = apartmani.get().get(id);
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
	@Path("/svi")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartmani() {
		List<ApartmanResponse> response = new ArrayList<>();

		DAL<Apartman> apartmani = apartmani(application);
		DAL<Korisnik> korisnici = korisnici(application);
		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		
		for(Apartman apartman:apartmani.get()) {
			if(apartman.getRemoved()) {
				continue;
			}
			
			Korisnik domacin = korisnici.get().get(apartman.getDomacinId());
			Boolean canEdit = korisnik!=null && korisnik.getId() == domacin.getId();
			ApartmanResponse apartmanResponse = new ApartmanResponse(apartman, domacin, canEdit);
			response.add(apartmanResponse);			
		}
		
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
}
