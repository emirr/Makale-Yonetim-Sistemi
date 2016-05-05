package com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.entity.Etkinlik;
import com.service.EtkinlikService;
import com.service.KullaniciService;

@ManagedBean
@ViewScoped
public class KullaniciEtkinlikController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Etkinlik> etkinlikler;
	private List<String> etkinlikZamanBilgileri = new ArrayList<>();
	String etkinlikBilgi;
	private EtkinlikService es = new EtkinlikService();
	long mevcutZaman = System.currentTimeMillis();
	public List<String> getEtkinlikZamanBilgileri() {
		return etkinlikZamanBilgileri;
	}

	public void setEtkinlikZamanBilgileri(List<String> etkinlikZamanBilgileri) {
		this.etkinlikZamanBilgileri = etkinlikZamanBilgileri;
	}

	public List<Etkinlik> getEtkinlikler() {
		return etkinlikler;
	}

	public void setEtkinlikler(List<Etkinlik> etkinlikler) {
		this.etkinlikler = etkinlikler;
	}

	KullaniciService ks = new KullaniciService();
	@ManagedProperty("#{kullaniciController}")
	private KullaniciController kullaniciController;

	public KullaniciController getKullaniciController() {
		return kullaniciController;
	}

	public void setKullaniciController(KullaniciController kullaniciController) {
		this.kullaniciController = kullaniciController;
	}

	@PostConstruct
	public void init() {
		//etkinlikler = kullaniciController.getKullanici().getEtkinlikler();
		etkinlikler = es.retrieveEtkinlikByIdDesc(kullaniciController.getKullanici().getId());
		for(Etkinlik etkinlik : etkinlikler){
			etkinlikBilgi = "";
			etkinlikBilgi = etkinlikZaman(etkinlik);
			System.out.println("etkinlik bilgi:" + etkinlikBilgi);
			etkinlikZamanBilgileri.add(etkinlikBilgi);
		}
	}

	public String etkinlikZaman(Etkinlik etkinlik) {
		long etkinlikZaman = etkinlik.getEtkinlikZamani();
		

		int gecenGun = (int) (((mevcutZaman - etkinlikZaman) / 1000) / 3600) / 24;
		System.out.println("etkinlik" + etkinlik.getId() + "için geçen gün sayýsý:" + gecenGun);
		if (gecenGun > 0) {
			etkinlikBilgi += gecenGun;
			etkinlikBilgi += " gün önce";
		}

		if (!(gecenGun > 0)) {
			int gecenSaat = (int) ((mevcutZaman - etkinlikZaman) / 1000) / 3600;
			System.out.println("etkinlik" + etkinlik.getId() + "için geçen saat sayýsý:" + gecenSaat);

			if (gecenSaat > 0) {
				etkinlikBilgi += gecenSaat;
				etkinlikBilgi += " saat önce";
			} else {
				int gecenDakika = (int) ((mevcutZaman - etkinlikZaman) / 1000) / 60;
				System.out.println("etkinlik" + etkinlik.getId() + "için geçen dk. sayýsý:" + gecenDakika);

				if (gecenDakika > 0) {
					etkinlikBilgi += gecenDakika;
					etkinlikBilgi += " dakika önce";
				}
				else{
					int gecenSaniye = (int) (mevcutZaman - etkinlikZaman) / 1000;
					System.out.println("etkinlik" + etkinlik.getId() + "için geçen saniye sayýsý:" + gecenSaniye);

					etkinlikBilgi += gecenSaniye;
					etkinlikBilgi += " saniye önce";
				}

			}
		}
		return etkinlikBilgi;
	}
	public String anaSayfa() {
		KullaniciController.urlHandler();
		System.out.println("handler");
		return "/pages/protected/kullanici/kullaniciAnaSayfasi.xhtml?faces-redirect=true";
	}
}
