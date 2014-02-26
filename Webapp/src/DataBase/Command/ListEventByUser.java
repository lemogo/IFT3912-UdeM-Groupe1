/**
 * 
 */
package DataBase.Command;

import java.util.Map;

/**
 * this class make the command to view all event that one user has created
 * @author william
 *
 */
public class ListEventByUser extends AbstractCommand{

	/**
	 * 
	 */
	public ListEventByUser(Map<String,String> info) {
		query_ = buildQuery(info);
		
	}
	
	/**
	 * @param userInfo Map to be parsed and build the right query
	 * @return a string that is the query
	 */
	public String buildQuery(Map<String,String> info){
		String str="" ;
		return str ;
	}

}
