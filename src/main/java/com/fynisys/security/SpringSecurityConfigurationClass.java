package com.fynisys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import com.fynisys.model.FUND_USERS;
import com.fynisys.repository.FUND_USERSRepository;


@Configuration
public class SpringSecurityConfigurationClass extends WebSecurityConfigurerAdapter {
	
	/**
	 * Autowire to Cors filter so that it can be use
	 */
	@Autowired
	private CorsFilter corsfilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	   /*
	    * Add filter to avoid the cors related issue
	    * */
		http.addFilterBefore(corsfilter, ChannelProcessingFilter.class);
	   /*
	    * This is a place where we define URL flows for validation 
	    */
		http.csrf().disable()
		    .authorizeRequests()
		    .antMatchers("/").permitAll()
		    /*
		     * This next below allows to websocket request
		     */
		    /*
		     * For Breaking security
		     */
		    .antMatchers(HttpMethod.POST, "/*").permitAll()
		    .antMatchers(HttpMethod.GET, "/*").permitAll()
		    /*
		     * End Security
		     */
		    /*
		     * End of websocket configuration
		     */
		    .antMatchers(HttpMethod.POST, "/sendtemppass").permitAll()
		    .antMatchers(HttpMethod.POST, "/sendotp").permitAll()
		    .antMatchers(HttpMethod.POST, "/resetpassword").permitAll()
	        .antMatchers(HttpMethod.POST, "/login").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        /*
	         * This is for logout filteration
	         * */
            .logout().logoutUrl("/signout").invalidateHttpSession(true).logoutSuccessHandler(jWTLogoutHandler())
	        .and()
	        /*
	         *  We filter the /login requests
	         */
	        .addFilterBefore(jWTLoginFilter(authenticationManager()),
	                UsernamePasswordAuthenticationFilter.class)
	        /*
	         *  And filter other requests to check the presence of JWT in header
	         */
	        .addFilterBefore(jWTAuthenticationFilter(),
	                UsernamePasswordAuthenticationFilter.class); 
			
	  }
	/*
	 * 
	 * Filter can not directly access to @Componet so make them @Bean
	 * */
	/*
	 * For login filter
	 */
	@Bean public JWTLoginFilter jWTLoginFilter(AuthenticationManager auth) {
		return new JWTLoginFilter("/login",auth);
	}
	/*
	 * For logout Handler
	 */
	@Bean public JWTLogoutHandler jWTLogoutHandler() {
		return new JWTLogoutHandler();
	}
	/*
	 * For Authentication Filter
	 */
	@Bean public JWTAuthenticationFilter jWTAuthenticationFilter() {
		return new JWTAuthenticationFilter();
	}
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

  @Autowired
  FUND_USERSRepository accountRepository;

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService());
  }
  
  @Bean
  UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FUND_USERS account = accountRepository.getLogin(username);
        if(account != null) {
        	/**
        	 * Here we added username and password so on login request username and password will authenticate
        	 * but in other case it will extract user name and match with token
        	 */
        return new User(account.getSVU_USER_NAME(), account.getSVU_USER_PASSWORD(), true, true, true, true,
                AuthorityUtils.createAuthorityList("USER"));
        } else {
          throw new UsernameNotFoundException("could not find the user '"
                  + username + "'");
        }
      }
      
    };
  
  }
}