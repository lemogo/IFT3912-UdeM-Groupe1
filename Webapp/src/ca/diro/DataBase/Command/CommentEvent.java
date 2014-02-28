/**
 * 
 */
package ca.diro.DataBase.Command;

import java.util.Map;

/**
* this class permit to set query in order to allow an signed user to comment event 
 * @author william
 */
public class CommentEvent extends AbstractCommand{
	/**
	 * @param info string 
	 */
	public CommentEvent(String info) {
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
