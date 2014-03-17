package me.guymer.activiti.web;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Controller
@RequestMapping(value = "/history")
public class HistoryController {

	@Inject
	private RuntimeService runtimeService;

	@Inject
	private HistoryService historyService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		/*List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();

		model.addAttribute("processes", list);*/

		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
		historicProcessInstanceQuery.unfinished();
		historicProcessInstanceQuery.orderByProcessInstanceEndTime();
		historicProcessInstanceQuery.desc();

		List<HistoricProcessInstance> unfinishedProcesses = historicProcessInstanceQuery.list();

		model.addAttribute("unfinishedProcesses", unfinishedProcesses);

		HistoricProcessInstanceQuery historicProcessInstanceQuery2 = historyService.createHistoricProcessInstanceQuery();
		historicProcessInstanceQuery2.finished();
		historicProcessInstanceQuery2.orderByProcessInstanceEndTime();
		historicProcessInstanceQuery2.desc();

		List<HistoricProcessInstance> finishedProcesses = historicProcessInstanceQuery2.list();

		model.addAttribute("finishedProcesses", finishedProcesses);

		return "history/list";
	}

	@RequestMapping(value = "/process/instance/{processInstanceId}", method = RequestMethod.GET)
	public String process(@PathVariable String processInstanceId, Model model) {

		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
		historicProcessInstanceQuery.processInstanceId(processInstanceId);

		HistoricProcessInstance historicProcessInstance = historicProcessInstanceQuery.singleResult();

		model.addAttribute("historicProcessInstance", objectToString(historicProcessInstance));
		model.addAttribute("tasks", objectToString(getTasks(processInstanceId)));
		model.addAttribute("activities", objectToString(getActivities(processInstanceId)));
		model.addAttribute("variables", objectToString(getVariables(processInstanceId)));

		return "history/process/instance";
	}

	private String objectToString(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writerWithDefaultPrettyPrinter = mapper.writerWithDefaultPrettyPrinter();
		String writeValueAsString = null;

		try {
			writeValueAsString = writerWithDefaultPrettyPrinter.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		writeValueAsString = writeValueAsString.replaceAll("\n", "<br/>");

		return writeValueAsString;
	}

	private List<HistoricTaskInstance> getTasks(String processInstanceId) {
		List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricTaskInstanceStartTime()
				.asc()
				.list();

		return taskList;
	}

	private List<HistoricActivityInstance> getActivities(String processInstanceId) {
		List<HistoricActivityInstance> activityList = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricActivityInstanceStartTime()
				.asc()
				.list();

		return activityList;
	}

	private List<HistoricDetail> getVariables(String processInstanceId) {
		List<HistoricDetail> variableList = historyService.createHistoricDetailQuery()
				.processInstanceId(processInstanceId)
				.variableUpdates()
				.orderByTime()
				.desc()
				.list();

		return variableList;
	}
}
