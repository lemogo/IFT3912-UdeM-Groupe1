package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DisconnectUserHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5083848477776213162L;

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
			disconnectHelper(request, response);
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}
	
	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try{
			disconnectHelper(request, response);
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}

	private void disconnectHelper(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(true);
		boolean isLoggedIn=session.getAttribute("auth")==null? false:true;

		if (isLoggedIn)
			request.getSession(false).invalidate();
		
		else{
			//Do nothing the user is already logout
		}
			String setLocation = "/Webapp/accueil";
			response.sendRedirect(setLocation);
	}
}
