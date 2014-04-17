/**
 * 
 */
package ca.diro.DataBase.Command;



/**
 * this class implement command to allow logged user to modified their account informations  parameter except the user's name   
 * @author william
 *
 */
public class ModifyAccount extends CommandUpdate{

	/**
	 * Constructor
	 * @param userId 
	 * @param email 
	 * @param username 
	 * @param password 
	 * @param age 
	 * @param description 
	 * 
	 */
	public ModifyAccount(String userId, String fullname, String email, String username, String password, String age, String description )   {
		
		query_ = changeUserInformation(userId,fullname, email,  username,  password,age, description) ; 
	}
	
	/**
	 * Constructor
	 * @param userId 
	 * @param email 
	 * @param fullname 
	 * @param password 
	 * @param age 
	 * @param description 
	 * 
	 */
	public ModifyAccount(String userId, String fullname, String age, String description )   {
		
		query_ = changeUserInformation(userId,  fullname, age, description) ; 
	}
	
	/**
	 * Method to build the query to set user informations 
	 * @param userId 
	 * @param fullname
	 * @param email
	 * @param username
	 * @param password
	 * @param age
	 * @param description
	 * @return str <code>String</code> which is the query to be executed 
	 */
	private String changeUserInformation(String userId, String fullname, String email, String username, String password, String age, String description ){
		
		String str = "" ;	
		str = "update signeduser set fullname = '" + fullname + "' , email = '" + email + "', username = '" + username + "'  ," +
									"password = '" + password + "'  ,description = '" + description + "',  age = '" + age   +
									"' where suserid = "+ userId ;
		 return str ;
	}
	
	
	/**
	 * Constructor
	 * @param userId 
	 * @param email 
	 * @param username 
	 * @param age 
	 * @param description 
	 * 
	 */
	public ModifyAccount(String userId, String fullname, String email, String username,  String age, String description )   {
		
		query_ = changeUserInformation(userId,fullname, email,  username,  age, description) ; 
	}
	
	
	/**
	 * Method to build the query to set user informations 
	 * @param userId 
	 * @param fullname
	 * @param email
	 * @param username
	 * @param age
	 * @param description
	 * @return str <code>String</code> which is the query to be executed 
	 */
	private String changeUserInformation(String userId, String fullname, String email, String username, String age, String description ){
		
		String str = "" ;	
		str = "update signeduser set fullname = '" + fullname + "' , email = '" + email + "', username = '" + username
				+ "'  ,description = '" + description + "',  age = " + age   +
									" where suserid = "+ userId ;
		 return str ;
	}

	/**
	 * Method to build the query to set user informations 
	 * @param userId 
	 * @param fullname
	 * @param email
	 * @param username
	 * @param age
	 * @param description
	 * @return str <code>String</code> which is the query to be executed 
	 */
	private String changeUserInformation(String userId, String fullname, String age, String description ){
		
		String str = "" ;	
		str = "update signeduser set fullname = '" + fullname
				+ "'  ,description = '" + description + "',  age = '" + age   +
									"' where suserid = "+ userId ;
		 return str ;
	}
	

}
