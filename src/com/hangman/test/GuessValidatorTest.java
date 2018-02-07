package com.hangman.test;

//JUnit Assert static methods
import static org.junit.Assert.*;

//JUnit Annotations
import org.junit.Before;
import org.junit.After;
import org.junit.Test;


//Hangman libs
import com.hangman.validator.GuessValidator;

/**************************************************************************
 * <b>Title:</b> com.hangman.test.GuessValidatorTest.java
 * <b>Project:</b>HangMan
 * <b>Description: JUnit test class to validate key methods of 
 * the GuessValidator class</b>
 * @author Devon Franklin
 * @version: 1.0
 * @since Feb 3, 2018
 ****************************************************/

public class GuessValidatorTest {
	//Our class to test
	protected GuessValidator gv;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		/*Set all options to false, we want to verify each
		*option truly validates correctly*/
		//TODO allow validator class methods to be public
		gv =  new GuessValidator(false, false, false, false);
	}
	
	@Test
	public void testAreDigitsAllowed() {
		fail("Not yet implemented");
	}

	@Test
	public void testAreSpecialCharactersAllowed() throws Exception {
		//Fixtures
		String a = "tA";
		String b = "$%$^^%^&";
		String c = " #$1fd235";
		String d = "pe5opl3";
		String e = "some times";
		
		assertTrue("No special characters given", gv.validateInput(a));
		assertFalse("Passing all special characters", gv.validateInput(b));
		assertFalse("Passing a string with specials chars and non-special chars", gv.validateInput(c));
		assertTrue("Passing a string with numbers", gv.validateInput(d));
		assertTrue("Passing string with a space", gv.validateInput(e));		
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		gv = null;
	}

}