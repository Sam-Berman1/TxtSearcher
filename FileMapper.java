package finalproject;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * 
 * @author Sam Berman
 * 12/6/2019
 * 
 * This program takes a .txt file (who's format follows URL, Description, Keywords)
 * and iterates through the file line by line. It then splits words separated by commas, creates a node
 * of data from it, and adds it to a Map
 * 
 * The program enables a user to type in a full or partial keyword and search through
 * the map for any partial or full keys that match.
 */
public class FileMapper {
	public static Scanner kybd = new Scanner(System.in);
	public static ArrayList<String> dupeChecker = new ArrayList<String>();
	public static Map<MultiKey, String> map = new HashMap<>();
	
	
	public static void main(String[] args) {
		System.out.println("**************************");
		System.out.println("*  Welcome to WebSearch  *");
		System.out.println("**************************");
		mapText();
		search();
		
	}
	
	/**
	 * <p>mapText - iterates through txt file and separates words by commas 
	 * 		the 1st word is the WebSiteURL (Value).
	 * 		the 2nd word (sentence) is the WebSite Description. (Member of Key)
	 * 		the rest of the words are Search Keys. (Members of Key)
	 * 
	 * The program then adds these Values to a node in the Map
	 * I used a MultiKey class as a workaround for Maps usually only allowing one Key per Value
	 */
	public static void mapText() {
		BufferedReader input;
		try{
			input = new BufferedReader(new FileReader("C:\\Users\\sammy\\eclipse-workspace\\TxtSearcher\\TestFile.txt"));
		}
		catch(FileNotFoundException fileNotFoundException)
			{
			System.out.println("Unable to read test file.");
			return;
			}	 
			
		try{
			String webSiteURL;
			String webSiteDescription;
			String[] webSiteKeywords = new String[20];
				
			String str;
			while ((str = input.readLine()) != null) {
				String[] tokens=str.split(", ");
				if (tokens.length>2)
				{
					webSiteURL = tokens[0];
					webSiteDescription = tokens[1];
					for (int i=2;i<tokens.length; i++)
						map.put(new MultiKey(tokens[i], webSiteDescription), webSiteURL);
				}				  
			}
		}			 	
						 
		catch (IOException ioException){
			System.out.println("Unable to read test file C:\\TestData\\TestFile.txt");
		}
	}
	
	/**
	 * <p>search - takes a String from the user
	 * 	and then iterates through the map for anything that matches.
	 * 	if there's an exact match the URL(s) or Value(s) are printed.
	 * 	if there wasn't an exact match the program will show keys 
	 * 	(if any) that started with the characters entered.
	 * 	the user can then select the keywords found by index and re-search for 
	 *  the urls associated with that keyword
	 *  
	 * there is a commented out variable & loop to change search to show any keys 
	 * that contained the users input. (more results)
	 */
	public static void search() {
	try {
		System.out.println("\nType a KeyWord and Press Enter to Search: ");

		String search = kybd.nextLine();
		for(Map.Entry<MultiKey, String> entry : map.entrySet()) {
			for(String s : entry.getKey().getMultiKeys()) {
				boolean partialSearch = s.toLowerCase().startsWith(search.toLowerCase());
				boolean containsWholeWord = s.toLowerCase().contains(search.toLowerCase()); /* uncomment this for different "did you mean" results */
				if(s.equals(search)) {
					System.out.println(entry.getValue());
				}
				else if(partialSearch == true && !dupeChecker.contains(s) || containsWholeWord == true && !dupeChecker.contains(s)) {
				//else if(partialSearch == true && !dupeChecker.contains(s)) {
					dupeChecker.add(s);
				}
			}
		}
		if(dupeChecker.size() > 1) {
			System.out.println("I didn't find anything that matched " + search);
			System.out.println("did you mean:");
			for(int i = 0; i < dupeChecker.size(); i++) {
				System.out.println(i+1 + " " + dupeChecker.get(i) + " ");
			}
			System.out.println("\nType the number associated to the keyword you meant");
			Integer reSearch = kybd.nextInt();
			String reSearcher = dupeChecker.get(reSearch - 1);
			for(Map.Entry<MultiKey, String> entry : map.entrySet()) {
				for(String s : entry.getKey().getMultiKeys()) {
					if(s.equals(reSearcher)) {
						System.out.println(entry.getValue());
					}
			}
			}
		}		
	}	
	catch(InputMismatchException e) {
		System.out.println("Please Type Proper Values When Prompted");
		FileMapper.search();
		kybd.reset();
	}
	}
}		


			 
	
	
