package com.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jDemo {
	
	
	public static void main(String[] args) {
			
		final Logger logger = Logger.getLogger(Log4jDemo.class);
		
		logger.setLevel(Level.DEBUG);
		
		logger.debug("This is debug");
		logger.info("This is Info");
		logger.warn("This is Warning");
		logger.error("This is Error");
		logger.fatal("This is Fatal");

		}

}
