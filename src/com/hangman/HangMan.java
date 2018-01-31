package com.hangman;

//jdk 1.7
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//hangman libs
import com.hangman.generator.WordGenerator;
import com.hangman.validator.GuessValidator;
import com.hangman.validator.InputValidator;

/**************************************************************************
 * <b>Title:</b> com.hangmanHangMan.java
 * <b>Project:</b>HangMan
 * <b>Description: Application class that executes the guessing game of Hangman.</b>
 * @author Devon Franklin
 * @version: 1.0
 * @since Jan 23, 2018
 ****************************************************/

public class HangMan {
	private List<String> userGuesses;
	private InputValidator validator;
	private int maxGuesses;
	
	//variables for word generation
	private static final String DEFAULT_FILE_LOC = "./GuessWords/wordListOne.txt";
	private static final String DEFAULT_FILE_DELIMITTER = ",";
	private String wordListFile;
	private String fileDelimitter;
	
	public HangMan(){
		//set default values
		this(3, DEFAULT_FILE_LOC, DEFAULT_FILE_DELIMITTER);
	}

	/**
	 * Overloaded constructor to allow setting max number of guesses for game
	 * @param maxGuesses
	 */
	public HangMan(int maxGuesses){
		this(maxGuesses, DEFAULT_FILE_LOC, DEFAULT_FILE_DELIMITTER);
	}
	
	/**
	 * Overloaded constructor to allow customized settings to game
	 * @param maxGuesses - The max allowed guesses for the game
	 * @param wordListFile - The file containing list of words for game
	 * @param fileDelimitter - The delimitter between words in file
	 */
	public HangMan(int maxGuesses, String wordListFile, String fileDelimitter){
		this.maxGuesses = maxGuesses;
		this.wordListFile = wordListFile;
		this.fileDelimitter = fileDelimitter;
		this.userGuesses = new ArrayList<>();
		
		//initialize validator with desired rules
		setValidatorRules();
	}
	
	/**
	 * Sets the validator rules for this game. Rules are as follows:
	 * 1) acceptable input is a single letter or a word
	 * 2) no numbers or special characters
	 * 3) upper or lowercase letters shouldn't matter
	 * Child classes should override this method to set their own unique rules.
	 */
	protected void setValidatorRules(){
		this.validator = new GuessValidator(true, false, false, true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO determine the constructor to run based on args passed
		HangMan hg = new HangMan(5);
		//hg.run();
		hg.getUserInput();
	}
	
	/**
	 * Executes the game of Hangman
	 */
	public void run(){
		String wordToGuess = retrieveWord();
		if(wordToGuess == null) return; //exit if no word to guess
		
		boolean isSolved = false;
		int guessesMade = 0;
		
		//display underscores for each letter of word for user
		List<String> underscores = generateUnderScores(wordToGuess);
		
		System.out.println("Please guess your letter. \n");

		while(!isSolved && guessesMade <= maxGuesses){
			//receive and validate user input
			
			//Determine guess made. if single letter guess, check if correct or not
			
			//if entire word guess, if incorrect game is over
			
			//track guess
			
			//continue until word is guess correctly or out of attempts	
			break;
		}
	}
	
	/**
	 * Retrieves a random word for the game from specified file location
	 * @return
	 */
	protected String retrieveWord(){
		String randomWord = null;
		WordGenerator generator = new WordGenerator(wordListFile, fileDelimitter);
		
		try {
			randomWord = generator.retrieveRandomWord();
		} catch (Exception e) {
			System.err.println("Error attempting to retrieve word; " +  e);
		}
		return randomWord;
	}
	
	/**
	 * Returns a list containing under scores corresponding to the characters from given word
	 * @param word - word to generate underscores from
	 * @return
	 */
	protected List<String> generateUnderScores(String word){
		List<String> wordUnderScores = new ArrayList<>();
		String[] chars = word.split("");//first index will be a blank
		
		//populate list with underscores or white spaces
		for (int i = 1; i < chars.length; i++) {
			String c = chars[i];
			if(c.equals(" ")){
				wordUnderScores.add(" ");
			}else{
				wordUnderScores.add("_");
			}
		}		
		return wordUnderScores;
	}
	
	/**
	 * Retrieves user input and validates it according to validator settings.
	 * @return - The validated input
	 */
	protected String getUserInput(){
		String userInput = null;
		boolean validInput;
		
		//get the user input 
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			String input = sc.next();
			System.out.println(input);
			
			//validate user input
			validInput = validator.validateInput(input);
			
			//exit once we get valid input
			if(validInput) break;
		}
		
		//close when finish
		sc.close();
		
		return userInput;	
	}
}