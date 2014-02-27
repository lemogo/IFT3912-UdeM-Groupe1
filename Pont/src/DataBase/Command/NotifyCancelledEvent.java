/**
 * 
 */
package DataBase.Command;

import java.util.Map;

/**
 * this class make command to notify users subscripted in an event when  cancelled 
 * @author william
 *
 */
public class NotifyCancelledEvent extends AbstractCommand{
	
	/**
	 * Constructor 
	 * @param info sting to build query with
	 */
	public NotifyCancelledEvent(String info) {
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
