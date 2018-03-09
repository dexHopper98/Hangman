package com.hangman.test;

//log4j 1.x
import org.apache.log4j.Logger;

//JUnit libs
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**************************************************************************
 * <b>Title:</b> com.hangman.test.TestRunner.java
 * <b>Project:</b>HangMan
 * <b>Description: The Unit Test runner class to execute the set 
 * of test defined by the class</b>
 * @author Devon Franklin
 * @version: 1.0
 * @since Feb 6, 2018
 ****************************************************/

public class TestRunner {
	private static final Logger log = Logger.getLogger(TestRunner.class.getName());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(GuessValidatorTest.class);
		for (Failure failure :  result.getFailures()) {
			log.info(failure);
		}
		
		log.info("Number of tests ran: " + result.getRunCount());
		log.info("Run time: " + result.getRunTime());
	}

}

