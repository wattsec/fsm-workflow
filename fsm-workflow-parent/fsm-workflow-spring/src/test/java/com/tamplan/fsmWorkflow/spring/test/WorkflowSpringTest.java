package com.tamplan.fsmWorkflow.spring.test;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.io.Resources;
import com.tamplan.fsmWorkflow.core.domain.Workflow;
import com.tamplan.fsmWorkflow.core.domain.WorkflowVersion;
import com.tamplan.fsmWorkflow.core.service.ScmFsm;
import com.tamplan.fsmWorkflow.spring.WorkflowSpringService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class WorkflowSpringTest
{

	private static Log logger = LogFactory.getLog(WorkflowSpringTest.class);
	
	@Autowired
	private SampleWorkflowService sampleWorkflowService;
	
	@Autowired
	private WorkflowSpringService workflowSpringService;

	static String sampleScm;
	boolean workflowExists;
	
	@BeforeClass
	public static void beforeAllTests() {
		sampleScm = loadSampleFsm();
	}
	
	private static String loadSampleFsm() {
		String str;
		try {
			str = Resources.toString(ScmFsm.class.getResource("/sample.xml"), 
					Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return str;
	}
	
	@Before
	public void beforeTest() {
		if ( workflowExists ) {
			return;
		}
		
		Workflow workflow = workflowSpringService.findWorkflowByName("w1");
		if ( workflow == null ) {
			workflow = workflowSpringService.addWorkflow("w1", "TargetEntity", sampleScm);			
			
			workflowExists  = true;
		}
	}

    @Test
    public void shouldInvokeWorkflowService()
    {
    	sampleWorkflowService.startProcess();
    }
}
