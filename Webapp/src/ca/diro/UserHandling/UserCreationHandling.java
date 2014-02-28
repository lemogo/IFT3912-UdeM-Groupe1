package ca.diro.UserHandling;

/**
 * Handle the creation of a user in the database
 * 
 * @author girardil
 * @version 1.0
 */
public class UserCreationHandling {
	/**
	 * Access database to create an <code>User</code> with
	 * given information
	 * 
	 * /**
	 * @param fullName
	 * 				The full name of the new <code>User</code>
	 * @param email
	 * 				The email of the new <code>User</code>
	 * @param login
	 * 				The user name of the new <code>User</code>
	 * @param password
	 * 				The password of the new <code>User</code>
	 * @param age
	 * 				The age of the new <code>User</code>
	 * @param desc
	 * 				The description text of the new <code>User</code>
	 *
	 */
	public void createUser(String fullName, String email, String login, String password, String age, String desc){
		String allInfos = fullName + ";" + email + ";" + login + ";" + password + ";" + age + ";" + desc;
		// TODO: Register in DB
	}
	
}
