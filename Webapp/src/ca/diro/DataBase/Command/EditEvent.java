/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this class permit to set query in order to allow an signed user to edit  event 
 * @author william
 *
 */
public class EditEvent extends Command{

	/**
	 * Constructor 
	 * @param info string to build query with
	 */
	public EditEvent(String info) {
		query_ = buildQuery(info);
		
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * 
	 * @param info
	 *            string to build query
	 * @return a string that is the query
	 */
	private String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}
}
