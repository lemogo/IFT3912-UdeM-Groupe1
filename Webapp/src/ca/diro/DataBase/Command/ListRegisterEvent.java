/**
 * 
 */
package ca.diro.DataBase.Command;


/**
 * Command to see all event where a signed user is subscripted 
 * @author william
 *
 */
public class ListRegisterEvent extends AbstractCommand{

	/**
	 * @param info string to build query
	 */
	public ListRegisterEvent(String info) {
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
