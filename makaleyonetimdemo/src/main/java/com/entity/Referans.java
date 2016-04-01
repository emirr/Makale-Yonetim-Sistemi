package com.entity;

import java.io.Serializable;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "REFERANS")
// namedqueryler eklenecek
public class Referans implements Serializable{
	public Referans(long yil, int sayi, int ciltNo, int basSayfaNo, int sonSayfaNo) {
		super();
		this.yil = yil;
		this.sayi = sayi;
		this.ciltNo = ciltNo;
		this.basSayfaNo = basSayfaNo;
		this.sonSayfaNo = sonSayfaNo;
		//this.DERGI = dergi;
		yazarlar = new HashSet<Yazar>();
	}

	public Referans() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "yil")
	private long yil;
	@Column(name = "sayi")
	private int sayi;
	@Column(name = "ciltNo")
	private int ciltNo;
	private int basSayfaNo;
	private int sonSayfaNo;
	
	@ManyToOne
	private Dergi DERGI;
	@ManyToMany(mappedBy = "referanslar")
	private HashSet<Yazar> yazarlar;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getYil() {
		return yil;
	}

	public void setYil(long yil) {
		this.yil = yil;
	}

	public int getSayi() {
		return sayi;
	}

	public void setSayi(int sayi) {
		this.sayi = sayi;
	}

	public int getCiltNo() {
		return ciltNo;
	}

	public void setCiltNo(int ciltNo) {
		this.ciltNo = ciltNo;
	}

	public int getBasSayfaNo() {
		return basSayfaNo;
	}

	public void setBasSayfaNo(int basSayfaNo) {
		this.basSayfaNo = basSayfaNo;
	}

	public int getSonSayfaNo() {
		return sonSayfaNo;
	}

	public void setSonSayfaNo(int sonSayfaNo) {
		this.sonSayfaNo = sonSayfaNo;
	}

	public Dergi getDergi() {
		return DERGI;
	}

	public void setDergi(Dergi dergi) {
		this.DERGI = dergi;
	}
	public HashSet<Yazar> getYazarlar() {
		return yazarlar;
	}
	public void setYazarlar(HashSet<Yazar> yazarlar) {
		this.yazarlar = yazarlar;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DERGI == null) ? 0 : DERGI.hashCode());
		result = prime * result + basSayfaNo;
		result = prime * result + ciltNo;
		result = prime * result + id;
		result = prime * result + sayi;
		result = prime * result + sonSayfaNo;
		result = prime * result + (int) (yil ^ (yil >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Referans other = (Referans) obj;
		if (DERGI == null) {
			if (other.DERGI != null)
				return false;
		} else if (!DERGI.equals(other.DERGI))
			return false;
		if (basSayfaNo != other.basSayfaNo)
			return false;
		if (ciltNo != other.ciltNo)
			return false;
//		if (id != other.id)
//			return false;
		if (sayi != other.sayi)
			return false;
		if (sonSayfaNo != other.sonSayfaNo)
			return false;
		if (yil != other.yil)
			return false;
		return true;
	}
}
