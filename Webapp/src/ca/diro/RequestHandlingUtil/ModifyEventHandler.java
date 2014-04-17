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
		try
		{
			//TODO: check if the user is the owner of the modified event
			String eventId = request.getParameter("id");
			String title = request.getParameter("eventName");
			String date = request.getParameter("eventDate");
			String location = request.getParameter("eventLocation");
			String nbplace = request.getParameter("eventNumPeople");
			String description = request.getParameter("eventDescription");
			//modify the event in the database
			authentifyUser(request.getSession(true));
			EditEvent cmd = new EditEvent(eventId, title, date, location, nbplace, description);

			if(Main.getDatabase().executeDb(cmd)){
				//redirects the current request to the newly modify event
				response.setHeader("modificationSuccess", "true");
				request.getRequestDispatcher("/evenement/"+eventId).forward(request, response);
			}else{
				//show modification error message
				response.setHeader("error", "Desole! Il y a eu une erreur lors de la modification de l'evenement");
				request.getRequestDispatcher("/modifier-mes-informations/").forward(request, response);
			}
		}
		catch (Exception e){
			catchHelper( request, response, e);
		}
	}
}
