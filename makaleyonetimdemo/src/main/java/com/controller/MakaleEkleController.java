package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

import com.entity.Dergi;
import com.entity.Makale;
import com.entity.MakaleMetadata;
import com.entity.MakalePath;
import com.entity.MakaleYayinTipi;
import com.entity.Referans;
import com.entity.Yazar;
import com.service.DergiService;
import com.service.MakaleMetadataService;
import com.service.MakalePathService;
import com.service.MakaleService;
import com.service.MakaleYayinTipiService;
import com.service.ReferansService;
import com.service.YazarService;
import com.util.S3DosyaIslemleri;

@ManagedBean
@ViewScoped
public class MakaleEkleController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Makale makale = new Makale();
	private String konu, anahtarKelimeler, makalenot;
	public String getKonu() {
		return konu;
	}

	public void setKonu(String konu) {
		this.konu = konu;
	}

	public String getAnahtarKelimeler() {
		return anahtarKelimeler;
	}

	public void setAnahtarKelimeler(String anahtarKelimeler) {
		this.anahtarKelimeler = anahtarKelimeler;
	}

	public String getMakalenot() {
		return makalenot;
	}

	public void setMakalenot(String not) {
		this.makalenot = not;
	}

	public int getPuan() {
		return puan;
	}

	public void setPuan(int puan) {
		this.puan = puan;
	}

	private int puan;
	private Referans referans = new Referans();
	//private Dergi dergi = new Dergi();
	private String yazarAdlari;
	private boolean newRef, eserAdDenetlendi;
	public boolean isEserAdDenetlendi() {
		return eserAdDenetlendi;
	}

	public void setEserAdDenetlendi(boolean isEserAdDenetlendi) {
		this.eserAdDenetlendi = isEserAdDenetlendi;
	}

	UploadedFile file;
	 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
	public String getYazarAdlari() {
		return yazarAdlari;
	}

	public void setYazarAdlari(String yazarAdlari) {
		this.yazarAdlari = yazarAdlari;
	}

	MakaleYayinTipiService myts = new MakaleYayinTipiService();
	private MakaleMetadata makaleMetadata = new MakaleMetadata();
	private Yazar yazar = new Yazar();
	private String yazarList;
	public String getYazarList() {
		return yazarList;
	}

	public void setYazarList(String yazarList) {
		this.yazarList = yazarList;
	}

	private String yazarAd = "";
	public String getYazarAd() {
		return yazarAd;
	}

	public void setYazarAd(String yazarAd) {
		System.out.println("setValue: " + yazarAd);
		this.yazarAd = yazarAd;
	}

	public HashSet<Yazar> getYazarSet() {
		return yazarSet;
	}

	public void setYazarSet(HashSet<Yazar> yazarSet) {
		this.yazarSet = yazarSet;
	}

	public Yazar getYazar() {
		return yazar;
	}

	public MakaleMetadata getMakaleMetadata() {
		return makaleMetadata;
	}

	public void setMakaleMetadata(MakaleMetadata makaleMetadata) {
		this.makaleMetadata = makaleMetadata;
	}

	public boolean isReferansOlusturuldu() {
		return isReferansOlusturuldu;
	}

	public void setReferansOlusturuldu(boolean isReferansOlusturuldu) {
		this.isReferansOlusturuldu = isReferansOlusturuldu;
	}

	public boolean istipOlusturuldu() {
		return tipOlusturuldu;
	}

	public void settipOlusturuldu(boolean tipOlusturuldu) {
		this.tipOlusturuldu = tipOlusturuldu;
	}

	public boolean isS3YuklemesiYapildi() {
		return s3YuklemesiYapildi;
	}

	public void setS3YuklemesiYapildi(boolean isS3YuklemesiYapildi) {
		this.s3YuklemesiYapildi = isS3YuklemesiYapildi;
	}

	public boolean isMakaleOlusturuldu() {
		return makaleOlusturuldu;
	}

	public void setMakaleOlusturuldu(boolean isMakaleOlusturuldu) {
		this.makaleOlusturuldu = isMakaleOlusturuldu;
	}

	public boolean isMetadataOlusturuldu() {
		return isMetadataOlusturuldu;
	}

	public void setMetadataOlusturuldu(boolean isMetadataOlusturuldu) {
		this.isMetadataOlusturuldu = isMetadataOlusturuldu;
	}

	public Boolean getIsYeniReferans() {
		return isYeniReferans;
	}

	public void setIsYeniReferans(Boolean isYeniReferans) {
		this.isYeniReferans = isYeniReferans;
	}

	public void setYazar(Yazar yazar) {
		this.yazar = yazar;
	}

	private MakaleYayinTipi makaleyayintipi = new MakaleYayinTipi();
	private MakalePath makalepath;
	private String makaleTip;
	public String getMakaleTip() {
		return makaleTip;
	}

	public void setMakaleTip(String makaleTip) {
		this.makaleTip = makaleTip;
	}

	private HashSet<Yazar> yazarSet = new HashSet<>();
	private boolean isReferansOlusturuldu,  tipOlusturuldu, 
			s3YuklemesiYapildi, makaleOlusturuldu, isMetadataOlusturuldu;
	private Boolean isYeniReferans = null;
	private int referansSinamaSayisi;
	@ManagedProperty("#{kullaniciController}")
	private KullaniciController kullaniciController;

	public KullaniciController getKullaniciController() {
		return kullaniciController;
	}

	public void setKullaniciController(KullaniciController kullaniciController) {
		this.kullaniciController = kullaniciController;
	}

	public Makale getMakale() {
		return makale;
	}

	public void setMakale(Makale makale) {
		this.makale = makale;
	}

	public Referans getReferans() {
		return referans;
	}

	public void setReferans(Referans referans) {
		this.referans = referans;
		System.out.println("refsayi:" + referans.getSayi());
	}

//	public Dergi getDergi() {
//		return dergi;
//	}
//
//	public void setDergi(Dergi dergi) {
//		this.dergi = dergi;
//	}

	public MakaleYayinTipi getMakaleyayintipi() {
		return makaleyayintipi;
	}

	public void setMakaleyayintipi(MakaleYayinTipi makaleyayintipi) {
		this.makaleyayintipi = makaleyayintipi;
	}

	public MakalePath getMakalepath() {
		return makalepath;
	}

	public void setMakalepath(MakalePath makalepath) {
		this.makalepath = makalepath;
	}

	public void eserAdDenetim(){
		System.out.println("eseraddenetimi yap�l�yor.");
		eserAdDenetlendi = true;
		ReferansService rs = new ReferansService();
		Referans ref = rs.findReferans(referans.getReferansad());
		if(ref == null)
			newRef = true;
		else//burada haz�r refrans� al
		{
//			if(makaleTip.equals("Makale"))
//				dergi = ref.getDergi();
	
			referans = ref;
			yazarList = referans.getYazarlar().toString();
		}
	}
	public boolean isNewRef(){
		
			
		return newRef;
	}
	
	public void hataBildir(){
		ReferansService rs = new ReferansService();
		System.out.println("hata mesaj:"+referans.getRefhatamsg());
		rs.updateReferansHata(referans, 1, referans.getRefhatamsg());
		//referans.setReferanshata(1);//referans durumu hatal� olarak blidirildi.
	
	}
	
	public String referansiKaydet() {
		if (referansSinamaSayisi == 0) {
			ReferansService rs = new ReferansService();
//			DergiService ds = new DergiService();
//			if(makaleTip.equals("Makale")){
//				if (ds.findExistDergi(dergi) == null) {
//					ds.createDergi(dergi);
//					isDergiOlusturuldu = true;
//					System.out.println("vt de bu dergi yokmu�");
//				} else
//					System.out.println("vt de bu dergi varm��");
//
//				dergi = ds.findExistDergi(dergi);
//				referans.setDergi(dergi);
//			}
			if(referans!=null)
				System.out.println("konferansad�:"+referans.getKonferansad());
			else
				System.out.println("ref nuull");
			//System.out.println("say�:" + referans.getSayi());
			System.out.println("kullan�c� id:" + kullaniciController.getKullanici().getId());
			if (rs.kullaniciSahipOlduguReferanslar(kullaniciController.getKullanici().getId()) != null) {
				if (!rs.kullaniciSahipOlduguReferanslar(kullaniciController.getKullanici().getId())
						.contains(referans)) {
					if (rs.findReferans(referans.getReferansad()) == null) {
						isYeniReferans = true;
						rs.createReferans(referans);
						isReferansOlusturuldu = true;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("KAYIT BA�ARILI",
								"Girdi�iniz dergi-referans bilgileri sisteme kaydedildi, bir sonraki adım için next'e basın."));
						System.out.println("referans yeni");
						// return "makalebilgi";
					} else {
						isYeniReferans = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("KAYIT BAŞARILI",
								"Girdiğiniz referans bilgileri sistemde kayıtlı, bir sonraki adim için next'e basın."));
						System.out.println("böyle bir referans var"); 
						// return "dogrulamayukleme";
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Sahip olduğun bir referansı tekrar yükleyemezsin.", ""));
				}
			}
			YazarService ys = new YazarService();
			System.out.println("referans i�in yazarlar olu�turuluyor...");
			ys.createYazarForReferans(referans, yazarSet);
			referansSinamaSayisi++;
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Birden fazla s�nama yapamazs�n.", ""));
			System.out.println("birden fazla s�nama yapamazs�n");
		}
		return "";

	}

	public String verileriKaydet() {
		MakaleService ms = new MakaleService();
		MakaleMetadataService mmds = new MakaleMetadataService();
		Makale mkl = new Makale();
		if(anahtarKelimeler != null)
			mkl.setAnahtarKelimeler(anahtarKelimeler);
		mkl.setMakaleAdi(referans.getReferansad());
		//mkl.setReferans(referans);
		//mkl.setMakaleYayinTipi(makaleyayintipi);
		MakaleMetadata mmd = new MakaleMetadata();
		mmd.setMakaleKonu(konu);
		if(makalenot != null)
			mmd.setMakaleNot(makalenot);
		mmd.setMakalePuan(puan);
		mmd.setMakale(mkl);
		if(s3YuklemesiYapildi){
			ms.createMakaleForKullanici(mkl, kullaniciController.getKullanici().getId(), makalepath, makaleyayintipi, referans);
			makaleOlusturuldu = true;
			System.out.println("yüklenen makale id:" + mkl.getId());
			mmds.createMakaleMetadata(mmd, mkl.getId());
			isMetadataOlusturuldu = true;
		}
		else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Önce dosyayı yüklemelisiniz.", ""));
		}
		return "/pages/protected/kullanici/kullaniciAnaSayfasi.xhtml?faces-redirect=true";
	}

	public String silme() {
		// if(isMetadataOlusturuldu){
		// MakaleMetadataService mmds = new MakaleMetadataService();
		// mmds.deleteMakaleMetadata(makaleMetadata);
		// }
		// if(isMakaleOlusturuldu){
		// MakaleService ms = new MakaleService();
		// ms.deleteMakale(makale);
		// }
		// else{
		if (tipOlusturuldu) {
			myts.deleteMakaleYayinTipi(makaleyayintipi);
			System.out.println("yayın tipi silindi.");
//			YazarService ys = new YazarService();
//			ReferansService rs = new ReferansService();

//			HashSet<Yazar> yazarList1 = rs.removeMakaleReferansFromYazarlar(referans);
//			//List<Yazar> newYazarList = 
//			//System.out.println("yazarlist size:"+ yazarList.size());
//			for(Yazar yazar : yazarList1){
//				if(yazar.getReferansler().size() == 0){
//					System.out.println("referans� kalmayan yazar ad�:" + yazar.getYazarAdi());
//					ys.deleteYazar(yazar);
//				}
//			}
		}
		if (s3YuklemesiYapildi) {
			MakalePathService mps = new MakalePathService();
			mps.deleteMakalePath(makalepath);
			System.out.println("path silindi.");
			// deleteFromS3() metodu makalepath service taraf�ndan halledilir.
		}
		if (isReferansOlusturuldu) {
			ReferansService rs = new ReferansService();
			rs.deleteMakaleReferans(referans);
			System.out.println("referans silindi.");
		}
		if(makaleOlusturuldu){
			MakaleService ms = new MakaleService();
			ms.deleteMakale(makale);
			System.out.println("referans silindi.");

		}
		// }
		KullaniciController.urlHandler();
		System.out.println("handler");
		return "/pages/protected/kullanici/kullaniciAnaSayfasi.xhtml?faces-redirect=true";

	}

	public String anaSayfaDon() {
		KullaniciController.urlHandler();
		System.out.println("handler");
		return "/pages/protected/kullanici/kullaniciAnaSayfasi.xhtml?faces-redirect=true";
	}
	
	//bu metod referans�Denetle metodundan �nce �a��r�l�r.
	public void validateSayfaNo(ComponentSystemEvent event) {

		FacesContext fc = FacesContext.getCurrentInstance();

		UIComponent components = event.getComponent();

		UIInput uiBasSayfa = (UIInput) components.findComponent("basSayfaNo");
		int basSayfaNo = Integer.parseInt(uiBasSayfa.getLocalValue().toString());
		System.out.println("bassayfa:" + basSayfaNo);
		String passwordId = uiBasSayfa.getClientId();

		UIInput uiSonSayfa = (UIInput) components.findComponent("sonSayfaNo");
		int sonSayfaNo = Integer.parseInt(uiSonSayfa.getLocalValue().toString());
		System.out.println("sonsayfa:" + sonSayfaNo);

		UIInput uiDergiAd = (UIInput) components.findComponent("dergiAd");
		String dergiAd = uiDergiAd.getLocalValue().toString();
		System.out.println("dergiAd:" + dergiAd);

		UIInput uiDergiYil = (UIInput) components.findComponent("dergiYil");
		long dergiYil = Integer.parseInt(uiDergiYil.getLocalValue().toString());
		System.out.println("dergiYil:" + dergiYil);

		UIInput uiDergiCilt = (UIInput) components.findComponent("dergiCiltNo");
		int dergiCilt = Integer.parseInt(uiDergiCilt.getLocalValue().toString());
		System.out.println("dergiCilt:" + dergiCilt);

		UIInput uiDergiSayi = (UIInput) components.findComponent("dergiSayi");
		int dergiSayi = Integer.parseInt(uiDergiSayi.getLocalValue().toString());
		System.out.println("dergiSayi:" + dergiSayi);

		DergiService ds1 = new DergiService();
		Dergi dergi1 = ds1.findDergiByAd(dergiAd);
		ReferansService rs1 = new ReferansService();
		List<Referans> referans = null;
		if (dergi1 != null) {
			referans = rs1.findReferans(dergiYil, dergiSayi, dergiCilt, dergi1);
		}

		if (basSayfaNo > sonSayfaNo) {

			FacesMessage msg = new FacesMessage("Baş sayfa, son sayfadan büyük olamaz");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(passwordId, msg);
			fc.renderResponse();

		} else {
			if (referans != null) {
				for (Referans ref : referans) {
					if (ref.getBasSayfaNo() == basSayfaNo) {
						if (ref.getSonSayfaNo() != sonSayfaNo) {
							FacesMessage msg = new FacesMessage("Geçerli sayfa numaraları giriniz");
							msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							fc.addMessage(passwordId, msg);
							fc.renderResponse();
							break;
						}

					} else {
						if (ref.getSonSayfaNo() == sonSayfaNo) {
							FacesMessage msg = new FacesMessage("Geçerli sayfa numaraları giriniz");
							msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							fc.addMessage(passwordId, msg);
							fc.renderResponse();
							break;
						}

					}
					if ((ref.getBasSayfaNo() > basSayfaNo && ref.getSonSayfaNo() < sonSayfaNo)
							|| (ref.getBasSayfaNo() < basSayfaNo && ref.getSonSayfaNo() > sonSayfaNo)) {
						FacesMessage msg = new FacesMessage("Geçerli sayfa numaraları giriniz");
						msg.setSeverity(FacesMessage.SEVERITY_ERROR);
						fc.addMessage(passwordId, msg);
						fc.renderResponse();
						break;
					}
				}
			}
		}

	}

	
	
	

	public String onFlowProcess(FlowEvent event) {
//		if (isYeniReferans != null) {
//			if (isYeniReferans)
//				return event.getNewStep();
//			else
//				return "dogrulamayukleme";
//		} else {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
//					"UYARI: Dergi-Referans bilgilerini kaydetmeden bir sonraki ad�ma ge�ilemez.", ""));
//			// System.out.println("�nce referans bilgilerini s�namal�s�n");
//			return "dergireferans";
//		}
		if(tipOlusturuldu){
			return event.getNewStep();
		}
		else{
			return "tipsecim";
		}
		
	}
	
	//1.ad�mda kaydet butonu ile buras� �al��t�r�l�r.
	public void tipOlustur(){
		boolean isMakale = false;
		boolean isBildiri = false;
		boolean isKitap = false;
		boolean isTez = false;
		
		if (makaleTip.equals("Tez")){
			isTez = true;
			makaleyayintipi.setTez(isTez);

		}
		if (makaleTip.equals("Bildiri")){
			isBildiri = true;
			makaleyayintipi.setBildiri(isBildiri);

		}
		if (makaleTip.equals("Kitap")){
			isKitap = true;
			makaleyayintipi.setKitap(isKitap);

		}
		if (makaleTip.equals("Makale")){
			isMakale = true;
			makaleyayintipi.setMakale(isMakale);

		}
		System.out.println("makale yayin tipi olu�turuluyor..."+makaleTip);
		myts.createMakaleYayinTipi(makaleyayintipi);
		
		tipOlusturuldu = true;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				""+makaleTip+" tipi seçildi", ""));
		
		
	}
	//2. ad�mda yazar ekle butonuna her bas�ld���nda bu metod �a��r�l�r.
	public void yazarEkle(){
		YazarService ys = new YazarService();
		yazarAd = yazarAd.toLowerCase();
		System.out.println("yazar adı:" + yazarAd);
		Yazar yzr = ys.findYazarByAd(yazarAd);
		if(yzr == null){ 
			yzr = new Yazar(yazarAd);
			//ys.create(yzr);
			//yzr = ys.findYazarByAd(yazarAd);
			System.out.println("eklenen yazarnew:" + yzr.getYazarAdi());
		}
		else
			System.out.println("eklenen yazarold:" + yzr.getYazarAdi());
		yazarSet.add(yzr);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Yazar kaydedildi", ""));
		setYazarAdlari(yazarSet.toString());
		System.out.println(getYazarAdlari());
		yazarAd = "";
	}
	//yazar autocomplete
	public List<Yazar> completeYazar(String yazarAd) {
		YazarService ys2 = new YazarService();
		List<Yazar> yazarlar = new ArrayList<>();
		for (Yazar yzr : ys2.tumYazarlar()) {
			if (yzr.getYazarAdi().toLowerCase().contains(yazarAd))
				yazarlar.add(yzr);
		}
		return yazarlar;
	}
	//eserad autocomplete
	public List<String> completeEserAd(String referansad) {
		ReferansService rs = new ReferansService();
		List<String> referanslar = new ArrayList<>();
		Makale mkl = null;
		MakaleService ms = new MakaleService();
		for (Referans ref : rs.allRefs()) {
			mkl = ms.findMakaleByReferans(ref);
			if(mkl != null){
				System.out.println("makaletip:"+ mkl.getMakaleYayinTipi());
				System.out.println("makaleTip:"+makaleTip);
				System.out.println("refad19:"+ref.getReferansad());

			}
			else
				System.out.println("mkl null");
			if(mkl != null && mkl.getMakaleYayinTipi().toString().equals(makaleTip)){
				System.out.println("refad20:"+ref.getReferansad());
				if (ref.getReferansad().toLowerCase().contains(referansad)){
					System.out.println("refad:"+referansad);
					referanslar.add(ref.getReferansad());
				}
			}

			
				
		}
		return referanslar;
	}
	//kitapevi autocomplete
	public List<String> completeKitapevi(String kitapevi) {
		ReferansService rs = new ReferansService();
		List<String> referanslar = new ArrayList<>();
		for (Referans ref : rs.allRefs()) {
			if (ref.getKitapevi() != null && ref.getKitapevi().toLowerCase().contains(kitapevi))
				referanslar.add(ref.getKitapevi());
		}
		return referanslar;
	}
	//kurumad autocomplete
	public List<String> completeKurumAd(String kurumad) {
		ReferansService rs = new ReferansService();
		List<String> referanslar = new ArrayList<>();
		for (Referans ref : rs.allRefs()) {
			if (ref.getKurumad() !=null && ref.getKurumad().toLowerCase().contains(kurumad))
				referanslar.add(ref.getKurumad());
		}
		return referanslar;
	}
	//konferansad autocomplete
	public List<String> completeKonferansAd(String konferansad) {
		ReferansService rs = new ReferansService();
		List<String> referanslar = new ArrayList<>();
		for (Referans ref : rs.allRefs()) {
			if (ref.getKonferansad() !=null && ref.getKonferansad().toLowerCase().contains(konferansad))
				referanslar.add(ref.getKonferansad());
		}
		return referanslar;
	}
	public List<String> completeDergi(String dergiad) {
		ReferansService rs = new ReferansService();
		List<String> referanslar = new ArrayList<>();
		for (Referans ref : rs.allRefs()) {
			if (ref.getDergiad() != null && ref.getDergiad().toLowerCase().contains(dergiad))
				referanslar.add(ref.getDergiad());
		}
		return referanslar;
	}
	public List<String> completeYer(String yerad) {
		ReferansService rs = new ReferansService();
		List<String> referanslar = new ArrayList<>();
		for (Referans ref : rs.allRefs()) {
			System.out.println("refid:"+ref.getId());
			if (ref.getYer() != null && ref.getYer() != null && ref.getYer().contains(yerad)){
				referanslar.add(ref.getYer());
				System.out.println("yer:"+ ref.getYer());
			}
		}
		return referanslar;
	}
	
	public List<String> completeEserKonu(String eserKonu) {
		MakaleService ms = new MakaleService();
		List<String> konular = new ArrayList<>();
		for (Makale makale : ms.tumMakaleler()) {
			if (makale.getMakaleMetadata().getMakaleKonu().contains(eserKonu))
				konular.add(makale.getMakaleMetadata().getMakaleKonu());
		}
		return konular;
	}
	public void resetTipYazar(){
		makaleTip = "";
		yazarSet.clear();
	}
	public void fileUploadListener(FileUploadEvent e){
        // Get uploaded file from the FileUploadEvent
        this.file = e.getFile();
        // Print out the information of the file
        System.out.println("Uploaded File Name Is :: "+file.getFileName()+" :: Uploaded File Size :: "+file.getSize());
        makalepath = new MakalePath(referans.getId());
        System.out.println("fileupload için refId:"+referans.getId());
        MakalePathService mps = new MakalePathService();
        mps.createMakalePath(makalepath);
        System.out.println("makalepath oluşturuldu:" + makalepath.getMakalePath());
        InputStream in = null;
		try {
			in = file.getInputstream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        S3DosyaIslemleri.s3DosyaYukleme(makalepath.getMakalePath(), in);
        s3YuklemesiYapildi = true;
	}
	public void fileUpload(){
        // Get uploaded file from the FileUploadEvent
       // this.file = e.getFile();
        // Print out the information of the file
		System.out.println("dfhdh");
        System.out.println("Uploaded File Name Is :: "+file.getFileName()+" :: Uploaded File Size :: "+file.getSize());
        makalepath = new MakalePath(referans.getId());
        MakalePathService mps = new MakalePathService();
        mps.createMakalePath(makalepath);
        System.out.println("makalepath olu�turuldu:" + makalepath.getMakalePath());
        InputStream in = null;
		try {
			in = file.getInputstream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        S3DosyaIslemleri.s3DosyaYukleme(makalepath.getMakalePath(), in);
	}
}
