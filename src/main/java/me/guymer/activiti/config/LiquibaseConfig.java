package me.guymer.activiti.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfig {
	
	@Value("${default.schema}")
	private String defaultSchema;
	
	@Inject
	private DataSource dataSource;
	
	@Bean
	public SpringLiquibase springLiquibase() {
		final SpringLiquibase springLiquibase = new SpringLiquibase();
		springLiquibase.setDataSource(dataSource);
		springLiquibase.setDefaultSchema(defaultSchema);
		springLiquibase.setChangeLog("classpath:sql/liquibase/db.changelog-master.xml");
		
		return springLiquibase;
	}
	
}
