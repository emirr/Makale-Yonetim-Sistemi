package com.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
//import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.entity.Kullanici;
//import com.entity.Rol;
import com.service.KullaniciService;

@ManagedBean//(name = "kullaniciController")
//@Named("kullaniciController")
@SessionScoped
public class KullaniciController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// public boolean isLoggedIn()
	private String kullaniciAd;
	private String sifre;
	private boolean loggedIn;
	
	private Kullanici kullanici;
	public Kullanici getKullanici() {
		return kullanici;
	}
	private KullaniciService kullaniciService = new KullaniciService();

	public KullaniciService getKullaniciService() {
		return kullaniciService;
	}

	public String getKullaniciAd() {
		return kullaniciAd;
	}

	public void setKullaniciAd(String kullaniciAd) {
		this.kullaniciAd = kullaniciAd;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String login() {
		kullanici = kullaniciService.findKullaniciByAdSifre(kullaniciAd, sifre);
		
		if (kullanici != null) {
			loggedIn = true;
			System.out.println("kullanici ad-sifre:" + kullanici.getKullaniciAd() + "-" + kullanici.getId() + kullanici.getRol().toString());
//			HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
//	                .getExternalContext().getSession(false);
//			session.setAttribute("kullaniciAd", kullaniciAd);
//			FacesContext context = FacesContext.getCurrentInstance();
//			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//			request.getSession().setAttribute("kullaniciController", this);
			
			//System.out.println("origrequest" + origRequest.getRequestURL().toString());
			urlDuzeltici();
			
			if(kullanici.getRol().toString().equals("ADMIN"))
				return "/pages/protected/admin/adminAnaSayfa.xhtml?faces-redirect=true";
			else
				return "/pages/protected/kullanici/kullaniciAnaSayfasi.xhtml?faces-redirect=true";
			
		} else{
			System.out.println("böyle bir kullanici yok");
//			 FacesContext.getCurrentInstance().addMessage(
//	                    null,
//	                    new FacesMessage(FacesMessage.SEVERITY_WARN,
//	                            "Gecersiz kullanici adi/sifre",
//	          "Lütfen gecerli bir kullanici adi/sifre girin."));
			urlDuzeltici();
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	public String logout() {
        HttpSession session = (HttpSession)  FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        urlDuzeltici();
        return "/pages/public/login.xhtml?faces-redirect=true";
    }
	public String urlDuzeltici(){
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = origRequest.getRequestURL().toString();
		//System.out.println("url:" + url);
		//System.out.println("yeni url:"+url.substring(0, url.length() - origRequest.getRequestURI().length()) + origRequest.getContextPath() + "/");
		return url.substring(0, url.length() - origRequest.getRequestURI().length()) + origRequest.getContextPath() + "/faces";
	}
}
