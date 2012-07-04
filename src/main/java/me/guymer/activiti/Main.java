package me.guymer.activiti;

import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
		try {
			RepositoryService repositoryService = context.getBean(RepositoryService.class);
			RuntimeService runtimeService = context.getBean(RuntimeService.class);
			TaskService taskService = context.getBean(TaskService.class);
			FormService formService = context.getBean(FormService.class);
			
			
			//Deployment deployment = repositoryService.createDeployment().addClasspathResource("process.bpmn20.xml").deploy();
			
			//ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("test-process");
			
			//taskService.claim("2107", "1");
			
			List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("1").list();
			
			for (Task t : tasks) {
				LOGGER.info("{} {}", t.getId(), t.getName());
			}
			
			List<Task> userTasks = taskService.createTaskQuery().taskAssignee("1").list();
			
			for (Task t : userTasks) {
				LOGGER.info("{} {}", t.getId(), t.getName());
				
				TaskFormData taskFormData = formService.getTaskFormData(t.getId());
				
				for (FormProperty formProperty : taskFormData.getFormProperties()) {
					LOGGER.info("formProperty", formProperty.getId());
				}
			}
			
			//taskService.complete(task.getId());
			
		} finally {
			context.close();
		}
	}

}
