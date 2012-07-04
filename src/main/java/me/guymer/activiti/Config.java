package me.guymer.activiti;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@ComponentScan("me.guymer.activiti")
@EnableTransactionManagement
public class Config {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

	@Inject
	private List<SessionFactory> sessionFactories;
	
	@Bean
	public DataSource dataSource() {
		final BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass("org.postgresql.Driver");
		dataSource.setJdbcUrl("jdbc:postgresql:postgres");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		
		return dataSource;
	}
	
	@Bean
	public NamedParameterJdbcTemplate jdbcTemplate() {
		final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource());
		
		return namedParameterJdbcTemplate;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
		
		return transactionManager;
	}
	
	@Bean
	public ProcessEngineConfigurationImpl processEngineConfiguration() throws IOException {
		final SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
		processEngineConfiguration.setDataSource(dataSource());
		processEngineConfiguration.setTransactionManager(transactionManager());
		processEngineConfiguration.setDatabaseSchemaUpdate("true");
		processEngineConfiguration.setJobExecutorActivate(false);
		processEngineConfiguration.setCustomSessionFactories(sessionFactories);
		
		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath*:/process/**/*.bpmn20.xml");
		
		processEngineConfiguration.setDeploymentResources(resources);
		
		LOGGER.info("processEngineConfiguration {}", sessionFactories.size());
		
		return processEngineConfiguration;
	}
	
	@Bean
	public ProcessEngine processEngine() throws Exception {
		final ProcessEngineFactoryBean processEngine = new ProcessEngineFactoryBean();
		processEngine.setProcessEngineConfiguration(processEngineConfiguration());
		
		return processEngine.getObject();
	}
	
	@Bean
	public RepositoryService repositoryService() throws Exception {
		final ProcessEngine processEngine = processEngine();
		
		return processEngine.getRepositoryService();
	}
	
	@Bean
	public RuntimeService runtimeService() throws Exception {
		final ProcessEngine processEngine = processEngine();
		
		return processEngine.getRuntimeService();
	}
	
	@Bean
	public TaskService taskService() throws Exception {
		final ProcessEngine processEngine = processEngine();
		
		return processEngine.getTaskService();
	}
	
	@Bean
	public HistoryService historyService() throws Exception {
		final ProcessEngine processEngine = processEngine();
		
		return processEngine.getHistoryService();
	}
	
	@Bean
	public ManagementService managementService() throws Exception {
		final ProcessEngine processEngine = processEngine();
		
		return processEngine.getManagementService();
	}
	
	@Bean
	public FormService formService() throws Exception {
		final ProcessEngine processEngine = processEngine();
		
		return processEngine.getFormService();
	}
	
}
