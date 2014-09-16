package com.tamplan.fsmWorkflow.core.service;

import java.io.ByteArrayInputStream;
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.scxml.SCXMLExecutor;
import org.apache.commons.scxml.env.AbstractStateMachine;
import org.apache.commons.scxml.env.SimpleErrorHandler;
import org.apache.commons.scxml.env.jexl.JexlContext;
import org.apache.commons.scxml.env.jexl.JexlEvaluator;
import org.apache.commons.scxml.io.SCXMLParser;
import org.apache.commons.scxml.model.ModelException;
import org.apache.commons.scxml.model.SCXML;
import org.apache.commons.scxml.model.TransitionTarget;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

public class ScmFsm extends AbstractStateMachine {
	
	private final static Log logger = LogFactory.getLog(ScmFsm.class);
	
	private ScmErrorReporter errorReporter;
	private ScmListener scmListener;
	
	public ScmFsm(String fsmContent) {
		 super(readFromString(fsmContent), new JexlContext(), new JexlEvaluator());
		 
		 errorReporter = new ScmErrorReporter();
		 scmListener = new ScmListener();
		 
		 getEngine().setErrorReporter(errorReporter);
		 getEngine().addListener(getEngine().getStateMachine(), scmListener);
	}
	
	
	private static SCXML readFromString(String content) {
		try {
			
			ByteArrayInputStream bis = new ByteArrayInputStream(
					content.getBytes("UTF-8"));
			
			ErrorHandler errorHandler = new SimpleErrorHandler();
			
			Digester parser = SCXMLParser.newInstance();
			parser.setLogger(logger);
			parser.setErrorHandler(errorHandler);
			
			SCXML scxml = SCXMLParser.parse(new InputSource(bis), errorHandler);
			
			return scxml;
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}

	}

	
	@Override
	public boolean invoke(String methodName) {
		logger.info("invoked method:" + methodName);
		return true;
	}
	
	@Override
	public boolean fireEvent(String event) {
		try {
			scmListener.clear();
			return super.fireEvent(event);
		} finally {

		}
	}
	
	public boolean isStateTransitionObserved() {
		return scmListener.isStateTransitionObserved();
	}

	public String getCurrentStatusId() {
		return ((TransitionTarget)getEngine().getCurrentStatus().getStates().iterator().next()).getId();
	}

	
	public void setCurrentState(final String id) throws IllegalArgumentException{
		SCXMLExecutor exec = getEngine();
		
		try {
			exec.reset();
		} catch (ModelException me) {
			throw new RuntimeException("Provided SCXMLExecutor "
					+ "instance cannot be reset.", me);
		}

		TransitionTarget active = (TransitionTarget) exec.getStateMachine()
				.getTargets().get(id);
		
		if (active == null) {
			throw new IllegalArgumentException("No target with id '" + id
					+ "' present in state machine.");
		}
		
		Set current = exec.getCurrentStatus().getStates();
		current.clear();
		current.add(active);
	}
	
}
