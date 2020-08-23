package data;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * @author Danny Dinelli
 *
 */
public class Test{
private SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/m/dd");
	/**
	 * Default constructor
	 */
	public Test() {
		
	}
	/**
	 * Method checks if entered string is only alphabetical 
	 * @param word - String to be checked
	 * @return - returns the entered string if correct or empty string if incorrect 
	 */
	public String allAlpha(String word) {
		int count = 0;
		for(int i = 0; i < word.length();i++) {
			char character = word.charAt(i);
			if(character >= 'A' && character <= 'Z' || character >= 'a' && character <= 'z' || character == ' ') {
				count++;
			}
		}//end of for loop
		if(count == word.length()) {
			return word;
		}
		return "?";
	}//end of allAlpha method
	/**
	 * Method checks if entered string is only numbers and alphabetic characters 
	 * @param word - word to be checked
	 * @return - returns the entered string if correct or empty string if incorrect 
	 */
	public String alphaAndNums(String word) {
		int count = 0;
		for(int i = 0; i < word.length();i++) {
			char character = word.charAt(i);
				if(character >= 'A' && character <= 'Z' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == ' ') {
					count++;
				}
		}
			if(count == word.length()) {
				return word;
			}
		return "?";
	}//end of alpha and nums method
	/**
	 * Method checks if entered string is only numbers 
	 * @param word - word to be checked
	 * @return - returns the entered string if correct or empty string if incorrect 
	 */
	public String justNumbers(String word) {
		int count = 0;
		for(int i = 0; i < word.length();i++) {
			char character = word.charAt(i);
				if(character >= '0' && character <= '9') {
					count++;
				}
		}
		if(count == word.length()) {
			return word;
		}
		return "?";
	}//end of justNumbers
	/**
	 * Method checks if enters at least one @ and . in string
	 * @param word - word to be checked
	 * @return - returns the entered string if correct or empty string if incorrect 
	 */
	public String atSymbolAndDot(String word) {
		int count = 0;
		for(int i = 0; i < word.length();i++) {
			char character = word.charAt(i);
			if(character == '@') {
				if(count < 2) {
					count++;
				}
			}
		}
		if(count == 1) {
			return word;
		}
		return "?";
	}
	/**
	 * Method checks if date entered is a valid  date
	 * @param stringDate - date to be checked
	 * @return - returns the entered string if correct or empty string if incorrect 
	 */
public String checkDate(String stringDate)	{
	try {
		Date date = simpleDate.parse(stringDate);
		String newDate = simpleDate.format(date);
		return newDate;
	}
	catch(Exception e){
		
	}
	return "?";
}//end of checkDate method
}