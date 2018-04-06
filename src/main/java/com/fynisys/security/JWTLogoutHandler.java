package com.fynisys.security;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;



@Component
public class JWTLogoutHandler extends SimpleUrlLogoutSuccessHandler{

	@Autowired
	TokenAuthenticationService tokenAuthenticationService;
	
	private final Logger logger=LoggerFactory.getLogger("Logout Handler"); 
	
    public void onLogoutSuccess(javax.servlet.http.HttpServletRequest request, 
            javax.servlet.http.HttpServletResponse response,
            Authentication authentication) {
    	try {
    		if(tokenAuthenticationService.revokeToken(request))
    		{
			logger.info("successfully logout with token");
			JSONObject json=new JSONObject();
			json.put("msg", "Logout Successfully");
			response.setContentType("application/json");
			response.getWriter().println(json);
			response.getWriter().flush();
    		}
    		else {
    			logger.info("Some error with Token because it is null");
    			JSONObject json=new JSONObject();
    			json.put("msg", "Logout Not Successfully Please verify your Token");
    			response.setContentType("application/json");
    			response.getWriter().println(json);
    			response.getWriter().flush();	
    		}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
    }
}