public class User{
	private String first;
	private String last; 
	private String salt;
	private String password;

	public User(String s){
		String[] str = s.split(":");
		salt = str[1].substring(0,2);
		password = str[1].substring(2);
		String[] name = str[4].split(" ");
		first = name[0];
		last = name[1];

	}

	public String getFirstName(){
		return first;
	}
	public String getLastName(){
		return last;
	}
	public String getSalt(){
		return salt;
	}
	public String getPassword(){
		return password;
	}
}