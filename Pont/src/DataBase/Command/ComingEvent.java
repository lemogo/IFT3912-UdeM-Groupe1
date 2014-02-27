/**
 * 
 */
package DataBase.Command;

/**
 * this command permit user to coming event 
 * @author william
 *
 */
public class ComingEvent  extends AbstractCommand{
	/**
	 * Constructor
	 */
	public ComingEvent() {
		this.query_ = buildQuery() ;
	}
	
	/**
	 * Method to define query
	 * @return the query in string
	 */
	private String buildQuery(){
		String str="" ;
		//TODO parse query
		return str ;
	}

}
