package com.mercury.chat;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Lo4jTest {
	
	static final Logger logger = LogManager.getLogger(Lo4jTest.class);
	
	@Test
	public void testLog(){
		logger.log(Level.INFO, "test log");
	}
	
	
}
