package com.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MAKALEMETADATA")
// namedqueryler eklenecek
public class MakaleMetadata implements Serializable{
	public MakaleMetadata() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MakaleMetadata(String makaleNot, int makalePuan, String makaleKonu) {
		super();
		//this.anahtarKelimeler = anahtarKelimeler;
		this.makaleNot = makaleNot;
		this.makalePuan = makalePuan;
		this.makaleKonu = makaleKonu;
		makale = new Makale();
		//kullanici = new Kullanici();
	}
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
//	@ManyToOne
//	@JoinColumn(name = "kullanici_id")
//	private Kullanici kullanici;
	@OneToOne
	@JoinColumn(name = "makale_id")
	private Makale makale;
	private String makaleNot;
	private int makalePuan;
	private String makaleKonu;
	
//	public Kullanici getKullanici() {
//		return kullanici;
//	}
//	public void setKullanici(Kullanici kullanici) {
//		this.kullanici = kullanici;
//	}
	public Makale getMakale() {
		return makale;
	}
	public void setMakale(Makale makale) {
		this.makale = makale;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMakaleNot() {
		return makaleNot;
	}
	public void setMakaleNot(String makaleNot) {
		this.makaleNot = makaleNot;
	}
	public int getMakalePuan() {
		return makalePuan;
	}
	public void setMakalePuan(int makalePuan) {
		this.makalePuan = makalePuan;
	}
	public String getMakaleKonu() {
		return makaleKonu;
	}
	public void setMakaleKonu(String makaleKonu) {
		this.makaleKonu = makaleKonu;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((kullanici == null) ? 0 : kullanici.hashCode());
		result = prime * result + ((makale == null) ? 0 : makale.hashCode());
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
		MakaleMetadata other = (MakaleMetadata) obj;
//		if (kullanici == null) {
//			if (other.kullanici != null)
//				return false;
//		} else if (!kullanici.equals(other.kullanici))
//			return false;
		if (makale == null) {
			if (other.makale != null)
				return false;
		} else if (!makale.equals(other.makale))
			return false;
		return true;
	}
	
}
