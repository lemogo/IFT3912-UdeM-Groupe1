package ca.diro.RequestHandlingUtil;

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

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			catchHelper(request, response, e);
		}
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			UnsupportedEncodingException, FileNotFoundException, SQLException, ServletException {
		String pathInfo = request.getPathInfo();
		if (pathInfo == null) pathInfo="";
		else pathInfo = pathInfo.substring(1);

		processRequestHelper(request, response, pathInfo);
	}

	private void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response, String pathInfo)
			throws SQLException, UnsupportedEncodingException,
			FileNotFoundException, IOException {

		try {
			// TODO: Determine if this is the correct way to send this
			// information.
			PrintWriter responseWriter = response.getWriter();
			responseWriter.print(buildSearchEventList(request).toString());
			responseWriter.flush();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private JSONArray buildSearchEventList(HttpServletRequest request)
			throws SQLException, JSONException {
		HashMap<String, Event> sources = new HashMap<String, Event>();
		String searchParam = request.getParameter("searchStr");
		searchParam = searchParam == null ? "" : searchParam;
		
		LinkedList<String> searchInput = new LinkedList<String>(
				Arrays.asList(searchParam.split("[\\s]+")));
		ResearchEvent researchComand = new ResearchEvent(searchInput);
		
		String offsetParam = request.getParameter("offset");
		offsetParam = offsetParam == null ? "0" : offsetParam;
		
		String filter = request.getParameter("filter");

		int offset = Integer.parseInt(offsetParam) + 10;
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

		JSONObject events = new JSONObject();
		events.put("events", sources);
		JSONResponse.put(events);

		return JSONResponse;
	}
}
