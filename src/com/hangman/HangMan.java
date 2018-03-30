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
	private Scanner sc;
	private GuessValidator validator;
	private int maxIncorrectGuesses;
	private int guessesMade;
	private List<String> underscores;
	private static final String UNDERSCORE_WITH_SPACE = "_ "; 
	
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
		boolean keepGuessing = true;
		String userInput;
		
		//populate underscores for each letter of word for user
		populateUnderScores(wordToGuess);
		
		//display games messages
		displayGameInfo();
		
		//receive and validate user input
		while(keepGuessing && guessesMade < maxIncorrectGuesses){
				userInput = getUserInput();
				log.debug("User input: " + userInput);
				
				//determine status of user guess
				GuessStatus status = checkGuess(userInput, wordToGuess);
				
				switch(status){
				case CORRECT : System.out.println("Correct guess! Please make another guess.");
					break;
				case INCORRECT : 
					System.out.println("Incorrect guess. Please make another guess.");
					System.out.println("Guesses left: " + (maxIncorrectGuesses - guessesMade));
					break;
				case PREVIOUS_GUESS : System.out.println("Letter guess already made. Please make another guess.");
					break;
				case SOLVED : 
					System.out.println("Congraulations, word has been solved: " + wordToGuess);
					keepGuessing = false;
					break;
				case FAILED : 
					System.out.println("Sorry, failed to solve the word. The word to guess was: " + wordToGuess);
					keepGuessing = false;
					break;
				default: break;
				}
				
				printUnderScores();
		}
		
		if(guessesMade == maxIncorrectGuesses){
			System.out.println("\nSorry, failed to solve the word. The word to guess was: " + wordToGuess);
		}
		cleanUp();
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
		String[] chars = word.split("");
		
		//populate list with underscores matching given word
		for (int i = 0; i < chars.length; i++) {
			String c = chars[i];
			if(c.equals(" ")){
				underscores.add(" ");
			}else{
				underscores.add(UNDERSCORE_WITH_SPACE);
			}
		}		
	}
	
	/**
	/**
	 * Helper method to display information(rules, word to guess, etc.) to user about the game
	 */
	protected void displayGameInfo(){
		System.out.println(generateGameMessage());
		System.out.print("\nPlease make your guess \nThe word to guess is: ");
		printUnderScores();
		System.out.println();
	}
	
	/**
	 * Generates the game message displaying the rules of the Hangman game
	 * @return
	 */
	protected String generateGameMessage(){
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
	 * Helper to quickly print current underscores for the word to guess
	 */
	protected void printUnderScores(){
		//TODO use a JAVA 8 approach here instead, remove method
		for (String s : underscores) {
			System.out.print(s);
		}
	}
	
	/**
	 * Retrieves user input and validates it according to validator settings.
	 * @return - The validated input
	 * @throws Exception
	 */
	private String getUserInput() throws Exception{
		String userInput = "";
		boolean validInput = false;
		
		//get the user input 
		sc = new Scanner(System.in);
		try{
			while(sc.hasNext()){
				//get user input and validate
				userInput = sc.next();
				validInput = validator.validateInput(userInput);	

				//exit once we get valid input
				if(validInput) break;
				else System.out.println(validator.getErrorMessage() + ". Please give a valid character.");
			}			
		}catch(Exception e){
			throw new Exception("Error fetching user input ", e);
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
			return checkSingleLetterGuess(userInput, wordToGuess);
		}else{
			return checkEntireWordGuess(wordToGuess, userInput);
		}
	}
	
	/**
	 * Validates if the single letter guess is correctly or incorrectly made
	 * @param letterGuess	   
	 * @param wordToGuess
	 * @return
	 */
	private GuessStatus checkSingleLetterGuess(String letterGuess, String wordToGuess){
		GuessStatus status = GuessStatus.CORRECT;
		boolean match = wordToGuess.toLowerCase().indexOf(letterGuess.toLowerCase()) > -1;

		if(match){
			//update all underscore occurrences that correspond to letter guess
			updateUnderscores(letterGuess, wordToGuess, 0);
			
			//check if the word is complete
			if(!underscores.contains(UNDERSCORE_WITH_SPACE)) status = GuessStatus.SOLVED;
		}else{
			guessesMade++; //wrong guess, update the incorrect guesses made
			status = GuessStatus.INCORRECT;
		}
		//track the guess made whether correct or incorrect
		userGuesses.add(letterGuess);
		
		return status;
	}
	
	/**
	 * Updates corresponding indices in underscores with matching letter guess. Continues to call itself
	 * until all occurrences have been replaced.
	 * @param letterGuess
	 * @param wordToGuess
	 * @param startIndex
	 */
	private void updateUnderscores(String letterGuess, String wordToGuess, int startIndex){
		int matchIndex = wordToGuess.toLowerCase().indexOf(letterGuess.toLowerCase(), startIndex);
		if(matchIndex  == -1) return; // no more matches, end
		
		underscores.remove(matchIndex);
		underscores.add(matchIndex, letterGuess + " ");
		
		//recursively call until all occurrences of letterGuess have been updated in underscores
		updateUnderscores(letterGuess, wordToGuess, matchIndex + 1);
	}
	
	/**
	 * Validates if the word guess matches the random word to guess
	 * @param guessedWord
	 * @param wordToGuess
	 * @return
	 */
	private GuessStatus checkEntireWordGuess(String guessedWord, String wordToGuess){
		GuessStatus status = null;
		if(guessedWord.equalsIgnoreCase(wordToGuess)){
			status = GuessStatus.SOLVED;
		}else{
			status = GuessStatus.FAILED;
		}
		return status;
	}
	
	/**
	 * Performs an clean up activities for class
	 */
	private void cleanUp(){
		if(sc != null){
			sc.close();
		}
	}
}