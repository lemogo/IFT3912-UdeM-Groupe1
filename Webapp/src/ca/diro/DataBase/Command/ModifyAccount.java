/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.SQLException;

import org.json.JSONException;

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
	 * @throws JSONException 
	 */
	public ModifyAccount(String info, DataBase db )   {
		
		myDb = db;
		try {
			jsonInfo = parseToJson(info);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to change the full name of a user
	 * @return true if user's full name  changed   well else false 
	 */
	public boolean changeUserFullName(){
		
		boolean returnValue = false ;
		 try {
			 int userId = jsonInfo.getInt("userId");
			 String fullName = jsonInfo.getString("fullName");
			myDb.statement().executeUpdate("update signeduser set fullname = '" + fullName + "' " +
											"where suserid = "+ userId  );
			returnValue = true;
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
	 * Method to change the email of signed user     
	 * @param userId <code>String</code> give id of that signed user
	 *  @param email <code>String</code> the new email 
	 * @return true if event title changed   well else false 
	 */
	public boolean changeUserEmail(){
		
		boolean returnValue = false ;
		 try {
			 int userId = jsonInfo.getInt("userId");
			 String email = jsonInfo.getString("email");
			myDb.statement().executeUpdate("update signeduser set email = '" + email + "' " +
											"where suserid = "+ userId  );
			returnValue = true;
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
	 * Method to change the user Name of signed user     
	 * @param userId <code>String</code> give id of that signed user
	 *  @param userName <code>String</code> the new email 
	 * @return true if user Name changed   well else false 
	 */
	public boolean changeUserName(){
		
		boolean returnValue = false ;
		 try {
			 int userId = jsonInfo.getInt("userId");
			 String userName = jsonInfo.getString("userName");
			 myDb.statement().executeUpdate("update signeduser set username = '" + userName + "' " +
											"where suserid = "+ userId  );
			returnValue = true;
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
	 * Method to change the password of the user     
	 * @param userId <code>String</code> id of the current user
	 * @param password <code>String</code> the new password 
	 * @return true if password changed well else false 
	 */
	public boolean changeUserPassword(){
		
		boolean returnValue = false ;
		 try {
			 int userId = jsonInfo.getInt("userId");
			 String password = jsonInfo.getString("password");
			 myDb.statement().executeUpdate("update signeduser set password = '" + password + "'  " +
											"where suserid = "+ userId  );
			returnValue = true;
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
	 * Method to change the description of the user     
	 * @param userId <code>String</code> id of the current user
	 * @param description <code>String</code> giving the description of the user 
	 * @return true if description changed   well else false 
	 */
	public boolean changeUserDescription(){
		
		boolean returnValue = false ;
		 try {
			 int userId = jsonInfo.getInt("userId");
			 String description = jsonInfo.getString("description");
			 myDb.statement().executeUpdate("update signeduser set description = '" + description + "' " +
											"where suserid = "+ userId  );
			returnValue = true;
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
	 * Method to change user's age     
	 * @param eventId <code>String</code> of the current event's id 
	 * @param age <code>String</code> the new age we want to change s 
	 * @return true if age changed   well else false 
	 */
	public boolean changeUserAge(){
		
		boolean returnValue = false ;
		 try {
				int userId = jsonInfo.getInt("userId");
				int age = jsonInfo.getInt("age");
				myDb.statement().executeUpdate("update signeduser set age = " + age + " " +
											"where suserid = "+ userId  );
			returnValue = true;
		} catch (SQLException e) {
				System.err.println(e.getMessage());
		}
		 catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
		}
		 return returnValue ;
	}
	
	
	
	DataBase myDb ;
	
	
	

}
