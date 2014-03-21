package ca.diro.RequestHandlingUtil;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Request;

import ca.diro.Main;
import ca.diro.DataBase.Command.CreateUserAccount;

public class ConnectUserHandler extends RequestHandler {
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

			//TODO:Verify User's credential and retrieve User id from the database
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			Boolean authenticatedSuccessfully = true;
			//TODO:Authenticate the user here

			String JSONRequest = "{ username : " + username + ", password : " +
			password + " }";
System.out.println(JSONRequest);
			CreateUserAccount userCreation = new CreateUserAccount(JSONRequest,
			Main.getDatabase());

			Main.getDatabase().executeDb(userCreation);
			ResultSet results = userCreation.getResultSet();

			results.next();
			int userID = results.getInt(0);

			HttpSession newUserSession = request.getSession(true);

			newUserSession.setAttribute("UserID", userID);
						
			if (authenticatedSuccessfully){
			//Redirects the current request to the newly created event
			String setPattern = "/";
			String setLocation = "/Webapp/membre/"+username;
			redirectRequest(target, baseRequest, request, response, setPattern,
					setLocation);
			}else{
				//TODO:send error message to user and return to login page
			}

		}
		catch (Exception e)
		{
			catchHelper(baseRequest, request, response, e);		
		}

	}
}
