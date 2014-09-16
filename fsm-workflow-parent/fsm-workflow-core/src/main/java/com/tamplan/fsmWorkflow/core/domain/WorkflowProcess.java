package com.tamplan.fsmWorkflow.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
/**
 * 
 * @author erdinckocaman
 *
 */

@Entity
@Table(name="WORKFLOW_PROCESS", indexes={@Index(columnList="target_entity_id")})
public class WorkflowProcess {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="workflow_version_id")
	private WorkflowVersion workflowVersion;
	
	@Version
	@Column(name="version")
	private Long version;
	
	@Column(name="target_entity_id", nullable = false)
	private String targetEntityId;
	
	@Column(name="date_created", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	@Column(name="date_updated", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdated;
	
	// Application related status
	@Column(name="status", nullable = false)
	private String status;
	
	// System related status, like live, stopped
	@Column(name="process_status", nullable = false)
	private String processStatus;
	
	
	// -------------------------------------------------------------------------
	// Getters/Setters
	// -------------------------------------------------------------------------


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WorkflowVersion getWorkflowVersion() {
		return workflowVersion;
	}

	public void setWorkflowVersion(WorkflowVersion workflowVersion) {
		this.workflowVersion = workflowVersion;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getTargetEntityId() {
		return targetEntityId;
	}

	public void setTargetEntityId(String targetEntityId) {
		this.targetEntityId = targetEntityId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	// -------------------------------------------------------------------------
	// Custom methods
	// -------------------------------------------------------------------------

	public boolean isFinished() {
		return WorkflowProcessStatus.FINISHED.getStatus().equals(getProcessStatus());
	}
	
	
	
}
