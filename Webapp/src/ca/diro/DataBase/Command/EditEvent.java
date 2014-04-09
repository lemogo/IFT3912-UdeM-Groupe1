/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this class permit to set query in order to allow an signed user to edit  event 
 * @author william
 *
 */
public class EditEvent extends CommandUpdate{

	/**
	 * Constructor 
	 * @param eventId String to build query with
	 * @param dateTime 
	 * @param location 
	 * @param nbrPlaces 
	 * @param description 
	 *
	 * @throws ClassNotFoundException 
	 */
	public EditEvent(String eventId, String title, String dateTime, String location, String nbrPlaces, String description)  {
		
		query_ = changeEventInformation( eventId,  title, dateTime,  location,  nbrPlaces,  description);
		
	}
	
	/**
	 * Method to change the event'information   event 
	 * @param eventId String 
	 * @param title String
	 * @param dateTime String
	 * @param location String
	 * @param nbrPlaces String
	 * @param description String
	 * @return str <code>String</code> which is the query
	 */
	private String changeEventInformation(String eventId, String title, String dateTime, String location, String nbrPlaces, String description){
		
		String str = "" ;	
		str = "update event set title = '" + title + "' , dateEvent = '" + dateTime + "', location = '" + location + "'  ," +
																		"numberplaces = " + nbrPlaces+ "  ,description = '" + description + "' " +
																		"where eventid = "+ eventId  ;
		 return str ;
	}
	
}
