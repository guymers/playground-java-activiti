package me.guymer.activiti.web;

import java.util.List;

import javax.inject.Inject;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Inject
	private TaskService taskService;

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String users(@PathVariable String userId, Model model) {

		List<Task> availableTasks = taskService.createTaskQuery().taskCandidateUser(userId).list();
		List<Task> assignedTasks = taskService.createTaskQuery().taskAssignee(userId).list();
		List<Task> ownedTasks = taskService.createTaskQuery().taskOwner(userId).list();

		model.addAttribute("availableTasks", availableTasks);
		model.addAttribute("assignedTasks", assignedTasks);
		model.addAttribute("ownedTasks", ownedTasks);

		return "user";
	}

	@RequestMapping(value = "/{userId}/claim/{taskId}", method = RequestMethod.GET)
	public String claimTask(@PathVariable String userId, @PathVariable String taskId, Model model) {

		taskService.claim(taskId, userId);

		return "user/claim_task";
	}
}
