/**
 * 
 */
package DataBase.Command;

import java.util.Map;

/**
 * this class permit to set query in order to allow an signed user to cancel event 
 * @author william
 */
public class CancelEvent extends AbstractCommand{

	/**
	 * 
	 */
	public CancelEvent(Map<String,String> info) {
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