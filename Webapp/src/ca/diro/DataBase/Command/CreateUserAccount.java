/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

import ca.diro.DataBase.DataBase;

/**
 * Command to create User Session using data from user
 * 
 * @author william
 * 
 */

public class CreateUserAccount extends CommandUpdate {

	/**
	 * Constructor
	 * @param db TODO
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public CreateUserAccount(String userName, String password, String fullName, String email, String age, String description, DataBase db )  {
		myDb = db ;
		query_ = createNewAccount( userName,  password, fullName, email,  age,  description) ;

	}
	
	/**
	 * Method to handle creation of account from anonymous user 
	 * @param userName <code>String</code> Object to retrieve anonymous user subscription information
	 * @param password String
	 * @param fullName String
	 * @param email String
	 * @param age String
	 * @param description String
	 * @return true if successful execution else false
	 */
	public String createNewAccount(String userName, String password, String fullName, String email, String age, String description){
	
		String str = "";
		
			str = "insert into signeduser  (username, password,fullname,email,age,description)"  +
					"values('" + userName +"', '" + password +"', '" + fullName +"', '"+ email  +"', " + Integer.parseInt(age) + " , '"+ description + "')";
			//myDb.statement().executeUpdate("insert into sessionuser (datecreation, email ) " +
				//		"values(CURRENT_DATE(),'" + email +"')");
		 return str;
			
	}
	
	/**
	 * to get the new userId after created a new account 
	 * @return curentId  <code>ResultSet</code> Object 
	 */
	public ResultSet getCurrentUserId(){
	
		String str = "select max(suserid) from signeduser " ;
					
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
