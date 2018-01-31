package com.hangman.validator;
/**************************************************************************
 * <b>Title:</b> com.hangman.ioInputValidator.java
 * <b>Project:</b>HangMan
 * <b>Description: Validator interface that defines the base functionality
 * for a validator</b>
 * @author Devon Franklin
 * @version: 1.0
 * @since Jan 23, 2018
 ****************************************************/

public interface InputValidator  {
	
	/**
	 * Receives an input object and validates it according to validator rules. 
	 * @param input - The input data to validate
	 * @return - True or False, corresponding whether input validated successfully or not.
	 */
	public abstract boolean validateInput(Object input);
		
}

