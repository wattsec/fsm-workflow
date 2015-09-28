package com.tamplan.fsmWorkflow.core;

import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.io.Resources;
import com.tamplan.fsmWorkflow.core.domain.Workflow;
import com.tamplan.fsmWorkflow.core.domain.WorkflowRuntimeContext;
import com.tamplan.fsmWorkflow.core.domain.WorkflowVersion;
import com.tamplan.fsmWorkflow.core.service.ScmFsm;
import com.tamplan.fsmWorkflow.core.service.WorkflowProvisioningService;
import com.tamplan.fsmWorkflow.core.service.WorkflowRuntimeService;
import com.tamplan.fsmWorkflow.core.service.exception.IllegalStateTransitionException;
public class WorkflowTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction t;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("workflow_test");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		Workflow workflow = new Workflow();
		workflow.setName("W#1");
		workflow.setTargetEntity("Entity1");
		
		em.persist(workflow);
		
		WorkflowVersion wv = new WorkflowVersion();
		wv.setWorkflow(workflow);
		wv.setContent(loadSampleFsm());
		wv.setVersion(1);
		
		em.persist(wv);
		
		em.close();
		t.commit();
		
	}
	
	private static String loadSampleFsm() throws Exception{
		String str = Resources.toString(ScmFsm.class.getResource("/sample.xml"), 
				Charset.forName("UTF-8"));
		
		return str;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	@Before
	public void setUp() throws Exception {
		em = emf.createEntityManager();
		t = em.getTransaction();
		t.begin();
		
		
	}
	
	@After
	public void tearDown() throws Exception {
		em.close();
		t.commit();
	}
	
	@Test
	public void shouldRunWorkflow() {
        WorkflowProvisioningService provisioningService = new WorkflowProvisioningService();
        provisioningService.setEm(em);
        
        WorkflowRuntimeService workflowRuntimeService = new WorkflowRuntimeService();
        workflowRuntimeService.setEm(em);
        
        WorkflowRuntimeContext workflowRuntimeContext = new WorkflowRuntimeContext();
        workflowRuntimeContext.setTargetEntityId("1");
        workflowRuntimeContext.setWorkflowVersion(provisioningService.findWorkflowVersionBy("W#1", 1));

        try {
            workflowRuntimeService.handleEvent(provisioningService,
            		workflowRuntimeContext, "Entity1", "event1");
            
            assertTrue(false);
        }catch(IllegalStateTransitionException e) {
            assertTrue(true);

        }

        workflowRuntimeService.handleEvent(provisioningService,
        		workflowRuntimeContext, "Entity1", "atm.connected");
	}
	
}
