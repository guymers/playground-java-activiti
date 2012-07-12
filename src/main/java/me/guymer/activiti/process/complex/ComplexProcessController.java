package me.guymer.activiti.process.complex;

import javax.inject.Inject;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/process/complex-process")
public class ComplexProcessController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComplexProcessController.class);
	
	@Inject
	private ComplexProcessService complexProcessService;
	
	@Inject
	private TaskService taskService;
	
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public String startPost(@RequestBody MultiValueMap<String, String> map, Model model) {
		
		ComplexProcess complexProcess = mapToComplexProcess(map);
		
		complexProcessService.start(complexProcess);
		
		return "process/start";
	}
	
	@RequestMapping(value = "/{taskId}/complete", method = RequestMethod.POST)
	public String completeTaskPost(@PathVariable("taskId") String taskId, @RequestBody MultiValueMap<String, String> map, Model model) {
		LOGGER.info("complete_task '{}'", taskId);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		if (task == null) {
			model.addAttribute("error", "task does not exist");
			
			return "error";
		}
		
		ComplexProcess complexProcess = mapToComplexProcess(map);
		
		complexProcessService.complete(task, complexProcess);
		
		model.addAttribute("task", task);
		
		return "process/complete";
	}
	
	private ComplexProcess mapToComplexProcess(MultiValueMap<String, String> map) {
		ComplexProcess complexProcess = new ComplexProcess();
		complexProcess.setInformation(map.getFirst("information"));
		
		String amount = map.getFirst("amount");
		amount = (amount == null || amount.compareTo("") == 0) ? "0" : amount;
		
		complexProcess.setAmount(Integer.parseInt(amount));
		
		return complexProcess;
	}
	
}
