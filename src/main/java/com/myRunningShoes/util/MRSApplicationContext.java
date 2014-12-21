package com.myRunningShoes.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MRSApplicationContext {
	
	private static volatile ApplicationContext context = null;

	private MRSApplicationContext() {
	}

	public static synchronized ApplicationContext getInstance() {
			if (context == null) {
				context = new GenericXmlApplicationContext(
						"classpath:app-context-xml.xml");
				//((GenericXmlApplicationContext) context).refresh();
			}
		return context;
	}
}
