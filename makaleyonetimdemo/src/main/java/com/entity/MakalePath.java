package com.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MAKALEPATH")
// namedqueryler eklenecek
public class MakalePath implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	private String makalePath;
	public int getId() {
		return id;
	}
	public MakalePath() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MakalePath(int referansId) {
		super();
		this.makalePath = "\\refPath"+referansId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMakalePath() {
		return makalePath;
	}
	public void setMakalePath(String makalePath) {
		this.makalePath = makalePath;
	}
	@Override
	public String toString() {
		return "MakalePath [makalePath=" + makalePath + "]";
	}
	
}
