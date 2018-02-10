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
	private boolean isValidInput;

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
		isValidInput = checkUpperCase(input);
		
		return isValidInput;
	}
	
	/**
	 * Helper method to verify if the given input allows digits or not
	 * @param input
	 * @return
	 */
	public boolean checkDigits(String input){
		if(allowDigits) return isValidInput; //if digits are allowed, nothing to check
		
		List<String> digits = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
		for (String d : digits) {
			if(input.indexOf(d) > -1){
				isValidInput = false;
				setErrorMessage("Digits are not allowed");
				break;
			}
		}
		return isValidInput;
	}
	
	/**
	 * Helper method to determine if special characters are allowed or not. 
	 * Special characters are any characters that are NOT letters, numbers, or white-spaces
	 * @param input
	 * @return
	 */
	public boolean checkSpecialChars(String input){
		if(allowSpecialChars) return isValidInput;
		
		//If not an alpha character, number, or space, fail check
		Pattern p = Pattern.compile("[^a-zA-Z\\s\\d]");
		Matcher m = p.matcher(input);
		if (m.find()) {
			isValidInput = false;
			setErrorMessage("Special characters not allowed");
		}
		return isValidInput;
	}
	
	/**
	 * Validates that an entire phrase guess is allowed. An entire phrase is any input
	 * that is beyond a single letter, validators may be limited to single or many letter inputs.
	 * @param input
	 * @return
	 */
	public boolean checkMultiCharacterGuess(String input){
		if(allowMultiCharacterGuess) return isValidInput;
		
		if(input.length() > 1){
			setErrorMessage("More than one character at time not allowed");
			isValidInput = false;
		}
		return isValidInput;
	}
	
	/**
	 * Validates that if capital letters are allowed within the given input.
	 * @param input
	 * @return
	 */
	public boolean checkUpperCase(String input){
		if(allowUpperCase) return isValidInput;
		
		//start from 1 to account for blank character at beginning
		String[] chars = input.split("");
		for (int i=1; i < chars.length; i++) {
			String ch = chars[i];
			System.out.println("The char: " + ch);
			
			//search for the capital version of within input
			if(ch.toUpperCase().equals(ch)){
				setErrorMessage("Capital characters are not allowed");
				isValidInput = false;
			}
		}
		
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
	 * @return the allowMultiCharacterGuess
	 */
	public boolean isAllowMultiCharacterGuess() {
		return allowMultiCharacterGuess;
	}

	/**
	 * @return the errorMessage
	 */
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	private void setErrorMessage(String errorMessage){
		this.errorMessage = errorMessage;
	}

}