/**
 * 
 */
package ca.diro.DataBase.Command;


/**
 * this class make the command to view all event that one user has created
 * @author william
 *
 */
public class ListEventByUser extends AbstractCommand{
	/**
	 * Constructor 
	 * @param info string to build query 
	 */
	public ListEventByUser(String info) {
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
