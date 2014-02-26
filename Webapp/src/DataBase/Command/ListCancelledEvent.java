/**
 * 
 */
package DataBase.Command;

/**
 * Command to see the list of all cancelled event 
 * @author william
 *
 */
public class ListCancelledEvent extends  AbstractCommand{

	/**
	 * 
	 */
	public ListCancelledEvent() {
		this.query_ = buildQuery() ;
	}
	
	/**
	 * Method to define query
	 * @return the query in string
	 */
	public String buildQuery(){
		String str="" ;
		return str ;
	}

}
