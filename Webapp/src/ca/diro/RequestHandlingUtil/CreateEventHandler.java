package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
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
			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
			if(isLoggedIn){
				userId = (String)session.getAttribute(USER_ID_ATTRIBUTE);
				//TODO:Add the event from the database				
				String title = request.getParameter("eventName");
				String date = request.getParameter("eventDate");
				String location = request.getParameter("eventLocation");
				String nbplace = request.getParameter("eventNumPeople");
				String description = request.getParameter("eventDescription");

				DataBase db = Main.getDatabase();

				AddEvent cmd = new AddEvent(userId, title, date,location,
						nbplace.equals("Illimité") ? ""+Integer.MAX_VALUE : nbplace, 
								description,db);
				boolean addedSuccessfully = db.executeDb(cmd);
				cmd.getCurentId();
				//TODO: Add the event to the database
				if (addedSuccessfully){
					String setLocation = "/liste-des-evenements/";
					response.addHeader("addSuccess", "true");
					response.sendRedirect("/Webapp"+setLocation);
//					request.getRequestDispatcher(setLocation).forward(request, response);
				}else{
					//redirect the user to the create event page with the same info
					//if possible indicate to the user the reason of the failure to create the event 
				}
			}
			else{
				//redirect user to login page
//				request.getRequestDispatcher("/connexion").forward(request, response);
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
