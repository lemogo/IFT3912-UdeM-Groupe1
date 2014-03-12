/**
 * 
 */
package ca.diro.DataBase.Command;


/**
 * @author pc-user
 * 
 */
public class CommandHello extends Command{

	/**
	 * 
	 */
	public CommandHello() {
		String eventId = "1";
		this.query_ =  "select * from signeduser" ;
				
				//"select numberplaces  from event " +
				//"where eventid = "+ Integer.parseInt(eventId) ;
				
				
				//"select count(eventid) from subscriteEvent " +
				//"where eventid = "+ Integer.parseInt(eventId) ;
	}
}