/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.SQLException;

import org.json.JSONException;

import ca.diro.DataBase.DataBase;

/**
 * This class implement a command  allowing anonymous user to subscribe in one  event 
 * @author william
 *
 */
public class SubscriteToEvent extends Command{

	/**
	 * Constructor 
	 * @param info String to build query
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public SubscriteToEvent(String info , DataBase db)  {
		myDb  = db;
		try {
			jsonInfo = parseToJson(info);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}

		
	}
	
	/**
	 * Method to handle subscription in event by anonymous user 
	 * @param info <code>String</code> Object to retrieve anonymous user subscription information
	 * @return true if successful execution else false
	 */
	public boolean anonymSubsEvent(String info){
		
		
		
		boolean returnValue = false ;
		try {
				int eventId = jsonInfo.getInt("eventId");
				myDb.statement().executeUpdate("insert into generaluser" );
			 
				myDb.statement().executeUpdate("insert into subsEventGeneral values("+ eventId +")");
				returnValue = true ;
				
		 } catch (SQLException e) {
			 System.err.println(e.getMessage());
		}
		 catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
		}
		 return returnValue ;
		
		//TODO anonym user subscription event 
		
	}
	/**
	 * Method to handle subscription in event by signed user 
	 * @param info <code>String</code> Object to retrieve anonymous user subscription information
	 * @return true if successful execution else false
	 */
	public boolean signedUserSubs(String info){
		//TODO signed user subscription event
		
		boolean returnValue = false ;
		try {
			int eventId = jsonInfo.getInt("eventId");
			int userId = jsonInfo.getInt("userId");
			myDb.statement().executeUpdate("insert into subsEventSigned values("+ eventId + ", "+ userId +")");
			returnValue = true ;
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
	 * Object DataBase to execute update subscription in an event 
	 */
	private DataBase myDb ;
	
}
