package com.mercury.chat;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;


public class BlockingQueueTest {
	@Test
	public void testBlockingQueue(){
		BlockingQueue<String> bk = new LinkedBlockingQueue<String>(1);
		boolean offer1 = bk.offer("1");
		assertTrue(offer1);
		boolean offer2 = bk.offer("2");
		assertFalse(offer2);
	}
}
