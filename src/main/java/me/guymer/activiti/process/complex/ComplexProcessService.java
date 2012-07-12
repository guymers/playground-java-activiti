package me.guymer.activiti.process.complex;

import javax.inject.Inject;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComplexProcessService {
	
	public static final String ID = "complex-process";
	
	@Inject
	private RepositoryService repositoryService;
	
	@Inject
	private RuntimeService runtimeService;
	
	@Inject
	private TaskService taskService;
	
	@Inject
	private FormService formService;
	
	@Inject
	private ComplexProcessDAO complexProcessDAO;
	
	@Transactional
	public void start(ComplexProcess complexProcess) {
		complexProcessDAO.create(complexProcess);
		
		String businessKey = Integer.toString(complexProcess.getId());
		
		runtimeService.startProcessInstanceByKey(ID, businessKey);
	}
	
	@Transactional
	public void complete(Task task, ComplexProcess complexProcess) {
		
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		processDefinitionQuery.active();
		processDefinitionQuery.processDefinitionId(task.getProcessDefinitionId());
		
		ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
		
		if (ID.compareTo(processDefinition.getKey()) != 0) {
			throw new IllegalArgumentException("task does not belong to process");
		}
		
		ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
		processInstanceQuery.active();
		processInstanceQuery.processInstanceId(task.getProcessInstanceId());
		
		ProcessInstance processInstance = processInstanceQuery.singleResult();
		
		String processBusinessKey = processInstance.getBusinessKey();
		int id = Integer.parseInt(processBusinessKey);
		
		complexProcess.setId(id);
		complexProcessDAO.update(complexProcess);
		
		taskService.complete(task.getId());
	}
	
	public boolean isUnderThreshold(DelegateExecution execution) {
		String processBusinessKey = execution.getProcessBusinessKey();
		int id = Integer.parseInt(processBusinessKey);
		
		ComplexProcess complexProcess = complexProcessDAO.get(id);
		
		return complexProcess.getAmount() < 100;
	}
	
}
