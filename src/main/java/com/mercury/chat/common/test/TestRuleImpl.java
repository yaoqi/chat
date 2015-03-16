package com.mercury.chat.common.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestRuleImpl implements TestRule {
	static final Logger logger = LogManager.getLogger(TestRuleImpl.class);
	private ConfigurableApplicationContext context;
	private final String[] locations;
	private final Object target;

	public TestRuleImpl(String[] locations, Object target) {
		this.locations = locations;
		this.target = target;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		context = new ClassPathXmlApplicationContext(this.getLocations());
		context.getAutowireCapableBeanFactory().autowireBean(this.getTarget());
		context.start();
		Statement testStatement = base;
		testStatement = new TestStatement(getContext(), base, description);
		return testStatement;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public String[] getLocations() {
		return locations;
	}

	public Object getTarget() {
		return target;
	}

}
