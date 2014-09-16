package com.tamplan.fsmWorkflow.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tamplan.fsmWorkflow.core.domain.WorkflowRuntimeContext;
import com.tamplan.fsmWorkflow.core.domain.WorkflowVersion;

@Aspect
@Component
public class WorkflowServiceAspect {
	
	private final static Log logger = LogFactory.getLog(WorkflowServiceAspect.class);
	
	@Autowired
	private WorkflowSpringService workflowSpringService;

	@Before("@annotation(com.tamplan.fsmWorkflow.spring.WorkflowEvent)")
	public void logBefore(JoinPoint joinPoint) {
		logger.info("logBefore() is running, signature=" + joinPoint.getSignature().getName());		
		
		WorkflowService workflowServiceAnnot = joinPoint.getTarget().getClass().getAnnotation(WorkflowService.class);
		if ( workflowServiceAnnot == null ) {
			throw new IllegalStateException("Workflow event must be in a workflow context, event=" + joinPoint.getSignature().getName());
		}
		
		
		String workflowName = workflowServiceAnnot.workflowName();
		String workflowVersionStr = workflowServiceAnnot.version();
		
		if ( logger.isInfoEnabled()) {
			logger.info("workflow name=" + workflowName + " version=" + workflowVersionStr);
		}
		
		
		WorkflowVersion workflowVersion = workflowSpringService.findWorkflowVersionBy(workflowName, workflowVersionStr);
		
		if ( logger.isInfoEnabled()) {
			logger.info("workflow version found=" + workflowVersion);
		}
		
		WorkflowRuntimeContext context = WorkflowRuntimeContext.initContext();
		context.setWorkflowVersion(workflowVersion);
		
	}
	
	
	
}
