/**
 * 
 */
package DataBase.Command;

import java.util.Map;

/**
 * this command permit user to open an session
 * @author william
 *
 */
public class OpenSession  extends AbstractCommand{

	/**
	 * 
	 */
	public OpenSession(Map<String,String> sessionInfo) {
		query_ = buildQuery(sessionInfo);
		
	}
	
	/**
	 * @param sessionInfo Map to be parsed and buid the right query
	 * @return a string that is the query
	 */
	public String buildQuery(Map<String,String> sessionInfo){
		String str="" ;
		return str ;
	}
}
