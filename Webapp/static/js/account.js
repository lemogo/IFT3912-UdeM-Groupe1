$(document).ready(function(){
	$('#datepicker').datepicker({
	dateFormat : "yy-mm-dd",
	changeYear: true,
	defaultDate: "2000-01-01",
	yearRange: '1900:2014'
	}
	);
});