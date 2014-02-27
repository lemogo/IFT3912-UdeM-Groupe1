/**
 * 
 */
package DataBase.Command;

import java.util.Map;

/**
 * This class run build query for searching event using events at title and description as criteria 
 * @author william
 *
 */
public class ResearchEvent extends AbstractCommand{

	
	/**
	 * Constructor 
	 * @param info string to build query
	 */
	public ResearchEvent(String info) {
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
