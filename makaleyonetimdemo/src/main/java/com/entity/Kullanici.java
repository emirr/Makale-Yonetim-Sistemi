package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "KULLANICI")
//NamedQueryler eklenecek
public class Kullanici implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private int id;
	
	@Column(name = "kullaniciAd")
    private String kullaniciAd;
	@Column(name = "mail", unique = true)
	private String mail;
	private String unvan;
	private String okul;
	@Column(name = "isOnayli")
	private boolean isOnayli;
	@Size(min = 4, max = 20)
    @Column(name = "Sifre")
	private String sifre;
    
	 @Column(name = "message")
		private String message;
	 public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMessagestat() {
		return messagestat;
	}

	public void setMessagestat(int messagestat) {
		this.messagestat = messagestat;
	}

	@Column(name = "messagestat")
		private int messagestat;
	 
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    //@OneToMany
   // private List<Makale> makaleler;
    
    public Kullanici(String kullaniciAd, String mail, String unvan, String okul) {
		super();
		//this.id = id;
		this.kullaniciAd = kullaniciAd;
		this.mail = mail;
		this.unvan = unvan;
		this.okul = okul;
		//this.sifre = sifre;
		//makaleler = new ArrayList<Makale>();
		etkinlikler = new ArrayList<Etkinlik>();
		//metadata = new HashSet<MakaleMetadata>();
	}

	public Kullanici() {
		super();
		// TODO Auto-generated constructor stub
	}

	@OneToMany(mappedBy = "kullanici", cascade = {CascadeType.REMOVE})
    private List<Etkinlik> etkinlikler;
//	@OneToMany(mappedBy = "kullanici")
//	private Set<MakaleMetadata> metadata;
	//metadata set olarak tutulursa ayný makale ve kullanici adýna sahip bir adet metadata olacak demektir.
//	public Set<MakaleMetadata> getMetadata() {
//		return metadata;
//	}
//
//	public void setMetadata(Set<MakaleMetadata> metadata) {
//		this.metadata = metadata;
//	}

	public String getUnvan() {
		return unvan;
	}

	public void setUnvan(String unvan) {
		this.unvan = unvan;
	}

	public String getOkul() {
		return okul;
	}

	public void setOkul(String okul) {
		this.okul = okul;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean isOnayli() {
		return isOnayli;
	}

	public void setOnayli(boolean isOnayli) {
		this.isOnayli = isOnayli;
	}

	public List<Etkinlik> getEtkinlikler() {
		return etkinlikler;
	}

	public void setEtkinlikler(List<Etkinlik> etkinlikler) {
		this.etkinlikler = etkinlikler;
	}

//	public List<Makale> getMakaleler() {
//		return makaleler;
//	}
//
//	public void setMakaleler(List<Makale> makaleler) {
//		this.makaleler = makaleler;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKullaniciAd() {
		return kullaniciAd;
	}

	public void setKullaniciAd(String kullaniciAd) {
		this.kullaniciAd = kullaniciAd;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public boolean isAdmin() {
        return Rol.ADMIN.equals(rol);
    }
 
    public boolean isKullanici() {
        return Rol.KULLANICI.equals(rol);
    }
	
	@Override
    public int hashCode() {
        return getId();
    }
 
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Kullanici) {
            Kullanici Kullanici = (Kullanici) obj;
            return Kullanici.getId() == id;
        }
 
        return false;
    }

	@Override
	public String toString() {
		return "Kullanici [kullaniciAd=" + kullaniciAd + "]";
	}
    
}
