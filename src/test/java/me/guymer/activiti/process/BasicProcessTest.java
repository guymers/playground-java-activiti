package me.guymer.activiti.process;

import java.util.HashMap;
import java.util.Map;
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
public class BasicProcessTest {

	@Configuration
	@Import(Config.class)
	@PropertySource("classpath:properties/config.properties")
	static class ContextConfiguration {

		@Bean
		public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
			final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();

			Properties properties = new Properties();
			properties.setProperty("process.path", "classpath:process/basic.bpmn20.xml");

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
	}
}
