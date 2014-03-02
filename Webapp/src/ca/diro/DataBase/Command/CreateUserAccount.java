/**
 * 
 */
package ca.diro.DataBase.Command;


/**
 * Command to create User Account using data from user
 * 
 * @author william
 * 
 */
public class CreateUserAccount extends AbstractCommand {

	/**
	 * Constructor
	 * @param info string 
	 */
	public CreateUserAccount(String info) {
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
