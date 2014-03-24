package ca.diro.RequestHandlingUtil;

import java.io.IOException;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

/**
 * @author Lionnel
 *
 */
public class ModifyEventHandler extends RequestHandler {

//	public ModifyEventHandler(String contextPath) {
//		super(contextPath);
//		// TODO Auto-generated constructor stub
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doHandle(String target, Request baseRequest,
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

//			System.out.println("\nIn modify Parameters"
//					+target
////					+ request.getParameterNames()					//);
//							+"\t"+userId
//							+"\t"+title
//							+"\t"+date
//							+"\t"+location
//							+"\t"+nbplace
//							+"\t"+description);

						
			if(modifiedSuccessfully){
				String pathInfo = target.startsWith("/")?target.substring(1):target;
				//redirects the current request to the newly created event
				String setPattern = "/";
//				String setLocation = "/Webapp/evenement/"+pathInfo;
				String setLocation = "/Webapp/liste-des-evenements/";
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
