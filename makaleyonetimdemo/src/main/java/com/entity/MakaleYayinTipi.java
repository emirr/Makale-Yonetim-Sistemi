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
	private boolean isBildiri;
	@Column(name = "isKonferans")
	private boolean isKonferans;

	public MakaleYayinTipi() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MakaleYayinTipi(boolean isBildiri, boolean isKonferans, boolean isTez, boolean isKitap) {
		super();
		this.isBildiri = isBildiri;
		this.isKonferans = isKonferans;
		this.isTez = isTez;
		this.isKitap = isKitap;
	}

	@Column(name = "isTez")
	private boolean isTez;
	@Column(name = "isKitap")
	private boolean isKitap;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isBildiri() {
		return isBildiri;
	}

	public void setBildiri(boolean isBildiri) {
		this.isBildiri = isBildiri;
	}

	public boolean isKonferans() {
		return isKonferans;
	}

	public void setKonferans(boolean isKonferans) {
		this.isKonferans = isKonferans;
	}

	public boolean isTez() {
		return isTez;
	}

	public void setTez(boolean isTez) {
		this.isTez = isTez;
	}

	public boolean isKitap() {
		return isKitap;
	}

	public void setKitap(boolean isKitap) {
		this.isKitap = isKitap;
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
		if (isBildiri == true) {
			return "bildiri";
		} else {
			if (isKonferans == true) {
				return "konferans";
			} else {
				if (isTez == true) {
					return "tez";
				} else
					return "kitap";
			}
		}

	}
}
