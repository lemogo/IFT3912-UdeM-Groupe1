/**
 * 
 */
package DataBase.Command;

import java.util.Map;

/**
 * this class close an user's session
 * @author william
 *
 */
public class CloseAccount extends AbstractCommand{

	
	public CloseAccount(Map<String,String> info) {
		query_ = buildQuery(info);
		
	}
	
	/**
	 * @param info Map to be parsed and build the right query
	 * @return a string that is the query
	 */
	public String buildQuery(Map<String,String> info){
		String str="" ;
		return str ;
	}

}