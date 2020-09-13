package ViewModel;

import java.util.Date;

public class RezervacijaRequest{
	private Integer apartmanId;
	private Date pocetniDatumRezervacije;
	private Integer brojNocenja;
	private String poruka;

	public Integer getApartmanId() {
		return apartmanId;
	}

	public void setApartmanId(Integer apartmanId) {
		this.apartmanId = apartmanId;
	}

	public Date getPocetniDatumRezervacije() {
		return pocetniDatumRezervacije;
	}

	public void setPocetniDatumRezervacije(Date pocetniDatumRezervacije) {
		this.pocetniDatumRezervacije = pocetniDatumRezervacije;
	}

	public Integer getBrojNocenja() {
		return brojNocenja;
	}

	public void setBrojNocenja(Integer brojNocenja) {
		this.brojNocenja = brojNocenja;
	}

	public String getPoruka() {
		return poruka;
	}

	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}
}
