package ViewModel;

import Model.Korisnik;

public class UserInfoResponse {
	private String korisnickoIme;
	private String ime;
	private String prezime;
	private String pol;
	private String uloga;
	private Integer id;

	public UserInfoResponse(Korisnik korisnik) {
		korisnickoIme = korisnik.getKorisnickoIme();
		ime = korisnik.getIme();
		prezime = korisnik.getPrezime();
		pol = korisnik.getPol();
		uloga = korisnik.getUloga();
		id = korisnik.getId();
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getUloga() {
		return uloga;
	}

	public void setUloga(String uloga) {
		this.uloga = uloga;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
