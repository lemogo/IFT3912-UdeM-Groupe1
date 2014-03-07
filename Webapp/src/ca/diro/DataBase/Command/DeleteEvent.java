/**
 * 
 */
package ca.diro.DataBase.Command;

/**
  * this class permit to set query in order to allow a signed user to delete event 
 * @author william
 */
public class DeleteEvent extends Command{


	/**
	 * Constructor
	 * @param info String for query
	 */
	public DeleteEvent(String info) {
		query_ = buildQuery(info);
		
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}

}
