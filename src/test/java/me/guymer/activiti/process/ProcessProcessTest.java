package me.guymer.activiti.process;

import java.util.Properties;

import javax.inject.Inject;

import me.guymer.activiti.config.Config;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class ProcessProcessTest {
	
	@Configuration
	@Import(Config.class)
	@PropertySource("classpath:properties/config.properties")
	static class ContextConfiguration {
		
		@Bean
		public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
			final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
			
			Properties properties = new Properties();
			properties.setProperty("process.path", "classpath:process/process.bpmn20.xml");
			
			propertySourcesPlaceholderConfigurer.setProperties(properties);
			propertySourcesPlaceholderConfigurer.setLocalOverride(true);
			
			return propertySourcesPlaceholderConfigurer;
		}
	}
	
	@Inject
	private RuntimeService runtimeService;
	
	@Inject
	private TaskService taskService;
	
	@Test
	public void testProcessProcess() {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("test-process");
		
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
