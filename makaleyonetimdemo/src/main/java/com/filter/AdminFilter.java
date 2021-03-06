package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.KullaniciController;

public class AdminFilter  extends AbstractFilter implements Filter{
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		KullaniciController kullaniciControl = (KullaniciController) req.getSession().getAttribute("kullaniciController");
		//System.out.println("requested url");
		System.out.println("admin filtersinde");
		if (kullaniciControl != null) {
			//System.out.println("rol:"+kullaniciControl.getKullanici().getRol());
			if(kullaniciControl.getKullanici().getRol().toString().equals("ADMIN")){
				
				chain.doFilter(request, response);
			}
			else{
				System.out.println("niye reddetin");
				accessDenied(request, response, req);
			
			}
		}
		else
			doLogin(request, response, req);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
