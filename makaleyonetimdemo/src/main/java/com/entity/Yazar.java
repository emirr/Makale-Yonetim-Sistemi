package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "YAZAR")
// namedqueryler eklenecek
public class Yazar implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "yazaradi")
	private String yazarAdi;
	
	@ManyToMany
	private List<Referans> referanslar;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYazarAdi() {
		return yazarAdi;
	}

	public void setYazarAdi(String yazarAdi) {
		this.yazarAdi = yazarAdi;
	}

	public List<Referans> getReferansler() {
		return  referanslar;
	}

	public void setReferansler(List<Referans> referanslar) {
		this.referanslar = referanslar;
	}

	
 
    public Yazar(String yazarAdi) {
		super();
		//this.id = id;
		this.yazarAdi = yazarAdi;
		referanslar = new ArrayList<Referans>();
	}

	public Yazar() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((referanslar == null) ? 0 : referanslar.hashCode());
		result = prime * result + ((yazarAdi == null) ? 0 : yazarAdi.hashCode());
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
		Yazar other = (Yazar) obj;
//		if (id != other.id)
//			return false;
		if (referanslar == null) {
			if (other.referanslar != null)
				return false;
		} else if (!referanslar.equals(other.referanslar))
			return false;
		if (yazarAdi == null) {
			if (other.yazarAdi != null)
				return false;
		} else if (!yazarAdi.equals(other.yazarAdi))
			return false;
		return true;
	}
    @Override
    public String toString() {
        return yazarAdi;
    }
}
