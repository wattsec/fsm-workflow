package com.tamplan.fsmWorkflow.core.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.scxml.SCXMLListener;
import org.apache.commons.scxml.model.Transition;
import org.apache.commons.scxml.model.TransitionTarget;

public class ScmListener implements SCXMLListener{

	private final static Log logger = LogFactory.getLog(ScmListener.class);

	private boolean stateTransitionObserved;
	
	@Override
	public void onEntry(TransitionTarget state) {
		if ( logger.isInfoEnabled()) {
			logger.info("On entry state:" + state.getId());
		}
	}

	
	@Override
	public void onExit(TransitionTarget state) {
		if( logger.isInfoEnabled()) {
			logger.info("On exit state:" + state.getId());
		}
	}

	
	@Override
	public void onTransition(TransitionTarget from, TransitionTarget to,
			Transition transition) {
		
		if ( logger.isInfoEnabled()) {
			logger.info("On transition from state:" + from.getId() + " to state:" + to.getId());
		}
		
		stateTransitionObserved = true;
	}
	
	public void clear() {
		stateTransitionObserved = false;
	}
	
	public boolean isStateTransitionObserved() {
		return stateTransitionObserved;
	}

}
