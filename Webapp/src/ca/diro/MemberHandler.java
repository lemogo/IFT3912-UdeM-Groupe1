package ca.diro;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import ca.diro.Event;
import ca.diro.RequestHandler;

public class MemberHandler extends RequestHandler {
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
			if (pathInfo.startsWith(siteName)) pathInfo = pathInfo.substring(siteName.length());

			if(!(
					pathInfo.contains("/membre")
					)) {
				return;
			}

			// create a handle to the resource
			String filename = "membre.html"; 

//			if(request.getParameterMap().size()==0){
//				filename="modifier-un-evenement.html";
//			}
			
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

				//add event info here!!
				HashMap sources = new HashMap();
				sources.put("isOwner", "true");
//				sources.putAll(new HashMap<String,String>());
				sources.put("username","ownerUsername");
				sources.put("fullname","ownerFullname");
						sources.put("registeredSince","ownerRegisteredSince");
								sources.put("age","ownerAge");
										sources.put("description","ownerDescription");

				sources.put("ownerEventsList",Arrays.asList(
						new Event("ownerEvent_username1", "ownerEvent_title1", "ownerEvent_date1",
						"ownerEvent_location1", "ownerEvent_description1", "ownerEvent_id1",
						"ownerEvent_badgeClass1"),
						new Event("ownerEvent_username2", "ownerEvent_title2", "ownerEvent_date2",
						"ownerEvent_location2", "ownerEvent_description2", "ownerEvent_id2",
						"ownerEvent_badgeClass2")
						));
				sources.put("registeredEventsList",Arrays.asList(
						new Event("registeredEvents_username1", "registeredEvents_title1", "registeredEvents_date1",
						"registeredEvents_location1", "registeredEvents_description1", "registeredEvents_id1",
						"registeredEvents_badgeClass1")
						));
				//to display success message
				sources.put("addSuccess", "true");
//				sources.put("registerSuccess", "true");
//				sources.put("unregisterSuccess", "false");
				sources.put("user", "true");
//				sources.put("notifications_number", "0");

				System.out.println("in eventHandler");

				processTemplate(request, response, "header.html",sources);
				processTemplate(request, response, filename,sources);
				processTemplate(request, response, "footer.html");
			}

			baseRequest.setHandled(true);
		}
		catch (Exception e)
		{
			// Pour deboggage, on va afficher le stacktrace
			Map<String, String> params = new HashMap<String, String>();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintStream pout = new PrintStream(out);
			e.printStackTrace(pout);
			params.put("stacktrace", out.toString());
			out.close();

			// Template d'erreur
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			processTemplate(request, response, "500.html", params);
			baseRequest.setHandled(true);
		}

	}

}
