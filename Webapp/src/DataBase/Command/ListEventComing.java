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
	 * 
	 */
	public ListEventComing() {
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
