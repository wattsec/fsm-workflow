package com.tamplan.fsmWorkflow.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="WORKFLOW_PROCESS_CONTEXT")
public class WorkflowProcessContext {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name="workflow_process_history_id")
	private WorkflowProcessHistory workflowProcessHistory;
	
	@Column(name = "key", nullable = false)
	private String key;
	
	@Column(name = "value")
	private String value;
	
}
