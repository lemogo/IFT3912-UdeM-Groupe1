/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * THis class implement the Command to see passed events and the number of people attended
 * @author william
 *
 */
public class ListPassedEvent extends  Command{

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
