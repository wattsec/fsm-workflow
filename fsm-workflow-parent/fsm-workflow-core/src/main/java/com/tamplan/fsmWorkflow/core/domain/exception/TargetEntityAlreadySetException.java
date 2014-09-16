package com.tamplan.fsmWorkflow.core.domain.exception;

public class TargetEntityAlreadySetException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String currentId;
	private String candidateId;

	public TargetEntityAlreadySetException(String currentId,
			String candidateId) {
		
		this.currentId = currentId;
		this.candidateId = candidateId;
		
	}
	
	public String getCandidateId() {
		return candidateId;
	}
	
	public String getCurrentId() {
		return currentId;
	}



}
