package com.hangman.validator;

//jdk 1.7.x
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**************************************************************************
 * <b>Title:</b> com.hangman.GuessValidator.java
 * <b>Project:</b>HangMan
 * <b>Description: Handles guess validation for proper input guesses. 
 * Set options to determine how validation is handled. </b>
 * @author Devon Franklin
 * @version: 1.0
 * @since Jan 26, 2018
 ****************************************************/

public class GuessValidator implements DataValidator<String> {
	//member variable options with defaults of false
	private boolean allowUpperCase;
	private boolean allowDigits;
	private boolean allowSpecialChars;
	private boolean allowMultiCharacterGuess;
	
	//error message for retrieval if any validation checks fail
	private String errorMessage;
	
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
	 * @param allowMultiCharacterGuess
	 */
	public GuessValidator(boolean allowUpperCase, boolean allowDigits, boolean allowSpecialChars, boolean allowMultiCharacterGuess){
		this.allowUpperCase = allowUpperCase;
		this.allowDigits = allowDigits;
		this.allowSpecialChars = allowSpecialChars;
		this.allowMultiCharacterGuess = allowMultiCharacterGuess;
	}
	
	/**
	 * Wrapper method that validates input based on all the rules
	 * set during construction.
	 * */
	@Override
	public boolean validateInput(String input) throws Exception {
		boolean isValidInput = true;
		//quick fail if unable to perform validation
		if(input == null || input.length() == 0){
			setErrorMessage("Cannot validate string of size 0, or null");
			return false;
		}
		
		//determine if digits are allowed
		isValidInput = checkDigits(input);
		
		//determine if special characters are allowed
		isValidInput = checkSpecialChars(input);
		
		//determine if an entire phrase is allowed
		isValidInput = checkMultiCharacterGuess(input);
				
		//determine if uppercase characters are allowed
		
		return isValidInput;
	}
	
	/**
	 * Helper method to verify if the given input allows digits or not
	 * @param input
	 * @return
	 */
	public boolean checkDigits(String input){
		boolean result = true;
		if(allowDigits) return result; //if digits are allowed, nothing to check
		
		List<String> digits = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
		for (String d : digits) {
			if(input.indexOf(d) > -1){
				result = false;
				setErrorMessage("Digits not allowed within input string");
				break;
			}
		}
		return result;
	}
	
	/**
	 * Helper method to determine if special characters are allowed or not. 
	 * Special characters are any characters that are NOT letters, numbers, or white-spaces
	 * @param input
	 * @return
	 */
	public boolean checkSpecialChars(String input){
		boolean result = true;
		if(allowSpecialChars) return result;
		
		//If not an alpha character, number, or space, fail check
		Pattern p = Pattern.compile("[^a-zA-Z\\s\\d]");
		Matcher m = p.matcher(input);
		if (m.find()) {
			result = false;
			setErrorMessage("Special characters not allowed within input string");
		}
		return result;
	}
	
	/**
	 * Validates that an entire phrase guess is allowed. An entire phrase is any input
	 * that is beyond a single letter, validators may be limited to single or many letter inputs.
	 * @param input
	 * @return
	 */
	public boolean checkMultiCharacterGuess(String input){
		boolean result = true;
		if(allowMultiCharacterGuess) return result;
		
		if(input.length() > 1){
			setErrorMessage("More than one character at time not allowed within input string");
			result = false;
		}
		return result;
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
	 * @return the allowMultiCharacterGuess
	 */
	public boolean isAllowMultiCharacterGuess() {
		return allowMultiCharacterGuess;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	private void setErrorMessage(String errorMessage){
		this.errorMessage = errorMessage;
	}

}