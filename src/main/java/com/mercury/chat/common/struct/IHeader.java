package com.mercury.chat.common.struct;

public interface IHeader {
	byte getMessageType();

	String getTo();

	String getFrom();
}
