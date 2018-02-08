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
		gv =  new GuessValidator(false, false, false, false);
	}
	
	@Test
	public void digitsAreNotAllowedTest() {
		//Fixtures
		String no1 = "123";
		String no2 = "456 64";
		String no3 = "test64test";
		String no4 = "normal string";
		String no5 = "45.00";
		
		assertFalse("Input contains all digits", gv.checkDigits(no1));
		assertFalse("Input contains all digits and a space", gv.checkDigits(no2));
		assertFalse("Input contains digits and text", gv.checkDigits(no3));
		assertTrue("Input contains no digits", gv.checkDigits(no4));
		assertFalse("Input contains digits with a decimal", gv.checkDigits(no5));
	}

	@Test
	public void specialCharactersNotAllowedTest() {
		//Fixtures
		String a = "TAtaTa";
		String b = "$%$^^%^&";
		String c = " #$1fd235";
		String d = "pe5opl3";
		String e = "some times";
		
		assertTrue("Input has no special characters", gv.checkSpecialChars(a));
		assertFalse("Input has all special characters", gv.checkSpecialChars(b));
		assertFalse("Input has specials chars and non-special chars", gv.checkSpecialChars(c));
		assertTrue("Input has numbers and non-special chars", gv.checkSpecialChars(d));
		assertTrue("Input has a space and non-speical chars", gv.checkSpecialChars(e));		
	}
	
	@Test
	public void multiCharactersNotAllowedTest(){
		//Fixtures
		String v = "@@";
		String x = "a";
		String y = "abc";
		String z = "t  ";
		
		assertFalse("Input has multiple special characters", gv.checkMultiCharacterGuess(v));
		assertTrue("Input has only one characters", gv.checkMultiCharacterGuess(x));
		assertFalse("Input has multiple normal characters", gv.checkMultiCharacterGuess(y));
		assertFalse("Input has single character with multiple spaces", gv.checkMultiCharacterGuess(z));
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		gv = null;
	}

}