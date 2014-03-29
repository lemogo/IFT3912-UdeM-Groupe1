package ca.diro.RequestHandlingUtil;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.OpenSession;

public class ConnectUserHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1650415398532080100L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try{
			//TODO:verify if the user is currently login another account. 
			HttpSession newUserSession = request.getSession(true);
			if(newUserSession.getAttribute("auth")!=null){
				//If so,ask the user if he wants to logout from account X and proceed with login 
				
				return;
			}

			//TODO:Verify User's credential and retrieve User id from the database
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			//TODO:Authenticate the user here
//			String JSONRequest = "{ userName : " + username + ", password : " +
//					password + " }";
//			System.out.println(JSONRequest);
			OpenSession openCommand = new OpenSession(username, password);

			//			System.out.println("before database execution");

			Boolean authenticatedSuccessfully  = Main.getDatabase().executeDb(openCommand);
			ResultSet results = openCommand.getResultSet();
			Integer accessCount = new Integer(0);
			
			String userID =""+-1;
			if (results.next())	userID = results.getString(1);
			else authenticatedSuccessfully = false;

			if (authenticatedSuccessfully){
							System.out.println("before requesting session");
				newUserSession.setAttribute(USER_ID_ATTRIBUTE, userID);
				newUserSession.setAttribute("auth", Boolean.TRUE);
				newUserSession.setAttribute(USERNAME_ATTRIBUTE, username);
				if (newUserSession.isNew()){
//					System.out.println("new user session");
				}else{
					Integer oldAccessCount = (Integer)newUserSession.getAttribute("accessCount"); 
					if (oldAccessCount != null) {
						accessCount = new Integer(oldAccessCount.intValue() + 1);
					}
//					System.out.println("old user session");
				}
				newUserSession.setAttribute("accessCount", accessCount);

				//Redirects the current request to the user info page
				String setLocation = "/Webapp/membre/"+username;
				response.sendRedirect(setLocation);
			}else{
				//TODO:send error message to user and return to login page
				String setLocation = "/Webapp/connexion";
					newUserSession.setAttribute("auth", Boolean.FALSE);
				response.sendRedirect(setLocation);
			}
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}
}
