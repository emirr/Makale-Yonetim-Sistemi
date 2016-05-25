package com.filter;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.controller.KullaniciController;

public class AbstractFilter {
	public AbstractFilter() {
		super();
	}
	KullaniciController kc = new KullaniciController();
	protected void doLogin(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
		
		
		String url = req.getRequestURL().toString();
		//System.out.println("url:" + url);
		//System.out.println("yeni url:"+url.substring(0, url.length() - origRequest.getRequestURI().length()) + origRequest.getContextPath() + "/");
		url = url.substring(0, url.length() - req.getRequestURI().length()) + req.getContextPath() + "/faces";
		RequestDispatcher rd = req.getRequestDispatcher("/pages/public/login.xhtml");
		rd.forward(request, response);
	}
	
	protected void accessDenied(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
		System.out.println("erişim reddedielcek.");

		String url = req.getRequestURL().toString();
		//System.out.println("url:" + url);
		//System.out.println("yeni url:"+url.substring(0, url.length() - origRequest.getRequestURI().length()) + origRequest.getContextPath() + "/");
		url = url.substring(0, url.length() - req.getRequestURI().length()) + req.getContextPath() + "/faces";
		System.out.println("erişim reddedielcek.");
		RequestDispatcher rd = req.getRequestDispatcher("/pages/public/yetkiAsimSayfasi.xhtml");
		rd.forward(request, response);
	}
}
