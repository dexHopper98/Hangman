package com.hangman.validator;
/**************************************************************************
 * <b>Title:</b> com.hangman.GuessValidator.java
 * <b>Project:</b>HangMan
 * <b>Description: Handles guess validation for proper input guesses. 
 * Set options to determine how validation is handled. </b>
 * @author Devon Franklin
 * @version: 1.0
 * @since Jan 26, 2018
 ****************************************************/

public class GuessValidator implements InputValidator {
	//member variable options with defaults of false
	private boolean allowUpperCase;
	private boolean allowDigits;
	private boolean allowSpecialChars;
	private boolean allowEntirePhraseGuess;
	
	/**
	 * Initializes class with all validation options set to false
	 */
	public GuessValidator(){
		//no-arg constructor for simple initialization
	}
	
	/**
	 * Overloaded constructor to allow setting of validation options
	 * @param allowUpperCase
	 * @param allowDigits
	 * @param allowSpecialChars
	 * @param allowEntirePhraseGuess
	 */
	public GuessValidator(boolean allowUpperCase, boolean allowDigits, boolean allowSpecialChars, boolean allowEntirePhraseGuess){
		this.allowUpperCase = allowUpperCase;
		this.allowDigits = allowDigits;
		this.allowSpecialChars = allowSpecialChars;
		this.allowEntirePhraseGuess = allowEntirePhraseGuess;
	}
	
	/* (non-Javadoc)
	 * @see com.hangman.validator.InputValidator#validateInput(java.lang.Object)
	 */
	@Override
	public boolean validateInput(Object input) {
		boolean isValidInput = false;
		
		//determine if digits are allowed
		
		//determine if special characters are allowed
				
		//determine if an entire phrase is allowed
		
		//determine if uppercase characters are allowed
		
		return isValidInput;
	}

	
	//=====Allow only getters to prevent changing how this validator instance processes mid-stream====//
	/**
	 * @return the allowUpperCase
	 */
	public boolean isAllowUpperCase() {
		return allowUpperCase;
	}
	/**
	 * @return the allowDigits
	 */
	public boolean isAllowDigits() {
		return allowDigits;
	}
	/**
	 * @return the allowSpecialChars
	 */
	public boolean isAllowSpecialChars() {
		return allowSpecialChars;
	}
	/**
	 * @return the allowEntirePhraseGuess
	 */
	public boolean isAllowEntirePhraseGuess() {
		return allowEntirePhraseGuess;
	}

}