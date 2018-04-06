package com.fynisys.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	AccountCredentials creds;
	
 @Autowired
 TokenAuthenticationService tas;

public JWTLoginFilter(String url,AuthenticationManager authManager) {
	  super(new AntPathRequestMatcher(url));
      setAuthenticationManager(authManager);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException, IOException, ServletException {
	  /*creds= new ObjectMapper()
        .readValue(req.getInputStream(), AccountCredentials.class);*/
	  creds=new AccountCredentials();
	  creds.setUsername(req.getParameter("username"));
	  creds.setPassword(req.getParameter("password"));
    return getAuthenticationManager().authenticate(
        new UsernamePasswordAuthenticationToken(
            creds.getUsername(),
            creds.getPassword(),
            Collections.emptyList()
        )
    );
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest req,
      HttpServletResponse res, FilterChain chain,
      Authentication auth) throws IOException, ServletException {
	  String token= tas.addAuthentication(req,res, auth.getName(),creds);
	  tas.saveToken(req, token,auth);
  }
  
}