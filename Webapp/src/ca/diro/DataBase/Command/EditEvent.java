/**
 * 
 */
package ca.diro.DataBase.Command;


/**
 * this class permit to set query in order to allow an signed user to edit  event 
 * @author william
 *
 */
public class EditEvent extends AbstractCommand{

	/**
	 * Constructor 
	 * @param info string to build query with
	 */
	public EditEvent(String info) {
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
