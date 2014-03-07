/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * Command to see all event where a signed user is subscripted 
 * @author william
 *
 */
public class ListRegisterEvent extends Command{

	/**
	 * @param info String to build query
	 */
	public ListRegisterEvent(String info) {
		query_ = buildQuery(info);	
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	public String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}
}
