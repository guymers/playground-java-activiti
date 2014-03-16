package me.guymer.activiti.process;

import java.util.Properties;

import javax.inject.Inject;

import me.guymer.activiti.config.Config;
import me.guymer.activiti.groups.Groups;
import me.guymer.activiti.process.complex.ComplexProcess;
import me.guymer.activiti.process.complex.ComplexProcessService;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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
public class ComplexProcessTest {

	private static final int THRESHOLD = 100;

	@Configuration
	@Import(Config.class)
	@PropertySource("classpath:properties/config.properties")
	static class ContextConfiguration {

		@Bean
		public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
			final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();

			Properties properties = new Properties();
			properties.setProperty("process.path", "classpath:process/complex.bpmn20.xml");

			propertySourcesPlaceholderConfigurer.setProperties(properties);
			propertySourcesPlaceholderConfigurer.setLocalOverride(true);

			return propertySourcesPlaceholderConfigurer;
		}
	}

	@Inject
	private RuntimeService runtimeService;

	@Inject
	private TaskService taskService;

	@Inject
	private Groups groups;

	@Inject
	private ComplexProcessService complexProcessService;

	@Test
	public void testComplexUnderThresholdProcess() {
		ComplexProcess complexProcess = new ComplexProcess();
		complexProcess.setInformation("info");

		String processInstanceId = complexProcessService.start(complexProcess);

		// task 1
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();

		Assert.assertNotNull(task);
		Assert.assertEquals("Complex User Task 1", task.getName());

		Task userTask = taskService.createTaskQuery().taskCandidateUser(groups.getUser()).singleResult();
		Assert.assertEquals(task.getId(), userTask.getId());

		complexProcess.setAmount(THRESHOLD - 1);

		complexProcessService.complete(task, complexProcess);

		// task 2
		Task task2 = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();

		Assert.assertNotNull(task2);
		Assert.assertEquals("Complex User Task 2", task2.getName());

		Task userTask2 = taskService.createTaskQuery().taskCandidateUser(groups.getLeader()).singleResult();
		Assert.assertEquals(task2.getId(), userTask2.getId());

		complexProcessService.complete(task2, complexProcess);

		// process should be complete
		Assert.assertEquals(0, runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).count());
	}

	@Test
	public void testComplexNotUnderThresholdProcess() {
		ComplexProcess complexProcess = new ComplexProcess();
		complexProcess.setInformation("info");

		String processInstanceId = complexProcessService.start(complexProcess);

		// task 1
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();

		Assert.assertNotNull(task);
		Assert.assertEquals("Complex User Task 1", task.getName());

		Task userTask = taskService.createTaskQuery().taskCandidateUser(groups.getUser()).singleResult();
		Assert.assertEquals(task.getId(), userTask.getId());

		complexProcess.setAmount(THRESHOLD + 1);

		complexProcessService.complete(task, complexProcess);

		// task 3
		Task task3 = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();

		Assert.assertNotNull(task3);
		Assert.assertEquals("Complex User Task 3", task3.getName());

		Task userTask2 = taskService.createTaskQuery().taskCandidateUser(groups.getManager()).singleResult();
		Assert.assertEquals(task3.getId(), userTask2.getId());

		complexProcessService.complete(task3, complexProcess);

		// process should be complete
		Assert.assertEquals(0, runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).count());
	}
}
