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

	
	/** Constructor 
	 * @param info string to build query
	 */
	public CancelEvent(String info) {
		query_ = buildQuery(info);	
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
