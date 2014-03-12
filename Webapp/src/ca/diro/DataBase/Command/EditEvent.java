/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.SQLException;

import ca.diro.DataBase.*;

/**
 * this class permit to set query in order to allow an signed user to edit  event 
 * @author william
 *
 */
public class EditEvent extends Command{

	/**
	 * Constructor 
	 * @param info String to build query with
	 * @throws ClassNotFoundException 
	 */
	public EditEvent(String info) throws ClassNotFoundException {
		query_ = buildQuery(info);
		myDb = new DataBase() ;
		
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}
	
	/**
	 * Method to change the event'title   event 
	 * @param info give the eventId
	 * @param title2 
	 * @return true if event title changed   well else false 
	 */
	public boolean changeEventTitle(String eventId , String title){
		//String  eventId = info;
		//String title = "footbal" ;
		boolean returnValue = false ;
		 try {
			myDb.statement().executeUpdate("update event set title = '" + title + "' " +
											"where eventid = "+ eventId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	/**
	 * Method to change the event'date     
	 * @param eventId <code>String</code> of the current event's id 
	 *  @param dateTime <code>String</code> of date and time of the event  
	 * @return true if event title changed   well else false 
	 */
	public boolean changeEventDatetime(String eventId , String dateTime){
		
		boolean returnValue = false ;
		 try {
			myDb.statement().executeUpdate("update event set dateEvent = '" + dateTime + "' " +
											"where eventid = "+ eventId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	/**
	 * Method to change the event's location     
	 * @param eventId <code>String</code> of the current event's id 
	 * @param location  <code>String</code> giving the new location
	 * @return true if event title changed   well else false 
	 */
	public boolean changeEventLocation(String eventId , String location){
		
		boolean returnValue = false ;
		 try {
			myDb.statement().executeUpdate("update event set location = '" + location + "' " +
											"where eventid = "+ eventId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	
	/**
	 * Method to change the event's location     
	 * @param eventId <code>String</code> of the current event's id 
	 * @param place <code>String</code> giving the maximum places expected in the event 
	 * @return true if event title changed   well else false 
	 */
	public boolean changeEventPlaces(String eventId , String place){
		
		boolean returnValue = false ;
		 try {
			myDb.statement().executeUpdate("update event set numberplaces = " + place + "  " +
											"where eventid = "+ eventId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	
	/**
	 * Method to change the event's location     
	 * @param eventId <code>String</code> of the current event's id 
	 * @param description <code>String</code> giving the description of the event 
	 * @return true if event title changed   well else false 
	 */
	public boolean changeEventDesc(String eventId , String description){
		
		boolean returnValue = false ;
		 try {
			myDb.statement().executeUpdate("update event set description = '" + description + "' " +
											"where eventid = "+ eventId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	
	
	
	DataBase myDb ;
}
