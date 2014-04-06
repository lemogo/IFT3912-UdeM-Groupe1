package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.DeleteNotification;

public class DeleteNotificationHandler extends RequestHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5547996213734876579L;

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
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try{
			HttpSession session = request.getSession(true);
//			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;

			String userID = (String) session.getAttribute(USER_ID_ATTRIBUTE);
//			String username = (String) session.getAttribute(USERNAME_ATTRIBUTE);
			String eventID = (String) request.getParameter("eventid");

			//TODO:Get the user event info from the database
			DataBase myDb = Main.getDatabase();
			DeleteNotification cmd = new DeleteNotification(eventID, userID);

			if( myDb.executeDb(cmd)){ 

			}else{
				//TODO:stay on current page and show error message
				System.out.println("failled to delete Notification:"+eventID);
			}
			//refresh notification page
			String setLocation = "/Webapp/notifications";
			response.sendRedirect(setLocation);
		}
		catch (Exception e){
			System.out.println("In deleteEventHanler catch exception");
			catchHelper( request, response, e);		
		}
	}

}
