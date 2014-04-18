package ca.diro.RequestHandlingUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.diro.Main;
import ca.diro.DataBase.DBHelper;
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.CreateUserAccount;

public class CreateUserHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4706259148042715250L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HashMap<String, String> errors = new HashMap<String,String>();
		try {
			if (isLoggedIn(request.getSession(true))) {
				// possibly:show popup, user is already logged in as username,
				// ask user if they would like to logout and log in as ____
				// and redirect to user's page
				String username = request.getParameter(USERNAME_ATTRIBUTE);
				String setLocation = "/Webapp/membre/" + username;
				response.sendRedirect(setLocation);
				return;
			}
			String fullname = request.getParameter("fullname");
			String email = request.getParameter("email");
			String userName = request.getParameter("username");
			String password = (String) request.getParameter("password");
			String age = request.getParameter("age");
			String description = request.getParameter("description");

			// Add the User in the database
			DataBase db = Main.getDatabase();
			CreateUserAccount cmd = new CreateUserAccount(userName, password,
					fullname, email, age, description, db);

			
			DBHelper dbh = new DBHelper(db);
			ResultSet allUsers = dbh.getAllUser();
			boolean error = false;		
			while (allUsers.next()) {
				if (allUsers.getString("username").equals(userName)) {
					errors.put("errorUser",
							"Champ invalide: username en utilisation.");
					error = true;
				}
				if (allUsers.getString("email").equals(email)) {
					errors.put("errorEmail",
							"Champ invalide: courriel en utilisation.");
					error = true;
				}
			}
			if (!error && db.executeDb(cmd)) {
				// redirects the current request to the login handler who
				// then redirects to newly created event
				String setLocation = "/connect-user";
				request.getRequestDispatcher(setLocation).forward(request,
						response);
			} else if (!error){
				// return to the account creation page
				// and try to indicate to the user the source of the account
				// creation failure
				errors.put("errorGeneral",
						"Erreur lors de la creation du compte");
			}
			setDefaultResponseContentCharacterAndStatus(response);
			processTemplate(request, response, "header.html", errors);
			processTemplate(request, response, "enregistrement.html", errors);
			processTemplate(request, response, "footer.html");
		} catch (Exception e) {
			System.out.println("In CreateUserHandler catch exception");
			catchHelper(request, response, e);
		}
	}
}
