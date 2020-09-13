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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import Admin.Administratori;
import Model.Korisnik;
import Util.DAL;
import ViewModel.ErrorResponse;
import ViewModel.LoginRequest;
import ViewModel.RegisterRequest;
import ViewModel.UserInfoRequest;
import ViewModel.UserInfoResponse;

@Path("korisnik")
public class KorisnikController {
	@Context
	HttpServletRequest servletRequest;
	@Context
	ServletContext application;
	@Context
	HttpServletResponse response;

	private DAL<Korisnik> korisnici(ServletContext application) {
		@SuppressWarnings("unchecked")
		DAL<Korisnik> korisnici = (DAL<Korisnik>) application.getAttribute("korisnici");
		if (korisnici == null) {
			korisnici = new DAL<Korisnik>(Korisnik.class, application.getRealPath("") + "korisnici.txt");
			application.setAttribute("korisnici", korisnici);
		}

		return korisnici;
	}

	private List<Korisnik> administratori(ServletContext application) {
		@SuppressWarnings("unchecked")
		List<Korisnik> administratori = (List<Korisnik>) application.getAttribute("administratori");
		if (administratori == null) {
			administratori = Administratori.get(application);
		}

		return administratori;
	}

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
	@Path("/korisnici")
	public void getKorisniciPage() {
		try {
			Korisnik k = (Korisnik) servletRequest.getSession().getAttribute("korisnik");
			if (k == null) {
				servletRequest.getRequestDispatcher("/WEB-INF/korisnici.html").forward(servletRequest, response);
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
			return Response.ok(new UserInfoResponse(korisnik), MediaType.APPLICATION_JSON).build();
		}
	}

	@POST
	@Path("/info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response izmeniInfo(UserInfoRequest request) {

		Korisnik korisnik = (Korisnik) servletRequest.getSession().getAttribute("korisnik");

		if (korisnik == null) {
			try {
				response.sendRedirect("/WebProj");
				return null;
			} catch (IOException e) {
			}
		}

		DAL<Korisnik> korisnici = korisnici(application);
		Korisnik snimljeniKorisnik = korisnik(korisnici.get(), korisnik.getKorisnickoIme());
		if (snimljeniKorisnik == null) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Korisnik ne postoji ili izmena nije moguća.")).build();
		}
		
		if (!request.getStaraLozinka().equals(korisnik.getLozinka())) {
			return Response.status(403).build();
		}

		snimljeniKorisnik.setIme(request.getIme());
		snimljeniKorisnik.setPrezime(request.getPrezime());
		snimljeniKorisnik.setPol(request.getPol());
		snimljeniKorisnik.setLozinka(request.getNovaLozinka());
		korisnici.refresh();
		return Response.ok().build();
	}

	@POST
	@Path("/registracija")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void registruj(RegisterRequest k) {

		DAL<Korisnik> korisnici = korisnici(application);

		if (korisnik(korisnici.get(), k.getKorisnickoIme()) == null) {
			Korisnik noviKorisnik = new Korisnik(k.getKorisnickoIme(), k.getLozinka(), k.getIme(), k.getPrezime(),
					k.getPol(), "Korisnik", false);

			korisnici.add(noviKorisnik);
		} else {
			try {
				response.sendError(HttpServletResponse.SC_CONFLICT, "postoji");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void login(LoginRequest logovaniKorisnik) {
		String korisnickoIme = logovaniKorisnik.getKorisnickoIme();
		String lozinka = logovaniKorisnik.getLozinka();

		DAL<Korisnik> korisnici = korisnici(application);
		List<Korisnik> administratori = administratori(application);

		Korisnik korisnik = korisnik(korisnici.get(), korisnickoIme);

		if (korisnik == null) {
			korisnik = korisnik(administratori, korisnickoIme);
		}

		if (korisnik != null && korisnik.getLozinka().equals(lozinka)) {
			servletRequest.getSession().setAttribute("korisnik", korisnik);
		} else {
			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "neispravni podaci");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@POST
	@Path("/logout")
	public void logout() {
		servletRequest.getSession().invalidate();
	}

	@GET
	@Path("/svi")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getKorisnici() {
		DAL<Korisnik> korisnici = korisnici(application);
		List<UserInfoResponse> response = new ArrayList<UserInfoResponse>();

		for (Korisnik korisnik : korisnici.get()) {
			if (korisnik.getRemoved()) {
				continue;
			}

			response.add(new UserInfoResponse(korisnik));
		}

		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	private Korisnik korisnik(List<Korisnik> korisnici, String korisnickoIme) {
		for (Korisnik korisnik : korisnici) {
			if (korisnik.getKorisnickoIme().equals(korisnickoIme)) {
				return korisnik;
			}
		}
		return null;
	}
}
