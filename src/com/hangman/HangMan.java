package com.hangman;

//jdk 1.7
import java.util.List;


//hangman libs
import com.hangman.generator.WordGenerator;
import com.hangman.validator.InputValidator;

/**************************************************************************
 * <b>Title:</b> com.hangmanHangMan.java
 * <b>Project:</b>HangMan
 * <b>Description:</b>
 * @author Devon Franklin
 * @version: 1.0
 * @since Jan 23, 2018
 ****************************************************/

public class HangMan {
	
	private List<String> userGuesses;
	private WordGenerator generator;
	private InputValidator input;
	private int maxGuesses;
	
	public HangMan(){
		this.maxGuesses = 3; //set default
	}
	
	public HangMan(int maxGuesses){
		this.maxGuesses = maxGuesses;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//create instance and run
	}
	
	public void run(){
		//generate a random word for game
		
		//display underscores for each letter of word for user
		
		//receive user input
		
		//validate user input and if there single letter guess is correct or not
		
		//allow user to guess entire word, if incorrect though game is over
		
		//track guess
		
		//continue until word is guess correctly or out of attempts
	}

}

