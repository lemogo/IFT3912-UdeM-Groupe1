package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.CommentEvent;
import ca.diro.DataBase.Command.EditEvent;

/**
 * @author Lionnel
 *
 */
public class CommentEventHandler extends RequestHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4027027713427613019L;

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
			Boolean addedSuccessfully = true;

			HttpSession session = request.getSession(true);
			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
			int userId = Integer.parseInt((String) (session.getAttribute(USER_ID_ATTRIBUTE)==null?-1:session.getAttribute(USER_ID_ATTRIBUTE)));

			//TODO:modify the event in the database
			String eventId = request.getParameter("id");
			String description = request.getParameter("commentDescription");

			CommentEvent cmd = new CommentEvent(eventId, ""+userId, description, Main.getDatabase());  
						
			if(addedSuccessfully){
				//redirects the current request to the newly created event
				String setLocation = "/Webapp/evenement/"+eventId;//eventID;
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
