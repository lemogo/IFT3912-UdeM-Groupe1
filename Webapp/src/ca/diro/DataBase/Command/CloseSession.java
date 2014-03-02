/**
 * 
 */
package ca.diro.DataBase.Command;


/**
 * this class close an user's session
 * @author william
 *
 */
public class CloseSession extends AbstractCommand{
	/**
	 * Constructor 
	 * @param a string info to parse 
	 */
	public CloseSession(String info) {
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
