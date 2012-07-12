package me.guymer.activiti.process;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import me.guymer.activiti.DatabaseTestConfig;
import me.guymer.activiti.config.ActivitiConfig;
import me.guymer.activiti.config.PersistenceConfig;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class BasicProcessTest {
	
	@Configuration
	@Import({ DatabaseTestConfig.class, PersistenceConfig.class, ActivitiConfig.class })
	@ComponentScan(basePackages = { "me.guymer.activiti.custom", "me.guymer.activiti.users", "me.guymer.activiti.groups" })
	static class ContextConfiguration {
		// required for @Value to work correctly
		@Bean
		public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
			final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
			
			Properties properties = new Properties();
			properties.setProperty("process.path", "classpath:process/basic.bpmn20.xml");
			
			propertySourcesPlaceholderConfigurer.setProperties(properties);
			
			return propertySourcesPlaceholderConfigurer;
		}
	}
	
	@Inject
	private RuntimeService runtimeService;
	
	@Inject
	private TaskService taskService;
	
	@Test
	//@Deployment(resources = { "process/basic.bpmn20.xml" })
	public void testBasicProcess() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("customerName", "John Doe");
		variables.put("amount", 15000L);
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("basic-process", variables);
		
		String processInstanceId = processInstance.getProcessInstanceId();
		
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		
		Assert.assertNotNull(task);
		Assert.assertEquals("Basic First User Task", task.getName());
		// List<Task> availableTasks = taskService.createTaskQuery().taskCandidateUser(userId).list();
		
		taskService.complete(task.getId());
		
		// process should be complete
		Assert.assertEquals(0, runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).count());
		
		/*
		// Variable should be present containing the loanRequest created by the spring bean
		Object value = runtimeService.getVariable(processInstance.getId(), "loanRequest");
		assertNotNull(value);
		assertTrue(value instanceof LoanRequest);
		LoanRequest request = (LoanRequest) value;
		assertEquals("John Doe", request.getCustomerName());
		assertEquals(15000L, request.getAmount().longValue());
		assertFalse(request.isApproved());
		
		// We will approve the request, which will update the entity
		variables = new HashMap<String, Object>();
		variables.put("approvedByManager", Boolean.TRUE);
		
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		assertNotNull(task);
		taskService.complete(task.getId(), variables);
		
		// If approved, the processsInstance should be finished, gateway based on loanRequest.approved value
		assertEquals(0, runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).count());*/
		
	}
}
