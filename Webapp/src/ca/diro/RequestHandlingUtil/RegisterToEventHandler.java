package ca.diro.RequestHandlingUtil;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.PageInfoEvent;
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
		String eventID="";
		try{
			//TODO:check if the user is logged in and not already registered to the event
			HttpSession session = request.getSession(true);
			eventID = request.getParameter("id") == null ? "-1":request.getParameter("id");
			
			String userId = ""+authentifyUser(session);
			

			//check if there's still space left in the event if not show an error message (The event is full)
			if(getAvailablePlaces(eventID)<1){
				response.addHeader("error", "Désolé! Il ne reste plus de place dans l'événement.");
				//				response.addHeader("registerSuccess", "false");
//				response.addHeader("unsufficientPlace", "true");
				return;
			}
			//Register the User to the event in the database
			SubscriteToEvent subscribeCommand = new SubscriteToEvent(userId, eventID, isLoggedIn(session));
			if(Main.getDatabase().executeDb(subscribeCommand)) {
				response.addHeader("isRegistered", "true");
				response.addHeader("registerSuccess", "true");
			}
			else{
				response.addHeader("error", "Il y a eu une erreur lors de l'inscription à l'événement.\nSi vous n'êtes pas déjà inscrit, veuillez réessayer.\nSi le problème persiste, communiquer avec le groupe1.");
			}
		}
		catch (Exception e){
			catchHelper( request, response, e);
		}finally{
			//has to be a forward to be able to carry on the new header information
			String setLocation = "/evenement/"+eventID;
			request.getRequestDispatcher(setLocation).forward(request, response);
		}
	}

	private int getAvailablePlaces(String eventID) throws SQLException {
		return new PageInfoEvent(eventID,Main.getDatabase()).getAvailablePlaces();
	}

}
