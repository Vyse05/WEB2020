package ViewModel;

import java.text.SimpleDateFormat;

import Model.Apartman;
import Model.Korisnik;
import Model.Rezervacija;

public class RezervacijaResponse {
	private Integer id;
	private Integer apartmanId;
	private String apartman;
	private String pocetniDatumRezervacije;
	private Integer brojNocenja;
	private Integer ukupnaCena;
	private String poruka;
	private Integer gostId;
	private String gost;
	private String status;
	private String komentar;
	private Integer ocena;
	private Boolean prikazatiKomentar;

	public RezervacijaResponse(Rezervacija rezervacija, Korisnik gost, Apartman apartman) {
		super();
		this.id = rezervacija.getId();
		this.apartmanId = rezervacija.getApartmanId();
		this.apartman = apartman.getUlica() + " " + apartman.getBroj() + ", " + apartman.getPostanskiBroj() + " "
				+ apartman.getNaseljenoMesto();
		this.pocetniDatumRezervacije = new SimpleDateFormat("dd-MM-yyyy").format(rezervacija.getPocetniDatumRezervacije());
		this.brojNocenja = rezervacija.getBrojNocenja();
		this.ukupnaCena = rezervacija.getUkupnaCena();
		this.poruka = rezervacija.getPoruka();
		this.gostId = rezervacija.getGostId();
		this.gost = gost.getIme() + " " + gost.getPrezime();
		this.status = rezervacija.getStatus();
		this.komentar = rezervacija.getKomentar();
		this.ocena = rezervacija.getOcena();
		this.prikazatiKomentar = rezervacija.getPrikazatiKomentar();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getApartmanId() {
		return apartmanId;
	}

	public void setApartmanId(Integer apartmanId) {
		this.apartmanId = apartmanId;
	}

	public String getApartman() {
		return apartman;
	}

	public void setApartman(String apartman) {
		this.apartman = apartman;
	}

	public String getPocetniDatumRezervacije() {
		return pocetniDatumRezervacije;
	}

	public void setPocetniDatumRezervacije(String pocetniDatumRezervacije) {
		this.pocetniDatumRezervacije = pocetniDatumRezervacije;
	}

	public Integer getBrojNocenja() {
		return brojNocenja;
	}

	public void setBrojNocenja(Integer brojNocenja) {
		this.brojNocenja = brojNocenja;
	}

	public Integer getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(Integer ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	public String getPoruka() {
		return poruka;
	}

	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}

	public Integer getGostId() {
		return gostId;
	}

	public void setGostId(Integer gostId) {
		this.gostId = gostId;
	}

	public String getGost() {
		return gost;
	}

	public void setGost(String gost) {
		this.gost = gost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public Integer getOcena() {
		return ocena;
	}

	public void setOcena(Integer ocena) {
		this.ocena = ocena;
	}

	public Boolean getPrikazatiKomentar() {
		return prikazatiKomentar;
	}

	public void setPrikazatiKomentar(Boolean prikazatiKomentar) {
		this.prikazatiKomentar = prikazatiKomentar;
	}
}
