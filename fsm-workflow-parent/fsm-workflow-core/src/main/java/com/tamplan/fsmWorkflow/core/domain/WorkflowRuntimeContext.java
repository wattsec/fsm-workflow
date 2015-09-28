package com.tamplan.fsmWorkflow.core.domain;

import java.util.HashMap;
import java.util.Map;

import com.tamplan.fsmWorkflow.core.domain.exception.TargetEntityAlreadySetException;

public class WorkflowRuntimeContext {
	
	private WorkflowVersion workflowVersion;
	private Map<String, String> contextValues;
	private String targetEntityId;
	
	public WorkflowRuntimeContext() {
		contextValues = new HashMap<String, String>();
	}
	
	public void setTargetEntityId(String targetEntityId) {
		if ( this.targetEntityId != null ) {
			throw new TargetEntityAlreadySetException(this.targetEntityId, targetEntityId);
		}
		
		this.targetEntityId = targetEntityId;
	}
	
	public String getTargetEntityId() {
		return targetEntityId;
	}
	
	public void setWorkflowVersion(WorkflowVersion workflowVersion) {
		this.workflowVersion = workflowVersion;
	}
	
	public WorkflowVersion getWorkflowVersion() {
		return workflowVersion;
	}
	
	public void addContextValue(String key, String value) {
		
	}
	
	public Map<String, String> getContextValues() {
		return contextValues;
	}
	
}
