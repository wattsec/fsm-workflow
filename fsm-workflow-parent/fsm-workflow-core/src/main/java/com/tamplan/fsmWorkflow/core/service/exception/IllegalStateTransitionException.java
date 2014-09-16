package com.tamplan.fsmWorkflow.core.service.exception;

public class IllegalStateTransitionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String currentState;
	private String event;
	private String entity;
	private String entityId;
	
	public IllegalStateTransitionException() {
	}
	
	@Override
	public String getMessage() {
		return toString();
	}

	@Override
	public String toString() {
		return "IllegalStateTransitionException [currentState=" + currentState
				+ ", event=" + event + ", entity=" + entity + ", entityId="
				+ entityId + "]";
	}



	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	public void setEntityId(String entityId){
		this.entityId = entityId;
	}
}
