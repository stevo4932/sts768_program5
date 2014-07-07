import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PasswordCrack{

	private static LinkedList<String> makeWords(File file){
		try{
			Scanner scr = new Scanner(file);
			LinkedList<String> list = new LinkedList<String>();
			while(scr.hasNextLine())
				list.add(scr.nextLine());
			return list;
		}catch(FileNotFoundException e){System.out.println("Can't open word list.");return null;}
	}

	private static LinkedList<User> makeUsers(File file){
		try{
			Scanner scr = new Scanner(file);
			LinkedList<User> list = new LinkedList<User>();
			while(scr.hasNextLine())
				list.add(new User(scr.nextLine()));
			return list;
		}catch(FileNotFoundException e){return null;}
	}
	//add user specific words to list. 
	//Input: User and wordlist
	//Output: updated word list. 
	private static LinkedList<String> addWords(User u, LinkedList<String> list){
		//add anything else you think you might need. 
		list.addFirst(u.getFirstName().toLowerCase());
		list.addFirst(u.getLastName().toLowerCase());
		list.addFirst(new String(u.getFirstName()+u.getLastName()));
		return list;
	}


	public static String processWord(String password, int i, char c){
		Options op = new Options();
		//System.out.println("entered i: "+i);
		switch(i){
			case 1: 
				return op.allUpper(password);
			case 2: 
				return op.allLower(password);
			case 3:
				return op.reverse(password);
			case 4:
				return op.capitalize(password);
			case 5:
				return op.duplicate(password);
			case 6:
				return op.reflect(password);
			case 7:
				return op.deleteLast(password);
			case 8:
				return op.deleteFirst(password);
			case 9:
				return op.toggle0(password);
			case 10:
				return op.toggle1(password);
			case 11:
				return op.prepend(password, c);
			case 12:
				return op.append(password, c);
			case 13:
				return op.nCapitalize(password, c);
			default:
				return password;
		}
	}

	private static String loop1(User u, LinkedList<String> words){
		words = addWords(u, words);
		Options op = new Options();
		jcrypt crypt = new jcrypt();
		Iterator<String> itr = words.iterator();
		while(itr.hasNext()){
			String word = itr.next();
			//for(int i = 0; i < op.length(); i++){ //process one
				for(int j = 0; j < op.length(); j++){ //process two
					if(j == 11 || j == 12){
						for(char c = 33; c < 127; c++){
							String newWord = crypt.compCrypt(u.getPassword(), u.getSalt(), processWord(word, j, c));
							if(newWord != null)
								return newWord;
						}
					}else if(j == 13){
						for(char k = 0; k <= word.length(); k++){
							String newWord = crypt.compCrypt(u.getPassword(), u.getSalt(), processWord(word, j, k));
							if(newWord != null)
								return newWord;
						}
					}else{
						String newWord = crypt.compCrypt(u.getPassword(), u.getSalt(), processWord(word, j, (char)0));
						if(newWord != null)
							return newWord;
					}
				}
			//}
		}
		return loop2(u, words);
	}	

	public static String loop2(User u, LinkedList<String> words){
		Options op = new Options();
		jcrypt crypt = new jcrypt();
		Iterator<String> itr = words.iterator();
		while(itr.hasNext()){
			String word = itr.next();
			for(int i = 1; i < op.length(); i++){ //process one
				for(int j = 1; j < op.length(); j++){ //process two
					
					if(i == 13 && (j == 11 || j == 12)){
						for(char l = 33; l < 127; l++){
							for(char k = 1; k <= word.length(); k++){
								String newWord = crypt.compCrypt(u.getPassword(), u.getSalt(), processWord(processWord(word, i, k), j, l));
								if(newWord != null)
									return newWord;
							}
						}
					}else if(j == 13 && (i == 11 || i == 12)){
						for(char l = 33; l < 127; l++){
							for(char k = 1; k <= word.length(); k++){
								String newWord = crypt.compCrypt(u.getPassword(), u.getSalt(), processWord(processWord(word, i, l), j, k));
								if(newWord != null)
									return newWord;
							}
						}
					}else if(i == 11 || j == 11 || i == 12 || j == 12){
						for(char c = 33; c < 127; c++){
							String newWord = crypt.compCrypt(u.getPassword(), u.getSalt(), processWord(processWord(word, i, c), j, c));
							if(newWord != null)
								return newWord;
						}
					}else if (i == 13 ^ j == 13){
						for(char k = 1; k <= word.length(); k++){
							String newWord = crypt.compCrypt(u.getPassword(), u.getSalt(), processWord(processWord(word, i, k), j, k));
							if(newWord != null)
								return newWord;
						}
					}else if( i == 13 && j == 13){
						for(char l = 0; l <= word.length(); l++){
							for(char k = 1; k <= word.length(); k++){
								String newWord = crypt.compCrypt(u.getPassword(), u.getSalt(), processWord(processWord(word, i, k), j, l));
								if(newWord != null)
									return newWord;
							}
						}
					}else{
						String newWord = crypt.compCrypt(u.getPassword(), u.getSalt(), processWord(processWord(word, i, (char)0), j, (char)0));
						if(newWord != null)
							return newWord;
					}
				}
			}
		}
		return null;
	}


	//Input; possible passwords
	//Output: User's password (null if no Matches)
	public static void main(String[] args){
		StopWatch watch = new StopWatch();
		File dictionary = new File(args[0]);
		File passwords = new File(args[1]);

		LinkedList<User> userList = makeUsers(passwords);
		LinkedList<String> wordList = makeWords(dictionary);
		Iterator<User> itr = userList.iterator();
		watch.start();
		while(itr.hasNext()){	
			User u = itr.next();
			System.out.println(u.getFirstName()+ "  " +loop1(u, wordList));
		}	
		watch.stop();
		System.out.println("Elapsed Time: "+ watch.getElapsedTimeSecs());	
	}
}