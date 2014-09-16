package com.tamplan.fsmWorkflow.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="WORKFLOW_VERSION")
public class WorkflowVersion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional=false)
	private Workflow workflow;
	
	@Column(name="version", nullable = false)
	private Integer version;
	
	@Column(name="content", nullable = false)
	@Lob
	private String content;
	
	

	@Override
	public String toString() {
		return "WorkflowVersion [id=" + id + ", workflow=" + workflow
				+ ", version=" + version + ", content=" + content + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
