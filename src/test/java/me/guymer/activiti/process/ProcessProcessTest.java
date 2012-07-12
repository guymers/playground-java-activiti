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
public class ProcessProcessTest {
	
	@Configuration
	@Import({ DatabaseTestConfig.class, PersistenceConfig.class, ActivitiConfig.class })
	@ComponentScan(basePackages = { "me.guymer.activiti.custom", "me.guymer.activiti.users", "me.guymer.activiti.groups" })
	static class ContextConfiguration {
		// required for @Value to work correctly
		@Bean
		public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
			final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
			
			Properties properties = new Properties();
			properties.setProperty("process.path", "classpath:process/process.bpmn20.xml");
			
			propertySourcesPlaceholderConfigurer.setProperties(properties);
			
			return propertySourcesPlaceholderConfigurer;
		}
	}
	
	@Inject
	private RuntimeService runtimeService;
	
	@Inject
	private TaskService taskService;
	
	@Test
	public void testBasicProcess() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("customerName", "John Doe");
		variables.put("amount", 15000L);
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("test-process", variables);
		
		String processInstanceId = processInstance.getProcessInstanceId();
		
		Task userTask1 = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		
		Assert.assertNotNull(userTask1);
		Assert.assertEquals("First User Task", userTask1.getName());
		
		taskService.complete(userTask1.getId());
		
		Task userTask2 = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		
		Assert.assertNotNull(userTask2);
		Assert.assertEquals("Second User Task", userTask2.getName());
		
		taskService.complete(userTask2.getId());
		
		// process should be complete
		Assert.assertEquals(0, runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).count());
		
	}
}
