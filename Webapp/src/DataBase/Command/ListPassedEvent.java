/**
 * 
 */
package DataBase.Command;

/**
 * Command to see passed event with number people attended
 * @author william
 *
 */
public class ListPassedEvent extends  AbstractCommand{

	/**
	 * 
	 */
	public ListPassedEvent() {
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