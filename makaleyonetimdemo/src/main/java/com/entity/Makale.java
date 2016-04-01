package com.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MAKALE")
//NamedQueryler eklenecek
public class Makale implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "makaleadi")
	private String makaleAdi;
	@Column(name = "anahtarKelimeler")
	private String anahtarKelimeler;
	//iliþkinin sahip tarafý kullanýcý sýnýfýdýr.
	@ManyToOne
	//(mappedBy = "makaleler" )
	//@ManyToMany
	@JoinColumn(name = "kullanici_Id")
	private Kullanici kullanicilar;
	@ManyToOne
	//(cascade = {CascadeType.REMOVE})
	private MakalePath makalePath;
//	private int path_Id;
	@ManyToOne
	 @JoinColumn(name = "makaleyayintipi_id")
	private MakaleYayinTipi makaleYayinTipi;
//	private int makaleYayinTipi;
	//private int tip_Id;
	//private int ref_Id;
	//private int 
	//(cascade = {CascadeType.REMOVE})
	public Makale(String makaleAdi,String anahtar) {
		super();
		//this.id = id;
		this.makaleAdi = makaleAdi;
		this.anahtarKelimeler = anahtar;
//		this.makalePath = makalePath;
//		this.makaleYayinTipi = makaleYayinTipi;
//		this.referans = referans;
		//this.makaleMetadata = makaleMetadata;
//		kullanicilar = new Kullanici();
		//yazarlar = new ArrayList<Yazar>();
//		makaleMetadata = new MakaleMetadata();
	}
	public Makale() {
		super();
		// TODO Auto-generated constructor stub
	}
	@ManyToOne
	//(cascade = {CascadeType.REMOVE})
	 @JoinColumn(name = "referans_id")
	private Referans referans;
	
	@OneToOne(mappedBy = "makale")
	//cascade = {CascadeType.REMOVE})
	 //@Column(name = "makalemetadata_id")
	private MakaleMetadata makaleMetadata;
	
	
	public String getAnahtarKelimeler() {
		return anahtarKelimeler;
	}
	public void setAnahtarKelimeler(String anahtarKelimeler) {
		this.anahtarKelimeler = anahtarKelimeler;
	}
	public Kullanici getKullanicilar() {
		return kullanicilar;
	}
	public void setKullanicilar(Kullanici kullanicilar) {
		this.kullanicilar = kullanicilar;
	}
	
//	public List<Kullanici> getKullanicilar() {
//		return kullanicilar;
//	}
//
//	public void setKullanicilar(List<Kullanici> kullanicilar) {
//		this.kullanicilar = kullanicilar;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMakaleAdi() {
		return makaleAdi;
	}

	public void setMakaleAdi(String makaleAdi) {
		this.makaleAdi = makaleAdi;
	}

	public MakalePath getMakalePath() {
		return makalePath;
	}

	public void setMakalePath(MakalePath makalePath) {
		this.makalePath = makalePath;
	}

	public MakaleYayinTipi getMakaleYayinTipi() {
		return makaleYayinTipi;
	}

	public void setMakaleYayinTipi(MakaleYayinTipi makaleYayinTipi) {
		this.makaleYayinTipi = makaleYayinTipi;
	}

	public Referans getReferans() {
		return referans;
	}

	public void setReferans(Referans referans) {
		this.referans = referans;
	}

	public MakaleMetadata getMakaleMetadata() {
		return makaleMetadata;
	}

	public void setMakaleMetadata(MakaleMetadata makaleMetadata) {
		this.makaleMetadata = makaleMetadata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anahtarKelimeler == null) ? 0 : anahtarKelimeler.hashCode());
		result = prime * result + ((makaleAdi == null) ? 0 : makaleAdi.hashCode());
		return result;
	}
 
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof Makale) {
//            Makale Makale = (Makale) obj;
//            return Makale.getId() == id;
//        }
// 
//        return false;
//    }
    @Override
    public String toString() {
        return makaleAdi;
    }
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Makale other = (Makale) obj;
		if (anahtarKelimeler == null) {
			if (other.anahtarKelimeler != null)
				return false;
		} else if (!anahtarKelimeler.equals(other.anahtarKelimeler))
			return false;
		if (makaleAdi == null) {
			if (other.makaleAdi != null)
				return false;
		} else if (!makaleAdi.equals(other.makaleAdi))
			return false;
		return true;
	}
}
