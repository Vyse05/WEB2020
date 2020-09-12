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
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Model.Apartman;
import Model.Sadrzaj;
import Util.DAL;
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
		@SuppressWarnings("unchecked")
		DAL<Sadrzaj> sadrzaji = (DAL<Sadrzaj>) application.getAttribute("sadrzaji");
		if (sadrzaji == null) {
			sadrzaji = new DAL<Sadrzaj>(Sadrzaj.class, application.getRealPath("") + "/sadrzaji.txt");
			application.setAttribute("sadrzaji", sadrzaji);
		}

		return sadrzaji;
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
}
