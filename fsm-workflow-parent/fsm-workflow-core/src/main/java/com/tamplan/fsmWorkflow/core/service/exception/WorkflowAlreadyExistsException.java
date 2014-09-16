package com.tamplan.fsmWorkflow.core.service.exception;

public class WorkflowAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String workflowName;

	public WorkflowAlreadyExistsException(String workflowName) {
		this.workflowName = workflowName;
	}
	
	public String getWorkflowName() {
		return workflowName;
	}


}
