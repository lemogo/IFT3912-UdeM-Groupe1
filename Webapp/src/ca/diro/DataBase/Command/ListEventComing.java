/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * Command for getting coming event with available places
 * @author william
 *
 */
public class ListEventComing extends Command{

	/**
	 * Constructor 
	 */
	public ListEventComing() {
		this.query_ = buildQuery() ;
	}
	
	/**
	 * Method to define query
	 * @return str <code>String</code> Object the query
	 */
	private String buildQuery(){
		String str="" ;
		//TODO parse query
		return str ;
	}
	
	/**
	 * Method for get available places of each coming event 
	 * @return place the number of available places for each event 
	 */
	public int availablePLaces(){
		//TODO nombre de place disponible
		int place = 0 ;
		return place ;
	} 

}
