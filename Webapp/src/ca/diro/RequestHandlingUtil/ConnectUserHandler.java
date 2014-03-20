package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;

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

//	private static final SecurityHandler basicAuth(String username, String password, String realm) {
//
//		HashLoginService l = new HashLoginService();
//		l.putUser(username, Credential.getCredential(password), new String[] {"user"});
//		l.setName(realm);
//
//		Constraint constraint = new Constraint();
//		constraint.setName(Constraint.__BASIC_AUTH);
//		constraint.setRoles(new String[]{"user"});
//		constraint.setAuthenticate(true);
//
//		ConstraintMapping cm = new ConstraintMapping();
//		cm.setConstraint(constraint);
//		cm.setPathSpec("/*");
//
//		ConstraintSecurityHandler csh = new ConstraintSecurityHandler();
//		csh.setAuthenticator(new BasicAuthenticator());
//		csh.setRealmName("myrealm");
//		csh.addConstraintMapping(cm);
//		csh.setLoginService(l);
//
//		return csh;
//
//	}
}
