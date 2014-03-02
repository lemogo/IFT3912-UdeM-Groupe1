/**
 * 
 */
package ca.diro.DataBase.Command;


/**
 * this command class give information on a user that can be use to generate user's own page
 * @author william
 *
 */
public class PageInfoUser extends AbstractCommand{
	/**
	 * Constructor
	 * @param info string to build query
	 */
	public PageInfoUser(String info) {
		query_ = buildQuery(info);
		
	}
	
	/**
	 * @param info String from JSON format to be parsed and build the right query
	 * @return a string that is the query
	 */
	private String buildQuery(String info){
		String str="" ;
		return str ;
	}

}
