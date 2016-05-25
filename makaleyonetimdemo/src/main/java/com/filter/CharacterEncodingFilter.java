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

public class CharacterEncodingFilter implements Filter {
 public void init(FilterConfig config) throws ServletException {
 }
 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 System.out.println("char encoding filter");
 HttpServletRequest req = (HttpServletRequest) request;
	
 System.out.println(""+req.getRequestURL());
 

request.setCharacterEncoding("UTF-8");
  response.setCharacterEncoding("UTF-8");
  chain.doFilter(request, response);
 }
@Override
public void destroy() {
	// TODO Auto-generated method stub
	
}
 }