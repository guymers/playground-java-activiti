package me.guymer.activiti.web;

import java.util.List;

import javax.inject.Inject;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class IndexController {

	@Inject
	private RepositoryService repositoryService;

	@Inject
	private IdentityService identityService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		ProcessDefinitionQuery processDefinitionQuery = createProcessDefinitionQuery();
		processDefinitionQuery.orderByProcessDefinitionName();
		processDefinitionQuery.asc();

		List<ProcessDefinition> processes = processDefinitionQuery.list();

		model.addAttribute("processes", processes);

		List<User> users = identityService.createUserQuery().orderByUserId().asc().list();

		model.addAttribute("users", users);

		return "index";
	}

	private ProcessDefinitionQuery createProcessDefinitionQuery() {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		processDefinitionQuery.active();
		processDefinitionQuery.latestVersion();

		return processDefinitionQuery;
	}
}
