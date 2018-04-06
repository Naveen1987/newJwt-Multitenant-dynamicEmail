package com.fynisys.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTCreator {
	
	@Value("${token.security.EXPIRE}")
	  private long EXPIRATIONTIME;
	    
	  @Value("${token.security.SECRET}")
	  private String SECRET;
	  
	  static final String HEADER_STRING = "Authorization";
	  
	  private final Logger logger=LoggerFactory.getLogger("Token Creator");
	  
	  public String getUsernameFromToken(String token) {
		    String username;
		    try {
		      final Claims claims = this.getClaimsFromToken(token);
		      username = claims.getSubject();
		    } catch (Exception e) {
		      username = null;
		    }
		    return username;
		  }

	  public String getSchemaIDToken(String token) {
		    String created;
		    try {
		      final Claims claims = this.getClaimsFromToken(token);
		      created =  claims.get("sc").toString();
		    } catch (Exception e) {
		      created = null;
		    }
		    return created;
		  }
	  
		  public Date getCreatedDateFromToken(String token) {
		    Date created;
		    try {
		      final Claims claims = this.getClaimsFromToken(token);
		      created = new Date((Long) claims.get("created"));
		    } catch (Exception e) {
		      created = null;
		    }
		    return created;
		  }

		  public Date getExpirationDateFromToken(String token) {
		    
			  Date expiration;
		    try {
		      final Claims claims = this.getClaimsFromToken(token);
		      expiration = claims.getExpiration();
		    } catch (Exception e) {
		      expiration = null;
		    }
		    return expiration;
		  }


		  private Claims getClaimsFromToken(String token) {
		    Claims claims;
		    try {
		      claims = Jwts.parser()
		        .setSigningKey(this.SECRET)
		        .parseClaimsJws(token)
		        .getBody();
		    } catch (Exception e) {
		      claims = null;
		    }
		    return claims;
		  }
		  
		  public Date generateCurrentDate() {
		    return new Date(System.currentTimeMillis());
		  }

		  public Date generateExpirationDate() {
		    return new Date(System.currentTimeMillis() + EXPIRATIONTIME);
		  }

		  public Boolean isTokenExpired(String token) {
		    final Date expiration = this.getExpirationDateFromToken(token);
		    if(expiration!=null) {
		    	return expiration.before(this.generateCurrentDate());
		    }else {
		    	logger.error("Token has been Expired");
		    	return true;
		    }
		    
	    }
		  
		  public String generateToken(AccountCredentials  userDetails,String schemaName) {
			    Map<String, Object> claims = new HashMap<String, Object>();
			    claims.put("sub", userDetails.getUsername());
			    /*
			     * SC define Schema Table code
			     */
			    claims.put("sc", schemaName);
			    claims.put("created", this.generateCurrentDate());
			    return this.generateToken(claims);
			  }

		  public String generateToken(Map<String, Object> claims) {
				  return Jwts.builder()
			      .setClaims(claims)
			      .setExpiration(this.generateExpirationDate())
			      .signWith(SignatureAlgorithm.HS512, this.SECRET)
			      .compact();
			  }
		  
		  
}
