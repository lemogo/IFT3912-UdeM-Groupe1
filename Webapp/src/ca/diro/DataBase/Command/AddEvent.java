/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.ResultSet;
import java.sql.SQLException;
import ca.diro.DataBase.DataBase;

/**
 * this command allow signed user to add an event in his account
 * 
 * @author william
 * 
 */
public class AddEvent extends CommandUpdate {
	
	public AddEvent(String userId, String title, String date,String location, String nbplace, String description, DataBase db)   {
			myDb = db ;
			query_ = addNewEvent(userId, title, date, location, nbplace, description);
			//getCurrentEventId();
		
	}

	/**
	 *  Method to  build the right query
	 * @param userId string
	 * @param title string
	 * @param date string
	 * @param location string
	 * @param nbplace string
	 * @param description string
	 *  @return a <code>String</code> that is the query
	 */
	public String addNewEvent(String userId, String title, String date,String location, String nbplace, String description){
		String str = "";
		str = "insert into event  (title, suserid, dateevent, location, description, numberplaces) "  +
				"values('" + title +"', " + userId +", '" + date +"', '"+ location  +"', '" + description + "' , "+ nbplace + ")";
		
		return str ;
	}
	
	public ResultSet getCurrentEventId(){
		
		String str = "select max(eventid) from event "  ;
					
		try {
			curentId   = myDb.statement().executeQuery(str);
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return curentId ;
	}
	
	
	/**
	 * @return curentId <code>ResultSet</code> Object the new current eventId
	 */
	public ResultSet getCurentId() {
		return curentId;
	}
	
	/**
	 * 
	 * Database Object
	 */
	DataBase myDb ; 
	/**
	 * ResultSet
	 */
	ResultSet curentId  ;
	
	
	
}
