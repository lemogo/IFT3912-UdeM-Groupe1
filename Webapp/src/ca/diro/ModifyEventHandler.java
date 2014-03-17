package ca.diro;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.RedirectProtocolHandler;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewritePatternRule;
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
			String pathInfo = request.getPathInfo().substring(1);
			System.out.println("in modify - pathInfo:"+pathInfo+"\tlocation:"+baseRequest.getRequestURI()+"\tresponse:"+response.getLocale());

			if(
//					pathInfo.contains("/modify-event")
					pathInfo.contains(".")
					) {
				super.handle(target, baseRequest, request, response);
				return;
			}
			RewriteHandler rewriter = new RewriteHandler(); 
			rewriter.setHandler(this);
			RewritePatternRule oldToNew = new RewritePatternRule();
			  oldToNew.setPattern("modify-event");
			  oldToNew.setReplacement("evenement");
			  rewriter.addRule(oldToNew);
			Main.eventContext.getHandler().handle("/Webapp/evenement/"+"id", baseRequest, request, response);
			baseRequest.setHandled(true);
			return;
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
