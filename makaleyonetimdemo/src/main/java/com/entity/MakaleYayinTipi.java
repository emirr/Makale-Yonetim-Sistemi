package com.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MAKALEYAYINTIPI")
// namedqueryler eklenecek
public class MakaleYayinTipi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "isBildiri")
	private boolean bildiri;
	@Column(name = "isMakale")
	private boolean makale;

	public MakaleYayinTipi() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MakaleYayinTipi(boolean isBildiri, boolean isMakale, boolean isTez, boolean isKitap) {
		super();
		this.bildiri = isBildiri;
		this.makale = isMakale;
		this.tez = isTez;
		this.kitap = isKitap;
	}

	@Column(name = "isTez")
	private boolean tez;
	@Column(name = "isKitap")
	private boolean kitap;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isBildiri() {
		return bildiri;
	}

	public void setBildiri(boolean isBildiri) {
		this.bildiri = isBildiri;
	}

	public boolean isMakale() {
		return makale;
	}

	public void setMakale(boolean isMakale) {
		this.makale = isMakale;
	}

	public boolean isTez() {
		return tez;
	}

	public void setTez(boolean isTez) {
		this.tez = isTez;
	}

	public boolean isKitap() {
		return kitap;
	}

	public void setKitap(boolean isKitap) {
		this.kitap = isKitap;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Yazar) {
			MakaleYayinTipi makaleYayinTipi = (MakaleYayinTipi) obj;
			return makaleYayinTipi.getId() == id;
		}

		return false;
	}

	@Override
	public String toString() {
		if (bildiri == true) {
			return "Bildiri";
		} else {
			if (makale == true) {
				return "Makale";
			} else {
				if (tez == true) {
					return "Tez";
				} else
					return "Kitap";
			}
		}

	}
}
