package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		doGet(request, response);
	}

	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		try{
			if (isLoggedIn(request.getSession(true)))
				request.getSession(false).invalidate();
			response.sendRedirect("/Webapp/accueil");
		}catch (Exception e){
			catchHelper( request, response, e);		
		}
	}

}
