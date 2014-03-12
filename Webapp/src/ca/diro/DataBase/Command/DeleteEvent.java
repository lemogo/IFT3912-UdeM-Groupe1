/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.SQLException;

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
	public DeleteEvent(String info) throws ClassNotFoundException, SQLException {
		
		myDb  = new DataBase() ;
		myHelper  = new DBHelper();
		can = new CancelEvent(info);
	}
	
	
	/**
	 * Method to delete  event 
	 * @param info give the eventId
	 * @return true if event removed well else false 
	 */
	public boolean removeEvent(String info) {
		//TODO perform remove query
		String  eventId = info;
		boolean returnValue = false ;
		//if(myHelper.checkEventStatus(eventId)){
			//can.nofifySignedUser(eventId) ;
		//}
		 try {
			myDb.statement().executeUpdate("delete from event where eventid = "+ eventId );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		 return returnValue ;
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
