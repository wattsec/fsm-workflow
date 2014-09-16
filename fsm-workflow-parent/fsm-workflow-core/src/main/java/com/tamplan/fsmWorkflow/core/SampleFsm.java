package com.tamplan.fsmWorkflow.core;

import org.apache.commons.scxml.ErrorReporter;
import org.apache.commons.scxml.SCXMLListener;
import org.apache.commons.scxml.env.AbstractStateMachine;
import org.apache.commons.scxml.model.Transition;
import org.apache.commons.scxml.model.TransitionTarget;

public class SampleFsm extends AbstractStateMachine {

	public SampleFsm() {
		super(SampleFsm.class.getResource("/sample.xml"));
		
	}
	
	public void idle() {
		System.out.println("action: idle state");
	}
	
	public void loading() {
		System.out.println("action: loading state");
	}
	
	@Override
	public boolean invoke(String methodName) {
		System.out.println("invoke method=" + methodName);
		//return super.invoke(methodName);
		return true;
		
	}
	
	public static void main(String []args){
		SampleFsm sampleFsm = new SampleFsm();
		
		
		sampleFsm.getEngine().setErrorReporter(new ErrorReporter() {
			
			@Override
			public void onError(String errCode, String errDetail, Object errCtx) {
				System.out.println("err=" + errCode + ":" + errDetail);
				
			}
		});
		
		sampleFsm.getEngine().addListener(sampleFsm.getEngine().getStateMachine(), new SCXMLListener() {
			
			@Override
			public void onTransition(TransitionTarget from, TransitionTarget to,
					Transition transition) {
				System.out.println("on transition, from=" + from.getId() + " to=" + to.getId());
			}
			
			@Override
			public void onExit(TransitionTarget state) {
				System.out.println("on exit=" + state.getId());
				
			}
			
			@Override
			public void onEntry(TransitionTarget state) {
				System.out.println("on entry=" + state.getId());
			}
		});
		
		sampleFsm.fireEvent("atm.connected");

		//System.out.println(((TransitionTarget)sampleFsm.getEngine().getCurrentStatus().getStates().iterator().next()).getId());
		
	}
	
	
}
