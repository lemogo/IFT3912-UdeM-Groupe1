package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.CommentEvent;

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
		try
		{
			HttpSession session = request.getSession(true);
			if(!isLoggedIn(session)||authentifyUser(session)==-1){
				response.setHeader("error", "veillez vous connecter avant d'ajouter un commentaire!");
				request.getRequestDispatcher("/connexion").forward(request, response);
				return;
			}
			
			int userId = authentifyUser(session);
			String description = request.getParameter("commentDescription");
			String eventId = request.getParameter("id");
//			CommentEvent cmd = 
					new CommentEvent(eventId, ""+userId, description, Main.getDatabase());  
			//redirects the current request to the newly commented event
			String setLocation = "/Webapp/evenement/"+eventId;
			response.sendRedirect(setLocation);
		}
		catch (Exception e){
			//TODO:show modification error message
			catchHelper( request, response, e);
		}
	}
}
