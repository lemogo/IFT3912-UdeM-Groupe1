/**
 * 
 */
package ca.diro.DataBase.Command;

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
	 * Parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * 
	 * @param info
	 *            string to build query
	 * @return a string that is the query
	 */
	public String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}
}
