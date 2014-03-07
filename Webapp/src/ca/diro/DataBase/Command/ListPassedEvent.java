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
	 * Method for get available places of each coming event 
	 * @return place the number of available places for each event 
	 */
	public String buildQuery(){
		String str="" ;
		//TODO parse query
		return str ;
	}

}
