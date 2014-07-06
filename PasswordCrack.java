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
	//Input; possible passwords
	//Output: User's password (null if no Matches)
	private static String findPassword(User u, LinkedList<String> list){
		jcrypt crypt = new jcrypt();
		Options op = new Options();
		list = addWords(u, list);
		Iterator<String> itr = list.iterator();
		while(itr.hasNext()){
			String word = itr.next();
			for(int i = 0; i < op.length(); i++){
				if(i < 11){
					String output = crypt.compCrypt(u.getPassword(), u.getSalt(),processWord(word, i, (char)1));
					if(output != null)
						return output;
				}else if( i == 11 || i == 12){
					for(char c = 33; c < 127; c++){
						String output = crypt.compCrypt(u.getPassword(), u.getSalt(),processWord(word, i, c));
						if(output != null)
							return output;
					}
				}else{
					for(char c = 0; c <= word.length(); c++){
						String output = crypt.compCrypt(u.getPassword(), u.getSalt(),processWord(word, i, c));
						if(output != null)
							return output;
					}
				}
			}
		}
		return null;
	}

	public static void main(String[] args){
		File dictionary = new File(args[0]);
		File passwords = new File(args[1]);

		LinkedList<User> userList = makeUsers(passwords);
		LinkedList<String> wordList = makeWords(dictionary);
		Iterator<User> itr = userList.iterator();
		while(itr.hasNext()){	
			User u = itr.next();
			String result = findPassword(u, wordList);
			String user = u.getFirstName();
			System.out.println(user+ "  " +result);
		}		
	}
}