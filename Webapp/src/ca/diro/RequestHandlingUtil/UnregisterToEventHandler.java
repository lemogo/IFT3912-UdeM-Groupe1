package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.UnsubscriteToEvent;

public class UnregisterToEventHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3281510009778298040L;

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
			Boolean unregisteredSuccessfully;
			HttpSession session = request.getSession(true);
			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
			if(isLoggedIn){
				String userId = (String) session.getAttribute(USER_ID_ATTRIBUTE);
				String eventId = request.getParameter("id");
				
				//TODO:Unregister the User to the event in the database
				UnsubscriteToEvent cmd = new UnsubscriteToEvent(userId, eventId);
				unregisteredSuccessfully = 
						Main.getDatabase().executeDb(cmd);

				//redirects the current request to the newly created event
				if(unregisteredSuccessfully){
					String setLocation = "/evenement/"+eventId;
					response.setHeader("unregisterSuccess", "true");
					request.getRequestDispatcher(setLocation).forward(request, response);
				}
				else{
					//TODO:show unregister error message
				}
			}else{
				//TODO:show unregister error message
			}
		}catch (Exception e){
			catchHelper(request, response, e);		
		}
	}
}
