package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.entity.Kullanici;
import com.entity.Makale;
import com.entity.MakaleYayinTipi;
import com.entity.Referans;
import com.service.KullaniciService;
import com.service.MakaleService;
import com.service.ReferansService;

@ManagedBean
@ViewScoped
public class AdminAnaSayfaController {
	KullaniciService ks = new KullaniciService();
	ReferansService rs = new ReferansService();
	Referans referans2;
	boolean duzeltilebilir;
	public boolean isDuzeltilebilir() {
		return duzeltilebilir;
	}

	public void setDuzeltilebilir(boolean duzeltilebilir) {
		this.duzeltilebilir = duzeltilebilir;
	}

	public Referans getReferans2() {
		return referans2;
	}

	public void setReferans2(Referans referans) {
		this.referans2 = referans;
	}

	Makale mkl;
	String refHatamsg, yer, konferansad, kurumad, kitapevi,dergiad,referansAd ;
	MakaleYayinTipi makaleyayintipi;
	public MakaleYayinTipi getMakaleyayintipi() {
		return makaleyayintipi;
	}

	public void setMakaleyayintipi(MakaleYayinTipi makaleyayintipi) {
		this.makaleyayintipi = makaleyayintipi;
	}

	int ay,refsayi,refciltno,refbassayfano,refsonsayfano;
	long refyil;
	public String getRefHatamsg() {
		return refHatamsg;
	}

	public void setRefHatamsg(String refHatamsg) {
		this.refHatamsg = refHatamsg;
	}

	private List<Makale> filteredReferanslar;
	public List<Makale> getFilteredReferanslar() {
		return filteredReferanslar;
	}

	public void setFilteredReferanslar(List<Makale> filteredReferanslar) {
		this.filteredReferanslar = filteredReferanslar;
	}

	private List<Kullanici> filteredKullanicilar;

	public List<Kullanici> getFilteredKullanicilar() {
		return filteredKullanicilar;
	}

	public void setFilteredKullanicilar(List<Kullanici> filteredKullanicilar) {
		this.filteredKullanicilar = filteredKullanicilar;
	}

	List<Kullanici> onayliKullaniciList, onayBekleyenKullaniciList;
	List<Referans> hataliReferanslar;
	@ManagedProperty("#{kullaniciController}")
	private KullaniciController kullaniciController;

	public KullaniciController getKullaniciController() {
		return kullaniciController;
	}

	public void setKullaniciController(KullaniciController kullaniciController) {
		this.kullaniciController = kullaniciController;
	}
	public AdminAnaSayfaController() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void init() {
		
		onayliKullaniciList = ks.listOnayliKullanicilar();
		onayBekleyenKullaniciList = ks.listOnayBekleyenKullanicilar();
		hataliReferanslar = rs.hataliReferanslar();
		referans2 = new Referans();
		
	}
	
	public List<Kullanici> getOnayliKullaniciList() {
		return onayliKullaniciList;
	}

	public void setOnayliKullaniciList(List<Kullanici> onayliKullaniciList) {
		this.onayliKullaniciList = onayliKullaniciList;
	}

	public List<Kullanici> getOnayBekleyenKullaniciList() {
		return onayBekleyenKullaniciList;
	}

	public void setOnayBekleyenKullaniciList(List<Kullanici> onayBekleyenKullaniciList) {
		this.onayBekleyenKullaniciList = onayBekleyenKullaniciList;
	}

	public List<Referans> getHataliReferanslar() {
		return hataliReferanslar;
	}

	public void setHataliReferanslar(List<Referans> hataliReferanslar) {
		this.hataliReferanslar = hataliReferanslar;
	}

	public void kullaniciyaOnayVer(int kullaniciId){
		System.out.println("kullanıcıya onay verilecek.");
		ks.kullaniciOnayla(kullaniciController.getKullanici().getId(), kullaniciId);
		System.out.println("kullanıcı onaylanmış olmalı.");
		onayBekleyenKullaniciList = ks.listOnayBekleyenKullanicilar();

		onayliKullaniciList = ks.listOnayliKullanicilar();
		System.out.println("kullanıcı onaylanmış olmalı.");

		//kullanıcıya şifre verildikten sonra mail atma eklenmeli
		FacesContext fc = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage("Kullanıcı Onaylandı");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		fc.addMessage(null, msg);
		fc.renderResponse();
	}
	
	public void kullaniciSil(int kullaniciId){
		Kullanici silinenKullanici = ks.findKullaniciById(kullaniciId);
		ks.deleteKullanici(silinenKullanici, kullaniciController.getKullanici());
		System.out.println("kullanıcı silinmiş olmalı.");
		onayliKullaniciList = ks.listOnayliKullanicilar();

		FacesContext fc = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage("Kullanıcı Silindi");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		fc.addMessage(null, msg);
		fc.renderResponse();
//		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kullanıcı Silindi",""));
	}
	
	public void referansHataDuzelt(){
		//Referans ref = rs.findReferans(referansAd);
		//hatanın düzeltildiği varsayıldı
		referans2.setReferanshata(0);
		referans2.setRefhatamsg("");
		if (refyil != referans2.getYil() || refsayi != referans2.getSayi() || refciltno != referans2.getCiltNo()
				|| refbassayfano != referans2.getBasSayfaNo() || refsonsayfano != referans2.getSonSayfaNo() || !referansAd.equals(referans2.getReferansad()) || (yer!=null && !yer.equals(referans2.getYer())) || ay != referans2.getAy() || (konferansad!=null && !konferansad.equals(referans2.getKonferansad())) || (kurumad!=null && !kurumad.equals(referans2.getKurumad())) || (kitapevi!=null && !kitapevi.equals(referans2.getKitapevi())) || (dergiad!=null && !dergiad.equals(referans2.getDergiad()))){
			rs.duzeltReferans(kullaniciController.getKullanici(),referans2, referans2.getYil(), referans2.getSayi(),referans2.getCiltNo(), referans2.getBasSayfaNo(), referans2.getSonSayfaNo(), referans2.getAy(),referans2.getKitapevi() ,referans2.getYer(), referans2.getKurumad(), referans2.getKonferansad(), referans2.getReferansad(), referans2.getDergiad());
		}
		System.out.println("ref hata düzeltildi.");
		hataliReferanslar = rs.hataliReferanslar();
		duzeltilebilir = false;
		MakaleService ms = new MakaleService();

		List<Makale> makaleler = ms.findAllMakaleForReferans(referans2);
		for(Makale makale : makaleler ){
			Kullanici kullanici = makale.getKullanicilar();
			ks.setKullaniciMesaj(kullanici, 1, ""+referans2.getReferansad()+" hataları düzeltildi.");
		}
		FacesContext fc = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage("Referans düzeltildi.");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		fc.addMessage(null, msg);
		fc.renderResponse();
	}
	public void setReferansMsg(String refad){
		System.out.println("refhatamsg için ref:"+refad);
		Referans ref = rs.findReferans(refad);
		refHatamsg = ref.getRefhatamsg();
	}
	public void setReferansDetaylari(String refad){
		System.out.println("parametre refad:"+refad);
		referans2 = rs.findReferans(refad);
		MakaleService ms = new MakaleService();
		mkl =  ms.findMakaleByReferans(referans2);
		makaleyayintipi = mkl.getMakaleYayinTipi();
		if(referans2.getDergiad() != null){
			System.out.println("dergi ad:" + referans2.getDergiad());
			dergiad = referans2.getDergiad();
		}
		
	
		referansAd = referans2.getReferansad();
		System.out.println("referans ad:" + referansAd);
		
		if(referans2.getYer() != null){
			yer = referans2.getYer();
			System.out.println("referans yer:" + yer);
		}
		if(referans2.getAy() != 0){
			ay = referans2.getAy();
			System.out.println("referans ay:" + ay);
		}
		
		if(referans2.getKurumad() != null){
			kurumad = referans2.getKurumad();
			System.out.println("kurum ad:" + kurumad);
		}
		
		if(referans2.getKitapevi() != null){
			kitapevi = referans2.getKitapevi();
			System.out.println("kitapevi:" + kitapevi);
		}
		
		if(referans2.getKonferansad() != null){
			konferansad = referans2.getKonferansad();
			System.out.println("konferans ad:" + konferansad);
		}
		
		System.out.println("dergi say�:" + referans2.getSayi());
		refsayi = referans2.getSayi();
		System.out.println("dergi y�l:" + referans2.getYil());
		refyil = referans2.getYil();
		System.out.println("dergi cilt no:" + referans2.getCiltNo());
		refciltno = referans2.getCiltNo();
		System.out.println("ba� sayfa no:" + referans2.getBasSayfaNo());
		refbassayfano = referans2.getBasSayfaNo();
		System.out.println("son sayfa no:" + referans2.getSonSayfaNo());
		refsonsayfano = referans2.getSonSayfaNo();
	
	}

	public String getReferansAd() {
		return referansAd;
	}

	public void setReferansAd(String referansAd) {
		this.referansAd = referansAd;
	}
	public void duzeltme(){
		duzeltilebilir = true;
	}
}
