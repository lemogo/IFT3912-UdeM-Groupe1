/**
 * 
 */
package DataBase.Command;

import java.util.*;

/**
 * Command to create User Account using data from user
 * 
 * @author william
 * 
 */
public class CreateUserAccount extends AbstractCommand {

	public CreateUserAccount(Map<String, String> info) {
		query_ = buildQuery(info);

	}

	/**
	 * @param userInfo
	 *            Map to be parsed and buid the right query
	 * @return a string that is the query
	 */
	public String buildQuery(Map<String, String> userInfo) {
		String str = "";
		return str;
	}

}
