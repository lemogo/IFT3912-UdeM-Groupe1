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
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.diro.Main;
import ca.diro.DataBase.Command.Command;
import ca.diro.DataBase.Command.PageInfoEvent;
import ca.diro.DataBase.Command.ResearchCancelledEvent;
import ca.diro.DataBase.Command.ResearchEvent;
import ca.diro.DataBase.Command.ResearchPastEvent;

public class SearchEventListHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5818151764848416043L;

	public static final int NUMBER_EVENTS_PER_PAGE = 10;

	private static final String EVENT_FUTURE = "0";
	private static final String EVENT_PAST = "1";
	private static final String EVENT_CANCELLED = "2";

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
			UnsupportedEncodingException, FileNotFoundException, SQLException,
			ServletException {
		String pathInfo = request.getPathInfo();
		if (pathInfo == null)
			pathInfo = "";
		else
			pathInfo = pathInfo.substring(1);

		processRequestHelper(request, response, pathInfo);
	}

	private void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response, String pathInfo) throws SQLException,
			UnsupportedEncodingException, FileNotFoundException, IOException {

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

	private JSONObject buildSearchEventList(HttpServletRequest request)
			throws SQLException, JSONException {
		HashMap<String, Event> sources = new HashMap<String, Event>();
		String searchParam = request.getParameter("searchStr");
		searchParam = searchParam == null ? "" : searchParam;

		LinkedList<String> searchInput = new LinkedList<String>(
				Arrays.asList(searchParam.split("[\\s]+")));
		// To add the filter possibility on searches.
		String filter = request.getParameter("filter");

		Command researchCommand = new ResearchEvent(searchInput);

		if (filter.equals(EVENT_PAST)) {
			researchCommand = new ResearchPastEvent(searchInput);
		} else if (filter.equals(EVENT_CANCELLED)) {
			researchCommand = new ResearchCancelledEvent(searchInput);
		}

		String offsetParam = request.getParameter("offset");
		offsetParam = offsetParam == null ? "0" : offsetParam;

		// Change to custom number if required.
		int numEventDisplay = NUMBER_EVENTS_PER_PAGE;

		int offset = Integer.parseInt(offsetParam);

		if (Main.getDatabase().executeDb(researchCommand)) {

			ResultSet listResultSet = researchCommand.getResultSet();
			while (listResultSet.next() && sources.size() < numEventDisplay) {
				if (offset == 0) {
					// Set badge to red if event is cancelled or past.
					String badgeClasse = filter.equals(EVENT_FUTURE) ? computeBadgeColor(Integer
							.parseInt(listResultSet.getString("numberplaces")))
							: computeBadgeColor(0);
					Event currentEvent = new Event(listResultSet, badgeClasse);
					if (!filter.equals(EVENT_FUTURE))
						currentEvent.setNumPlacesLeft(0);
					sources.put(currentEvent.getTitle(), currentEvent);
				} else {
					offset--;
				}
			}
		}
		if (filter.equals(EVENT_FUTURE)) {
			for (Entry<String, Event> entry : sources.entrySet()) {
				PageInfoEvent pageInfoEvent = new PageInfoEvent(""
						+ entry.getValue().getId(), Main.getDatabase());
				if (Main.getDatabase().executeDb(pageInfoEvent)) {
					entry.getValue().setNumPlacesLeft(
							pageInfoEvent.getAvailablePlaces());
				}
			}
		}
		return buildJSONResponse(sources);
	}

	private JSONObject buildJSONResponse(HashMap<String, Event> sources)
			throws JSONException {
		JSONObject JSONResponse = new JSONObject();

		JSONResponse.put("count", sources.size());

		JSONArray events = new JSONArray();
		for (Map.Entry<String, Event> eventEntry : sources.entrySet()) {
			events.put(new JSONObject(eventEntry.getValue().toMap()));
		}
		JSONResponse.put("events", events);

		return JSONResponse;
	}
}
