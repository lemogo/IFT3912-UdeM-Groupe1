/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;

import ca.diro.DataBase.DataBase;

/**
 * @author william
 *
 */
public class PageInfoEvent extends Command{

	
	/**
	 * Constructor
	 * @param eventId
	 * @param db
	 */
	public PageInfoEvent(String eventId, DataBase db)   {
			myDb = db ;
			
			query_ = buildQuery(eventId);
			this.availablePlaces =  getRemainingPlaces(eventId);
		
	}
	
	/**
	 * Method to build query
	 * and build the right query
	 * @param eventId 
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String eventId)  {
		
		
		String str = "select event.title, event.dateevent, event.location, event.numberplaces, event.description, username, event.suserid from  event " +
					"join signeduser on event.suserid = signeduser.suserid "+"where event.eventid = "+ eventId ;
		return str;
	}
	
	private  int getAttendPlaces(String eventId) {
		
		int place = 0 ;
		int place1 = 0 ;
		ResultSet rs =  null ;
		
		try {
			
			rs = myDb.statement().executeQuery( "select count(eventid) from subsEventSigned " +
													"where eventid = "+ eventId);
			rs.next() ;
			place1 = rs.getInt(1);
			
			rs = myDb.statement().executeQuery( "select count(eventid) from subsEventgeneral " +
					"where eventid = "+ eventId);
			rs.next() ;
			place = rs.getInt(1);
			place = place + place1 ;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return place ;
	}
	/**
	 * Method to get the number of person who remain to complete  the event 
	 * @param place a <code>String</code> giving the Id of the event 
	 * @return place
	 */
	private  int getRemainingPlaces(String eventId){
		//TODO total places 
		int place = 0 ;
		
		try {
			
			ResultSet rs = myDb.statement().executeQuery("select numberplaces  from event " +
															"where eventid = "+ eventId );
			while (rs.next()) {
				place = rs.getInt(1);
			}
			place =   place - this.getAttendPlaces(eventId) ;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		
		return place ;
	}
	
	
	
	/**
	 * @return availablePlaces 
	 */
	public int getAvailablePlaces() {
		return availablePlaces;
	}
	/**
	 * DataBase Object
	 */
	DataBase myDb ;
	/**
	 * number of available places 
	 */
	private int availablePlaces ;
	

}
