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
import com.service.MakalePathService;
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
	private Referans referans = new Referans();
	private Dergi dergi = new Dergi();
	private String yazarAdlari;
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

	public boolean isTipYazarOlusturuldu() {
		return tipYazarOlusturuldu;
	}

	public void setTipYazarOlusturuldu(boolean tipYazarOlusturuldu) {
		this.tipYazarOlusturuldu = tipYazarOlusturuldu;
	}

	public boolean isS3YuklemesiYapýldý() {
		return isS3YuklemesiYapýldý;
	}

	public void setS3YuklemesiYapýldý(boolean isS3YuklemesiYapýldý) {
		this.isS3YuklemesiYapýldý = isS3YuklemesiYapýldý;
	}

	public boolean isMakaleOlusturuldu() {
		return isMakaleOlusturuldu;
	}

	public void setMakaleOlusturuldu(boolean isMakaleOlusturuldu) {
		this.isMakaleOlusturuldu = isMakaleOlusturuldu;
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
	private boolean isReferansOlusturuldu, isDergiOlusturuldu, tipYazarOlusturuldu, 
			isS3YuklemesiYapýldý, isMakaleOlusturuldu, isMetadataOlusturuldu;
	private Boolean isYeniReferans = null;
	private int referansSýnamaSayýsý;
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

	public Dergi getDergi() {
		return dergi;
	}

	public void setDergi(Dergi dergi) {
		this.dergi = dergi;
	}

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

	

	public String referansiDenetle() {
		if (referansSýnamaSayýsý == 0) {
			ReferansService rs = new ReferansService();
			DergiService ds = new DergiService();

			if (ds.findExistDergi(dergi) == null) {
				ds.createDergi(dergi);
				isDergiOlusturuldu = true;
				System.out.println("vt de bu dergi yokmuþ");
			} else
				System.out.println("vt de bu dergi varmýþ");

			dergi = ds.findExistDergi(dergi);

			referans.setDergi(dergi);
			System.out.println("dergi sayý:" + referans.getSayi());
			System.out.println("kullanýcý id:" + kullaniciController.getKullanici().getId());
			if (rs.kullaniciSahipOlduguReferanslar(kullaniciController.getKullanici().getId()) != null) {
				if (!rs.kullaniciSahipOlduguReferanslar(kullaniciController.getKullanici().getId())
						.contains(referans)) {
					if (rs.findReferans(referans.getYil(), referans.getSayi(), referans.getCiltNo(),
							referans.getBasSayfaNo(), referans.getSonSayfaNo(), dergi) == null) {
						isYeniReferans = true;
						rs.createReferans(referans, dergi);
						isReferansOlusturuldu = true;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("KAYIT BAÞARILI",
								"Girdiðiniz dergi-referans bilgileri sisteme kaydedildi, bir sonraki adým için next'e basýn."));
						System.out.println("referans yeni");
						// return "makalebilgi";
					} else {
						isYeniReferans = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("KAYIT BAÞARILI",
								"Girdiðiniz dergi-referans bilgileri sistemde kayýtlý, bir sonraki adým için next'e basýn."));
						System.out.println("böyle bir referans var");
						// return "dogrulamayukleme";
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Sahip olduðun bir referansý tekrar yükleyemezsin.", ""));
				}
			}

			referansSýnamaSayýsý++;
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Birden fazla sýnama yapamazsýn.", ""));
			System.out.println("birden fazla sýnama yapamazsýn");
		}
		return "";

	}

	public void makaleTipYazarVeriYukleme() {

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
		if (tipYazarOlusturuldu) {
			myts.deleteMakaleYayinTipi(makaleyayintipi);
			System.out.println("yayýn tipi silindi.");
//			YazarService ys = new YazarService();
//			ReferansService rs = new ReferansService();

//			HashSet<Yazar> yazarList1 = rs.removeMakaleReferansFromYazarlar(referans);
//			//List<Yazar> newYazarList = 
//			//System.out.println("yazarlist size:"+ yazarList.size());
//			for(Yazar yazar : yazarList1){
//				if(yazar.getReferansler().size() == 0){
//					System.out.println("referansý kalmayan yazar adý:" + yazar.getYazarAdi());
//					ys.deleteYazar(yazar);
//				}
//			}
		}
		if (isS3YuklemesiYapýldý) {
			MakalePathService mps = new MakalePathService();
			mps.deleteMakalePath(makalepath);
			System.out.println("path silindi.");
			// deleteFromS3() metodu makalepath service tarafýndan halledilir.
		}
		if (isReferansOlusturuldu) {
			ReferansService rs = new ReferansService();
			rs.deleteMakaleReferans(referans);
			System.out.println("dergi-referans silindi.");
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
	
	//bu metod referansýDenetle metodundan önce çaðýrýlýr.
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

			FacesMessage msg = new FacesMessage("Baþ sayfa, son sayfadan büyük olamaz");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(passwordId, msg);
			fc.renderResponse();

		} else {
			if (referans != null) {
				for (Referans ref : referans) {
					if (ref.getBasSayfaNo() == basSayfaNo) {
						if (ref.getSonSayfaNo() != sonSayfaNo) {
							FacesMessage msg = new FacesMessage("Geçerli sayfa numaralarý giriniz");
							msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							fc.addMessage(passwordId, msg);
							fc.renderResponse();
							break;
						}

					} else {
						if (ref.getSonSayfaNo() == sonSayfaNo) {
							FacesMessage msg = new FacesMessage("Geçerli sayfa numaralarý giriniz");
							msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							fc.addMessage(passwordId, msg);
							fc.renderResponse();
							break;
						}

					}
					if ((ref.getBasSayfaNo() > basSayfaNo && ref.getSonSayfaNo() < sonSayfaNo)
							|| (ref.getBasSayfaNo() < basSayfaNo && ref.getSonSayfaNo() > sonSayfaNo)) {
						FacesMessage msg = new FacesMessage("Geçerli sayfa numaralarý giriniz");
						msg.setSeverity(FacesMessage.SEVERITY_ERROR);
						fc.addMessage(passwordId, msg);
						fc.renderResponse();
						break;
					}
				}
			}
		}

	}

	public List<Dergi> completeDergi(String dergiAd) {
		DergiService ds2 = new DergiService();
		List<Dergi> dergiler = new ArrayList<>();
		for (Dergi dergi : ds2.listDergiler()) {
			if (dergi.getDergiAdi().toLowerCase().contains(dergiAd))
				dergiler.add(dergi);
		}
		return dergiler;
	}
	
	

	public String onFlowProcess(FlowEvent event) {
		if (isYeniReferans != null) {
			if (isYeniReferans)
				return event.getNewStep();
			else
				return "dogrulamayukleme";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"UYARI: Dergi-Referans bilgilerini kaydetmeden bir sonraki adýma geçilemez.", ""));
			// System.out.println("önce referans bilgilerini sýnamalýsýn");
			return "dergireferans";
		}
	}
	
	//2.adýmda kaydet butonu ile burasý çalýþtýrýlýr.
	public void tipYazarOlustur(){
		boolean isKonferans = false;
		boolean isBildiri = false;
		boolean isKitap = false;
		boolean isTez = false;

		if (makaleTip.equals("Tez")){
			isTez = true;
			makaleyayintipi.setBildiri(isTez);

		}
		if (makaleTip.equals("Bildiri")){
			isBildiri = true;
			makaleyayintipi.setBildiri(isBildiri);

		}
		if (makaleTip.equals("Kitap")){
			isKitap = true;
			makaleyayintipi.setBildiri(isKitap);

		}
		if (makaleTip.equals("Konferans")){
			isKonferans = true;
			makaleyayintipi.setBildiri(isKonferans);

		}
		System.out.println("makale yayin tipi oluþturuluyor...");
		myts.createMakaleYayinTipi(makaleyayintipi);
		YazarService ys = new YazarService();
		System.out.println("referans için yazarlar oluþturuluyor...");
		ys.createYazarForReferans(referans, yazarSet);
		
		tipYazarOlusturuldu = true;
		
		
	}
	//2. adýmda yazar ekle butonuna her basýldýðýnda bu metod çaðýrýlýr.
	public void yazarEkle(){
		YazarService ys = new YazarService();
		System.out.println("yazar adý:" + yazarAd);
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
        MakalePathService mps = new MakalePathService();
        mps.createMakalePath(makalepath);
        System.out.println("makalepath oluþturuldu:" + makalepath.getMakalePath());
        InputStream in = null;
		try {
			in = file.getInputstream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        S3DosyaIslemleri.s3DosyaYukleme(makalepath.getMakalePath(), in);
	}
	public void fUpd2(){
        // Get uploaded file from the FileUploadEvent
       // this.file = e.getFile();
        // Print out the information of the file
		System.out.println("dfhdh");
        System.out.println("Uploaded File Name Is :: "+file.getFileName()+" :: Uploaded File Size :: "+file.getSize());
        makalepath = new MakalePath(referans.getId());
        MakalePathService mps = new MakalePathService();
        mps.createMakalePath(makalepath);
        System.out.println("makalepath oluþturuldu:" + makalepath.getMakalePath());
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
