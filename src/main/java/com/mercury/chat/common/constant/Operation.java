package com.mercury.chat.common.constant;

import org.apache.commons.lang.ObjectUtils;

public enum Operation {
	
	LOAD((byte) 0),ADD((byte) 1),UPDATE((byte) 2),DELETE((byte) 3);
	
	private byte value;

	private Operation(byte value) {
		this.value = value;
	}
	
	public static Operation valOf(byte value){
		Operation[] operations = Operation.class.getEnumConstants();
		for(Operation operation:operations){
			if(ObjectUtils.equals(value, operation.value)){
				return operation;
			}
		}
		return null;
	}
	
}
