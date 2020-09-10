package ViewModel;

import Model.Sadrzaj;

public class SadrzajResponse {
	private Integer id;
	private String naziv;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public SadrzajResponse(Sadrzaj sadrzaj) {
		super();
		this.id = sadrzaj.getId();
		this.naziv = sadrzaj.getNaziv();
	}
}
