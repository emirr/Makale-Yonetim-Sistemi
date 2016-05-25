package com.controller;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import com.entity.Kullanici;
import com.service.KullaniciService;

@ManagedBean
@ViewScoped
public class KullaniciHesapAyarController implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean editable;
	private String resetSifre = "";
	private String resetSifreOnay = "";

	KullaniciService ks = new KullaniciService();
	@ManagedProperty("#{kullaniciController}")
	private KullaniciController kullaniciController;

	public void setKullaniciController(KullaniciController kullaniciController) {
		this.kullaniciController = kullaniciController;
	}

	private Kullanici kisi;
	private String prevAd;
	private String prevMail;
	private String prevUnvan;
	private String prevOkul;
	private String prevSifre;
	private String tip;

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getResetSifre() {
		return resetSifre;
	}

	public void setResetSifre(String resetSifre) {
		this.resetSifre = resetSifre;
	}

	public String getResetSifreOnay() {
		return resetSifreOnay;
	}

	public void setResetSifreOnay(String resetSifreOnay) {
		this.resetSifreOnay = resetSifreOnay;
	}

	public String getPrevAd() {
		return prevAd;
	}

	public void setPrevAd(String prevAd) {
		this.prevAd = prevAd;
	}

	public String getPrevMail() {
		return prevMail;
	}

	public void setPrevMail(String prevMail) {
		this.prevMail = prevMail;
	}

	public String getPrevUnvan() {
		return prevUnvan;
	}

	public void setPrevUnvan(String prevUnvan) {
		this.prevUnvan = prevUnvan;
	}

	public String getPrevOkul() {
		return prevOkul;
	}

	public void setPrevOkul(String prevOkul) {
		this.prevOkul = prevOkul;
	}

	public String getPrevSifre() {
		return prevSifre;
	}

	public void setPrevSifre(String prevSifre) {
		this.prevSifre = prevSifre;
	}

	public Kullanici getKisi() {
		return kisi;
	}

	public void setKisi(Kullanici kisi) {
		this.kisi = kisi;
	}

	public KullaniciController getKullaniciController() {
		return kullaniciController;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void changeEditable() {
		editable = !editable;
	}

	@PostConstruct
	public void init() {
		kisi = kullaniciController.getKullanici();
		System.out.println("kisi ad:" + kisi.getKullaniciAd());
		prevAd = kisi.getKullaniciAd();
		prevMail = kisi.getMail();
		prevUnvan = kisi.getUnvan();
		prevOkul = kisi.getOkul();
		prevSifre = kisi.getSifre();
		tip = kisi.getUnvan();
	}
	// public void setMevcutVeri4Kisi(){
	// kisi = kullaniciController.getKullanici();
	// System.out.println("kisi ad:" + kisi.getKullaniciAd());
	// prevAd = kisi.getKullaniciAd();
	// prevMail = kisi.getMail();
	// prevUnvan = kisi.getUnvan();
	// prevOkul = kisi.getOkul();
	// prevSifre = kisi.getSifre();
	// }

	public String anaSayfa() {
		KullaniciController.urlHandler();
		System.out.println("handler");
		return "/pages/protected/kullanici/kullaniciAnaSayfasi.xhtml?faces-redirect=true";
	}

	public String guncelle() {
		System.out.println("g�ncellemeler yap�l�yor.");

		if (!tip.equals(prevUnvan) || !prevMail.equals(kisi.getMail()) || !prevOkul.equals(kisi.getOkul())
				|| !prevAd.equals(kisi.getKullaniciAd()) || (!resetSifre.equals("") && !prevSifre.equals(resetSifre))) {
			System.out.println("yeni unvan:" + tip);
			System.out.println("yeni okul:" + kisi.getOkul());
			System.out.println("yeni mail:" + kisi.getMail());
			System.out.println("yeni ad:" + kisi.getKullaniciAd());
			System.out.println("yeni sifre:" + resetSifre);
			if(!resetSifre.equals(""))
				kisi.setSifre(resetSifre);
			ks.updateKullanici(kisi.getId(), kisi.getSifre(), kisi.getKullaniciAd(), tip, kisi.getOkul(),
					kisi.getMail());

		}

		resetSifre = "";
		resetSifreOnay = "";
		return "";
	}

	// bu metod g�ncelle metodundan �nce �a��r�lacak
	public void validateGuncellemeBilgileri(ComponentSystemEvent event) {

		 FacesContext fc = FacesContext.getCurrentInstance();
		if (editable) {
			UIComponent components = event.getComponent();

			UIInput uiYeniSifre = (UIInput) components.findComponent("yeniSifre");
			String yeniSifre = uiYeniSifre.getLocalValue().toString();
			System.out.println("yeniSifre:" + yeniSifre);

			if (!yeniSifre.equals("")) {
				UIInput uiYeniSifreDogrula = (UIInput) components.findComponent("yeniSifreDogrula");
				String yeniSifreDogrula = uiYeniSifreDogrula.getLocalValue().toString();
				System.out.println("yeniSifreDo�rulama:" + yeniSifreDogrula);
				if (!yeniSifre.equals(yeniSifreDogrula)) {
					System.out.println("�ifre do�rulama hatas�");
					FacesMessage msg = new FacesMessage("şifre doğrulama hatası");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					fc.addMessage(yeniSifre, msg);
					fc.renderResponse();
				}
			}

			UIInput uiYeniMail = (UIInput) components.findComponent("yeniMail");
			String yeniMail = uiYeniMail.getLocalValue().toString();
			//String yeniMail = kisi.getMail();
			System.out.println("yeniMail:" + yeniMail);
			
			String regex = "^(.+)@(.+)$";
			 
			Pattern pattern = Pattern.compile(regex);
			 
			Matcher matcher = pattern.matcher(yeniMail);
			
			if(!matcher.matches()){
				FacesMessage msg = new FacesMessage("Yanlış mail formatı");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				fc.addMessage(yeniMail, msg);
				fc.renderResponse();
			}
			   

			if (!prevMail.equals(yeniMail)) {
				if (ks.findKullaniciByMail(yeniMail) != null) {
					System.out.println("bu mail adresi alınamaz");
					FacesMessage msg = new FacesMessage("bu mail adresi alınamaz");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					fc.addMessage(yeniMail, msg);
					fc.renderResponse();
				}

			}
			

		}
	}
}
