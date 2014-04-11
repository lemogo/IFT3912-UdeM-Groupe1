function eventTemplate(id, title, badgeClass, numPlacesLeft, date, location, description) {
	return '<div class="panel panel-info">' +
				'<div class="panel-heading">' +
				'	<div class="detailsButtons">' +
				'		<a class="btn btn-primary" href="evenement/' + id + '" role="button">Voir les détails / S\'inscrire</a>' +
				'	</div>' +
				'	<h4 class="panel-title">' +
				'		<a  href="evenement/' + id + '">' + title + '</a>' +
				'		<span class="place-left">' +
				'			<span class="badge ' + badgeClass + '" data-toggle="tooltip" data-placement="left" title="Places restantes">' + numPlacesLeft + '</span> places restantes' +
				'		</span>' +
				'	</h4>' +
				'</div>' +

				'<div class="panel-collapse collapse in">' +
				'	<div class="panel panel-default">' +
				'		<div class="panel-body">' +
				'			<h5 class="panel-title-item-heading">Date et heure</h5>' +
				'			<p>' + date + '</p>' +
				'			<h5 class="panel-title-item-heading">Lieu</h5>' +
				'			<p>' + location + '</p>' +
				'			<h5 class="panel-title-item-heading">Description</h5>' +
				'			<p>' + description + '</p>' +
				'		</div>' +
				'	</div>' +
				'</div>' +
			'</div>';
}