/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.SQLException;

import org.json.JSONException;

import ca.diro.DataBase.DataBase;

/**
 * this command allow signed user to add an event in his account
 * 
 * @author william
 * 
 */
public class AddEvent extends Command {
	/**
	 * Constructor
	 * 
	 * @param a string info to parse
	 * @throws JSONException 
	 * @throws ClassNotFoundException 
	 */
	public AddEvent(String info, DataBase db )   {
		myDb  = db;
		try {
			jsonInfo = parseToJson(info);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
	public AddEvent(String userId, String title, String date,
			String location, String nbplace, String description, DataBase db )   {
		myDb  = db;
		try {
			addNewEvent(userId, title, date, location, nbplace, description);
		} catch (SQLException e) {
		 	System.err.println(e.getMessage());
		}
	}

	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info string type
	 * @return a string that is the query
	 */
	private String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}
	
	/**
	 * Method to handle creation of account from anonymous user 
	 * @param info <code>String</code> Object to data of new created event get d
	 * @return true if successful execution else false
	 * @throws  
	 */
	public boolean addNewEvent() {
		
		boolean returnValue = false ;
		try {
				String userId = jsonInfo.getString("userId") ;
				String title = jsonInfo.getString("title") ;
				String date = jsonInfo.getString("datetime") ;
				String location = jsonInfo.getString("location") ;
				String nbplace = jsonInfo.getString("numberplaces") ;
				String description = jsonInfo.getString("description") ;
			
				returnValue = addNewEvent(userId, title, date, location,
						nbplace, description);
				
		 } catch (SQLException e) {
			 	System.err.println(e.getMessage());
		}
		catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
		}
		return returnValue ;
		
		//TODO create user account  	
	}

	public boolean addNewEvent(String userId, String title, String date,
			String location, String nbplace, String description)
			throws SQLException {
		boolean returnValue;
		myDb.statement().executeUpdate("insert into event  (title, suserid, dateevent, location, description, numberplaces) "  +
				"values('" + title +"', '" + userId +"', '" + date +"', '"+ location  +"', '" + description + "' , "+ nbplace + ")");
		returnValue = true ;
		return returnValue;
	}
	
	
	/**
	 * object DataBase 
	 */
	private DataBase myDb ;
	

}
