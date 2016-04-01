package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.KullaniciController;

public class LoginFilter extends AbstractFilter implements Filter {

	/**
	 * Creates a new instance of LoginFilter
	 */
	public LoginFilter() {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		KullaniciController kullaniciControl = (KullaniciController) req.getSession()
				.getAttribute("kullaniciController");
		// String reqURI = req.getRequestURI();
		String reqURI = req.getRequestURI();
		System.out.println("login filtersinde");
		System.out.println("requets for:" + reqURI);
		if (kullaniciControl == null || !kullaniciControl.isLoggedIn()) {
			String contextPath = ((HttpServletRequest) request).getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath + "/faces/pages/public/login.xhtml");
		} else
			chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
