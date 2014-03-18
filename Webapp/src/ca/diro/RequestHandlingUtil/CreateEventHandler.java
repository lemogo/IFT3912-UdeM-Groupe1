package ca.diro.RequestHandlingUtil;

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

			if(
					pathInfo.contains(".")
					) {
				super.handle(target, baseRequest, request, response);
				return;
			}

//			if(!(
//					pathInfo.contains("/create-event")
//					)) {
//				return;
//			}

			System.out.println("\nParameters"+ request.getParameterNames()					);

			// create a handle to the resource
			String filename = "liste-des-evenements.html"; 

			if(request.getParameterMap().size()==0){
				filename="ajouter-un-evenement.html";
			}
			
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
				
				//add event info here!!
				HashMap sources = new HashMap();
				sources.put("events",Arrays.asList(
						new Event("username1", "title1", "date1",
						"location1", "description1", "id1",
						"badgeClass1")
						,new Event("username2", "title2", "date2",
								"location2", "description2", "id2",
								"badgeClass2")
								));
				//to display success message
				sources.put("addSuccess", "true");
				sources.put("user", "true");
				sources.put("notifications_number", "0");

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
