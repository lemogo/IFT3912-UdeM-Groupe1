package ca.diro.RequestHandlingUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

public class EventModificationPageHandler extends RequestHandler {
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
			System.out.println("in EventModification - pathInfo:"+pathInfo+"\tcontextPath:"+request.getContextPath());
			System.out.println("in EventModification - pathInfo:"+pathInfo+"\tcontextPath:"+("/Webapp/"+pathInfo));

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				super.handle(target, baseRequest, request, response);
				return;
			}
			if((isAnotherContext(pathInfo)&&!pathInfo.equals(""))||target.contains("evenement-modification/")){//&&!request.getContextPath().equals("/Webapp/"+pathInfo)){ 	        
				redirectToPathContext(target, baseRequest, request, response,
						pathInfo);
				return;
			}


			// create a handle to the resource
			String filename = "modifier-un-evenement.html"; 

			File staticResource = new File(staticDir, filename);
			File dynamicResource = new File(dynamicDir, filename);

			// Ressource existe
			if (!staticResource.exists() && !dynamicResource.exists())
			{
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				processTemplate(request, response, "404.html");
			}
			else
			{
				response.setContentType("text/html");
				response.setCharacterEncoding("utf-8");
				response.setStatus(HttpServletResponse.SC_OK);

				processTemplate(request, response, "header.html");
				
				String eventID = pathInfo;
				//TODO:Get the user event info from the database
				
				//TODO:Add event info here!!
				HashMap sources = new HashMap();
				sources.put("event",
						new Event("Event_username1", "Event_title1", "Event_date1",
								"Event_location1", "Event_description1", "Event_id1",
								"Event_badgeClass1")
						);
				
				//to display success message
				sources.put("addSuccess", "true");
				sources.put("isOwner", "true");
				//				sources.put("registerSuccess", "true");
				//				sources.put("unregisterSuccess", "false");
				sources.put("user", "true");
				sources.put("notifications_number", "0");


				processTemplate(request, response, filename,sources);
				processTemplate(request, response, "footer.html");
			}

			baseRequest.setHandled(true);
		}
		catch (Exception e)
		{
			catchHelper(baseRequest, request, response, e);
		}

	}

}
