package com.hangman.validator;
/**************************************************************************
 * <b>Title:</b> com.hangman.ioInputValidator.java
 * <b>Project:</b>HangMan
 * <b>Description: Generic Validator interface that defines the base functionality
 * for a validator. Implementing class will define the type of data it validates.</b>
 * @author Devon Franklin
 * @version: 1.0
 * @since Jan 23, 2018
 ****************************************************/

public interface DataValidator<T extends Object>  {
	
	/**
	 * Receives an input object and validates it according to validator rules. 
	 * @param data - The input data to validate
	 * @return - True or False, corresponding whether input validated successfully or not.
	 */
	public abstract boolean validateInput(T data) throws Exception;
		
}

