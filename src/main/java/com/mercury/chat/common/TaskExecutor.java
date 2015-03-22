package com.mercury.chat.common;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TaskExecutor {
	
	public final static ExecutorService taskExecutor = Executors.newFixedThreadPool(Integer.parseInt(System.getProperty("Task.Executor.Size", "50")));
	
	public final static EventExecutorGroup businessGroup = new DefaultEventLoopGroup(50, Executors.newFixedThreadPool(50));
	
	private static class SingletonHolder {
		private static final TaskExecutor INSTANCE = new TaskExecutor();
	}

	private TaskExecutor() {
	}

	public static final TaskExecutor getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
}
