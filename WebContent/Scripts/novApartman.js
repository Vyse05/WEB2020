$(document).on('submit','#forma',function(e){
	$("#errorSnimljeno").hide();
	$("#snimljeno").hide();
	$("#errorTip").hide();
	$("#errorGeografskaSirina").hide();
	$("#errorGeografskaDuzina").hide();
	$("#errorUlica").hide();
	$("#errorBroj").hide();
	$("#errorNaseljenoMesto").hide();
	$("#errorPostanskiBroj").hide();
	$("#errorBrojSoba").hide();
	$("#errorBrojGostiju").hide();
	$("#errorCena").hide();
	$("#errorVremeZaPrijavu").hide();
	$("#errorVremeZaOdjavu").hide();
	
	e.preventDefault();

	var tip = $("#tip").val();
	var geografskaSirina = $("#geografskaSirina").val();
	var geografskaDuzina = $("#geografskaDuzina").val();
	var ulica = $("#ulica").val();
	var broj = $("#broj").val();
	var naseljenoMesto = $("#naseljenoMesto").val();
	var postanskiBroj = $("#postanskiBroj").val();
	var brojSoba = $("#brojSoba").val();
	var brojGostiju = $("#brojGostiju").val();
	var cena = $("#cena").val();
	var vremeZaPrijavu = $("#vremeZaPrijavu").val();
	var vremeZaOdjavu = $("#vremeZaOdjavu").val();
	
	if (tip == "") {
		$("#errorTip").show();
	} else if (geografskaSirina == "") {
		$("#errorGeografskaSirina").show();
	}  else if (geografskaDuzina == "") {
		$("#errorGeografskaDuzina").show();
	} else if (ulica == "") {
		$("#errorUlica").show();
	} else if (broj == "") {
		$("#errorBroj").show();
	}else if (naseljenoMesto == "") {
		$("#errorNaseljenoMesto").show();
	}else if (postanskiBroj == "") {
		$("#errorPostanskiBroj").show();
	}else if (brojSoba == "") {
		$("#errorBrojSoba").show();
	}else if (brojGostiju == "") {
		$("#errorBrojGostiju").show();
	}else if (cena == "") {
		$("#errorCena").show();
	}else if (vremeZaPrijavu == "") {
		$("#errorvremeZaPrijavu").show();
	}else if (vremeZaOdjavu == "") {
		$("#errorvremeZaOdjavu").show();
	}
	else {
		$.ajax({
			type : 'POST',
			url : "/WebProj/rest/apartman/nov",
			contentType : 'application/json',
			data : formToJSON(tip, geografskaSirina, geografskaDuzina, ulica, broj, naseljenoMesto, postanskiBroj, brojSoba, brojGostiju, cena, vremeZaPrijavu, vremeZaOdjavu),
			success : function() {
				$("#snimljeno").show();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#errorSnimljeno").show();
			}
		});		
	}
	
});

function formToJSON(tip, geografskaSirina, geografskaDuzina, ulica,
		broj, naseljenoMesto, postanskiBroj, brojSoba,
		brojGostiju, cena, vremeZaPrijavu, vremeZaOdjavu) {
	return JSON.stringify({
		"tip" : tip,
		"geografskaSirina" : geografskaSirina,
		"geografskaDuzina" : geografskaDuzina,
		"ulica" : ulica,
		"broj" : broj,
		"naseljenoMesto": naseljenoMesto,
		"postanskiBroj": postanskiBroj,
		"brojSoba": brojSoba,
		"brojGostiju": brojGostiju,
		"cena": cena,
		"vremeZaPrijavu": vremeZaPrijavu,
		"vremeZaOdjavu": vremeZaOdjavu,
		"sadrzajIds":[1,2,3]
	});
}
