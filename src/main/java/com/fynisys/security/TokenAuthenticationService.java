package com.fynisys.security;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fynisys.model.FUND_USERS;
import com.fynisys.otptempass.service.TempPassGeneratorService;
import com.fynisys.repository.FUND_USERSRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import static java.util.Collections.emptyList;
import java.io.IOException;


@Component
public class TokenAuthenticationService {
 
//  @Value("${token.security.EXPIRE}")
//  private long EXPIRATIONTIME;
//    
//  @Value("${token.security.SECRET}")
//  private String SECRET;
  
  
  private final Logger logger=LoggerFactory.getLogger("Token Authentication Service");
  
  @Autowired
  TokenCacheService tcs;
  
  @Autowired
  FUND_USERSRepository accountRepository;
  
  @Autowired
  TempPassGeneratorService tempPassGeneratorService;
  
  @Autowired
  JWTCreator jwtCreator;
  
  static final String HEADER_STRING = "Authorization";
  
  @Value("${token.security.EXPIRE}")
  private long EXPIRATIONTIME;
    
  @Value("${token.security.SECRET}")
  private String SECRET;
  
    /*
     * 
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
		  
	     private Date generateCurrentDate() {
		    return new Date(System.currentTimeMillis());
		  }

		  private Date generateExpirationDate() {
		    return new Date(System.currentTimeMillis() + EXPIRATIONTIME);
		  }

		  private Boolean isTokenExpired(String token) {
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
			    
			    claims.put("sc", schemaName);
			    claims.put("created", this.generateCurrentDate());
			    return this.generateToken(claims);
			  }

			  private String generateToken(Map<String, Object> claims) {
				  return Jwts.builder()
			      .setClaims(claims)
			      .setExpiration(this.generateExpirationDate())
			      .signWith(SignatureAlgorithm.HS512, this.SECRET)
			      .compact();
			  }
		  
 */
  
 public String addAuthentication(HttpServletRequest req,HttpServletResponse res, String username,AccountCredentials creds) throws IOException {
	 			
			    	/*
			         * For Adding Login user Information in Response
			         */
			        JSONObject json=new JSONObject();
			        String schemaName="";
			        try {	    
			        	FUND_USERS account = accountRepository.getLogin(username);
			    		if(account!=null){
			    			//edited by vikas
			    			
			    			if(account.getSVU_BLOCK().equals("1".trim()))
			    			{
			    				json.put("msg", "User is deactivated");
			    				
			    			}
			    			else
			    			{
			    			if(account.getSVU_FLAG()==0){
			    				Calendar tempassdate=account.getSVU_LAST_CHANGE();
			    				if(tempPassGeneratorService.invalidPassword(tempassdate)){
			    					json.put("msg","Again send OTP");
			    					
			    				}else{
			    					json.put("msg","reset password");
			    								
			    				}
			    			}
			    			else{
			    				/*
			    				 * Scehma Id in Variable
			    				 */
			    				schemaName=account.getFCD_DEFAULT_FCODE()+"";
			    				json.put("msg","Successfully login");
			    				json.put("displayname", account.getSVU_NAME());
			    				json.put("svc_uid", account.getSVC_UID());
			    				json.put("lastseen", account.getSVU_USER_LASTSEEN().getTimeInMillis());
			    				json.put("roleid", account.getFund_roles().getFRL_ROLEID());
			    				json.put("rolename", account.getFund_roles().getFRL_ROLENAME());
			    				account.setSVU_USER_LASTSEEN(Calendar.getInstance());
			    				try{
			    				accountRepository.save(account);
			    								
			    				}catch (Exception e) {
			    					json.put("msg", e.getMessage());
			    					
			    				}
			    			}
			    		}
			    		}
			    		else{
			    			json.put("msg","Invalid username or password");
			    			
			    		}
			        }catch (Exception e) {
			        	logger.error(e.getMessage());
			    
			        	}
			    /*
			     * Now creating response
			     */
			     /*
		 		 * Adding Token		   
		 		 */
		 		String JWT = jwtCreator.generateToken(creds,schemaName);
				res.setHeader("Access-Control-Expose-Headers", HEADER_STRING);
				res.addHeader(HEADER_STRING, JWT);
				 /*
		         * Now preparing response body
		    	 * Add a comment to this line
		         */
			    res.setContentType("application/json");
			    res.getWriter().println(json);
			    return JWT;
			  }  
	  
  public  Authentication getAuthentication(HttpServletRequest request) {
   String token = request.getHeader(HEADER_STRING);    
   if(jwtCreator.isTokenExpired(token)) {	   	   
	   try {
		   if(revokeToken(request)) {
			   logger.info("Token History deleted");
		   }else {
			   logger.info("History Not deleted");
		   }
		  
	   }catch (Exception e) {
		logger.error(e.getMessage());
	}
	   return null;
   }else {
	   if (token != null) {
		      // parse the token.
		      String user = jwtCreator.getUsernameFromToken(token);
		      return user != null ?
		          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
		          null;
		    }
   }
    return null;
  }
  
  public boolean invalidToken(HttpServletRequest request) {
	  String token =request.getHeader(HEADER_STRING);
      if(tcs.contains(token))
      {
    	  return false;
      }
	  return true; 
	}
  
  public boolean saveToken(HttpServletRequest request,String token,Authentication auth) throws JsonParseException, JsonMappingException, IOException {  
     try {
    	  tcs.store(token, auth);
    	  return true;
      }catch (Exception e) {
		logger.error(e.getMessage());
	}
	  return false;
	}
  public boolean revokeToken(HttpServletRequest request) {
	  String token =request.getHeader(HEADER_STRING);
      try {
    	  if(token==null) {
    		  logger.error("Null Token Founded");
    		  return false;
    	  }
    	  else {
    	   tcs.evictToken(token);
           return true;
    	  }
    	  
      }catch (Exception e) {
		logger.error(e.getMessage());
	}
	  return false;
	}
}