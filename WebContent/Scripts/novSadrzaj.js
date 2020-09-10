$(document).on('submit', '#forma', function(e) {
	$("#errorSnimljeno").hide();
	$("#snimljeno").hide();
	$("#errorNaziv").hide();

	e.preventDefault();

	var naziv = $("#naziv").val();

	if (naziv == "") {
		$("#errorNaziv").show();
	} else {
		$.ajax({
			type : 'POST',
			url : "/WebProj/rest/sadrzaj/nov",
			contentType : 'application/json',
			data : formToJSON(naziv),
			success : function() {
				$("#snimljeno").show();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#errorSnimljeno").show();
			}
		});
	}

});

function formToJSON(naziv) {
	return JSON.stringify({
		"naziv" : naziv
	});
}
