/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * Command to see the list of all cancelled events 
 * @author william
 *
 */
public class ListCancelledEvent extends  AbstractCommand{	
	/**
	 * Constructor 
	 */
	public ListCancelledEvent() {
		this.query_ = buildQuery() ;
	}
	
	/**
	 * Method to define query
	 * @return the query in string
	 */
	private String buildQuery(){
		String str="" ;
		//TODO parse query
		return str ;
	}

}
