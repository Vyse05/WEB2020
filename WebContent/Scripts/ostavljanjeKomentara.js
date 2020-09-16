$(document).on('submit','#forma',function(e){
	$("#errorKomentar").hide();
	$("#errorSnimljeno").hide();
	$("#snimljeno").hide();
	e.preventDefault();
	
	var url = window.location.pathname;
	var id = url.substring(url.lastIndexOf('/') + 1);
	
	var komentar = $("#komentar").val();
	var ocena = $("#ocena").val();
	
	if (komentar == "") {
		$("#errorKomentar").show();
	}
	else {
		$.ajax({
			type : 'PUT',
			url : "/WebProj/rest/rezervacija/"+id+"/komentar",
			contentType : 'application/json',
			data : formToJSON(komentar, ocena),
			success : function() {
				
				$("#snimljeno").show();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(XMLHttpRequest.status==403)
				{
					$("#errorSnimljeno").show();
				}
			}
		});		
	}
	
});

function formToJSON(komentar, ocena) {
	return JSON.stringify({
		"komentar" : komentar,
		"ocena" : ocena,
	});
}
