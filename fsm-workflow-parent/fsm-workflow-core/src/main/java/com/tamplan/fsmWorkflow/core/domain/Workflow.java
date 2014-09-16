package com.tamplan.fsmWorkflow.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;


@Entity
@Table(name="WORKFLOW", indexes={@Index(columnList = "name")})
public class Workflow {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name", nullable = false)
	private String name;
	
	
	@Column(name="target_entity", nullable = false)
	private String targetEntity;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTargetEntity() {
		return targetEntity;
	}


	public void setTargetEntity(String targetEntity) {
		this.targetEntity = targetEntity;
	}
	
	
	
}
