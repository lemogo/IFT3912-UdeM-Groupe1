$(document).ready(function(){
	$(".alert .deleteNotification").click(function(){
		$(this).parent().fadeOut(500);
		$.post('deleteNotification', {
			id : $(this).attr("rel")
		},
		function(data) {
			
		}, 'html');
	});
});