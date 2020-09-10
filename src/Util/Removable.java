package Util;

public abstract class Removable {
	private Boolean removed;
	private Integer id;
	
	public Removable() {
		removed = false;
		id = -1;
	}

	public Removable(Boolean removed, Integer id) {
		this.removed = removed;
		this.id = id;
	}

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}