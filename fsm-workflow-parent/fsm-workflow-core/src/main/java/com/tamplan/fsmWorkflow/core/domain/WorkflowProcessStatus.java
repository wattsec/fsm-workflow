package com.tamplan.fsmWorkflow.core.domain;

public enum WorkflowProcessStatus {
	CREATED("C"), LIVE("L"), FINISHED("F"), PAUSED("P");
	
	private String status;

	private WorkflowProcessStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
