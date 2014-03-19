/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * Command for getting coming event with available places
 * @author william
 *
 */
public class ListComingEvent extends Command{

	/**
	 * Constructor 
	 */
	public ListComingEvent() {
		this.query_ = buildQuery() ;
	}
	
	/**
	 * Method to define query
	 * @return str <code>String</code> Object the query
	 */
	private String buildQuery(){
		String str="select eventid, title, location, dateevent, description from event " +
				"where dateevent > CURRENT_DATE()"  ;
		//TODO parse query
		return str ;
	}
	
	

}
