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
	public Referans(String referansad, long yil, int sayi, int ciltNo, int basSayfaNo, int sonSayfaNo) {
		super();
		this.referansad = referansad;
		this.yil = yil;
		this.sayi = sayi;
		this.ciltNo = ciltNo;
		this.basSayfaNo = basSayfaNo;
		this.sonSayfaNo = sonSayfaNo;
		//this.DERGI = dergi;
		yazarlar = new HashSet<Yazar>();
	}
	
	public Referans(String referansad, long yil, String kitapevi) {
		super();
		this.referansad = referansad;
		this.yil = yil;
		this.kitapevi = kitapevi;
		
		//this.DERGI = dergi;
		yazarlar = new HashSet<Yazar>();
	}
	
	public Referans(String referansad, long yil, int sayi,int ay, String tezYer, String kurum) {
		super();
		this.referansad = referansad;
		this.yil = yil;
		this.sayi = sayi;
		this.ay = ay;
		this.kurumad = kurum;
		this.yer = tezYer;
		//this.DERGI = dergi;
		yazarlar = new HashSet<Yazar>();
	}
	
	public Referans(String referansad, long yil, int sayi, String konferansad, String konferansyeri,int ay,int basSayfaNo, int sonSayfaNo) {
		super();
		this.referansad = referansad;
		this.konferansad = konferansad;
		this.yer = konferansyeri;
		this.yil = yil;
		this.ay = ay;
		this.sayi = sayi;
		//this.ciltNo = ciltNo;
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
	
//	@ManyToOne
//	private Dergi DERGI;
	@ManyToMany(mappedBy = "referanslar")
	private HashSet<Yazar> yazarlar;
	
	@Column(name = "referansad")
	private String referansad;
	public String getReferansad() {
		System.out.println("in referans class:"+referansad);
		return referansad;
	}

	public void setReferansad(String referansad) {
		this.referansad = referansad;
	}

	public int getReferanshata() {
		return referanshata;
	}

	public void setReferanshata(int referanshata) {
		this.referanshata = referanshata;
	}

	public String getRefhatamsg() {
		return refhatamsg;
	}

	public void setRefhatamsg(String refhatamsg) {
		this.refhatamsg = refhatamsg;
		System.out.println("refhatamsg1:"+refhatamsg);
	}

	public String getKonferansad() {
		return konferansad;
	}

	public void setKonferansad(String konferansad) {
		this.konferansad = konferansad;
		System.out.println("rekonfad:"+konferansad);
	}

	public String getKurumad() {
		return kurumad;
	}

	public void setKurumad(String kurumad) {
		this.kurumad = kurumad;
	}

	public String getKitapevi() {
		return kitapevi;
	}

	public void setKitapevi(String kitapevi) {
		this.kitapevi = kitapevi;
	}

	public String getYer() {
		return yer;
	}

	public void setYer(String yer) {
		this.yer = yer;
		System.out.println("refyer:"+yer);
	}

	public int getAy() {
		return ay;
	}

	public void setAy(int ay) {
		this.ay = ay;
	}

	@Column(name = "referanshata")
	private int referanshata;
	@Column(name = "refhatamsg")
	private String refhatamsg;
	@Column(name = "konferansad")
	private String konferansad;
	@Column(name = "kurumad")
	private String kurumad;
	@Column(name = "kitapevi")
	private String kitapevi;
	@Column(name = "yer")
	private String yer;
	@Column(name = "ay")
	private int ay;
	@Column(name = "dergiad")
	private String dergiad;
	public String getDergiad() {
		return dergiad;
	}

	public void setDergiad(String dergiad) {
		this.dergiad = dergiad;
	}

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

//	public Dergi getDergi() {
//		return DERGI;
//	}
//
//	public void setDergi(Dergi dergi) {
//		this.DERGI = dergi;
//	}
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
		//result = prime * result + ((DERGI == null) ? 0 : DERGI.hashCode());
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
		if (referansad == null) {
			if (other.referansad != null)
				return false;
		} else if (!referansad.equals(other.referansad))
			return false;
		
		return true;
	}
}
