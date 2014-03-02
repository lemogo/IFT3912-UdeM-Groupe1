/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * This class run build query for searching event using events at title and description as criteria 
 * @author william
 *
 */
public class ResearchEvent extends Command{

	
	/**
	 * Constructor 
	 * @param info string to build query
	 */
	public ResearchEvent(String info) {
		query_ = buildQuery(info);
		
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * 
	 * @param info
	 *            string to build query
	 * @return a string that is the query
	 */
	private String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}
}
