/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.SQLException;

import org.json.JSONException;

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
	 * @throws JSONException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public CreateUserAccount(String info , DataBase db)  {
		myDb  = db;
		try {
			jsonInfo = parseToJson(info);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}

	}

	
	/**
	 * Method to handle creation of account from anonymous user 
	 * @param info <code>String</code> Object to retrieve anonymous user subscription information
	 * @return true if successful execution else false
	 */
	public boolean createNewAccount(String info){
	
		boolean returnValue = false ;
		try {
				
			String fullName = jsonInfo.getString("fullname");
			String userName = jsonInfo.getString("username");
			String password = jsonInfo.getString("password");
			String age = jsonInfo.getString("age");
			String email = jsonInfo.getString("email");
			String desc = jsonInfo.getString("description") ;
		
			myDb.statement().executeUpdate("insert into signeduser  (username, password,fullname,email,age,description)"  +
					"values('" + userName +"', '" + password +"', '" + fullName +"', '"+ email  +"', " + Integer.parseInt(age) + " , '"+ desc + "')");
			myDb.statement().executeUpdate("insert into sessionuser (datecreation, email ) " +
						"values(CURRENT_DATE(),'" + email +"')");
				
				returnValue = true ;
				
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
	
	/**
	 * Object DataBase to execute update subscription in an event 
	 */
	private DataBase myDb ;

}
