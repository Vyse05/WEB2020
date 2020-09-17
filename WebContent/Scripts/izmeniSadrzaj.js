$(window).on('load', function(){
	var url = window.location.pathname;
	var id = url.substring(url.lastIndexOf('/') + 1);
	$("#canEdit").val(true);
	$.ajax({
		type : 'GET',
		url : '/WebProj/rest/sadrzaj/id',
		dataType : "json",
		success : function(data) {
				$("#naziv").val(data.naziv);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//TODO
			}
		});
});

$(document).on('submit','#forma',function(e){
	$("#errorSnimljeno").hide();
	$("#snimljeno").hide();
	$("#errorNaziv").hide();

	
	e.preventDefault();
	var naziv = $("#naziv").val();
	
	
	if (naziv == "") {
		$("#errorNaziv").show();
	} else {
		$.ajax({
			type : 'PUT',
			url : "/WebProj/rest/sadrzaj/"+id,
			contentType : 'application/json',
			data : formToJSON(naziv),
			success : function() {
				$("#snimljeno").show();
			},
			error : function(xhr, status) {
				$("#errorSnimljeno").show();
				$("#errorSnimljeno").text("Nije snimljeno");
			}
		});		
	}
	
});

function formToJSON(naziv) {
	return JSON.stringify({
		"naziv" : naziv,
	});
}

function canEdit(){ 
	return $("#canEdit").val();
}