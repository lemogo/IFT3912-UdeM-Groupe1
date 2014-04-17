package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.AddEvent;

public class CreateEventHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5115305762641395708L;

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
		try{
			String userId = "-1";
			HttpSession session = request.getSession(true);
			if(isLoggedIn(session)){
				userId = (String)session.getAttribute(USER_ID_ATTRIBUTE);
				//Add the event from the database				
				String title = request.getParameter("eventName");
				String date = request.getParameter("eventDate");
				String location = request.getParameter("eventLocation");
				String nbplace = request.getParameter("eventNumPeople");
				String description = request.getParameter("eventDescription");

				AddEvent cmd = new AddEvent(userId, title, date,location,
						nbplace.equals("Illimité") ? ""+Integer.MAX_VALUE : nbplace, 
								description,Main.getDatabase());
				boolean addedSuccessfully = Main.getDatabase().executeDb(cmd);
				cmd.getCurentId();
				// Add the event to the database
				if (addedSuccessfully){
					response.addHeader("addSuccess", "true");
					response.sendRedirect("/Webapp/liste-des-evenements/");
				}else{
					//redirect the user to the create event page with the same info
					//if possible indicate to the user the reason of the failure to create the event 
				}
			}
			else{
				//redirect user to login page
				response.sendRedirect("/Webapp/connexion");
				return;
			}
		}
		catch (Exception e){
			System.out.println("In create event catch exception");
			catchHelper( request, response, e);
		}
	}
}
