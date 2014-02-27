/**
 * 
 */
package DataBase.Command;

/**
 * Command for getting comming event with available places
 * @author william
 *
 */
public class ListEventComing extends AbstractCommand{

	/**
	 * Constructor 
	 */
	public ListEventComing() {
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
