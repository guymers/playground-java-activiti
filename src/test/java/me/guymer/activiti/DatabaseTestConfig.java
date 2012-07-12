package me.guymer.activiti;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DatabaseTestConfig {
	
	@Bean
	public DataSource dataSource() {
		final EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
		embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.H2);
		//embeddedDatabaseBuilder.addScript("classpath:sql/schema.sql");
		//embeddedDatabaseBuilder.addScript("classpath:sql/data.sql");
		
		return embeddedDatabaseBuilder.build();
	}
	
}
