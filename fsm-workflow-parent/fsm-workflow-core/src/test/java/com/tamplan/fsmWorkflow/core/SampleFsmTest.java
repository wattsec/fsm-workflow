package com.tamplan.fsmWorkflow.core;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.io.Resources;
import com.tamplan.fsmWorkflow.core.service.ScmFsm;
import static org.junit.Assert.*;

public class SampleFsmTest {

	ScmFsm sampleFsm;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String str = null;
		try {
			str = Resources.toString(ScmFsm.class.getResource("/sample.xml"), 
					Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
		sampleFsm = new ScmFsm(str);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void shouldMakeTransitionWhenProperEventFired() {
		sampleFsm.fireEvent("atm.connected");
		assertEquals("loading", sampleFsm.getCurrentStatusId());
	}

}
