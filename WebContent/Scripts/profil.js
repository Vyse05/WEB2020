$(window).on('load', function(){
	$("#canEdit").val(true);
	$.ajax({
		type : 'GET',
		url : '/WebProj/rest/korisnik/info',
		dataType : "json",
		success : function(data) {
			$("#ime").val(data.ime);
			$("#prezime").val(data.prezime);
			$("#username").val(data.korisnickoIme);
			$("#pol").val(data.pol);
			if(data.uloga == 'Administrator'){
				$("#ime").prop( "disabled", true );
				$("#prezime").prop( "disabled", true );
				$("#oldPassword").prop( "disabled", true );
				$("#password1").prop( "disabled", true );
				$("#password2").prop( "disabled", true );
				$("#username").prop( "disabled", true );
				$("#pol").prop( "disabled", true );	
				$("#submit").prop( "hidden", true );	
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//TODO
			}
		});
});

$(document).on('submit','#forma',function(e){
	$("#errorSnimljeno").hide();
	$("#snimljeno").hide();
	$("#errorIme").hide();
	$("#errorPrezime").hide();
	$("#errorPol").hide();
	$("#errorOldPassword").hide();
	$("#errorPassword1").hide();
	$("#errorPassword2").hide();
	$("#errorPasswordMatch").hide();
	
	e.preventDefault();
	var ime = $("#ime").val();
	var prezime = $("#prezime").val();
	var oldPassword = $("#oldPassword").val();
	var password1 = $("#password1").val();
	var password2 = $("#password2").val();
	var username = $("#username").val();
	var pol = $("#pol").val();
	
	if (ime == "") {
		$("#errorIme").show();
	}  else if (prezime == "") {
		$("#errorPrezime").show();
	} else if (username == "") {
		$("#errorKorisnickoNijeUneto").show();
	} else if (pol == "") {
		$("#errorPol").show();
	}else if (oldPassword == "") {
		$("#errorOldPassword").show();
	}else if (password1 == "") {
		$("#errorPassword1").show();
	}  else if (password2 == "") {
		$("#errorPassword2").show();
	} else if (password1 != password2) {
		$("#errorPasswordMatch").show();
	}
	else {
		$.ajax({
			type : 'POST',
			url : "/WebProj/rest/korisnik/info",
			contentType : 'application/json',
			data : formToJSON(ime, prezime, pol, oldPassword, password1),
			success : function() {
				$("#snimljeno").show();
			},
			error : function(xhr, status) {
				$("#errorSnimljeno").show();
				$("#errorSnimljeno").text("Nije snimljeno: "+$.parseJSON(xhr.responseText).error);
			}
		});		
	}
	
});

function formToJSON(ime, prezime, pol, staraLozinka, novaLozinka) {
	return JSON.stringify({
		"staraLozinka" : staraLozinka,
		"novaLozinka" : novaLozinka,
		"ime" : ime,
		"prezime" : prezime,
		"pol" : pol
	});
}

function canEdit(){ 
	return $("#canEdit").val();
}