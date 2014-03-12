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
	 * @param sessionInfo String to build query with
	 */
	public OpenSession(String sessionInfo) {
		query_ = buildQuery(sessionInfo);
		
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String info) {
		String userName = "pat"  ;
		String password = "patson" ;
		
		String str = "select suserid from signeduser " +
						"where 	username = '"+ userName + "'  and " +
								"password = '" + password +"'";
		// TODO parse query
		return str;
	}
}
