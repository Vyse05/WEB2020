$(document).on('submit','#forma',function(e){
	$("#errorUsername").hide();
	$("#errorPassword").hide();
	
	e.preventDefault();
	
	var password = $("#password").val();
	var username = $("#username").val();
	
	if (username == "") {
		$("#errorUsername").show();
	} else if (password == "") {
		$("#errorPassword").show();
	}
	else {
		$.ajax({
			type : 'POST',
			url : "/WebProj/rest/korisnik/login",
			contentType : 'application/json',
			data : formToJSON(username, password),
			success : function() {
				window.location.href = "/WebProj/index.html";
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(XMLHttpRequest.status==403)
				{
					$("#errorLogin").show();
				}
			}
		});		
	}
	
});

function formToJSON(korisnickoIme, lozinka) {
	return JSON.stringify({
		"korisnickoIme" : korisnickoIme,
		"lozinka" : lozinka,
	});
}
