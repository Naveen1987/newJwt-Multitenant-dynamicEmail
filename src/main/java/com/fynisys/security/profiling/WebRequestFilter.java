package com.fynisys.security.profiling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fynisys.security.JWTCreator;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class WebRequestFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger("Web Filter");

	@Autowired
	JWTCreator jwtCreator;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
			logger.info("Setting Setting Start");
			HttpServletRequest req=(HttpServletRequest)request;
			Object token=req.getHeader("Authorization");
			if(token!=null) {
				/*
				 * Schema Setting
				 * */
			String schemaId=jwtCreator.getSchemaIDToken(token.toString());	
//			logger.info("Database Token->"+schemaId);
			TenantContext.setCurrentTenant(schemaId);
			}
			
			logger.info("Database Setting Complete");
			chain.doFilter(request, response); 
	}


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	
}