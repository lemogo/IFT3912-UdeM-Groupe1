/**
 * 
 */
package DataBase.Command;

import java.util.Map;

/**
 * this command allow logged user to modified their account informations  parameter except the user's name   
 * @author william
 *
 */
public class ModifyAccount extends AbstractCommand{

	public ModifyAccount(Map<String,String> info) {
		query_ = buildQuery(info);
		
	}
	
	/**
	 * @param userInfo Map to be parsed and build the right query
	 * @return a string that is the query
	 */
	public String buildQuery(Map<String,String> info){
		String str="" ;
		return str ;
	}

}