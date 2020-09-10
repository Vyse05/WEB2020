package Model;

import java.util.StringTokenizer;

import Util.Removable;

public class Korisnik extends Removable {
	private String korisnickoIme;
	private String lozinka;
	private String ime;
	private String prezime;
	private String pol;
	private String uloga;

	public Korisnik(Integer id, String korisnik) {
		setId(id);
		StringTokenizer tokenizer = new StringTokenizer(korisnik, ";");
		setRemoved(tokenizer.nextToken().trim().equals("true"));

		korisnickoIme = tokenizer.nextToken().trim();
		lozinka = tokenizer.nextToken().trim();
		ime = tokenizer.nextToken().trim();
		prezime = tokenizer.nextToken().trim();
		pol = tokenizer.nextToken().trim();
		uloga = tokenizer.nextToken().trim();
	}

	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String uloga,
			Boolean removed) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.uloga = uloga;
		setRemoved(removed);
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
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

	@Override
	public String toString() {
		return getRemoved() + ";" + korisnickoIme + ";" + lozinka + ";" + ime + ";" + prezime + ";" + pol + ";" + uloga;
	}
}