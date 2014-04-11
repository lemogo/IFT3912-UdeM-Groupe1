package ca.diro.RequestHandlingUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.diro.Main;
import ca.diro.DataBase.Command.PageInfoEvent;
import ca.diro.DataBase.Command.ResearchEvent;

public class SearchEventListHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5818151764848416043L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			catchHelper(request, response, e);
		}
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			UnsupportedEncodingException, FileNotFoundException, SQLException,
			ServletException {
		String pathInfo = request.getPathInfo();
		if (pathInfo == null)
			pathInfo = "";
		else
			pathInfo = pathInfo.substring(1);

		// The current request must be a file -> redirect to requestHandler
		if (pathInfo.contains(".")) {
			handleSimpleRequest(request, response, pathInfo);
			return;
		}

		// the pathInfo should be null
		// if(!pathInfo.equals("passes")&&!pathInfo.equals("annules")&&!pathInfo.equals("futur")&&!pathInfo.equals(""))
		if (isAnotherContext(pathInfo)) {
			String setLocation = "/Webapp/" + pathInfo;// "/";
			response.sendRedirect(setLocation);
			// request.getRequestDispatcher("/"+pathInfo).forward(request,
			// response);
			return;
		}

		// create a handle to the resource
		String filename = "liste-des-evenements.html";
		File staticResource = new File(staticDir, filename);
		File dynamicResource = new File(dynamicDir, filename);

		// Ressource existe
		if (!staticResource.exists() && !dynamicResource.exists()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			processTemplate(request, response, "404.html");
		} else {
			processRequestHelper(request, response, pathInfo, filename);
		}
	}

	private void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response, String pathInfo, String filename)
			throws SQLException, UnsupportedEncodingException,
			FileNotFoundException, IOException {
		setDefaultResponseContentCharacterAndStatus(response);

		HashMap<String, Object> sources = new HashMap<String, Object>();

		try {
			// TODO: Determine if this is the correct way to send this
			// information.
			PrintWriter responseWriter = response.getWriter();
			responseWriter.print(buildSearchEventList(request).toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession(true);
	}

	private JSONArray buildSearchEventList(HttpServletRequest request)
			throws SQLException, JSONException {
		HashMap<String, Event> sources = new HashMap<String, Event>();
		LinkedList<String> searchInput = new LinkedList<String>(
				Arrays.asList(request.getParameter("searchStr").split("[\\s]+")));
		ResearchEvent researchComand = new ResearchEvent(searchInput);

		int offset = Integer.parseInt(request.getParameter("offset")) + 10;
		// Change to custom number if required.
		int numEventDisplay = 10;

		if (Main.getDatabase().executeDb(researchComand)) {
			ResultSet listResultSet = researchComand.getResultSet();
			while (listResultSet.next() && sources.size() < numEventDisplay) {
				if (offset == 0) {
					// TODO: We're essentially searching the same info twice?
					// Might have to look into this.
					PageInfoEvent getEventCommand = new PageInfoEvent(
							listResultSet.getString("eventid"),
							Main.getDatabase());
					String badgeClasse = computeBadgeColor(getEventCommand
							.getAvailablePlaces());

					Event currentEvent = new Event(
							getEventCommand.getResultSet(), badgeClasse);
					sources.put(currentEvent.getTitle(), currentEvent);
					offset--;
				} else {
					offset--;
				}
			}
		}
		return buildJSONResponse(sources);
	}

	private JSONArray buildJSONResponse(HashMap<String, Event> sources)
			throws JSONException {
		JSONArray JSONResponse = new JSONArray();
		JSONResponse.put(new JSONObject().append("count", sources.size()));
		JSONArray events = new JSONArray();

		events.put(sources);

		JSONResponse.put(events);
		return JSONResponse;
	}
}
