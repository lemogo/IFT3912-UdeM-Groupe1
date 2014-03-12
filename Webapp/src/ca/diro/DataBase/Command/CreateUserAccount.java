/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.SQLException;

import ca.diro.DataBase.DataBase;

/**
 * Command to create User Session using data from user
 * 
 * @author william
 * 
 */
/**
 * @author pc-user
 *
 */
public class CreateUserAccount extends Command {

	/**
	 * Constructor
	 * @param info string 
	 * @throws ClassNotFoundException 
	 */
	public CreateUserAccount(String info) throws ClassNotFoundException {
		query_ = buildQuery(info);
		myDb  = new DataBase();

	}

	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	public String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}
	
	/**
	 * Method to handle creation of account from anonymous user 
	 * @param info <code>String</code> Object to retrieve anonymous user subscription information
	 * @return true if successful execution else false
	 */
	public boolean createNewAccount(String info){
		
		String fullName = "kolo gordo" ;
		String userName = "igor";
		String password = "kolo" ;
		String age = "22";
		String email = "vandeurg@inscription.com";
		String desc = "je suis cool avec le tenis" ;
		//String  eventId = "1";
		
		boolean returnValue = false ;
		try {
				myDb.statement().executeUpdate("insert into signeduser  (username, password,fullname,email,age,description)"  +
						"values('" + userName +"', '" + password +"', '" + fullName +"', '"+ email  +"', " + Integer.parseInt(age) + " , '"+ desc + "')");
				myDb.statement().executeUpdate("insert into sessionuser (datecreation, email ) " +
						"values(CURRENT_DATE(),'" + email +"')");
				
				returnValue = true ;
				
		 } catch (SQLException e) {
			 System.err.println(e.getMessage());
		}
		 return returnValue ;
		
		//TODO create user account  	
	}
	
	/**
	 * Object DataBase to execute update subscription in an event 
	 */
	private DataBase myDb ;

}
