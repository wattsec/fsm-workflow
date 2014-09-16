package com.tamplan.fsmWorkflow.core.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.tamplan.fsmWorkflow.core.domain.Workflow;
import com.tamplan.fsmWorkflow.core.domain.WorkflowProcess;
import com.tamplan.fsmWorkflow.core.domain.WorkflowVersion;
import com.tamplan.fsmWorkflow.core.service.exception.WorkflowAlreadyExistsException;

public class WorkflowProvisioningService {
	
	private EntityManager em;
	
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public Workflow addWorkflow(String name, String targetEntity, String workflowContent) {
		checkNotNull(targetEntity);
		checkNotNull(workflowContent);
		checkNotNull(name);
		checkNotNull(em);

		if ( findWorkflowByName(name) != null ) {
			throw new WorkflowAlreadyExistsException(name);
		}
		
		Workflow w = new Workflow();
		w.setName(name);
		w.setTargetEntity(targetEntity);
		
		em.persist(w);
		
		WorkflowVersion wVersion = new WorkflowVersion();
		wVersion.setContent(workflowContent);
		wVersion.setVersion(1);
		wVersion.setWorkflow(w);
		
		em.persist(wVersion);
		
		return w;
	}
	
	public Workflow findWorkflowByName(String name) {
		String sql = "select w from Workflow w where w.name=:name";
		
		
		TypedQuery<Workflow> q = em.createQuery(sql, Workflow.class);
		
		q.setParameter("name", name);
		
		Workflow workflow = null;
		try {
			workflow = q.getSingleResult();

		}catch(NoResultException e) {
			
		}
		
		return workflow;
	}
	
	
	public WorkflowProcess findWorkflowProcessByEntity(String entity, String id) {
		checkNotNull(entity);
		checkNotNull(id);
		
		String sql = "select w from WorkflowProcess w where w.workflowVersion.workflow.targetEntity=:entity and w.targetEntityId=:id";
		
		TypedQuery<WorkflowProcess> q = em.createQuery(sql, WorkflowProcess.class);
		
		q.setParameter("entity", entity);
		q.setParameter("id", id);
		
		try {
			WorkflowProcess workflowProcess = q.getSingleResult();
			return workflowProcess;

		}catch(NoResultException e) {
			return null;
		}
		
	}
	
	public WorkflowVersion addWorkflowVersion(Workflow workflow, String content, Integer version) {
		WorkflowVersion wv = new WorkflowVersion();
		wv.setWorkflow(workflow);
		wv.setContent(content);
		wv.setVersion(version);
		
		em.persist(wv);
		
		
		return wv;
		
	}
	
	public WorkflowVersion findWorkflowVersionBy(String workflowName, Integer version) {
		checkNotNull(version);
		checkNotNull(workflowName);
		
		String sql = "select distinct(w) from WorkflowVersion w where w.workflow.name=:workflowName "
				+ "and w.version=:version";
		
		TypedQuery<WorkflowVersion> q = em.createQuery(sql, WorkflowVersion.class);
		
		q.setParameter("workflowName", workflowName);
		q.setParameter("version", version);
		
		List<WorkflowVersion> resultList = q.getResultList();
		
		if ( resultList == null || resultList.size() == 0 ) {
			return null;
		}
		
		if ( resultList.size() > 1 ) {
			throw new IllegalArgumentException("More than one record for workflow=" +
					workflowName + " and version=" + version);
		}
		
		return resultList.get(0);
				
	}
	
	
}
