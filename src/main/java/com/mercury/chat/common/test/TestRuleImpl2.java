package com.mercury.chat.common.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestRuleImpl2 implements TestRule {
	static final Logger logger = LogManager.getLogger(TestRuleImpl2.class);
	private ConfigurableApplicationContext context;
	private final String[] locations;
	private final Object target;

	public TestRuleImpl2(String[] locations, Object target) {
		this.locations = locations;
		this.target = target;
	}

	private static ThreadLocal<ConfigurableApplicationContext> threadLocalVar = new ThreadLocal<ConfigurableApplicationContext>();
	
	@Override
	public Statement apply(Statement base, Description description) {
		if (threadLocalVar.get() == null) {
			context = new ClassPathXmlApplicationContext(this.getLocations());
			threadLocalVar.set(context);
			System.out.println("Thread id = " + Thread.currentThread().getId() + ", name = " + Thread.currentThread().getName());
		} else {
			this.context = threadLocalVar.get();
		}
		context.getAutowireCapableBeanFactory().autowireBean(this.getTarget());
		context.start();
		Statement testStatement = base;
		testStatement = new TestStatement2(getContext(), base, description);
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
