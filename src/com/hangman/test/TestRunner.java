package com.hangman.test;

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(GuessValidatorTest.class);
		for (Failure failure :  result.getFailures()) {
			System.out.println(failure);
		}
		
		System.out.println("Number of tests ran: " + result.getRunCount());
		System.out.println("Run time: " + result.getRunTime());
	}

}

