package com.fynisys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class FynisysDevelopmentApplication.
 */
@SpringBootApplication
public class FynisysDevelopmentApplication extends SpringBootServletInitializer {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(FynisysDevelopmentApplication.class, args);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.boot.web.support.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(FynisysDevelopmentApplication.class);
	}
	
/*
 * This is CorsFilter that use to resolve the cors issue in application it save to define a sperate filter to to same
 * */
	
	/**
 * Cors filter.
 *
 * @return the cors filter
 */
@Bean
	   public CorsFilter corsFilter() {
	       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	       CorsConfiguration config = new CorsConfiguration();
	       config.setAllowCredentials(true);
	       config.addAllowedOrigin("*");
	       config.addAllowedHeader("*");
	       config.addAllowedMethod("OPTIONS");
	       config.addAllowedMethod("GET");
	       config.addAllowedMethod("POST");
	       config.addAllowedMethod("PUT");
	       config.addAllowedMethod("DELETE");
	       source.registerCorsConfiguration("/**", config);
	       return new CorsFilter(source);
	   }

	
}
