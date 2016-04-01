package com.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DERGI")
// namedqueryler eklenecek
public class Dergi implements Serializable{
	public Dergi() {
		super();
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "dergiAdi")
	private String dergiAdi;
	public Dergi(String dergiAdi) {
		super();
		this.dergiAdi = dergiAdi;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDergiAdi() {
		return dergiAdi;
	}
	public void setDergiAdi(String dergiAdi) {
		this.dergiAdi = dergiAdi;
	}
	@Override
    public int hashCode() {
        return id;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dergi) {
            Dergi dergi = (Dergi) obj;
            return dergi.getDergiAdi() == dergiAdi;
        }
 
        return false;
    }
    @Override
    public String toString() {
        return dergiAdi;
    }
}
