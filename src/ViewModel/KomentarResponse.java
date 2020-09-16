package ViewModel;

import Model.Apartman;
import Model.Korisnik;
import Model.Rezervacija;

public class KomentarResponse {
	private Integer id;
	private Integer gostId;
	private String gost;
	private String komentar;
	private Integer ocena;
	private Boolean prikazatiKomentar;

	public KomentarResponse(Rezervacija rezervacija, Korisnik gost, Apartman apartman) {
		super();
		this.id = rezervacija.getId();
		this.gostId = rezervacija.getGostId();
		this.gost = gost.getIme() + " " + gost.getPrezime();
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
