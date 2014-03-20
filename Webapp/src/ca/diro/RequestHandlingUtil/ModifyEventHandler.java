package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.rewrite.handler.RuleContainer;
import org.eclipse.jetty.server.Request;

public class ModifyEventHandler extends RequestHandler {

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
			Boolean modifiedSuccessfully = true;

			//TODO:modify the event in the database
			String id = request.getParameter("id");
			String eventName = request.getParameter("eventName");
			String eventDate = request.getParameter("eventDate");
			String eventLocation = request.getParameter("eventLocation");
			String eventNumPeople = request.getParameter("eventNumPeople");
			String eventDescription = request.getParameter("eventDescription");

			if(modifiedSuccessfully){
				//redirects the current request to the newly created event
				String setPattern = "/";
				String setLocation = "/Webapp/evenement/"+id;
				redirectRequest(target, baseRequest, request, response, setPattern,
						setLocation);
			}else{
				//TODO:show modification error message
			}

		}
		catch (Exception e)
		{
			catchHelper(baseRequest, request, response, e);
		}

	}

}
