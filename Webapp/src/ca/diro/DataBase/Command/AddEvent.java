/**
 * 
 */
package ca.diro.DataBase.Command;


/**
 * this command allow signed user to add an event in his account 
 * @author william
 *
 */
public class AddEvent extends AbstractCommand{


	/**
	 * Constructor 
	 * @param a string info to parse 
	 */
	public AddEvent(String info) {
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
