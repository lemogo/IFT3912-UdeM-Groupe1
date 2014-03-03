package ca.diro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import ca.diro.UserHandlingUtils.UserPermissionHandler;

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
	/**
	 * The <code>resultSet</code> as a <code>JSONArray</code>.
	 */
	private JSONArray JSONResult;

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
	}

	/**
	 * Utility method. Returns the ResultSet as a JSONArray, to be converted
	 * into a String for the response.
	 * 
	 * @return The ResultSet as a JSONArray.
	 */
	private JSONArray resultSetToJSON() {
		// TODO Convert ResulSet to workable JSONArray.
		// The result could also be stored in the class attribute "JSONResult"
		// instead of being returned.
		return JSONResult;
	}
}
