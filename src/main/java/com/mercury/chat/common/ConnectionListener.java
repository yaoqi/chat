package com.mercury.chat.common;



public interface ConnectionListener{
    
	 void onConnection();
	 
	 void onClose();
	 
	 void onError(Throwable throwable);
	
}