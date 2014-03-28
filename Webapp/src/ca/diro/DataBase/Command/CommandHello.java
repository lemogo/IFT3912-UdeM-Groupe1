/**
 * 
 */
package ca.diro.DataBase.Command;


/**
 * @author william
 * 
 */
public class CommandHello extends Command{

	/**
	 * 
	 */
	public CommandHello() {
		String eventId = "1";
		this.query_ =  "select * from event" ;
	
	}
}