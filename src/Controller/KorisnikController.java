package Controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Model.Korisnik;

@Path("korisnik")
public class KorisnikController {
	@Context
	HttpServletRequest servletRequest;
	@Context
	ServletContext application;
	@Context
	HttpServletResponse response;

	@GET
	@Path("/login")
	public void getLogin() {
		try {
			Korisnik k = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
			if (k == null) {
				servletRequest.getRequestDispatcher("/WEB-INF/login.html").forward(servletRequest, response);
			} else {
				response.sendRedirect("/WebProj");
			}
		} catch (Exception e1) {
			try {
				response.sendRedirect("/WebProj");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/registracija")
	public void getRegistracija() {
		try {
			Korisnik k = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
			if (k == null) {
				servletRequest.getRequestDispatcher("/WEB-INF/registracija.html").forward(servletRequest, response);
			} else {
				response.sendRedirect("/WebProj");
			}
		} catch (Exception e1) {
			try {
				response.sendRedirect("/WebProj");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/profil")
	public void getProfil() {
		try {
			Korisnik k = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
			if (k == null) {
				response.sendRedirect("/WebProj/rest/korisnik/login");
			} else {
				servletRequest.getRequestDispatcher("/WEB-INF/profil.html").forward(servletRequest, response);
			}
		} catch (Exception e1) {
			try {
				response.sendRedirect("/WebProj");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/info")
	@Produces(MediaType.APPLICATION_JSON)
	public Response info() {
		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
		if (korisnik == null) {
			return Response.status(403).build();
		} else {
			return Response.ok().build();
		}
	}
}
