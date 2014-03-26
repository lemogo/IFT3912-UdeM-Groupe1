/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.SQLException;

import org.json.JSONException;

import ca.diro.DataBase.*;

/**
 * this class permit to set query in order to allow a signed user to delete event 
 * @author william
 */
public class DeleteEvent extends Command{


	/**
	 * Constructor
	 * @param info string for query
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public DeleteEvent(String info , DataBase db) throws ClassNotFoundException, SQLException {
		myDb  = db;
		try {
			jsonInfo = parseToJson(info);
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	public DeleteEvent( DataBase db) {
		myDb  = db;
	}

	/**
	 * Method to delete  event 
	 * @param info give the eventId
	 * @return true if event removed well else false 
	 */
	public boolean removeEvent(String info) {
		//TODO perform remove query

		boolean returnValue = false ;
		//if(myHelper.checkEventStatus(eventId)){
		//can.nofifySignedUser(eventId) ;
		//}
		try {
			String  eventId = jsonInfo.getString("eventId") ;
			returnValue = removeEvent(Integer.parseInt(eventId));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		catch (JSONException e1) {
			System.err.println(e1.getMessage());
			e1.printStackTrace();
		}

		return returnValue ;
	}


	public boolean removeEvent(int eventId) throws SQLException {
		boolean returnValue;
		myDb.statement().executeUpdate("delete from event where eventid = "+ eventId );
		returnValue = true;
		return returnValue;
	}

	/**
	 * Object DataBase
	 */
	DataBase  myDb ; 

	/**
	 * Objet DBHelper 
	 */
	DBHelper myHelper ;
	/**
	 * CancelEvent Object to notify users registered in that event 
	 */
	CancelEvent  can ; 

}
