package Model;

import java.util.Date;
import java.util.StringTokenizer;

import Util.Removable;
import ViewModel.RezervacijaRequest;

public class Rezervacija extends Removable {
	private Integer apartmanId;
	private Date pocetniDatumRezervacije;
	private Integer brojNocenja;
	private Integer ukupnaCena;
	private String poruka;
	private Integer gostId;
	private String status;
	private String komentar;
	private Integer ocena;
	private Boolean prikazatiKomentar;

	public Rezervacija(Integer id, String rezervacija) {
		setId(id);
		StringTokenizer tokenizer = new StringTokenizer(rezervacija, ";");
		setRemoved(tokenizer.nextToken().trim().equals("true"));

		apartmanId = Integer.parseInt(tokenizer.nextToken().trim());
		pocetniDatumRezervacije = new Date(Long.parseLong(tokenizer.nextToken().trim()));
		brojNocenja = Integer.parseInt(tokenizer.nextToken().trim());
		ukupnaCena = Integer.parseInt(tokenizer.nextToken().trim());
		poruka = tokenizer.nextToken().trim();
		gostId = Integer.parseInt(tokenizer.nextToken().trim());
		status = tokenizer.nextToken().trim();
		
		String komentarTemp = tokenizer.nextToken().trim();
		komentar = komentarTemp.equals("null") ? null : komentarTemp;
		
		String ocenaTemp = tokenizer.nextToken().trim();
		ocena = ocenaTemp.equals("null") ? null : Integer.parseInt(ocenaTemp);
		
		String prikazatiTemp = tokenizer.nextToken().trim();
		prikazatiKomentar = prikazatiTemp.equals("null") ? null : prikazatiTemp.equals("true");
	}

	public Rezervacija(RezervacijaRequest request, Apartman apartman, Korisnik korisnik) {
		this.apartmanId = request.getApartmanId();
		this.pocetniDatumRezervacije = request.getPocetniDatumRezervacije();
		this.brojNocenja = request.getBrojNocenja();
		this.ukupnaCena = request.getBrojNocenja() * apartman.getCena();
		this.poruka = request.getPoruka();
		this.gostId = korisnik.getId();
		this.status = "KREIRANA";
		this.komentar = null;
		this.ocena = null;
		this.prikazatiKomentar = null;
		setRemoved(false);
	}

	@Override
	public String toString() {
		return getRemoved() + ";" + apartmanId + ";" + pocetniDatumRezervacije.getTime() + ";" + brojNocenja + ";"
				+ ukupnaCena + ";" + poruka + ";" + gostId + ";" + status + ";" + komentar + ";" + ocena + ";"
				+ prikazatiKomentar;
	}

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
