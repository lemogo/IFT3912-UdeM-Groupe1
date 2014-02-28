import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * Handler for the Jetty server.
 * 
 * @author girardil
 * @version 1.0
 */
public class RequestHandler extends AbstractHandler {

	/**
	 * The list of supported commands in requests.
	 */
	private final static String[] supportedCommands = {};
	/**
	 * The <code>ResultSet</code> of a database query.
	 */
	private ResultSet resultSet;

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
	}

	/**
	 * Identifies the expected command from the given <code>HttpServletRequest</code> and
	 * returns the String that represents it.
	 * 
	 * @param request
	 *            The <code>HttpServletRequest</code> sent by the server.
	 * @return The String that represents the requested command.
	 */
	private String identifyCommand(HttpServletRequest request) {
		return "";
	}
}
