package com.hangman.generator;

//java IO/NIO2 libs
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//jdk 1.8.x
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//log4j 1.2.17
import org.apache.log4j.Logger;

/**************************************************************************
 * <b>Title:</b> com.hangman.generatorWordGenerator.java
 * <b>Project:</b>HangMan
 * <b>Description: Responsible for generating words from a source(DB, file, etc)
 * and will generate a random word or phrase.</b>
 * @author Devon Franklin
 * @version: 1.0
 * @since Jan 23, 2018
 ****************************************************/

public class WordGenerator {
	private Logger log  = Logger.getLogger(WordGenerator.class.getName());
	//list of generated words
	private List<String> generatedWords;
	
	/**
	 * Constructor that initializes from a file location.
	 * @param fileLoc - the path to the file
	 * @param delimiter - the delimiter that separates words in file
	 */
	public WordGenerator(String fileLoc, String delimiter){
		generatedWords = new ArrayList<>();
		
		//attempt to load the file of words
		loadFromFile(fileLoc, delimiter);		
	}
	
	/**
	 * Constructor that initializes from given list of words
	 * @param words
	 */
	public WordGenerator(List<String> words){
		this.generatedWords = words;
	}
	
	/**
	 * Retrieves a randomly chosen word from collection
	 * @return
	 * @throws Exception
	 */
	public String retrieveRandomWord() throws Exception{
		//give an error if collection is unusable
		if(generatedWords == null || generatedWords.size() == 0){
			throw new Exception("Unable to generate word, collection empty or null.");
		}
		Random rand = new Random(); 
		int randomVal = rand.nextInt(generatedWords.size()); 
		
		return generatedWords.get(randomVal);
	}
	
	/**
	 * Populates collection of words from a file location
	 * @param file - the path to the file
	 * @param delimiter - the delimiter that separates words
	 */
	protected void loadFromFile(String file, String delimiter){
		Path path = Paths.get(file);
		try {
			if(!Files.exists(path)) throw new IOException("File does not exist, please check.");
			
			//read file and parse using delimiter
			Scanner scan = new Scanner(path);
			scan.useDelimiter(delimiter);
			String val;
			while(scan.hasNext()){
				val = scan.next();
				
				//load words to collection
				generatedWords.add(val);
			}
			if(scan != null )scan.close();
			log.debug("Number of words loaded: " + generatedWords.size());
		} catch (IOException e) {
			log.error("Error attempting to create file from path:" + e);
		}
	}
}

