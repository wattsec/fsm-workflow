package com.tamplan.fsmWorkflow.core.domain;

import java.util.HashMap;
import java.util.Map;

import com.tamplan.fsmWorkflow.core.domain.exception.TargetEntityAlreadySetException;

public class WorkflowRuntimeContext {
	
	private WorkflowVersion workflowVersion;
	private Map<String, String> contextValues;
	private String targetEntityId;
	
	private final static ThreadLocal<WorkflowRuntimeContext> threadLocal;
	static {
		threadLocal = new ThreadLocal<WorkflowRuntimeContext>();
	}
	
	private WorkflowRuntimeContext() {
		contextValues = new HashMap<String, String>();
	}
	
	public static WorkflowRuntimeContext getCurrentContext() {
		return threadLocal.get();
	}
	
	public static void clearContext() {
		threadLocal.set(null);
	}
	
	public static WorkflowRuntimeContext initContext() {
		threadLocal.set(new WorkflowRuntimeContext());
		
		return getCurrentContext();
	}
	
	public void setTargetEntityId(String targetEntityId) {
		if ( this.targetEntityId != null ) {
			throw new TargetEntityAlreadySetException(this.targetEntityId, targetEntityId);
		}
		
		//TODO check if it is legal to set 
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
