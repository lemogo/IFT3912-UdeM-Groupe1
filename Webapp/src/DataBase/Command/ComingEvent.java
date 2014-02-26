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


	public ComingEvent() {
		this.query_ = buildQuery() ;
	}
	
	/**
	 * Method to define query
	 * @return the query in string
	 */
	public String buildQuery(){
		String str="" ;
		return str ;
	}

}
