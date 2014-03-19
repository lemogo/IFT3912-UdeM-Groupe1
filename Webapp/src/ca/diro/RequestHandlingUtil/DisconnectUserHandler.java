package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

public class DisconnectUserHandler extends RequestHandler {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try
		{
//			String pathInfo = request.getPathInfo().substring(1);
//			System.out.println("in modify - pathInfo:"+pathInfo+"\tURL:"+baseRequest.getRequestURL()+"\tlocation:"+baseRequest.getRequestURI()+"\tresponse:"+response.getLocale());
//			System.out.println("in modify - event name:"+request.getParameter("eventName"));

			//TODO:End user session
			request.getParameter("id");
//			request.getParameter("eventName");
//			request.getParameter("eventDate");
//			request.getParameter("eventLocation");
//			request.getParameter("eventNumPeople");
//			request.getParameter("eventDescription");
//			
			//redirects the current request to the newly created event
			
			String setPattern = "/";
			String setLocation = "/Webapp/accueil";
			redirectRequest(target, baseRequest, request, response, setPattern,
					setLocation);
		}
		catch (Exception e)
		{
			catchHelper(baseRequest, request, response, e);
		}

	}

}
