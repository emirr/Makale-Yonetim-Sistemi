package com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.entity.Dergi;
import com.entity.Kullanici;
import com.entity.Makale;
import com.entity.MakaleMetadata;
import com.entity.Referans;
import com.entity.Yazar;
import com.service.DergiService;
import com.service.KullaniciService;
import com.service.MakaleMetadataService;
import com.service.MakaleService;
import com.service.MakaleYayinTipiService;
import com.service.ReferansService;
import com.service.YazarService;

@ManagedBean
@ViewScoped
public class KullaniciAnaSayfaController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MakaleService ms = new MakaleService();
	private KullaniciService ks = new KullaniciService();
	private List<Makale> makaleler;
	private List<MakaleMetadata> metadatalar = new ArrayList<>();
	private MakaleMetadataService mds = new MakaleMetadataService();
	// private HashMap<Makale,MakaleMetadata> metadatalar = new HashMap<>();
	// private List<MakaleMetadata> metadata = new ArrayList();
	private MakaleMetadata metadata;
	private Dergi dergi;
	private Referans referans;
	private String makaletipi;
	private Makale makale;
	private Makale selectedMakale;
	private List<Yazar> yazarlar;
	private int makaleId;
	private boolean editStat;
	private boolean referansGuncellenebilir, yedekRef, yedekDergi;
	private boolean dergiGuncellenebilir;
	private String yazarList = new String();
	private int clickCount;
	private List<Kullanici> hazirList ;
	public List<Kullanici> getHazirList() {
		return hazirList;
	}

	public void setHazirList(List<Kullanici> hazirList) {
		this.hazirList = hazirList;
	}

	private List<Kullanici> kullaniciList, selectedKullaniciList, filteredKullanicilar;
	public List<Kullanici> getFilteredKullanicilar() {
		return filteredKullanicilar;
	}

	public void setFilteredKullanicilar(List<Kullanici> filteredKullanicilar) {
		this.filteredKullanicilar = filteredKullanicilar;
	}

	public List<Kullanici> getSelectedKullaniciList() {
		return selectedKullaniciList;
	}

	public void setSelectedKullaniciList(List<Kullanici> selectedKullaniciList) {
		this.selectedKullaniciList = selectedKullaniciList;
	}

	public List<Kullanici> getKullaniciList() {
		return kullaniciList;
	}

	public void setKullaniciList(List<Kullanici> kullaniciList) {
		this.kullaniciList = kullaniciList;
	}

	public String getYazarList() {
		return yazarList;
	}

	public void setYazarList(String yazarList) {
		this.yazarList = yazarList;
	}

	private boolean isBildiri, isKonferans, isKitap, isTez;

	public boolean isBildiri() {
		return isBildiri;
	}

	public void setBildiri(boolean isBildiri) {
		this.isBildiri = isBildiri;
	}

	public boolean isKonferans() {
		return isKonferans;
	}

	public void setKonferans(boolean isKonferans) {
		this.isKonferans = isKonferans;
	}

	public boolean isKitap() {
		return isKitap;
	}

	public void setKitap(boolean isKitap) {
		this.isKitap = isKitap;
	}

	public boolean isTez() {
		return isTez;
	}

	public void setTez(boolean isTez) {
		this.isTez = isTez;
	}

	public String getDergiAd() {
		return dergiAd;
	}

	public void setDergiAd(String dergiAd) {
		this.dergiAd = dergiAd;
	}

	public String getMklad() {
		return mklad;
	}

	public void setMklad(String mklad) {
		this.mklad = mklad;
	}

	public String getMklkywrd() {
		return mklkywrd;
	}

	public void setMklkywrd(String mklkywrd) {
		this.mklkywrd = mklkywrd;
	}

	public String getMklkonu() {
		return mklkonu;
	}

	public void setMklkonu(String mklkonu) {
		this.mklkonu = mklkonu;
	}

	public int getMklpuan() {
		return mklpuan;
	}

	public void setMklpuan(int mklpuan) {
		this.mklpuan = mklpuan;
	}

	public String getMklnot() {
		return mklnot;
	}

	public void setMklnot(String mklnot) {
		this.mklnot = mklnot;
	}

	public long getRefyil() {
		return refyil;
	}

	public void setRefyil(long refyil) {
		this.refyil = refyil;
	}

	public int getRefsayi() {
		return refsayi;
	}

	public void setRefsayi(int refsayi) {
		this.refsayi = refsayi;
	}

	public int getRefciltno() {
		return refciltno;
	}

	public void setRefciltno(int refciltno) {
		this.refciltno = refciltno;
	}

	public int getRefbassayfano() {
		return refbassayfano;
	}

	public void setRefbassayfano(int refbassayfano) {
		this.refbassayfano = refbassayfano;
	}

	public int getRefsonsayfano() {
		return refsonsayfano;
	}

	public void setRefsonsayfano(int refsonsayfano) {
		this.refsonsayfano = refsonsayfano;
	}

	private String dergiAd, mklad, mklkywrd, mklkonu, mklnot;
	private int mklpuan;
	private long refyil;
	private int refsayi, refciltno, refbassayfano, refsonsayfano;
	private String tip;

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public boolean isDergiGuncellenebilir() {
		return dergiGuncellenebilir;
	}

	public void setDergiGuncellenebilir(boolean dergiGuncellenebilir) {
		this.dergiGuncellenebilir = dergiGuncellenebilir;
	}

	public boolean isReferansGuncellenebilir() {
		return referansGuncellenebilir;
	}

	public void setReferansGuncellenebilir(boolean referansGuncellenebilir) {
		this.referansGuncellenebilir = referansGuncellenebilir;
	}

	public boolean isEditStat() {
		return editStat;
	}

	public void setEditStat(boolean editStat) {
		this.editStat = editStat;
	}

	// private List<Makale> selectedMakale;
	// public List<Makale> getSelectedMakale() {
	// return selectedMakale;
	// }
	// public void setSelectedMakale(List<Makale> selectedMakale) {
	// this.selectedMakale = selectedMakale;
	// }
	private HashMap<Makale, MakaleMetadata> makaleAndMetaSet = new HashMap<>();
	private List<Makale> filteredMakaleler;

	public Makale getSelectedMakale() {
		return selectedMakale;
	}

	public void setSelectedMakale(Makale selectedMakale) {
		this.selectedMakale = selectedMakale;
	}

	public MakaleMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(MakaleMetadata metadata) {
		this.metadata = metadata;
	}

	public Dergi getDergi() {
		return dergi;
	}

	public void setDergi(Dergi dergi) {
		this.dergi = dergi;
	}

	public Referans getReferans() {
		return referans;
	}

	public void setReferans(Referans referans) {
		this.referans = referans;
	}

	public String getMakaletipi() {
		return makaletipi;
	}

	public void setMakaletipi(String makaletipi) {
		this.makaletipi = makaletipi;
	}

	public Makale getMakale() {
		return makale;
	}

	public void setMakale(Makale makale) {
		this.makale = makale;

	}

	public List<Yazar> getYazarlar() {
		return yazarlar;
	}

	public void setYazarlar(List<Yazar> yazarlar) {
		this.yazarlar = yazarlar;
	}

	public int getMakaleId() {
		return makaleId;
	}

	public void setMakaleId(int makaleId) {
		this.makaleId = makaleId;

	}

	public List<Makale> getFilteredMakaleler() {
		return filteredMakaleler;
	}

	public void setFilteredMakaleler(List<Makale> filteredMakaleler) {
		this.filteredMakaleler = filteredMakaleler;
	}

	// private String id =
	// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
	// .get("kullaniciId");
	@ManagedProperty("#{kullaniciController}")
	private KullaniciController kullaniciController;

	public HashMap<Makale, MakaleMetadata> getMakaleAndMetaSet() {
		return makaleAndMetaSet;
	}

	public KullaniciController getKullaniciController() {
		return kullaniciController;
	}

	public KullaniciAnaSayfaController() {
		// TODO Auto-generated constructor stub
	}

	//
	public void setKullaniciController(KullaniciController kullaniciController) {
		this.kullaniciController = kullaniciController;
	}
	// public KullaniciAnaSayfaController() {
	// // TODO Auto-generated constructor stub
	// System.out.println("idc"+kullaniciController.getKullaniciAd());
	// }

	@PostConstruct
	public void init() {
		makaleler = ms.makaleByKullaniciId(kullaniciController.getKullanici().getId());
		kullaniciList = ks.listDigerKullanicilar(kullaniciController.getKullanici().getId());
		
		System.out.println("kullaniciid:" + kullaniciController.getKullanici().getId());
		System.out.println("makaleler size:" + makaleler.size());
		// System.out.println("kullanici adi nedir:" +
		// kullaniciController.getKullanici().getKullaniciAd());
		for (Makale makale : makaleler) {
			System.out.println("idinit:" + makale.getId());
			// metadata.add(mds.findSpecMakaleMetadata(makale.getId()));
			metadata = mds.findSpecMakaleMetadata(makale.getId());
			makaleAndMetaSet.put(makale, metadata);
			metadatalar.add(metadata);

			// makaleAndMetaSet.entrySet().toArray();
		}
	}

	public List<Makale> getMakaleler() {
		return makaleler;
	}

	public List<MakaleMetadata> getMetadatalar() {
		return metadatalar;
	}

	public String guncelle() {
		System.out.println("güncellemeler yapýlýyor.");
		ReferansService rs = new ReferansService();
		DergiService ds = new DergiService();
		MakaleYayinTipiService myts = new MakaleYayinTipiService();
		YazarService ys = new YazarService();
//		ReferansGuncellenebilirlik(referans.getId());
//		DergiGuncellenebilirlik(dergi.getId());
		if (yedekDergi) {
			if (!dergiAd.equals(dergi.getDergiAdi())){
				System.out.println("dergi güncelleniyor.");
				System.out.println("yeni dergi adý" + dergi.getDergiAdi());

				ds.updateDergi(dergi.getId(),dergi.getDergiAdi(), makaleId);
			}
		}
		if (yedekRef) {
			if (refyil != referans.getYil() || refsayi != referans.getSayi() || refciltno != referans.getCiltNo()
					|| refbassayfano != referans.getBasSayfaNo() || refsonsayfano != referans.getSonSayfaNo()){
				System.out.println("makale referans güncelleniyor.");
				System.out.println("yeni referans yýl:" + referans.getYil());
				System.out.println("yeni referans sayý:" + referans.getSayi());
				
				System.out.println("yeni referans ciltno:" + referans.getCiltNo());
				System.out.println("yeni referans bassayfano:" + referans.getBasSayfaNo());
				System.out.println("yeni referans sonsayfano:" + referans.getSonSayfaNo());



				rs.updateReferans(makaleId, referans.getYil(), referans.getSayi(),referans.getCiltNo(), referans.getBasSayfaNo(), referans.getSonSayfaNo());
			}
		}
		if (!mklad.equals(makale.getMakaleAdi()) || !mklkywrd.equals(makale.getAnahtarKelimeler())){
			System.out.println("makale ad-keyword güncelleniyor.");
			System.out.println("yeni makale ad:" + makale.getMakaleAdi());
			System.out.println("yeni makale keyword:" + makale.getAnahtarKelimeler());

			ms.updateMakale(makaleId,makale.getMakaleAdi(), makale.getAnahtarKelimeler());
		}
		if (!tip.equals(makale.getMakaleYayinTipi().toString())) {
			System.out.println("makale tip güncelleniyor.");
			if (tip.equals("Tez"))
				isTez = true;
			if (tip.equals("Bildiri"))
				isBildiri = true;
			if (tip.equals("Kitap"))
				isKitap = true;
			if (tip.equals("Konferans"))
				isKonferans = true;
			System.out.println("yeni tip:" + tip);
			myts.updateMakaleYayinTipi(makaleId, isBildiri, isKonferans, isTez, isKitap);
		}

		if (!mklnot.equals(metadata.getMakaleNot()) || mklpuan != metadata.getMakalePuan()
				|| !mklkonu.equals(metadata.getMakaleKonu())) {
			System.out.println("metadata güncelleniyor.");
			System.out.println("yeni not:" + metadata.getMakaleNot());
			System.out.println("yeni puan:" + metadata.getMakalePuan());
			System.out.println("yeni konu:" + metadata.getMakaleKonu());
			mds.updateMakaleMetadata(makaleId, makale.getKullanicilar().getId(), metadata.getMakaleNot(), metadata.getMakalePuan(), metadata.getMakaleKonu());
		}
		makaleler = ms.makaleByKullaniciId(kullaniciController.getKullanici().getId());
		System.out.print("güncel makaleler:");
		makaleAndMetaSet.clear();
		for(Makale mkl : makaleler){
			System.out.print(mkl.getMakaleAdi() + " ");
			metadata = mds.findSpecMakaleMetadata(mkl.getId());
			makaleAndMetaSet.put(mkl, metadata);
			metadatalar.add(metadata);
		}
		
		return " ";
	}

	//}
	public void paylasilabirKisiler(int mId){
		makaleId = mId;
		ReferansService rs = new ReferansService();
		Referans ref1 = rs.referansForMakale(makaleId);
		hazirList = new ArrayList<>();
		for(Kullanici kisi : kullaniciList){
			if(!rs.kullaniciSahipOlduguReferanslar(kisi.getId()).contains(ref1))
				hazirList.add(kisi);
		}
		
		
	}
	public void setDetaylar(int secilenId) {
		System.out.println("detaylar set ediliyor.");
		yazarList = new String();
		makaleId = secilenId;
		makale = ms.findMakaleById(makaleId);
		System.out.println("makaleýd:" + makaleId);
		System.out.println("makale ad:" + makale.getMakaleAdi());
		mklad = makale.getMakaleAdi();
		System.out.println("makale keywords:" + makale.getAnahtarKelimeler());
		mklkywrd = makale.getAnahtarKelimeler();
		System.out.println("yayýn tipi:" + makale.getMakaleYayinTipi());
		tip = makale.getMakaleYayinTipi().toString();
		metadata = mds.findSpecMakaleMetadata(secilenId);
		System.out.println("makale konu:" + metadata.getMakaleKonu());
		mklkonu = metadata.getMakaleKonu();
		System.out.println("makale puan:" + metadata.getMakalePuan());
		mklpuan = metadata.getMakalePuan();
		System.out.println("makale notlar:" + metadata.getMakaleNot());
		mklnot = metadata.getMakaleNot();
		dergi = makale.getReferans().getDergi();
		System.out.println("dergi ad:" + dergi.getDergiAdi());
		dergiAd = dergi.getDergiAdi();
		referans = makale.getReferans();
		System.out.println("dergi sayý:" + referans.getSayi());
		refsayi = referans.getSayi();
		System.out.println("dergi yýl:" + referans.getYil());
		refyil = referans.getYil();
		System.out.println("dergi cilt no:" + referans.getCiltNo());
		refciltno = referans.getCiltNo();
		System.out.println("baþ sayfa no:" + referans.getBasSayfaNo());
		refbassayfano = referans.getBasSayfaNo();
		System.out.println("son sayfa no:" + referans.getSonSayfaNo());
		refsonsayfano = referans.getSonSayfaNo();
		YazarService ys = new YazarService();
		yazarlar = ys.findYazarByMakaleReferans(referans);
		System.out.print("makale yazarlarý: ");
		for (Yazar yzr : yazarlar) {
			System.out.print(yzr.getYazarAdi() + " ");
			yazarList += yzr.getYazarAdi() + ", ";
		}
		
		System.out.println(" ");
		// return makale;
		// Makale makale = (Makale) makaleler.getRowData();

	}

	public void changeEditable() {
		clickCount++;
		editStat = !editStat;
		System.out.println("editstat:" + editStat);
		if(clickCount % 2 != 0){
			referansGuncellenebilirlik(referans.getId());
			dergiGuncellenebilirlik(dergi.getId());
		}
		else{
			yedekRef = referansGuncellenebilir;
			yedekDergi = dergiGuncellenebilir;
			referansGuncellenebilir = false;
			dergiGuncellenebilir = false;
		}
		
	}

	public void referansGuncellenebilirlik(int refId) {
		ReferansService rs = new ReferansService();
		if (rs.makaleAdetForRef(refId) == 1) {
			referansGuncellenebilir = true;
		} else {
			referansGuncellenebilir = false;
		}
	}

	public void dergiGuncellenebilirlik(int dergiId) {
		DergiService ds = new DergiService();
		if (ds.referansAdetForDergi(dergiId) == 1) {
			dergiGuncellenebilir = true;
		} else {
			dergiGuncellenebilir = false;
		}
	}
	//
	public void kullaniciSecim(SelectEvent event){
		System.out.println("check yapýlýyor.");
		
	}
	public void makalePaylas(){
		int sahipKullaniciId = kullaniciController.getKullanici().getId();
		for(Kullanici digerKullanici : selectedKullaniciList){
			//diðerkullanýcýnýn idsi elde edilemiyor.
			System.out.println("diðerkullanýcýid:"+digerKullanici.getId() + "adý:" + digerKullanici.getKullaniciAd());
			System.out.println("makaleid"+ makaleId);
			ms.makalePaylas(sahipKullaniciId, makaleId, digerKullanici.getId());
		}
		
		resetSeciliKisiler();
		hazirList.clear();
	}
	public void resetSeciliKisiler(){
		selectedKullaniciList.clear();
	}
	public void makaleSilme(){
		Makale makale = ms.findMakaleById(makaleId);
		ms.deleteMakale(makale);
		
		makaleler = ms.makaleByKullaniciId(kullaniciController.getKullanici().getId());
		System.out.print("güncel makaleler:");
		makaleAndMetaSet.clear();
		for(Makale mkl : makaleler){
			System.out.print(mkl.getMakaleAdi() + " ");
			metadata = mds.findSpecMakaleMetadata(mkl.getId());
			makaleAndMetaSet.put(mkl, metadata);
			metadatalar.add(metadata);
		}
		
		FacesContext fc = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage("Makale Silindi");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		fc.addMessage(null, msg);
		fc.renderResponse();
		
	}
}
