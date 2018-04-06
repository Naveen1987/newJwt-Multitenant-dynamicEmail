package com.fynisys.security;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;



public class JWTAuthenticationFilter extends GenericFilterBean {

 @Autowired
 TokenAuthenticationService tas;
	
@Override
  public void doFilter(ServletRequest request,
             ServletResponse response,
             FilterChain filterChain)
      throws IOException, ServletException {
	/*
	 *  SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	 */
	 
	 /**
	  * Preflight 403 error when browser sending extra request with OPTION method 
	  */
	 
	 if (((HttpServletRequest)request).getMethod().equals("OPTIONS")) {
		  ((HttpServletResponse)response).getWriter().flush();
      } 
	  /*
	   *This else will bypass to other requests 
	   * */
	 else {
		 	if(tas.invalidToken((HttpServletRequest)request)) {
		 		/*
		 		 *When Token is expired 
		 		 */
		 		SecurityContextHolder.getContext().setAuthentication(null);
		 		filterChain.doFilter(request,response);
		 	}
		 	else {
			/*
			 * When Token is valid
			 */
		    Authentication authentication = tas.getAuthentication((HttpServletRequest)request);
		    SecurityContextHolder.getContext().setAuthentication(authentication);
		    filterChain.doFilter(request,response);
		 	}
	 	}
 }
}