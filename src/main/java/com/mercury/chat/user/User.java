package com.mercury.chat.user;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = -3992280630570349960L;
	
	private String userId;
	
	private String passWord;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public User(String userId, String passWord) {
		super();
		this.userId = userId;
		this.passWord = passWord;
	}
}

