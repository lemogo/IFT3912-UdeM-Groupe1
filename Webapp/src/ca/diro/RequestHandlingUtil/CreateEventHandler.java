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

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				super.handle(target, baseRequest, request, response);
				return;
			}
			if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
				redirectToPathContext(target, baseRequest, request, response,
						pathInfo);
				return;
			}
			String id = request.getParameter("id");
			String eventName = request.getParameter("eventName");
			String eventDate = request.getParameter("eventDate");
			String eventLocation = request.getParameter("eventLocation");
			String eventNumPeople = request.getParameter("eventNumPeople");
			String eventDescription = request.getParameter("eventDescription");

			System.out.println("\nParameters"+ request.getParameterNames()					//);
					+"\t"+id
					+"\t"+eventName
					+"\t"+eventDate
					+"\t"+eventLocation
					+"\t"+eventNumPeople
					+"\t"+eventDescription);
			
			Boolean addedSuccessfully = true;
			//TODO: Add the event to the database
			
			
			if (addedSuccessfully){
			//redirects the current request to the newly created event
			String setPattern = "/";
			String setLocation = "/Webapp/liste-des-evenements/"+eventName;
			redirectRequest(target, baseRequest, request, response, setPattern,
					setLocation);
			}else{
				//redirect the user to the event page with the same info
				//if possible indicate to the user the reason of the failure to create the event 
			}
		}
		catch (Exception e)
		{
			catchHelper(baseRequest, request, response, e);
		}

	}


}
