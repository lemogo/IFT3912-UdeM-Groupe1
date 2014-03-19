/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.SQLException;

import org.json.JSONException;

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
	 * @throws JSONException 
	 * @throws ClassNotFoundException 
	 */
	public EditEvent(String info, DataBase db)  {
		myDb = db;
		try {
			jsonInfo = parseToJson(info);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method to change the event'title   event 
	 * @param info give the eventId
	 * @param title2 
	 * @return true if event title changed   well else false 
	 */
	public boolean changeEventTitle(){
		//String  eventId = info;
		//String title = "footbal" ;
		boolean returnValue = false ;
		 try {
			 int eventId = jsonInfo.getInt("eventId");
			String title = jsonInfo.getString("title");
			myDb.statement().executeUpdate("update event set title = '" + title + "' " +
											"where eventid = "+ eventId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
			}
		 return returnValue ;
	}
	/**
	 * Method to change the event'date     
	 * @param eventId <code>String</code> of the current event's id 
	 *  @param dateTime <code>String</code> of date and time of the event  
	 * @return true if event title changed   well else false 
	 */
	public boolean changeEventDatetime(){
		
		boolean returnValue = false ;
		 try {
			int eventId = jsonInfo.getInt("eventId");
			String dateTime = jsonInfo.getString("datetime");
			myDb.statement().executeUpdate("update event set dateEvent = '" + dateTime + "' " +
											"where eventid = "+ eventId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
			}
		 return returnValue ;
	}
	/**
	 * Method to change the event's location     
	 * @param eventId <code>String</code> of the current event's id 
	 * @param location  <code>String</code> giving the new location
	 * @return true if event title changed   well else false 
	 */
	public boolean changeEventLocation(){
		
		boolean returnValue = false ;
		 try {
			int eventId = jsonInfo.getInt("eventId");
			String location = jsonInfo.getString("location");
			myDb.statement().executeUpdate("update event set location = '" + location + "' " +
											"where eventid = "+ eventId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
			}
		 return returnValue ;
	}
	
	/**
	 * Method to change the event's location     
	 * @param eventId <code>String</code> of the current event's id 
	 * @param place <code>String</code> giving the maximum places expected in the event 
	 * @return true if event title changed   well else false 
	 */
	public boolean changeEventPlaces(){
		
		boolean returnValue = false ;
		 try {
			 int eventId = jsonInfo.getInt("eventId");
			int place = jsonInfo.getInt("place");
			myDb.statement().executeUpdate("update event set numberplaces = " + place + "  " +
											"where eventid = "+ eventId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
			}
		 return returnValue ;
	}
	
	/**
	 * Method to change the event's location     
	 * @param eventId <code>String</code> of the current event's id 
	 * @param description <code>String</code> giving the description of the event 
	 * @return true if event title changed   well else false 
	 */
	public boolean changeEventDescription(){
		
		boolean returnValue = false ;
		 try {
			 int eventId = jsonInfo.getInt("eventId");
			 String description = jsonInfo.getString("description");
			myDb.statement().executeUpdate("update event set description = '" + description + "' " +
											"where eventid = "+ eventId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
			}
		 return returnValue ;
	}
		
	
	/**
	 * DataBase Object
	 */
	DataBase myDb ;
}
