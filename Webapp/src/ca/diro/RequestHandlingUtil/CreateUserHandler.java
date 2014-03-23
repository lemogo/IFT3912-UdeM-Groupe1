package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

public class CreateUserHandler extends RequestHandler {
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
		try
		{

			String fullname = request.getParameter("fullname");
			String email = request.getParameter("email");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String age = request.getParameter("age");
			String description = request.getParameter("description");
			
			System.out.println("In creae User parameters:"
					+fullname+"\t"
					+username+"\t"
					+password+"\t"
					+age+"\t"
					+description+"\t"
					+email+"\t"
					);
			
			//TODO:Add the User in the database
			Boolean userAddedSuccessfully = true;

			//TODO:get user id from database
			String userID = "";
			
			
			if (userAddedSuccessfully){
				//redirects the current request to the newly created event
				String setLocation = "/Webapp/membre/"+userID;
				response.sendRedirect(setLocation);
			}else{
				//return to the account creation page 
				//and try to indicate to the user the source of the account creation failure 
				System.out.println("failled to create new user account");
				String setLocation = "/Webapp/enregistrement";
				response.sendRedirect(setLocation);
				RequestDispatcher dispatcher = request.getRequestDispatcher(setLocation);
				dispatcher.forward(request, response);
			}

		}
		catch (Exception e)
		{
			System.out.println("In catch exception");
//			catchHelper(baseRequest, request, response, e);		
		}

	}

}
