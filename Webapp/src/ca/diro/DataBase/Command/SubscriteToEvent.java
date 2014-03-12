/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.SQLException;

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
	 */
	public SubscriteToEvent(String info) throws ClassNotFoundException {
		query_ = buildQuery(info);
		 myDb = new DataBase();
		
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
	 * Method to handle subscription in event by anonymous user 
	 * @param info <code>String</code> Object to retrieve anonymous user subscription information
	 * @return true if successful execution else false
	 */
	public boolean anonymSubsEvent(String info){
		
		String fullName = "gaston" ;
		String age = "22";
		String email = "vandeurg@inscription.com";
		String desc = "je suis cool avec le tenis" ;
		String  eventId = "1";
		
		
		boolean returnValue = false ;
		try {
	
				myDb.statement().executeUpdate("insert into generaluser (fullname,email,age,description) " +
						"values('" + fullName +"', '"+ email  +"', " + Integer.parseInt(age) + " , '"+ desc + "')");
			 
				myDb.statement().executeUpdate("insert into subsEventGeneral values("+ Integer.parseInt(eventId) + ", '"+ email +"')");
				returnValue = true ;
				return returnValue ;
		 } catch (SQLException e) {
			 System.err.println(e.getMessage());
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
		String userId = "1";
		String eventId = "3";
		boolean returnValue = false ;
		try {
	
			myDb.statement().executeUpdate("insert into subsEventSigned values("+ Integer.parseInt(eventId) + ", "+ Integer.parseInt(userId) +")");
			returnValue = true ;
		 } catch (SQLException e) {
			 System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	
	
	/**
	 * Object DataBase to execute update subscription in an event 
	 */
	private DataBase myDb ;
	
}
