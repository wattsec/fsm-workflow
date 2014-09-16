package com.tamplan.fsmWorkflow.core.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.scxml.ErrorReporter;

public class ScmErrorReporter implements ErrorReporter{
	
	private String errCode;
	private Object errCtx;
	private String errDetail;
	
	private final static Log logger = LogFactory.getLog(ScmFsm.class);


	@Override
	public void onError(String errCode, String errDetail, Object errCtx) {
		this.errCode = errCode;
		this.errDetail = errDetail;
		this.errCtx = errCtx;
		
		logger.error("On error, err code=" + errCode + " err detail=" + errDetail + " err ctx=" + errCtx); 
	}

	public String getErrCode() {
		return errCode;
	}
	
	public Object getErrCtx() {
		return errCtx;
	}
	
	public String getErrDetail() {
		return errDetail;
	}
	
	public void clear() {
		errCode = errDetail = null;
		errCtx = null;
	}
	
}
