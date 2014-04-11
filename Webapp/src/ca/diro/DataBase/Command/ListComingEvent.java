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
		String str="select event.eventid, event.title, event.location, event.dateevent, event.description, event.numberplaces"
				+ ", event.suserid, username "
				+ "from event " +
				"join signeduser on event.suserid = signeduser.suserid "+
				"where dateevent > CURRENT_DATE() and UPPER(status) not = 'CANCELLED'"  ;
		return str ;
	}

	

}
