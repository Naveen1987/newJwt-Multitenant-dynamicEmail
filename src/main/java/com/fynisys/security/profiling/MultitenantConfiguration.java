package com.fynisys.security.profiling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.io.*;
import java.util.*;

@Configuration
public class MultitenantConfiguration {

		
    @Autowired
    private DataSourceProperties properties;
  
    @Value("${oracle.servername}")
    private String serverName;
    
    @Value("${oracle.serverport}")
    private String serverPort;
    
    //Now we are reading default configuration from application.properties for data source
    @Bean
    public DataSource dataSource() throws FileNotFoundException, IOException {
    	/*
    	 * defaultDataSource() calling this method directly
    	 */
  
    	JdbcTemplate jdbc=new JdbcTemplate(defaultDataSource());
    	Map<Object,Object> resolvedDataSources = new HashMap<>();
    	String sql="select * from FWMS_COMPANY_DEFAULT";
    	try {
    	List<Map<String,Object>> results = jdbc.queryForList(sql);
    	
    	for (Map<String,Object> m : results){
             DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());
             String tenantId = m.get("FCD_DEFAULT_FCODE").toString();
			 dataSourceBuilder.driverClassName(properties.getDriverClassName())
			         .url("jdbc:oracle:thin:"+m.get("WMS_SCHEMA_USER").toString()+"/"+m.get("WMS_SCHEMA_PASSWORD").toString()+"@"+serverName+":"+serverPort+"/"+m.get("WMS_SCHEMA_NAME"))
			         .username(m.get("WMS_SCHEMA_USER").toString())
			         .password(m.get("WMS_SCHEMA_PASSWORD").toString());
			 if(properties.getType() != null) {
			     dataSourceBuilder.type(properties.getType());
			 }
			 
			 resolvedDataSources.put(tenantId, dataSourceBuilder.build());
			
    	} 

        MultitenantDataSource dataSource = new MultitenantDataSource();
        dataSource.setDefaultTargetDataSource(defaultDataSource());
        dataSource.setTargetDataSources(resolvedDataSources);
        dataSource.afterPropertiesSet();
        return dataSource;
    	}catch (Exception e) {
    		 System.out.println(e);
		}
    	MultitenantDataSource dataSource = new MultitenantDataSource();
	     dataSource.setDefaultTargetDataSource(defaultDataSource());
	     dataSource.setTargetDataSources(resolvedDataSources);
	     dataSource.afterPropertiesSet();
	     return dataSource;
    }
    

    
    /**
     * 
     * 
     * Creates the default data source for the application
     * @return
     */
  
    private DataSource defaultDataSource() {
    
        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader())
                .driverClassName(properties.getDriverClassName())
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword());

        if(properties.getType() != null) {
            dataSourceBuilder.type(properties.getType());
        }

        return dataSourceBuilder.build();
    }
}
