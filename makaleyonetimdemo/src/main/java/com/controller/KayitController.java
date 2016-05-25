package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;

import com.entity.Kullanici;
import com.service.KullaniciService;
@ManagedBean
@ViewScoped
public class KayitController {
	private String kullaniciAd;
public String getKullaniciAd() {
		return kullaniciAd;
	}
	public void setKullaniciAd(String kullaniciAd) {
		this.kullaniciAd = kullaniciAd;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getOkul() {
		return okul;
	}
	public void setOkul(String okul) {
		this.okul = okul;
	}
	public String getUnvan() {
		return unvan;
	}
	public void setUnvan(String unvan) {
		this.unvan = unvan;
	}
	//	private String sifre;
	private String mail;
	private String okul, unvan;
	public void kayitBasvuruAl(){
		KullaniciService ks = new KullaniciService();
		Kullanici kisi = new Kullanici(kullaniciAd, mail, unvan, okul);
		ks.createKullanici(kisi);
		
		FacesContext.getCurrentInstance().addMessage("haberId", new FacesMessage(FacesMessage.SEVERITY_INFO,"Kayıt talebiniz alındı!","Talebiniz onaylandıktan sonra şifreniz mail adresinize gönderilecektir."));
		//urlDuzeltici();
		//return "/pages/public/login.xhtml?faces-redirect=true";
	}
	public String giriseDon(){
		System.out.println("url düzelt");
		urlDuzeltici();
				return "/pages/public/girisSayfasi.xhtml?faces-redirect=true";
	}
	public  String urlDuzeltici(){
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = origRequest.getRequestURL().toString();
		//System.out.println("url:" + url);
		//System.out.println("yeni url:"+url.substring(0, url.length() - origRequest.getRequestURI().length()) + origRequest.getContextPath() + "/");
		return url.substring(0, url.length() - origRequest.getRequestURI().length()) + origRequest.getContextPath() + "/faces";
	}
	public void validateGuncellemeBilgileri(ComponentSystemEvent event) {
		 FacesContext fc = FacesContext.getCurrentInstance();

		UIComponent components = event.getComponent();
		UIInput uiYeniMail = (UIInput) components.findComponent("kisimail");
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
//			FacesContext.getCurrentInstance().addMessage("MailFormatId", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hatalı Mail Formatı!",""));
		}
		
		KullaniciService ks = new KullaniciService();
		if (ks.findKullaniciByMail(yeniMail) != null) {
			System.out.println("bu mail adresi alınamaz");
			//FacesContext.getCurrentInstance().addMessage("BenzersizMailId", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Bu mail alınamaz!",""));
			FacesMessage msg = new FacesMessage("Bu mail adresi alınamaz");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(yeniMail, msg);
			fc.renderResponse();
		}
	}
	public List<String> completeKurumAd(String kurumad) {
		KullaniciService rs = new KullaniciService();
		List<String> referanslar = new ArrayList<>();
		for (Kullanici ref : rs.listOnayliKullanicilar()) {
			if (ref.getOkul()!=null && ref.getOkul().toLowerCase().contains(kurumad))
				referanslar.add(ref.getOkul());
		}
		return referanslar;
	}
}
