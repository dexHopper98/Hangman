package com.hangman;

//jdk 1.8
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//log4j 1.x
import org.apache.log4j.Logger;

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
	private static final Logger log = Logger.getLogger(HangMan.class.getName());
	private List<String> userGuesses;
	private GuessValidator validator;
	private int maxIncorrectGuesses;
	private int guessesMade;
	private List<String> underscores;
	
	//variables for word generation
	private static final String DEFAULT_FILE_LOC = "./GuessWords/wordListOne.txt";
	private static final String DEFAULT_FILE_DELIMITTER = ",";
	private String wordListFile;
	private String fileDelimitter;
	
	//enum containing the possible guess status
	enum GuessStatus{
		CORRECT(0), INCORRECT(1), SOLVED(2), FAILED(3), PREVIOUS_GUESS(4);
		
		private int value;
		private GuessStatus(int value){
			this.value = value;
		}
		public int getValue(){ return value; }
	}
	
	/**
	 * Default constructor
	 */
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
			log.error("Error running hangman game: " + e);
		}
	}
	
	/**
	 * Executes the game of Hangman
	 */
	public void run() throws Exception{
		String wordToGuess = retrieveGuessWord();	
		boolean isSolved = false;
		String userInput;
		
		//populate underscores for each letter of word for user
		populateUnderScores(wordToGuess);
		
		//display games messages
		displayGameInfo();
		
		//receive and validate user input
		while(!isSolved && guessesMade <= maxIncorrectGuesses){
				userInput = getUserInput();
				log.debug("User input: " + userInput);
				
				//determine status of user guess
				GuessStatus status = checkGuess(userInput, wordToGuess);
				
				
			break;
		}
	}
	
	/**
	 * Retrieves a random word for the game from specified file location
	 * @return
	 */
	protected String retrieveGuessWord() throws Exception{
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
	private void populateUnderScores(String word){
		underscores = new ArrayList<>();
		String[] chars = word.split("");//first index will be a blank
		
		//populate list with underscores matching given word
		for (int i = 1; i < chars.length; i++) {
			String c = chars[i];
			if(c.equals(" ")){
				underscores.add(" ");
			}else{
				underscores.add("_ ");
			}
		}		
	}
	
	/**
	 * Helper method to display information(rules, word to guess, etc.) to user about the game
	 */
	private void displayGameInfo(){
		System.out.println(generateGameMessage());
		System.out.println("\nPlease make your guess");
		System.out.print("The word to guess is: " );
		for (String s : underscores) {
			System.out.print(s);
		}
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
			message.append("Or you may guess the entire word at once.\n");
			message.append("Careful, if you attempt to guess the entire word, and are wrong, game over!");
		}
		
		return message.toString();
	}
	
	/**
	 * Retrieves user input and validates it according to validator settings.
	 * @return - The validated input
	 * @throws Exception
	 */
	private String getUserInput() throws Exception{
		String userInput = null;
		boolean validInput = false;
		
		//get the user input 
		Scanner sc = new Scanner(System.in);
		try{
			while(sc.hasNext()){
				//get user input and validate
				userInput = sc.next();
				validInput = validator.validateInput(userInput);	

				//exit once we get valid input
				if(validInput) break;
				else System.out.println(validator.getErrorMessage());
			}			
		}catch(Exception e){
			throw new Exception("Error fetching user input ", e);
		}finally{
			sc.close(); //close when finish
		}

		return userInput;	
	}
	
	/**
	 * Checks the single letter guess or the entire word guess against the random Hangman word.
	 * @param userInput
	 * @param wordToGuess
	 */
	private GuessStatus checkGuess(String userInput, String wordToGuess){
		//quick fail if the guess has already been made
		if(userGuesses.contains(userInput)) {
			return GuessStatus.PREVIOUS_GUESS;
		}
				
		//determine if a single letter guess or if a entire word guess
		if(userInput.length() == 1){
			log.debug("Checking single letter guess");
			return checkSingleLetterGuess(userInput, wordToGuess);
		}else{
			log.debug("Checking entire letter guess");
			return checkEntireWordGuess(wordToGuess, userInput);
		}
	}
	
	/**
	 * Validates if the single letter guess is correctly or incorrectly made
	 * @param wordToGuess
	 * @param letterGuess
	 * @return
	 */
	private GuessStatus checkSingleLetterGuess(String wordToGuess, String letterGuess){
		GuessStatus status = null;
		int matchIndex = wordToGuess.toLowerCase().indexOf(letterGuess.toLowerCase());
		
		if(matchIndex > -1){
			log.debug("***Match found :" +  matchIndex);
			//find the matching position within the underscores and replace with letter guess
			underscores.remove(matchIndex);
			underscores.add(matchIndex, letterGuess + " ");			
		}else{
			guessesMade++; //wrong guess, update the incorrect guesses made
		}
		//track the guess made whether correct or incorrect
		userGuesses.add(letterGuess);
		
		return status;
	}
	
	/**
	 * Validates if the word guess matches the random word to guess
	 * @param wordToGuess
	 * @param guessedWord
	 * @return
	 */
	private GuessStatus checkEntireWordGuess(String wordToGuess, String guessedWord){
		GuessStatus status = null;
		if(guessedWord.equalsIgnoreCase(wordToGuess)){
			status = GuessStatus.SOLVED;
		}else{
			status = GuessStatus.FAILED;
		}
		return status;
	}
}