$(document).on('submit','#forma',function(e){
	$("#errorIme").hide();
	$("#errorPrezime").hide();
	$("#errorKorisnickoNijeUneto").hide();
	$("#errorKorisnickoZauzeto").hide();
	$("#errorPol").hide();
	$("#errorPassword1").hide();
	$("#errorPassword2").hide();
	$("#errorPasswordMatch").hide();
	
	e.preventDefault();
	console.log("registracija");
	var ime = $("#ime").val();
	var prezime = $("#prezime").val();
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
			url : "/WebProj/rest/korisnik/registracija",
			contentType : 'application/json',
			data : formToJSON(username, password1, ime, prezime, pol),
			success : function() {
					window.location.href = "/WebProj/rest/korisnik/login";
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#errorKorisnickoZauzeto").show();
			}
		});		
	}
	
});

function formToJSON(korisnickoIme, lozinka, ime, prezime, pol) {
	return JSON.stringify({
		"korisnickoIme" : korisnickoIme,
		"lozinka" : lozinka,
		"ime" : ime,
		"prezime" : prezime,
		"pol" : pol
	});
}