package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

public class CreateEventHandler extends RequestHandler {
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

			String pathInfo = request.getPathInfo().substring(1);
			//			if (pathInfo.startsWith(siteName)) pathInfo = pathInfo.substring(siteName.length());

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				super.handle(target, baseRequest, request, response);
				return;
			}
			if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){//&&!request.getContextPath().equals("/Webapp/"+pathInfo)){ 	        
				redirectToPathContext(target, baseRequest, request, response,
						pathInfo);
				return;
			}

			System.out.println("\nParameters"+ request.getParameterNames()					//);
			+"\t"+request.getParameter("id")
			+"\t"+request.getParameter("eventName")
			+"\t"+request.getParameter("eventDate")
			+"\t"+request.getParameter("eventLocation")
			+"\t"+request.getParameter("eventNumPeople")
			+"\t"+request.getParameter("eventDescription"));

			//TODO: Redirect to liste-des-evenements.html 
			//redirects the current request to the newly created event
			String setPattern = "/";
			String setLocation = "/Webapp/liste-des-evenements/"+request.getParameter("eventName");
	        redirectRequest(target, baseRequest, request, response, setPattern,
					setLocation);
		}
		catch (Exception e)
		{
			catchHelper(baseRequest, request, response, e);
		}

	}


}
