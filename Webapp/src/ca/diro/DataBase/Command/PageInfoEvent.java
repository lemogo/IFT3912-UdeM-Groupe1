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
	 * @throws JSONException 
	 * 
	 */
	public PageInfoEvent(String info, DataBase db)   {
			myDb = db ;
			try {
				jsonInfo = parseToJson(info);
				query_ = buildQuery();
				this.availablePlaces =  getRemainingPlaces(jsonInfo.getInt("eventId"));
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
			
	
		
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 * @throws JSONException 
	 */
	private String buildQuery() throws JSONException {
		
		int eventId = jsonInfo.getInt("eventId");
		String str = "select title, dateevent, location, numberplaces, description from  event " +
					"where 	eventid = "+ eventId ;
		// TODO parse query
		return str;
	}
	
	private  int getAttendPlaces(int eventId) {
		//TODO attended places
		int place = 0 ;
		int place1 = 0 ;
		ResultSet rs =  null ;
		
		try {
			//int eventId = jsonInfo.getInt("eventId");
			rs = myDb.statement().executeQuery( "select count(eventid) from subsEventSigned " +
													"where eventid = "+ eventId);
			while (rs.next()) {
				place1 = rs.getInt(1);
				//System.out.println(rs.getInt(1));
			}
			rs = myDb.statement().executeQuery( "select count(eventid) from subsEventgeneral " +
					"where eventid = "+ eventId);
			while (rs.next()) {
				place = rs.getInt(1);
			//System.out.println(rs.getInt(1));
			}
			place = place = place1 ;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		/* catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
		}*/
		//place = rs.getInt(1);
		return place ;
	}
	/**
	 * Method to get the number of person who remain to complete  the event 
	 * @param place a <code>String</code> giving the Id of the event 
	 * @return
	 */
	private  int getRemainingPlaces(int eventId){
		//TODO total places 
		int place = 0 ;
		
		try {
			//int eventId = jsonInfo.getInt("eventId");
			ResultSet rs = myDb.statement().executeQuery("select numberplaces  from event " +
															"where eventid = "+ eventId );
			while (rs.next()) {
				place = rs.getInt(1);
				//System.out.println(rs.getInt(1));
			}
			place =   place - this.getAttendPlaces(eventId) ;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		// catch (JSONException e1) {
			 //	System.err.println(e1.getMessage());
				//e1.printStackTrace();
		//}
		
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
