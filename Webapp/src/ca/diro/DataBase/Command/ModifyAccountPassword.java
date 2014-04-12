/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this class implement command to allow logged user to modified their account informations  parameter except the user's name   
 * @author william
 *
 */
public class ModifyAccountPassword extends CommandUpdate{

	/**
	 * Constructor
	 * @param password 
	 */
	public ModifyAccountPassword(String password, String userId)   {
		
		query_ = changeUserInformation(password, userId) ; 
	}
	
	
	/**
	 * Method to build the query to set user informations 
	 * @param password
	 * @return str <code>String</code> which is the query to be executed 
	 */
	private String changeUserInformation(String password, String userId){
		
		String str = "" ;	
		str = "update signeduser set password = '" + password + "'  where suserid = "+ userId ;
		 return str ;
	}
	
	
	
	

}
