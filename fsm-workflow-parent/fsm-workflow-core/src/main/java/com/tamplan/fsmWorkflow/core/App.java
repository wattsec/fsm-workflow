package com.tamplan.fsmWorkflow.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.tamplan.fsmWorkflow.core.service.WorkflowProvisioningService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	/*
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("workflow");
        EntityManager em = emf.createEntityManager();
        
        EntityTransaction t = em.getTransaction();
        
        t.begin();
        
        WorkflowProvisioningService provisioningService = new WorkflowProvisioningService();
        provisioningService.setEm(em);
        provisioningService.addWorkflow("Workflow #1", "tr.com.hede", "Workflow content");
        
        em.close();
        t.commit();
        */
    	
        
        System.exit(0);
        
        
    }
}
