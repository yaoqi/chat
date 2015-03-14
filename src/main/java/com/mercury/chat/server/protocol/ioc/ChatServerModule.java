package com.mercury.chat.server.protocol.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.mercury.chat.user.service.UserService;
import com.mercury.chat.user.service.UserServiceImpl;

public class ChatServerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
		
	}
	
}
