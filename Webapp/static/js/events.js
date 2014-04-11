$(document).ready(function(){
	var offset = 0;	//Offset for the event pager
	var limit = 10; //Number of events we want to fetch 
	var filter = 0; //0 : all events, 1 : passed events, 2 : cancelled events
	
	//Ajax request for events display
	var getEvents = function(){
		$(".loading").css("display", "block");
		$("#viewMoreEvents").css("display", "none");
		var searchStr = $("#search .searchInput").val();
		$.post('listEventsAjax', {
			offset : offset,
			searchStr : searchStr,
			filter : filter
		},
		function(data) { alert(data)
			for(var i = 0; i < data.count; i++) {
				var eventDisplay = eventTemplate(data.events[i].id, data.events[i].title, data.events[i].badgeClass, data.events[i].numPlacesLeft, data.events[i].date, data.events[i].location, data.events[i].description);
				$("#eventsHolder").append(eventDisplay);
			}
			$(".loading").css("display", "none");
			$("#viewMoreEvents").css("display", "block");
		}, 'json');
		offset += limit;
	};
	
	//Add listeners
	$("#viewMoreEvents").click(getEvents);
	
	$("#search .filterSearch .dropdown-menu a").click(function(){
		$("#search .filterSearch .btn").html($(this).html() + ' <span class="caret"></span>');
		$("#eventsHolder").html("");
		filter = $(this).attr("rel");
		offset = 0;
		getEvents();
	});
	
	$("#search .searchBtn").click(function(){
		$("#eventsHolder").html("");
		offset = 0;
		getEvents();
	});
	
	//Fetch the events when page is loaded!
	getEvents();
});