package me.guymer.activiti.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/process")
public class ProcessController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);

	@Inject
	private RepositoryService repositoryService;

	@Inject
	private RuntimeService runtimeService;

	@Inject
	private TaskService taskService;

	@Inject
	private FormService formService;

	@RequestMapping(value = "/start/{processKey}", method = RequestMethod.GET)
	public String startGet(@PathVariable String processKey, Model model) {
		LOGGER.info("processing '{}'", processKey);

		ProcessDefinition processDefinition = getProcessDefinitionByKey(processKey);

		if (processDefinition == null) {
			model.addAttribute("error", "process does not exist");

			return "error";
		}

		model.addAttribute("process", processDefinition);

		String id = processDefinition.getId();
		StartFormData startFormData = formService.getStartFormData(id);
		String formKey = startFormData.getFormKey();

		if (formKey == null) {
			return startProcess(processDefinition);
		}

		return "process/" + formKey;
	}

	@RequestMapping(value = "/start/{processKey}", method = RequestMethod.POST)
	public String startPost(@PathVariable String processKey, HttpServletRequest request, Model model) {
		LOGGER.info("starting '{}'", processKey);

		Map<?, ?> parameters = request.getParameterMap();

		for (Object key : parameters.keySet()) {
			Object value = parameters.get(key);

			LOGGER.info("parameter '{}' - '{}'", key, value);
		}

		ProcessDefinition processDefinition = getProcessDefinitionByKey(processKey);

		if (processDefinition == null) {
			model.addAttribute("error", "process does not exist");

			return "error";
		}

		model.addAttribute("process", processDefinition);

		return startProcess(processDefinition);
	}

	private ProcessDefinition getProcessDefinitionByKey(String key) {
		ProcessDefinitionQuery processDefinitionQuery = createProcessDefinitionQuery();
		processDefinitionQuery.processDefinitionKey(key);

		List<ProcessDefinition> list = processDefinitionQuery.list();

		if (list.size() != 1) {
			return null;
		}

		return processDefinitionQuery.singleResult();
	}

	private ProcessDefinitionQuery createProcessDefinitionQuery() {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		processDefinitionQuery.active();
		processDefinitionQuery.latestVersion();

		return processDefinitionQuery;
	}

	private String startProcess(ProcessDefinition processDefinition) {
		Map<String, Object> variables = new HashMap<String, Object>();

		return startProcess(processDefinition, variables);
	}

	private String startProcess(ProcessDefinition processDefinition, Map<String, Object> variables) {
		variables.put("location", 1);

		runtimeService.startProcessInstanceByKey(processDefinition.getKey(), variables);

		return "process/start";
	}

	@RequestMapping(value = "/complete_task/{taskId}", method = RequestMethod.GET)
	public String completeTaskGet(@PathVariable String taskId, Model model) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		if (task == null) {
			model.addAttribute("error", "task does not exist");

			return "error";
		}

		model.addAttribute("task", task);

		TaskFormData taskFormData = formService.getTaskFormData(task.getId());
		String formKey = taskFormData.getFormKey();

		if (formKey == null) {
			taskService.complete(task.getId());

			return "process/complete";
		}

		return "process/" + formKey;
	}

	@RequestMapping(value = "/complete_task/{taskId}", method = RequestMethod.POST)
	public String completeTaskPost(@PathVariable String taskId, Model model) {
		LOGGER.info("complete_task '{}'", taskId);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		if (task == null) {
			model.addAttribute("error", "task does not exist");

			return "error";
		}

		model.addAttribute("task", task);

		taskService.complete(task.getId());

		return "process/complete";
	}
}
