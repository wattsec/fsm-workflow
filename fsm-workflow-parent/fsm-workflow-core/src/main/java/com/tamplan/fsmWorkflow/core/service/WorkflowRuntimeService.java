package com.tamplan.fsmWorkflow.core.service;

import java.util.Date;

import javax.persistence.EntityManager;

import com.tamplan.fsmWorkflow.core.domain.WorkflowProcess;
import com.tamplan.fsmWorkflow.core.domain.WorkflowProcessStatus;
import com.tamplan.fsmWorkflow.core.domain.WorkflowRuntimeContext;
import com.tamplan.fsmWorkflow.core.domain.WorkflowVersion;
import com.tamplan.fsmWorkflow.core.service.exception.IllegalStateTransitionException;

public class WorkflowRuntimeService {
	
	private EntityManager em;
	
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public void handleEvent(WorkflowProvisioningService provisioningService, 
			WorkflowRuntimeContext runtimeContext, String entityName, 
			String event) {
		
		String entityId = runtimeContext.getTargetEntityId();
		
		if ( runtimeContext.getWorkflowVersion() == null ) {
			throw new IllegalStateException("No workflow found for entity=" + 
					entityName + " id=" + entityId + " event=" + event);
		}
		
		String targetEntity = runtimeContext.getWorkflowVersion().getWorkflow().getTargetEntity();
		
		if ( !targetEntity.equals(entityName)) {
			throw new IllegalStateException("No suitable workflow found for entity=" + 
					entityName + " id=" + entityId + " event=" + event + ", existing workflow is for the entity=" + targetEntity);
		}
		
		WorkflowVersion workflowVersion = runtimeContext.getWorkflowVersion();
		
		ScmFsm scmFsm = findFsmByWorkflowVersion(workflowVersion);
		
		WorkflowProcess workflowProcess = provisioningService.findWorkflowProcessByEntity(entityName, entityId);
		
		if ( workflowProcess == null ) {
			initNewProcess(runtimeContext, entityId, scmFsm.getCurrentStatusId());
			workflowProcess = provisioningService.findWorkflowProcessByEntity(entityName, entityId);
		}
		
		if ( workflowProcess.isFinished() ) {
			throw new IllegalStateException("Workflow already finished, process id=" + 
					workflowProcess.getId() + " can't handle event=" + event);
		}
		
		scmFsm.setCurrentState(workflowProcess.getStatus());
		boolean isFinal = scmFsm.fireEvent(event);

		if ( !scmFsm.isStateTransitionObserved()) {
			IllegalStateTransitionException e = new IllegalStateTransitionException();
			e.setEntity(entityName);
			e.setCurrentState(scmFsm.getCurrentStatusId());
			e.setEntityId(entityId);
			e.setEvent(event);
			
			throw e;
		}

		workflowProcess.setStatus(scmFsm.getCurrentStatusId());
		if ( isFinal ) {
			workflowProcess.setProcessStatus(WorkflowProcessStatus.FINISHED.getStatus());
		}
		
	}
	
	
	private void initNewProcess(WorkflowRuntimeContext runtimeContext,
			String entityId, String status) {
		Date now = new Date();
		
		WorkflowProcess workflowProcess = new WorkflowProcess();
		workflowProcess.setDateCreated(now);
		workflowProcess.setDateUpdated(now);
		workflowProcess.setProcessStatus(WorkflowProcessStatus.LIVE.getStatus());
		workflowProcess.setStatus(status);
		workflowProcess.setTargetEntityId(entityId);
		workflowProcess.setWorkflowVersion(runtimeContext.getWorkflowVersion());
		
		em.persist(workflowProcess);
	}
	
	
	//TODO pooling, cache?
	private ScmFsm findFsmByWorkflowVersion(WorkflowVersion workflowVersion) {
		ScmFsm scmFsm = new ScmFsm(workflowVersion.getContent());
		return scmFsm;
	}
	
}
