package Controller;

import java.util.ArrayList;
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

import Model.Korisnik;
import Model.Sadrzaj;
import Util.DAL;
import ViewModel.ErrorResponse;
import ViewModel.SadrzajRequest;
import ViewModel.SadrzajResponse;

@Path("sadrzaj")
public class SadrzajController {
	@Context
	HttpServletRequest servletRequest;
	@Context
	ServletContext application;
	@Context
	HttpServletResponse servletResponse;

	private DAL<Sadrzaj> sadrzaji(ServletContext application) {
		return new DAL<Sadrzaj>(Sadrzaj.class, application.getRealPath("") + "sadrzaji.txt");
	}
	
	@GET
	@Path("/svi")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSadrzaji() {
		DAL<Sadrzaj> sadrzaji = sadrzaji(application);		
		List<SadrzajResponse> response = new ArrayList<SadrzajResponse>();
		
		for(Sadrzaj sadrzaj : sadrzaji.get()) {
			if(sadrzaj.getRemoved()) {
				continue;
			}
			
			response.add(new SadrzajResponse(sadrzaj));
		}
		
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/nov")
	public void novPage() {
		try {
			servletRequest.getRequestDispatcher("/WEB-INF/novSadrzaj.html").forward(servletRequest, servletResponse);
		} catch (Exception e1) {
		}
	}

	@POST
	@Path("/nov")
	@Consumes(MediaType.APPLICATION_JSON)
	public void nov(SadrzajRequest request) {
		DAL<Sadrzaj> sadrzaji = sadrzaji(application);

		Sadrzaj novSadrzaj = new Sadrzaj(request.getNaziv(), false);
		sadrzaji.add(novSadrzaj);
	}
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id) {
		DAL<Sadrzaj> sadrzaji = sadrzaji(application);

		Sadrzaj sadrzaj;
		try {
			sadrzaj = sadrzaji.get().get(id);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Sadržaj ne postoji.")).build();
		}

		if (sadrzaj == null || sadrzaj.getRemoved()) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Sadržaj ne postoji.")).build();
		}

		return Response.status(Status.OK).entity(new SadrzajResponse(sadrzaj)).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id, SadrzajRequest request) {
		DAL<Sadrzaj> sadrzaji = sadrzaji(application);

		Sadrzaj sadrzaj;
		try {
			sadrzaj = sadrzaji.get().get(id);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Sadržaj ne postoji.")).build();
		}

		if (sadrzaj == null || sadrzaj.getRemoved()) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Sadržaj ne postoji.")).build();
		}
		
		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");

		if ( korisnik == null || !korisnik.getUloga().equals("Administrator")) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		sadrzaj.setNaziv(request.getNaziv());
		sadrzaji.refresh();
		
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response brisanje(@PathParam("id") int id) {
		DAL<Sadrzaj> sadrzaji = sadrzaji(application);

		Sadrzaj sadrzaj;
		try {
			sadrzaj = sadrzaji.get().get(id);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Sadržaj ne postoji.")).build();
		}

		if (sadrzaj == null || sadrzaj.getRemoved()) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Sadržaj ne postoji.")).build();
		}

		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");

		if ( korisnik == null || !korisnik.getUloga().equals("Administrator")) {
			return Response.status(Status.FORBIDDEN).build();
		}

		sadrzaj.setRemoved(true);
		sadrzaji.refresh();

		return Response.ok().build();
	}
}
