/**
 * 
 */
package ca.diro.DataBase.Command;

import java.util.LinkedList;

/**
 * This class run build query for searching event using events at title and description as criteria 
 * @author william
 *
 */
public class ResearchPastEvent extends Command{

	
	/**
	 * Constructor 
	 * @param info String to build query
	 */
	public ResearchPastEvent(LinkedList<String> stack) {
		
		//this.stack  = stack ;
		query_ = buildQuery(stack);
		
	}
	/**
	 * Iterative  Method to parse String from <code>LinkedLis</code> Object containing parameters
	 * and build the right query to make research for event
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	 
	
	public  String buildQuery(LinkedList<String> info ) {
		if (info.size() < 1) {
		    
		    return "";
		}
		String str =  "SELECT eventid, title, suserid, dateevent, location, description, numberplaces FROM Event where " ;
		str += "dateevent < CURRENT_TIMESTAMP() and UPPER(status) <> 'CANCELLED' and (";
		String st ="";
		for (String mot : info) {
			st += "or title LIKE '%" + mot+ "%' or description LIKE '%" + mot + "%'  " ;
			
		}
		st = st.replaceFirst("or", " ");
		st += ")";
		str += st;
		
		return str;
	}

	
}
