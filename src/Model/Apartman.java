package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import Util.Removable;

public class Apartman extends Removable {
	private Integer domacinId;
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
	private Boolean aktivno;
	private List<Integer> sadrzajIds;

	public Apartman(Integer id, String apartman) {
		sadrzajIds = new ArrayList<>();

		setId(id);
		StringTokenizer tokenizer = new StringTokenizer(apartman, ";");
		setRemoved(tokenizer.nextToken().trim().equals("true"));

		tip = tokenizer.nextToken().trim();
		domacinId = Integer.parseInt(tokenizer.nextToken().trim());
		geografskaSirina = tokenizer.nextToken().trim();
		geografskaDuzina = tokenizer.nextToken().trim();
		ulica = tokenizer.nextToken().trim();
		broj = tokenizer.nextToken().trim();
		naseljenoMesto = tokenizer.nextToken().trim();
		postanskiBroj = tokenizer.nextToken().trim();
		brojSoba = Integer.parseInt(tokenizer.nextToken().trim());
		brojGostiju = Integer.parseInt(tokenizer.nextToken().trim());
		cena = Integer.parseInt(tokenizer.nextToken().trim());
		vremeZaPrijavu = Integer.parseInt(tokenizer.nextToken().trim());
		vremeZaOdjavu = Integer.parseInt(tokenizer.nextToken().trim());
		String sadrzajiTemp = tokenizer.nextToken().trim();
		StringTokenizer sadrzajiTokenizer = new StringTokenizer(sadrzajiTemp, ",");
		while(true) {
			try {
				sadrzajIds.add(Integer.parseInt(sadrzajiTokenizer.nextToken().trim()));
			} catch (Exception e) {
				break;
			}	
		}

		aktivno = tokenizer.nextToken().trim().equals("true");
	}

	public Apartman(String tip, Integer domacinId, String geografskaSirina, String geografskaDuzina, String ulica,
			String broj, String naseljenoMesto, String postanskiBroj, Integer brojSoba, Integer brojGostiju,
			Integer cena, Integer vremeZaPrijavu, Integer vremeZaOdjavu, List<Integer> sadrzajIds, Boolean aktivno) {
		super();
		this.tip = tip;
		this.domacinId = domacinId;
		this.geografskaSirina = geografskaSirina;
		this.geografskaDuzina = geografskaDuzina;
		this.ulica = ulica;
		this.broj = broj;
		this.naseljenoMesto = naseljenoMesto;
		this.postanskiBroj = postanskiBroj;
		this.brojSoba = brojSoba;
		this.brojGostiju = brojGostiju;
		this.cena = cena;
		this.vremeZaPrijavu = vremeZaPrijavu;
		this.vremeZaOdjavu = vremeZaOdjavu;
		this.aktivno = aktivno;
		this.sadrzajIds = sadrzajIds;
		setRemoved(false);
	}

	@Override
	public String toString() {
		String result = getRemoved() + ";" + tip + ";" + domacinId + ";" + geografskaSirina + ";" + geografskaDuzina
				+ ";" + ulica + ";" + broj + ";" + naseljenoMesto + ";" + postanskiBroj + ";" + brojSoba + ";"
				+ brojGostiju + ";" + cena + ";" + vremeZaPrijavu + ";" + vremeZaOdjavu + ";";
		
		if(sadrzajIds.size()>0) {
			result = result + sadrzajIds.get(0);
			
			for (int i=1; i<sadrzajIds.size(); i++) {
				result = result + "," + sadrzajIds.get(i);
			}
		}

		result = result + ";" + aktivno;
		return result;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
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

	public Boolean getAktivno() {
		return aktivno;
	}

	public void setAktivno(Boolean aktivno) {
		this.aktivno = aktivno;
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

	public Integer getDomacinId() {
		return domacinId;
	}

	public void setDomacinId(Integer domacinId) {
		this.domacinId = domacinId;
	}

	public List<Integer> getSadrzajIds() {
		if (sadrzajIds == null) {
			sadrzajIds = new ArrayList<>();
		}
		return sadrzajIds;
	}

	public void setSadrzajIds(List<Integer> sadrzajIds) {
		this.sadrzajIds = sadrzajIds;
	}
}