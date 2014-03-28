/**
 * 
 */
package ca.diro.DataBase.Command;

/**
  * this class permit to set query in order to allow a signed user to delete event 
 * @author william
 */
public class DeleteEvent extends CommandUpdate{

	/**
	 * Constructor
	 * @param eventId string for query
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public DeleteEvent(String eventId)  {
		//myDb  = db;
		query_ = removeEvent(eventId)  ;

	}
	
	/**
	 * Method to delete  event 
	 * @param eventId give the eventId
	 * @return str <code>String</code> a String object which is the query 
	 */
	private String removeEvent(String eventId) {
		//TODO perform remove query
		
		String str = "";
		str = "delete from event where eventid = "+ eventId ;
		return str ;
	}


	
}
