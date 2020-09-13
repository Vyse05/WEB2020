$(window).on('load', function(){
	$.ajax({
		type : 'GET',
		url : '/WebProj/rest/korisnik/info',
		dataType : "json",
		success : function(data) {
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// Nije ulogovan
			window.location.href = "/WebProj/rest/korisnik/login";			
		}
		});
	
	var url = window.location.pathname;
	var apartmanId = url.substring(url.lastIndexOf('/') + 1);
	
	$.ajax({
		type : 'GET',
		url : '/WebProj/rest/apartman/'+apartmanId+'/info',
		dataType : "json",
		success : function(data) {
			 $("#apartmanId").val(apartmanId);
			 $("#apartman").text(data.ulica + " "+ data.broj + ", " +data.postanskiBroj + " " + data.naseljenoMesto);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// Ne postoji apartman
			$("#apartman").text("Apartman nije pronađen.");
		}
		});
});

$(document).on('submit','#forma',function(e){
	$("#errorPocetniDatumRezervacije").hide();
	$("#errorBrojNocenja").hide();
	$("#errorSnimljeno").hide();
	$("#snimljeno").hide();
	e.preventDefault();

	var apartmanId = $("#apartmanId").val();
	var pocetniDatumRezervacije = $("#pocetniDatumRezervacije").val();
	var brojNocenja = $("#brojNocenja").val();
	var poruka = $("#poruka").val();
	
	if (pocetniDatumRezervacije == "") {
		$("#errorPocetniDatumRezervacije").show();
	} else if (brojNocenja == "") {
		$("#errorBrojNocenja").show();
	}
	else {
		$.ajax({
			type : 'POST',
			url : "/WebProj/rest/rezervacija/nov",
			contentType : 'application/json',
			data : formToJSON(apartmanId, pocetniDatumRezervacije, brojNocenja, poruka),
			success : function() {
				$("#snimljeno").show();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#errorSnimljeno").show();
			}
		});		
	}
	
});

function formToJSON(apartmanId, pocetniDatumRezervacije, brojNocenja, poruka) {
	return JSON.stringify({
		"apartmanId" : apartmanId,
		"pocetniDatumRezervacije" : pocetniDatumRezervacije,
		"brojNocenja" : brojNocenja,
		"poruka" : poruka
	});
}
