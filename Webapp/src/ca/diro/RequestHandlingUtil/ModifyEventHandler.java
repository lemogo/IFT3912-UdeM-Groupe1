package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.diro.Main;
import ca.diro.DataBase.Command.EditEvent;

/**
 * @author Lionnel
 *
 */
public class ModifyEventHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8035200049284620904L;

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
		try
		{
			Boolean modifiedSuccessfully = true;

			//TODO:modify the event in the database
			String userId = request.getParameter("id");
			String title = request.getParameter("eventName");
			String date = request.getParameter("eventDate");
			String location = request.getParameter("eventLocation");
			String nbplace = request.getParameter("eventNumPeople");
			String description = request.getParameter("eventDescription");

			EditEvent cmd = new EditEvent(userId, title, date, location, nbplace, description);
			modifiedSuccessfully = Main.getDatabase().executeDb(cmd);

			if(modifiedSuccessfully){
				//redirects the current request to the newly created event
				String setLocation = "/Webapp/evenement/"+userId;//eventID;
//				String setLocation = "/Webapp/liste-des-evenements/";
				response.sendRedirect(setLocation);
			}else{
				//TODO:show modification error message
			}
		}
		catch (Exception e){
			catchHelper( request, response, e);
		}
	}
}
