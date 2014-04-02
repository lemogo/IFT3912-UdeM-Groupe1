package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
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
		String eventID="";
		String userId="";
		try{
			Boolean isRegisteredSucessfully = false;
			//TODO:check if the user is logged in and not already registered to the event
			HttpSession session = request.getSession(true);
			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
			eventID = request.getParameter("id") == null ? "1":request.getParameter("id");
			DataBase db = Main.getDatabase();
			
			if(!isLoggedIn){
				SubscriteToEvent cmd = new SubscriteToEvent(userId, eventID, false);
				isRegisteredSucessfully = db.executeDb(cmd);
				if(isRegisteredSucessfully) {
					response.addHeader("isRegistered", "true");
					response.addHeader("registerSuccess", "true");
				}
				else{
					//TODO:Error message
				}
			}else{
				//TODO:Register the User to the event in the database
				userId = (String) (session.getAttribute(USER_ID_ATTRIBUTE)==null?1:session.getAttribute(USER_ID_ATTRIBUTE));
//				String info = "{eventId:1,userId:2}" ;
				SubscriteToEvent cmd = new SubscriteToEvent(userId, eventID, true);
				isRegisteredSucessfully = db.executeDb(cmd); 
				if(isRegisteredSucessfully) response.addHeader("registerSuccess", "true");
				response.addHeader("isRegistered", "true");
			}
//			System.out.println("isRegisteredSucessfully:"+isRegisteredSucessfully);
//			if(isRegisteredSucessfully){
//				//redirects the current request to the newly created event
//				response.addHeader("registerSuccess", "true");
//			}else{
//				//TODO:show error message
//			}
		}
		catch (Exception e){
			catchHelper( request, response, e);
		}finally{
//			response.addHeader("isRegistered", "true");
//			String setLocation = "/Webapp/evenement/"+eventID;
			String setLocation = "/evenement/"+eventID;
//			System.out.println("\n\nIn registerEvent, redirecting to :"+setLocation+"\tuserId:"+userId+""+"\n\n");
//			response.sendRedirect(setLocation);
			RequestDispatcher dispacher = request.getRequestDispatcher(setLocation);
			dispacher.forward(request, response);
		}
	}
}
