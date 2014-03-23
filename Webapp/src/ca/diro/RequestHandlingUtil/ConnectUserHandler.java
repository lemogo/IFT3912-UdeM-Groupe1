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
			//If so,ask the user if he wants to logout from account X and proceed with login 
			
			//TODO:Verify User's credential and retrieve User id from the database
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			//TODO:Authenticate the user here

			String JSONRequest = "{ userName : " + username + ", password : " +
					password + " }";
			System.out.println(JSONRequest);
			OpenSession openCommand = new OpenSession(JSONRequest);

//			System.out.println("before database execution");
			
			Boolean authenticatedSuccessfully  = Main.getDatabase().executeDb(openCommand);
			ResultSet results = openCommand.getResultSet();

			int userID =-1;
			if (results.next())
			userID = results.getInt(1);
			else authenticatedSuccessfully = false;

			Integer accessCount = new Integer(0);

			System.out.println("before requesting session");
			HttpSession newUserSession = request.getSession(true);

			if (newUserSession.isNew()){
				newUserSession.setAttribute("UserID", userID);
				System.out.println("new user session");
			}else{
				Integer oldAccessCount = (Integer)newUserSession.getAttribute("accessCount"); 
				if (oldAccessCount != null) {
					accessCount = new Integer(oldAccessCount.intValue() + 1);
				}
				System.out.println("old user session");
			}
			newUserSession.setAttribute("accessCount", accessCount);
			
			if (authenticatedSuccessfully){
				//Redirects the current request to the newly created event
				String setLocation = "/Webapp/membre/"+username;
				response.sendRedirect(setLocation);
//				RequestDispatcher dispatcher = request.getRequestDispatcher(setLocation);
//				dispatcher.forward(request, response);
			}else{
				//TODO:send error message to user and return to login page
				String setLocation = "Webapp/connexion";
				response.sendRedirect(setLocation);
			}

		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}

	}
}
