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

	
	public ResearchEvent(Map<String,String> info) {
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
