package me.guymer.activiti.config;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ActivitiConfig {

	@Value("${activiti.database.type}")
	private String databaseType;

	@Value("${process.path}")
	private String processPath;

	@Inject
	private DataSource dataSource;

	@Inject
	private PlatformTransactionManager transactionManager;

	@Inject
	private EntityManagerFactory entityManagerFactory;

	@Inject
	private List<SessionFactory> sessionFactories;

	@Inject
	private ProcessEngine processEngine;

	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration() throws IOException {
		final SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
		processEngineConfiguration.setDatabaseType(databaseType);
		processEngineConfiguration.setDataSource(dataSource);
		processEngineConfiguration.setTransactionManager(transactionManager);
		processEngineConfiguration.setDatabaseSchemaUpdate("true");
		processEngineConfiguration.setJobExecutorActivate(true);
		processEngineConfiguration.setHistory(HistoryLevel.FULL.getKey());
		processEngineConfiguration.setCustomSessionFactories(sessionFactories);

		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = pathMatchingResourcePatternResolver.getResources(processPath);

		processEngineConfiguration.setDeploymentResources(resources);

		/*processEngineConfiguration.setJpaEntityManagerFactory(entityManagerFactory);
		processEngineConfiguration.setJpaHandleTransaction(true);
		processEngineConfiguration.setJpaCloseEntityManager(true);*/

		return processEngineConfiguration;
	}

	@Bean
	public ProcessEngineFactoryBean processEngineFactoryBean() throws IOException {
		final ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
		processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration());

		return processEngineFactoryBean;
	}

	@Bean
	public RepositoryService repositoryService() throws Exception {
		return processEngine.getRepositoryService();
	}

	@Bean
	public RuntimeService runtimeService() throws Exception {
		return processEngine.getRuntimeService();
	}

	@Bean
	public TaskService taskService() throws Exception {
		return processEngine.getTaskService();
	}

	@Bean
	public HistoryService historyService() throws Exception {
		return processEngine.getHistoryService();
	}

	@Bean
	public ManagementService managementService() throws Exception {
		return processEngine.getManagementService();
	}

	@Bean
	public IdentityService identityService() throws Exception {
		return processEngine.getIdentityService();
	}

	@Bean
	public FormService formService() throws Exception {
		return processEngine.getFormService();
	}
}
