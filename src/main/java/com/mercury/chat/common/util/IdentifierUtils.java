package com.mercury.chat.common.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IdentifierUtils {
	
	private AtomicLong uniqeid = new AtomicLong(0);  
	  
	public String generateID() {
		return UUID.randomUUID().toString();
	}
	
	public Long generateLongID() {  
        return uniqeid.incrementAndGet();
    } 
	
}
