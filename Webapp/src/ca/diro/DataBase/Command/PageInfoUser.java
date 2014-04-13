/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this command class give information on a user that can be use to generate user's own page
 * @author william
 *
 */
public class PageInfoUser extends Command{
	/**
	 * Constructor
	 * @param userId String 
	 */
	public PageInfoUser(int userId)  {
			query_ = buildQuery(userId);
	}
	
	/**
	 * Method to  build the right query
	 * @param userId 
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(int userId)  {
	
		String str = "select * from  signeduser " +
					"where 	suserid = "+ userId ;
		return str;
	}
	
	/**
	 * Constructor
	 * @param username String 
	 */
	public PageInfoUser(String username)  {
			query_ = buildQuery(username);
	}
	
	/**
	 * Method to  build the right query
	 * @param username 
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String username)  {
		
		String str = "select fullname, username, password, email, age, description, datecreation, suserid from  signeduser " +
					"where 	username = '"+ username +"'";
		return str;
	}
}
