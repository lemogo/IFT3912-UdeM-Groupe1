package ca.diro.DataBase.Command;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * This Abstract class allowing to create and encapsulate  query that has to be executed 
 * on the database using inheritance to implement specific query on subclass 
 * 
 * @author william
 */
public abstract class CommandUpdate extends Command {

	
	/**
	 * This method override is override the method from his parent  in order to execute executeUpdate instead of executeQuery
	 * @param stat <code>Statement</code> for connection to database
	 * @return true if good connection or false when connection failed.
	 */
	@Override
	public boolean executeCommand(Statement stat) {
		boolean returnValue = false;
		try {
			if(this.query_ != null && !this.query_.equals("")){
			stat.executeUpdate(query_);
			returnValue = true;
			}
			else return returnValue ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;
	}



}
