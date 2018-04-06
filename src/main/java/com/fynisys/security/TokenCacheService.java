package com.fynisys.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Component
public class TokenCacheService {
	private static final Logger logger = LoggerFactory.getLogger("Token Cache Service");
    private static final Cache restApiAuthTokenCache = CacheManager.getInstance().getCache("restApiAuthTokenCache");
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 60 * 60 * 1000;

    /*
     * This Scheduler is used to schedule then Expire token eviction and It will execute after one hour
     * */
    
    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        logger.info("Evicting expired tokens");
        restApiAuthTokenCache.evictExpiredElements();
    }
    
    public void store(String token,Authentication auth) {
    	restApiAuthTokenCache.put(new Element(token, auth));
    	logger.info("Token Saving :{ name:"+token+" value:"+auth.getName()+"}");
    }

    public boolean contains(String token) {
    	//logger.info("Token existance:"+(restApiAuthTokenCache.get(token)!=null));
        return restApiAuthTokenCache.get(token) != null;
    }

    public String retrieve(String token) {
    	logger.info("Token retival:"+token);
        return (String) restApiAuthTokenCache.get(token).getObjectValue();
    }
    
    public void evictToken(String token) {
        logger.info("Evicting Token:"+token);
        restApiAuthTokenCache.remove(token);
    }
    
    public void evictAllToken() {
        logger.info("Evicting All Token");
        restApiAuthTokenCache.removeAll();
    }
  
}
