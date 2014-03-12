/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.SQLException;

import ca.diro.DataBase.DataBase;

/**
 * this class implement command to allow logged user to modified their account informations  parameter except the user's name   
 * @author william
 *
 */
public class ModifyAccount extends Command{

	/**
	 * Constructor
	 * @param info String  to build query with
	 * @throws ClassNotFoundException 
	 */
	public ModifyAccount(String info) throws ClassNotFoundException {
		//query_ = buildQuery(info);
		myDb = new DataBase();
	}
	
	/**
	 * Method to change the full name of a user
	 * @param userId <code>String</code> give id of that signed user
	 * @param fullname <code>String</code> of new full name 
	 * @return true if user's full name  changed   well else false 
	 */
	public boolean changeUserFullName(String userId , String fullname){
		;
		boolean returnValue = false ;
		 try {
			myDb.statement().executeUpdate("update signeduser set fullname = '" + fullname + "' " +
											"where suserid = "+ userId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	/**
	 * Method to change the email of signed user     
	 * @param userId <code>String</code> give id of that signed user
	 *  @param email <code>String</code> the new email 
	 * @return true if event title changed   well else false 
	 */
	public boolean changeUserEmail(String userId , String email){
		
		boolean returnValue = false ;
		 try {
			myDb.statement().executeUpdate("update signeduser set email = '" + email + "' " +
											"where suserid = "+ userId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	
	/**
	 * Method to change the user Name of signed user     
	 * @param userId <code>String</code> give id of that signed user
	 *  @param userName <code>String</code> the new email 
	 * @return true if user Name changed   well else false 
	 */
	public boolean changeUserName(String userId , String userName){
		
		boolean returnValue = false ;
		 try {
			myDb.statement().executeUpdate("update signeduser set username = '" + userName + "' " +
											"where suserid = "+ userId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	
	/**
	 * Method to change the password of the user     
	 * @param userId <code>String</code> id of the current user
	 * @param password <code>String</code> the new password 
	 * @return true if password changed well else false 
	 */
	public boolean changeUserPassword(String userId , String password){
		
		boolean returnValue = false ;
		 try {
			myDb.statement().executeUpdate("update signeduser set password = '" + password + "'  " +
											"where suserid = "+ userId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	
	/**
	 * Method to change the description of the user     
	 * @param userId <code>String</code> id of the current user
	 * @param description <code>String</code> giving the description of the user 
	 * @return true if description changed   well else false 
	 */
	public boolean changeUserDesc(String userId , String description){
		
		boolean returnValue = false ;
		 try {
			myDb.statement().executeUpdate("update signeduser set description = '" + description + "' " +
											"where suserid = "+ userId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	
	/**
	 * Method to change user's age     
	 * @param eventId <code>String</code> of the current event's id 
	 * @param age <code>String</code> the new age we want to change s 
	 * @return true if age changed   well else false 
	 */
	public boolean changeUserAge(String userId , String age){
		
		boolean returnValue = false ;
		 try {
			myDb.statement().executeUpdate("update signeduser set age = " + age + " " +
											"where suserid = "+ userId  );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	
	
	
	DataBase myDb ;
	
	
	

}
