package ca.diro.RequestHandlingUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.rewrite.handler.RuleContainer;
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
//			String pathInfo = request.getPathInfo().substring(1);

			//TODO:Remove the event from the database
			request.getParameter("id");
//			request.getParameter("eventName");
//			request.getParameter("eventDate");
//			request.getParameter("eventLocation");
//			request.getParameter("eventNumPeople");
//			request.getParameter("eventDescription");
			
			//redirects the current request to the newly created event
	        if (isStarted())
	        {
	    		redirect.setPattern("/");
	    		redirect.setLocation("/Webapp/liste-des-evenements/");  
	            RuleContainer _rules = new RuleContainer();
	            _rules.setRules(this.getRules());
	            String returned = _rules.matchAndApply(target, request, response);
	            target = (returned == null) ? target : returned;

	            if (!baseRequest.isHandled())
	                super.handle(target, baseRequest, request, response);
	        }
			
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
