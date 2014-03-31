/**
 * 
 */
package ca.diro.DataBase.Command;
//import java.sql.SQLException;
//import ca.diro.DataBase.DataBase;

/**
* this class permit to set query in order to allow an signed user retrieve the list of comments on an event 
 * @author Lionnel
 */
public class ListCommentEvent extends Command{
	/**
	 * Constructor 
	 * @param eventId : String 
	 */
	public ListCommentEvent(String  eventId) {
		query_ = buildQuery(eventId);	
	}
	
	/**
	 * Method to build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String eventId) {
		return "select description, datecreation, suserid from  commentevent " +
						"where 	eventid = "+ eventId  ;
	}
}
