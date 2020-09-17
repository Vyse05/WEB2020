package ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ApartmanRequest {
	private String tip;
	private String geografskaSirina;
	private String geografskaDuzina;
	private String ulica;
	private String broj;
	private String naseljenoMesto;
	private String postanskiBroj;
	private Integer brojSoba;
	private Integer brojGostiju;
	private Integer cena;
	private Integer vremeZaPrijavu;
	private Integer vremeZaOdjavu;
	private List<Integer> sadrzajIds;

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getGeografskaSirina() {
		return geografskaSirina;
	}

	public void setGeografskaSirina(String geografskaSirina) {
		this.geografskaSirina = geografskaSirina;
	}

	public String getGeografskaDuzina() {
		return geografskaDuzina;
	}

	public void setGeografskaDuzina(String geografskaDuzina) {
		this.geografskaDuzina = geografskaDuzina;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public String getNaseljenoMesto() {
		return naseljenoMesto;
	}

	public void setNaseljenoMesto(String naseljenoMesto) {
		this.naseljenoMesto = naseljenoMesto;
	}

	public String getPostanskiBroj() {
		return postanskiBroj;
	}

	public void setPostanskiBroj(String postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}

	public Integer getBrojSoba() {
		return brojSoba;
	}

	public void setBrojSoba(Integer brojSoba) {
		this.brojSoba = brojSoba;
	}

	public Integer getBrojGostiju() {
		return brojGostiju;
	}

	public void setBrojGostiju(Integer brojGostiju) {
		this.brojGostiju = brojGostiju;
	}

	public Integer getCena() {
		return cena;
	}

	public void setCena(Integer cena) {
		this.cena = cena;
	}

	public Integer getVremeZaPrijavu() {
		return vremeZaPrijavu;
	}

	public void setVremeZaPrijavu(Integer vremeZaPrijavu) {
		this.vremeZaPrijavu = vremeZaPrijavu;
	}

	public Integer getVremeZaOdjavu() {
		return vremeZaOdjavu;
	}

	public void setVremeZaOdjavu(Integer vremeZaOdjavu) {
		this.vremeZaOdjavu = vremeZaOdjavu;
	}

	public List<Integer> getSadrzajIds() {
		if(sadrzajIds==null) {
			sadrzajIds = new ArrayList<>();
		}
		return sadrzajIds;
	}

	public void setSadrzajIds(List<Integer> sadrzajIds) {
		this.sadrzajIds = sadrzajIds;
	}
}
