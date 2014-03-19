/**
 * 
 */
package ca.diro.DataBase.Command;

import org.json.JSONException;

/**
 * this this make command permitting user to open a session
 * @author william
 *
 */
public class OpenSession  extends Command{

	/**
	 * @param sessionInfo String to build query with
	 * @throws JSONException 
	 */
	public OpenSession(String info)  {
		try {
			jsonInfo = parseToJson(info);
			query_ = buildQuery();
		} catch (JSONException e) {	
			e.printStackTrace();
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
		
		
		 String password = jsonInfo.getString("password");
		 String userName = jsonInfo.getString("userName");
		 String str = "select suserid from signeduser " +
						"where 	username = '"+ userName + "'  and " +
								"password = '" + password +"'";
		// TODO parse query
		return str;
	}
}
