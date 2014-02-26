/**
 * 
 */
package DataBase.Command;

import java.util.Map;

/**
 * Command to see all event where a signed user is subscripted 
 * @author william
 *
 */
public class ListRegisterEvent extends AbstractCommand{

	public ListRegisterEvent(Map<String,String> info) {
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