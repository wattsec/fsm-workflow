package com.tamplan.fsmWorkflow.spring;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tamplan.fsmWorkflow.core.domain.Workflow;
import com.tamplan.fsmWorkflow.core.domain.WorkflowVersion;
import com.tamplan.fsmWorkflow.core.service.WorkflowProvisioningService;

@Transactional
@Service
public class WorkflowSpringService {

	@PersistenceContext
	private EntityManager em;
	

	public Workflow addWorkflow(String name, String targetEntity, String workflowContent) {
		return getWorkflowProvisioningService().addWorkflow(name, targetEntity, workflowContent);

	}
	
	public Workflow findWorkflowByName(String name) {
		return getWorkflowProvisioningService().findWorkflowByName(name);

	}
	
	public WorkflowVersion addWorkflowVersion(Workflow workflow, String content, Integer version) {
		return getWorkflowProvisioningService().addWorkflowVersion(workflow, content, version);
	}
	
	public WorkflowVersion findWorkflowVersionBy(String name, String version) {
		return getWorkflowProvisioningService().findWorkflowVersionBy(name, Integer.valueOf(version));
	}
	
	private WorkflowProvisioningService getWorkflowProvisioningService() {
		WorkflowProvisioningService provisioningService = new WorkflowProvisioningService();
		provisioningService.setEm(em);
		
		return provisioningService;
	}
	
}
