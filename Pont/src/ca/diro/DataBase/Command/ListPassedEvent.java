/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * Command to see passed event with number people attended
 * @author william
 *
 */
public class ListPassedEvent extends  AbstractCommand{

	/**
	 * Constructor
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
		//TODO parse query
		return str ;
	}

}
