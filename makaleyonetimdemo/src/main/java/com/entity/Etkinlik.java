package com.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ETKINLIK")
// namedqueryler eklenecek
public class Etkinlik implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "etkinlikTabloAdi")
	private String etkinlikTabloAdi;
//	@Column(name = "etkinlikKullaniciAdi")
//	private String etkinlikKullaniciAdi;
//	@Column(name = "etkilenenKullaniciAdi")
//	private String etkilenenKullaniciAdi;
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "etkinlikZamani")
	//private java.util.Date etkinlikZamani;
	private long etkinlikZamani;
//	@Column(name = "isMakaleGuncelleme")
//	private boolean isMakaleGuncelleme;
//	@Column(name = "isMakaleSilme")
//	private boolean isMakaleSilme;
//	@Column(name = "isKullaniciSilme")
//	private boolean isKullaniciSilme;
//	@Column(name = "isKullaniciOnaylama")
//	private boolean isKullaniciOnaylama;
//	@Column(name = "isMakaleEkleme")
//	private boolean isMakaleEkleme;
//	@Column(name = "isYeniKayit")
//	private boolean isYeniKayit;
//	@Column(name = "isHesapGuncelleme")
//	private boolean isHesapGuncelleme;
//	@Column(name = "isMakalePaylasimi")
//	private boolean isMakalePaylasimi;
	private String tur;
	public String getTur() {
		return tur;
	}
	public void setTur(String tur) {
		this.tur = tur;
	}
	@ManyToOne
	@JoinColumn(name = "kullanici_id")
	private Kullanici kullanici;
//	public Kullanici getEtkilenenkullanici() {
//		return etkilenenkullanici;
//	}
//	public void setEtkilenenkullanici(Kullanici etkilenenkullanici) {
//		this.etkilenenkullanici = etkilenenkullanici;
//	}
	public Kullanici getKullanici() {
		return kullanici;
	}
	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}
//	public Etkinlik(String etkinlikTabloAdi, String tur,
//			long etkinlikZamani, Kullanici kullanici,Kullanici etkilenenKullanici) {
//		super();
//		this.etkinlikTabloAdi = etkinlikTabloAdi;
//		//this.etkilenenkullanici = etkilenenKullanici;
//		this.tur = tur;
//		this.etkinlikZamani = etkinlikZamani;
//		this.kullanici = kullanici;
//	}
	public Etkinlik(String etkinlikTabloAdi, String tur,
			long etkinlikZamani, Kullanici kullanici) {
		super();
		this.etkinlikTabloAdi = etkinlikTabloAdi;
		//this.etkilenenkullanici = etkilenenKullanici;
		this.tur = tur;
		this.etkinlikZamani = etkinlikZamani;
		this.kullanici = kullanici;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Etkinlik() {
		super();
	}
//	public boolean isMakaleGuncelleme() {
//		return isMakaleGuncelleme;
//	}
//	public void setMakaleGuncelleme(boolean isMakaleGuncelleme) {
//		this.isMakaleGuncelleme = isMakaleGuncelleme;
//	}
	
	public String getEtkilenenMakaleAdi() {
		return etkinlikTabloAdi;
	}
	public void setEtkilenenMakaleAdi(String etkinlikTabloAdi) {
		this.etkinlikTabloAdi = etkinlikTabloAdi;
	}
//	public String getEtkinlikKullaniciAdi() {
//		return etkinlikKullaniciAdi;
//	}
//	public void setEtkinlikKullaniciAdi(String etkinlikKullaniciAdi) {
//		this.etkinlikKullaniciAdi = etkinlikKullaniciAdi;
//	}
//	public String getEtkilenenKullaniciAdi() {
//		return etkilenenKullaniciAdi;
//	}
//	public void setEtkilenenKullaniciAdi(String etkilenenKullaniciAdi) {
//		this.etkilenenKullaniciAdi = etkilenenKullaniciAdi;
//	}
	public long getEtkinlikZamani() {
		return etkinlikZamani;
	}
	public void setEtkinlikZamani(long etkinlikZamani) {
		this.etkinlikZamani = etkinlikZamani;
	}
//	public boolean isMakaleSilme() {
//		return isMakaleSilme;
//	}
//	public void setMakaleSilme(boolean isMakaleSilme) {
//		this.isMakaleSilme = isMakaleSilme;
//	}
//	public boolean isKullaniciSilme() {
//		return isKullaniciSilme;
//	}
//	public void setKullaniciSilme(boolean isKullaniciSilme) {
//		this.isKullaniciSilme = isKullaniciSilme;
//	}
//	public void setMakalePaylasimi(boolean isMakalePaylasimi) {
//		this.isMakalePaylasimi = isMakalePaylasimi;
//	}
	
	
}
