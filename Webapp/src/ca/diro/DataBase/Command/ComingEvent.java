/**
 * 
 */
package ca.diro.DataBase.Command;

//FIXME: please change coming to something more descriptive
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
	 * Defines query
	 * @return the query in string
	 */
	public String buildQuery(){
		String str="" ;
		//TODO parse query
		return str ;
	}

}
