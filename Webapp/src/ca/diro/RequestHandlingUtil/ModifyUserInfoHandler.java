package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.ModifyAccount;

public class ModifyUserInfoHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4302156479740043175L;

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
			String id = ""+userId;
			String fullname = request.getParameter("fullname");
			String email = request.getParameter("email");
			String username = (String) session.getAttribute(USERNAME_ATTRIBUTE); 
			String password = request.getParameter("passwordNew")==""?request.getParameter("passwordOld"):
						request.getParameter("passwordOld");
			String age = request.getParameter("age");
			String description = request.getParameter("description");
			
			//TODO:Check if the user new info is legal(no illegal characters or malicious scripts)
			
			ModifyAccount cmd = new ModifyAccount(id, fullname, email, username, password, age, description);
			modifiedSuccessfully = Main.getDatabase().executeDb(cmd);
			
			if(modifiedSuccessfully){
				//TODO:add a header to the response to show a modification success message to the user
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
}
