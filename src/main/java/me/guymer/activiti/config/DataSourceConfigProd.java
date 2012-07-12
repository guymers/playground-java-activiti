package me.guymer.activiti.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@Profile("prod")
@PropertySource("classpath:properties/database.prod.properties")
public class DataSourceConfigProd implements DataSourceConfig {
	
	@Value("${database.driver}")
	private String driver;
	
	@Value("${database.url}")
	private String url;
	
	@Value("${database.username}")
	private String username;
	
	@Value("${database.password}")
	private String password;
	
	@Bean
	@Override
	public DataSource dataSource() {
		final BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(driver);
		dataSource.setJdbcUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		return dataSource;
	}
	
}
