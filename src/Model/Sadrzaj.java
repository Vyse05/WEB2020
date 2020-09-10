package Model;

import java.util.StringTokenizer;

import Util.Removable;

public class Sadrzaj extends Removable {
	private String naziv;
	
	public Sadrzaj(Integer id, String korisnik) {
		setId(id);
		StringTokenizer tokenizer = new StringTokenizer(korisnik, ";");
		setRemoved(tokenizer.nextToken().trim().equals("true"));

		naziv = tokenizer.nextToken().trim();
	}

	public Sadrzaj(String naziv,
			Boolean removed) {
		super();
		this.naziv = naziv;
		setRemoved(removed);
	}

	@Override
	public String toString() {
		return getRemoved() + ";" + naziv;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
}