/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this command permit user to coming event 
 * @author william
 *
 */
public class ComingEvent  extends Command{
	/**
	 * Constructor
	 */
	public ComingEvent() {
		this.query_ = buildQuery() ;
	}
	
	/**
	 * Method to define query
	 * @return str <code>String</code> Object the query
	 */
	private String buildQuery(){
		String str="" ;
		//TODO parse query
		return str ;
	}

}
