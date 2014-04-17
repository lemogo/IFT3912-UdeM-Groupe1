package ca.diro.RequestHandlingUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.CancelEvent;
import ca.diro.DataBase.Command.PageInfoEvent;

public class CancelEventHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5278158439261391279L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {

		String setLocation = "/liste-des-evenements/";
		try{
			HttpSession session = request.getSession(true);

			String username = (String) session.getAttribute(USERNAME_ATTRIBUTE);
			String eventID = (String) request.getParameter("id");

			//Get the user event info from the database
			PageInfoEvent eventInfoCommand = new PageInfoEvent(eventID,Main.getDatabase());

			if( Main.getDatabase().executeDb(eventInfoCommand)){ 
				ResultSet rs = eventInfoCommand.getResultSet();
				if (rs.next()) {
					//check if the logged user is really the owner of the event
					if(isAccountOwner(username, rs.getString("username"), rs.getString("password"))){
						//Remove the event from the database
						setLocation = cancelEventHelper(response, setLocation,
								eventID);
					}
				}
			}
		}
		catch (Exception e){
			System.out.println("In deleteEventHanler catch exception");
			catchHelper( request, response, e);		
		}finally{
			RequestDispatcher dispacher = request.getRequestDispatcher(setLocation);
			dispacher.forward(request, response);
		}
	}

	private String cancelEventHelper(HttpServletResponse response,
			String setLocation, String eventID) throws SQLException {
		CancelEvent cancelEventCommand = new CancelEvent(eventID, Main.getDatabase());
		if(Main.getDatabase().executeDb(cancelEventCommand)){
			cancelEventCommand.nofifySignedUser(eventID);
			response.addHeader("deleteSuccess", "true");
		}else{
			//TODO:stay on current page and show error message
			System.out.println("failled to delete event:"+eventID);
			setLocation = "/evenement/"+eventID;
			response.addHeader("deleteError", "failled to delete event:"+eventID);
		}
		return setLocation;
	}

}
