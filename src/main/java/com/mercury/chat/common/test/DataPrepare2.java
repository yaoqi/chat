package com.mercury.chat.common.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface DataPrepare2 {

	DbType[] dbTypes() default {DbType.H2};

	String[] schema() default { "" };

	boolean isEnable() default true;
	
	Class<?>[] domainClasses() default {};

	// Class<? extends Throwable> cacheLoader();

}
