package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Request;

import ca.diro.Main;
import ca.diro.DataBase.Command.ModifyAccount;

public class ModifyUserInfoHandler extends RequestHandler {
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
			Boolean modifiedSuccessfully = true;
			
			HttpSession session = request.getSession(true);
			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
			
			int userId = Integer.parseInt((String) (session.getAttribute(USER_ID_ATTRIBUTE)==null?-1:session.getAttribute(USER_ID_ATTRIBUTE)));


			//TODO:Modify User's Information in the database
			String id = ""+userId;// request.getParameter("id");
			String fullname = request.getParameter("fullname");
			String email = request.getParameter("email");
			String username = (String) session.getAttribute(USERNAME_ATTRIBUTE); //request.getParameter("username");
			String password = 
//					request.getParameter("passwordNew")==""?request.getParameter("passwordOld"):
						request.getParameter("passwordOld");
			String age = request.getParameter("age");
			String description = request.getParameter("description");

			ModifyAccount cmd = new ModifyAccount(id, fullname, email, username, password, age, description);
			modifiedSuccessfully = Main.getDatabase().executeDb(cmd);
			
			if(modifiedSuccessfully){
				//redirects the current request to the newly created event
//				response.setHeader(arg0, arg1);
				String setLocation = "/Webapp/membre/"+username;
				response.sendRedirect(setLocation);
			}else{
				//TODO:Show modification error message
			}

		}
		catch (Exception e){
			catchHelper( request, response, e);
		}

	}

//	@Override
//	public void doGet(
//			HttpServletRequest request, HttpServletResponse response)
//					throws IOException, ServletException {
//		// TODO Implement handling logic for simple requests (and command
//		// validation) and forwarding for requests that require specific
//		// permissions or handling.
//		try
//		{
//			Boolean modifiedSuccessfully = true;
//
//			//TODO:Modify User's Information in the database
//			String id = "2";//request.getParameter("id");
////			String fullname = request.getParameter("fullname");
////			String email = request.getParameter("email");
////			String username = request.getParameter("username");
////			String password = request.getParameter("password");
////			String age = request.getParameter("age");
////			String description = request.getParameter("description");
//
//			if(modifiedSuccessfully){
//				//redirects the current request to the newly created event
//				String setLocation = "/Webapp/membre/"+id;
//				response.sendRedirect(setLocation);
//			}else{
//				//TODO:Show modification error message
//			}
//
//		}
//		catch (Exception e)
//		{
//			catchHelper( request, response, e);
//		}
//
//	}
}
