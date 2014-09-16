package com.tamplan.fsmWorkflow.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "WORKFLOW_PROCESS_HISTORY" )
public class WorkflowProcessHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="workflow_process_id")
	private WorkflowProcess workflowProcess;
	
	@Column(name="from_state", nullable=false)
	private String fromState;
	
	@Column(name="to_state", nullable=false)
	private String toState;
	
	@Column(name="date_created", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
}
