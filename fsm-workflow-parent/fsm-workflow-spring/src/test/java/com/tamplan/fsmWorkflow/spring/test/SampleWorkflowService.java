package com.tamplan.fsmWorkflow.spring.test;

import com.tamplan.fsmWorkflow.spring.WorkflowEvent;
import com.tamplan.fsmWorkflow.spring.WorkflowService;

@WorkflowService(workflowName="w1")
public class SampleWorkflowService {

	
	@WorkflowEvent(event="atm.connected")
	public void startProcess() {
		
	}
}
