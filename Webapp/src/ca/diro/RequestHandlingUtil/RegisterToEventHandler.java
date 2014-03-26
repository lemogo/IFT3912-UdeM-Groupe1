package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.SubscriteToEvent;

public class RegisterToEventHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1300865621629348308L;

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
			Boolean registeredSuccessfully = true;
			//TODO:check if the user is logged in and not already registered to the event
			HttpSession session = request.getSession(true);
			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
			
			String eventID = request.getParameter("id") == null ? "2":request.getParameter("id");

			if(!isLoggedIn){
//				String info = "{eventId:1}";
				SubscriteToEvent cmd = new SubscriteToEvent(Main.getDatabase());
				boolean isRegisteredSucessfully = cmd.anonymSubsEvent(Integer.parseInt(eventID)); 
				if(!isRegisteredSucessfully){
					//TODO:Error message
				}

			}else{
				int userId = 2;
//				String info = "{eventId:1,userId:2}" ;
				SubscriteToEvent cmd = new SubscriteToEvent(Main.getDatabase());
				boolean boo = cmd.signedUserSubs(Integer.parseInt(eventID), userId); 


			//TODO:Register the User to the event in the database
			request.getParameter("id");
			//			request.getParameter("eventName");
			//			request.getParameter("eventDate");
			//			request.getParameter("eventLocation");
			//			request.getParameter("eventNumPeople");
			//			request.getParameter("eventDescription");

			}
			
			if(registeredSuccessfully){
				//redirects the current request to the newly created event
				String setLocation = "/Webapp/evenement/"+eventID;
				response.sendRedirect(setLocation);
			}else{
				//TODO:show error message
			}
		}
		catch (Exception e){
			catchHelper( request, response, e);
		}

	}

}
