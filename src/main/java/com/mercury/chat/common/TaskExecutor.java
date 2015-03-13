package com.mercury.chat.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TaskExecutor {
	
	public ExecutorService taskExecutor = Executors.newFixedThreadPool(50);
	
	private static class SingletonHolder {
		private static final TaskExecutor INSTANCE = new TaskExecutor();
	}

	private TaskExecutor() {
	}

	public static final TaskExecutor getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
}
