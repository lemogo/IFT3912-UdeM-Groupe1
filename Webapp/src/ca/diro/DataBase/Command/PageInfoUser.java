/**
 * 
 */
package ca.diro.DataBase.Command;

import org.json.JSONException;

/**
 * this command class give information on a user that can be use to generate user's own page
 * @author william
 *
 */
public class PageInfoUser extends Command{
	/**
	 * Constructor
	 * @param info String to build query
	 * @throws JSONException 
	 */
	public PageInfoUser(String info) throws JSONException {
		if (info!= null){
			jsonInfo = parseToJson(info);
			query_ = buildQuery();
		}
		else{
			query_ = null ;
		}
		
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 * @throws JSONException 
	 */
	private String buildQuery() throws JSONException {
		
		int userId = jsonInfo.getInt("userId");
		String str = "select fullname, username, email, age, description from  signeduser " +
					"where 	suserid = "+ userId ;
		// TODO parse query
		return str;
	}
	
}
