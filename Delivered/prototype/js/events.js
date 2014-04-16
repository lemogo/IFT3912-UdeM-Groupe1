$(document).ready(function(){
	$("#search .filterSearch .dropdown-menu a").click(function(){
		$("#search .filterSearch .btn").html($(this).html() + ' <span class="caret"></span>');
	});
});