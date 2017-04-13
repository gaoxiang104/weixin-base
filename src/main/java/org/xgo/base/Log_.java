package org.xgo.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Log_ {
	public Logger getLogger(String name) {
		return LogManager.getLogger(name);
	}
}
