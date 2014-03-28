/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this this make command permitting user to open a session
 * @author william
 *
 */
public class OpenSession  extends Command{

	/**
	 * Constructor
	 * @param password 
	 * @param username
	 */
	public OpenSession(String username, String password)  {
		
			query_ = buildQuery(username, password);	
	}
	
	private String buildQuery(String username, String password)  {
		
		 String str = "select suserid from signeduser " +
						"where 	username = '"+ username + "'  and " +
								"password = '" + password +"'";
		
		return str;
	}
}
