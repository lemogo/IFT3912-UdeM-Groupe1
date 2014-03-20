package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

public class DeleteEventHandler extends RequestHandler {
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

			Boolean deletedSuccessfully = true;
			//TODO:Remove the event from the database
			request.getParameter("id");
			//			request.getParameter("eventName");
			//			request.getParameter("eventDate");
			//			request.getParameter("eventLocation");
			//			request.getParameter("eventNumPeople");
			//			request.getParameter("eventDescription");

			if(deletedSuccessfully){
				//redirects the current request to the list of events
				String setPattern = "/";
				String setLocation = "/Webapp/liste-des-evenements/";
				redirectRequest(target, baseRequest, request, response, setPattern,
						setLocation);
			}else{
				//TODO:stay on current page and show error message
			}

		}
		catch (Exception e)
		{
			catchHelper(baseRequest, request, response, e);
		}

	}

}
