/**
 * 
 */
package ca.diro.DataBase.Command;

import java.util.Map;

/**
 * this command permit user to open a session
 * @author william
 *
 */
public class OpenSession  extends AbstractCommand{

	/**
	 * @param sessionInfo string to buid query with
	 */
	public OpenSession(String sessionInfo) {
		query_ = buildQuery(sessionInfo);
		
	}
	
	/**
	 * @param info String from JSON format to be parsed and build the right query
	 * @return a string that is the query
	 */
	private String buildQuery(String info){
		String str="" ;
		//TODO parse query
		return str ;
	}
}
