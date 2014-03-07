/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this class implement command to allow logged user to modified their account informations  parameter except the user's name   
 * @author william
 *
 */
public class ModifyAccount extends Command{

	/**
	 * Constructor
	 * @param info String  to build query with
	 */
	public ModifyAccount(String info) {
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
