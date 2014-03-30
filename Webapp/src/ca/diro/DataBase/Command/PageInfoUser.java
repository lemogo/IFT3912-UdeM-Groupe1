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
	public PageInfoUser(String userId)  {
			query_ = buildQuery(userId);
	}
	
	/**
	 * Method to  build the right query
	 * @param userId 
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String userId)  {
		
		//int userId = jsonInfo.getInt("userId");
		String str = "select fullname, username, password, email, age, description from  signeduser " +
					"where 	suserid = "+ userId ;
		return str;
	}
	
}
