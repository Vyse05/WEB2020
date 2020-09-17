package ViewModel;

import java.util.ArrayList;
import java.util.List;

import Model.Apartman;
import Model.Korisnik;
import Model.Sadrzaj;

public class ApartmanResponse {
	private String tip;
	private Integer id;
	private String domacin;
	private Integer domacinId;
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
	private Boolean canEdit;
	private Boolean aktivno;
	private List<String> unavailable;
	private List<SadrzajResponse> sadrzaji;

	public ApartmanResponse(Apartman apartman, Korisnik domacin, Boolean canEdit, List<Sadrzaj> sadrzaji) {
		super();
		this.tip = apartman.getTip();
		this.id = apartman.getId();
		this.domacin = domacin.getIme() + " " + domacin.getPrezime();
		this.domacinId = domacin.getId();
		this.geografskaSirina = apartman.getGeografskaSirina();
		this.geografskaDuzina = apartman.getGeografskaDuzina();
		this.ulica = apartman.getUlica();
		this.broj = apartman.getBroj();
		this.naseljenoMesto = apartman.getNaseljenoMesto();
		this.postanskiBroj = apartman.getPostanskiBroj();
		this.brojSoba = apartman.getBrojSoba();
		this.brojGostiju = apartman.getBrojGostiju();
		this.cena = apartman.getCena();
		this.vremeZaPrijavu = apartman.getVremeZaPrijavu();
		this.vremeZaOdjavu = apartman.getVremeZaOdjavu();

		this.sadrzaji = new ArrayList<>();
		for (Sadrzaj sadrzaj : sadrzaji) {
			this.sadrzaji.add(new SadrzajResponse(sadrzaj));
		}

		this.setAktivno(apartman.getAktivno());
		this.canEdit = canEdit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getDomacin() {
		return domacin;
	}

	public void setDomacin(String domacin) {
		this.domacin = domacin;
	}

	public Integer getDomacinId() {
		return domacinId;
	}

	public void setDomacinId(Integer domacinId) {
		this.domacinId = domacinId;
	}

	public Boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	public List<String> getUnavailable() {
		if (unavailable == null) {
			unavailable = new ArrayList<>();
		}
		return unavailable;
	}

	public void setUnavailable(List<String> unavailable) {
		this.unavailable = unavailable;
	}

	public Boolean getAktivno() {
		return aktivno;
	}

	public void setAktivno(Boolean aktivno) {
		this.aktivno = aktivno;
	}

	public List<SadrzajResponse> getSadrzaji() {
		if(sadrzaji == null) {
			sadrzaji = new ArrayList<>();
		}
		return sadrzaji;
	}

	public void setSadrzaji(List<SadrzajResponse> sadrzaji) {
		this.sadrzaji = sadrzaji;
	}
}
