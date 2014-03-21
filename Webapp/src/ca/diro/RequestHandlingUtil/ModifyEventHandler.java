package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

/**
 * @author Lionnel
 *
 */
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
			String userId = "";//request.getParameter("id");
			String title = request.getParameter("eventName");
			String date = request.getParameter("eventDate");
			String location = request.getParameter("eventLocation");
			String nbplace = request.getParameter("eventNumPeople");
			String description = request.getParameter("eventDescription");

			System.out.println("\nIn modify Parameters"
					+target
//					+ request.getParameterNames()					//);
							+"\t"+userId
							+"\t"+title
							+"\t"+date
							+"\t"+location
							+"\t"+nbplace
							+"\t"+description);

			
			
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
