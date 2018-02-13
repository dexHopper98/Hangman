package com.hangman;

//jdk 1.7
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;




//hangman libs
import com.hangman.generator.WordGenerator;
import com.hangman.validator.GuessValidator;

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
	private GuessValidator validator;
	private int maxIncorrectGuesses;
	private int guessesMade;
	
	//variables for word generation
	private static final String DEFAULT_FILE_LOC = "./GuessWords/wordListOne.txt";
	private static final String DEFAULT_FILE_DELIMITTER = ",";
	private String wordListFile;
	private String fileDelimitter;
	
	//enum containing the current guess status
	enum GuessStatus{
		CORRECT(0), INCORRECT(1), SOLVED(2), FAILED(3);
		
		private int value;
		private GuessStatus(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	
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
	 * @param maxIncorrectGuesses - The max incorrect guesses allowed for the game
	 * @param wordListFile - The file containing list of words for game
	 * @param fileDelimitter - The delimitter between words in file
	 */
	public HangMan(int maxIncorrectGuesses, String wordListFile, String fileDelimitter){
		this.maxIncorrectGuesses = maxIncorrectGuesses;
		this.wordListFile = wordListFile;
		this.fileDelimitter = fileDelimitter;
		this.userGuesses = new ArrayList<>();
		
		//initialize validator with desired rules
		setValidatorRules();
	}
	
	/**
	 * Sets the validator rules for this game. Rules are as follows:
	 * 1) upper or lowercase letters shouldn't matter
	 * 2) no numbers/digits
	 * 3) no special characters
	 * 4) a single letter or a word is acceptable input
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
		try {
			hg.run();
		} catch (Exception e) {
			System.err.println("Error running hangman game: " + e);
		}
	}
	
	/**
	 * Executes the game of Hangman
	 */
	public void run() throws Exception{
		String wordToGuess = retrieveWord();	
		boolean isSolved = false;
		String userInput;
		
		//display underscores for each letter of word for user
		List<String> underscores = generateUnderScores(wordToGuess);
		
		//display message about rules
		System.out.println(generateGameMessage());

		//receive and validate user input
		while(!isSolved && guessesMade <= maxIncorrectGuesses){
				userInput = getUserInput();
				
				//determine if a single letter or if a entire guess
				GuessStatus status = checkGuess(userInput, wordToGuess);
				
				switch(status){
				//if entire word guess, if incorrect game is over

				}
				
			break;
		}
	}
	
	/**
	 * Retrieves a random word for the game from specified file location
	 * @return
	 */
	protected String retrieveWord() throws Exception{
		String randomWord = null;
		WordGenerator generator = new WordGenerator(wordListFile, fileDelimitter);
		
		try {
			randomWord = generator.retrieveRandomWord();
		} catch (Exception e) {
			throw new Exception("Error attempting to retrieve word for game: " + e);
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
	 * Checks the single letter guess or the entire word guess against the random Hangman word.
	 * @param userInput
	 * @param wordToGuess
	 */
	private GuessStatus checkGuess(String userInput, String wordToGuess){
		GuessStatus status = null;
		
		//determine if a single letter or if a entire guess
		if(userInput.length() < 2){
			if(wordToGuess.indexOf(userInput) > -1){
				//found a match
				
			}else{
				//increment the incorrect guesses				
			}
			//track guess
			
		}else{//check the entire word guess
			if(userInput.equalsIgnoreCase(wordToGuess)){
				status = GuessStatus.SOLVED;
			}else{
				status = GuessStatus.FAILED;
			}
		}
		
		return status;
	}
	
	
	private void trackeGuesses(String guessMade){
		//track the guess made to ensure they don't make the guess again
	}
	
	/**
	 * Generates the game message displaying the rules of the Hangman game
	 * @return
	 */
	private String generateGameMessage(){
		StringBuilder message = new StringBuilder(500);
		message.append("Welcome to the game of Hangman!\n");
		message.append("You can may guess one letter at a time. ");
		if(validator.isAllowMultiCharacterGuess()){
			message.append("Or you may guess the entire phrase at once.\n");
		}
		message.append("Please make your guess");
		
		return message.toString();
	}
	
	/**
	 * Retrieves user input and validates it according to validator settings.
	 * @return - The validated input
	 * @throws Exception
	 */
	protected String getUserInput() throws Exception{
		String userInput = null;
		boolean validInput = false;
		
		//get the user input 
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			String input = sc.next();
			
			//validate user input
			try{
				validInput = validator.validateInput(input);	
			}catch(Exception e){
				throw new Exception("Error fetching user input ", e);
			}finally{
				//close when finish
				sc.close();
			}
			//exit once we get valid input
			if(validInput) break;
			else System.out.println(validator.getErrorMessage());
		}

		return userInput;	
	}
}