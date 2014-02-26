/**
 * 
 */
package DataBase.Command;

import java.util.Map;

/**
 * This command run the query that allow anonymous user to subscribe in one  event 
 * @author william
 *
 */
public class SubscribeToEvent extends AbstractCommand{

	public SubscribeToEvent(Map<String,String> info) {
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
