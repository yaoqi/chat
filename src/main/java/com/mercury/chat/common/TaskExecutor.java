package com.mercury.chat.common;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TaskExecutor {
	
	private final static int taskExecutorSize = Integer.parseInt(System.getProperty("Task.Executor.Size", "50"));
	
	private final static int businessGroupSize = Integer.parseInt(System.getProperty("Biz.Group.Size", "50"));
	
	public final static ExecutorService taskExecutor = new ThreadPoolExecutor(taskExecutorSize/4, taskExecutorSize, 60L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
	
	public final static ExecutorService businessGroupExecutor = new ThreadPoolExecutor(businessGroupSize/4, businessGroupSize, 60L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
	
	public final static EventExecutorGroup businessGroup = new DefaultEventLoopGroup(businessGroupSize, businessGroupExecutor);
	
}
