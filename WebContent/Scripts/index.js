$(window)
		.on(
				'load',
				function() {
					

					$.ajax({
						type : 'GET',
						url : '/WebProj/rest/korisnik/info',
						dataType : "json",
						success : function(data) {
							if (data.uloga == 'Administrator') {
								$("#neulogovan-group").prop("hidden", true);
								$("#korisnik-group").prop("hidden", true);
								$("#domacin-group").prop("hidden", true);
							} else if (data.uloga == "Korisnik") {
								$("#neulogovan-group").prop("hidden", true);
								$("#domacin-group").prop("hidden", true);
								$("#admin-group").prop("hidden", true);

							} else if (data.uloga == "Domaćin") {
								$("#neulogovan-group").prop("hidden", true);
								$("#korisnik-group").prop("hidden", true);
								$("#admin-group").prop("hidden", true);
							}
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							$("#domacin-group").prop("hidden", true);
							$("#korisnik-group").prop("hidden", true);
							$("#admin-group").prop("hidden", true);
							// TODO
						}
						
					});

					var rezervacijaFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Rezervisi</label>";
					};
					var brisanjeFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Obrisi</label>";
					};
					var aktivacijaFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Aktiviraj</label>";
					};
					var deaktivacijaFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Deaktiviraj</label>";
					};
					var komentarFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Procitaj</label>";
					};
					var izmeniFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Izmeni</label>";
					};

					var minMaxFilterEditor = function(cell, onRendered,
							success, cancel, editorParams) {

						var end;

						var container = document.createElement("span");

						// create and style inputs
						var start = document.createElement("input");
						start.setAttribute("type", "number");
						start.setAttribute("placeholder", "Min");
						start.setAttribute("min", 0);
						start.setAttribute("max", 100);
						start.style.padding = "4px";
						start.style.width = "50%";
						start.style.boxSizing = "border-box";

						start.value = cell.getValue();

						function buildValues() {
							success({
								start : start.value,
								end : end.value,
							});
						}

						function keypress(e) {
							if (e.keyCode == 13) {
								buildValues();
							}

							if (e.keyCode == 27) {
								cancel();
							}
						}

						end = start.cloneNode();
						end.setAttribute("placeholder", "Max");

						start.addEventListener("change", buildValues);
						start.addEventListener("blur", buildValues);
						start.addEventListener("keydown", keypress);

						end.addEventListener("change", buildValues);
						end.addEventListener("blur", buildValues);
						end.addEventListener("keydown", keypress);

						container.appendChild(start);
						container.appendChild(end);

						return container;
					}

					// custom max min filter function
					function minMaxFilterFunction(headerValue, rowValue,
							rowData, filterParams) {
						// headerValue - the value of the header filter element
						// rowValue - the value of the column in this row
						// rowData - the data for the row being filtered
						// filterParams - params object passed to the
						// headerFilterFuncParams property

						if (rowValue) {
							if (headerValue.start != "") {
								if (headerValue.end != "") {
									return rowValue >= headerValue.start
											&& rowValue <= headerValue.end;
								} else {
									return rowValue >= headerValue.start;
								}
							} else {
								if (headerValue.end != "") {
									return rowValue <= headerValue.end;
								}
							}
						}

						return true; // must return a boolean, true if it
						// passes the
						// filter.
					}

					var table = new Tabulator(
							"#neulogovan-table",
							{
								ajaxURL : "../WebProj/rest/apartman/svi", // ajax
								initialFilter : [ {
									field : "aktivno",
									type : "like",
									value : "true"
								} ],
								// URL
								height : "311px",
								pagination : "local",
								layout : "fitColumns",
								paginationSize : 10,
								columns : [
										{
											title : "Id",
											field : "id",
										},
										{
											title : "Lokacija",
											field : "naseljenoMesto",
											headerFilter : "input"
										},
										{
											title : "Cena",
											field : "cena",
											sorter : "number",
											hozAlign : "center",
											headerFilter : minMaxFilterEditor,
											headerFilterFunc : minMaxFilterFunction,
											headerFilterLiveFilter : false
										},
										{
											title : "Broj Soba",
											field : "brojSoba",
											sorter : "number",
											hozAlign : "center",
											headerFilter : "number",
											headerFilterPlaceholder : "at least...",
											headerFilterFunc : ">="
										},
										{
											title : "Broj Osoba",
											field : "brojGostiju",
											sorter : "number",
											hozAlign : "center",
											headerFilter : "number",
											headerFilterPlaceholder : "at least...",
											headerFilterFunc : ">="
										},
										{
											formatter : komentarFormat,
											title : "Komentari",
											hozAlign : "center",
											cellClick : function(e, cell) {
												window.location.href = "http://localhost:8080/WebProj/rest/apartman/komentari/"
														+ cell.getRow()
																.getData().id;
											}
										}, ],
							});

					var table = new Tabulator(
							"#korisnik-table",
							{
								ajaxURL : "../WebProj/rest/apartman/svi", // ajax
								initialFilter : [ {
									field : "aktivno",
									type : "like",
									value : "true"
								}, ],
								// URL
								height : "311px",
								pagination : "local",
								layout : "fitColumns",
								paginationSize : 10,
								columns : [
										{
											title : "Id",
											field : "id",
										},
										{
											title : "Lokacija",
											field : "naseljenoMesto",
											headerFilter : "input"
										},
										{
											title : "Cena",
											field : "cena",
											sorter : "number",
											hozAlign : "center",
											headerFilter : minMaxFilterEditor,
											headerFilterFunc : minMaxFilterFunction,
											headerFilterLiveFilter : false
										},
										{
											title : "Broj Soba",
											field : "brojSoba",
											sorter : "number",
											hozAlign : "center",
											headerFilter : "number",
											headerFilterPlaceholder : "at least...",
											headerFilterFunc : ">="
										},
										{
											title : "Broj Osoba",
											field : "brojGostiju",
											sorter : "number",
											hozAlign : "center",
											headerFilter : "number",
											headerFilterPlaceholder : "at least...",
											headerFilterFunc : ">="
										},
										{
											formatter : rezervacijaFormat,
											title : "Rezervacija",
											hozAlign : "center",
											cellClick : function(e, cell) {
												window.location.href = "http://localhost:8080/WebProj/rest/rezervacija/nov/"
														+ cell.getRow()
																.getData().id;
											}
										},
										{
											formatter : komentarFormat,
											title : "Komentari",
											hozAlign : "center",
											cellClick : function(e, cell) {
												window.location.href = "http://localhost:8080/WebProj/rest/apartman/komentari/"
														+ cell.getRow()
																.getData().id;
											}
										},

								],
							});
					var table = new Tabulator(
							"#domacin-table",
							{
								ajaxURL : "../WebProj/rest/apartman/svi", // ajax
								initialFilter : [ {
									field : "aktivno",
									type : "like",
									value : "true"
								}, {
									field : "canEdit",
									type : "like",
									value : "true"
								} ],
								// URL
								height : "311px",
								pagination : "local",
								layout : "fitColumns",
								paginationSize : 10,
								columns : [
										{
											title : "Id",
											field : "id",
										},
										{
											title : "Lokacija",
											field : "naseljenoMesto",
											headerFilter : "input"
										},
										{
											title : "Cena",
											field : "cena",
											sorter : "number",
											hozAlign : "center",
											headerFilter : minMaxFilterEditor,
											headerFilterFunc : minMaxFilterFunction,
											headerFilterLiveFilter : false
										},
										{
											title : "Broj Soba",
											field : "brojSoba",
											sorter : "number",
											hozAlign : "center",
											headerFilter : "number",
											headerFilterPlaceholder : "at least...",
											headerFilterFunc : ">="
										},
										{
											title : "Broj Osoba",
											field : "brojGostiju",
											sorter : "number",
											hozAlign : "center",
											headerFilter : "number",
											headerFilterPlaceholder : "at least...",
											headerFilterFunc : ">="
										},
										{
											formatter : komentarFormat,
											title : "Komentari",
											hozAlign : "center",
											cellClick : function(e, cell) {
												window.location.href = "http://localhost:8080/WebProj/rest/apartman/komentari/"
														+ cell.getRow()
																.getData().id;
											}
										},

										{
											formatter : function(cell,
													formatterParams, onRendered) {
												if (cell.getRow().getData().canEdit == true) {
													return "<label>Izmeni</label>";
												} else {
													return "";
												}
											},
											title : "Izmeni",
											hozAlign : "center",
											cellClick : function(e, cell) {
												window.location.href = "http://localhost:8080/WebProj/rest/apartman/"
														+ cell.getRow()
																.getData().id;
											}
										}, ],
							});

					var table = new Tabulator(
							"#domacin-table2",
							{
								ajaxURL : "../WebProj/rest/apartman/svi", // ajax
								// URL
								initialFilter : [ {
									field : "aktivno",
									type : "like",
									value : "false"
								}, {
									field : "canEdit",
									type : "like",
									value : "true"
								} ],
								height : "311px",
								pagination : "local",
								layout : "fitColumns",
								paginationSize : 10,
								columns : [
										{
											title : "Id",
											field : "id",
										},
										{
											title : "Lokacija",
											field : "naseljenoMesto",
										},
										{
											title : "Cena",
											field : "cena",
											hozAlign : "center",
										},
										{
											title : "Broj Soba",
											field : "brojSoba",
											hozAlign : "center",
										},
										{
											title : "Broj Osoba",
											field : "brojGostiju",
											hozAlign : "center",
										},
										{
											formatter : komentarFormat,
											title : "Komentari",
											hozAlign : "center",
											cellClick : function(e, cell) {
												window.location.href = "http://localhost:8080/WebProj/rest/apartman/komentari/"
														+ cell.getRow()
																.getData().id;
											}
										},
										{
											formatter : function(cell,
													formatterParams, onRendered) {
												if (cell.getRow().getData().canEdit == true) {
													return "<label>Izmeni</label>";
												} else {
													return "";
												}
											},
											title : "Izmeni",
											hozAlign : "center",
											cellClick : function(e, cell) {
												window.location.href = "http://localhost:8080/WebProj/rest/apartman/"
														+ cell.getRow()
																.getData().id;
											}
										}, ],
							});

					var table = new Tabulator(
							"#admin-table",
							{
								ajaxURL : "../WebProj/rest/apartman/svi", // ajax
								// URL
								height : "311px",
								pagination : "local",
								layout : "fitColumns",
								paginationSize : 10,
								columns : [
										{
											title : "Id",
											field : "id",
										},
										{
											title : "Lokacija",
											field : "naseljenoMesto",
											headerFilter : "input"
										},
										{
											title : "Cena",
											field : "cena",
											sorter : "number",
											hozAlign : "center",
											headerFilter : minMaxFilterEditor,
											headerFilterFunc : minMaxFilterFunction,
											headerFilterLiveFilter : false
										},
										{
											title : "Broj Soba",
											field : "brojSoba",
											sorter : "number",
											hozAlign : "center",
											headerFilter : "number",
											headerFilterPlaceholder : "at least...",
											headerFilterFunc : ">="
										},
										{
											title : "Broj Osoba",
											field : "brojGostiju",
											sorter : "number",
											hozAlign : "center",
											headerFilter : "number",
											headerFilterPlaceholder : "at least...",
											headerFilterFunc : ">="
										},
										{
											title : "Aktiviran",
											field : "aktivno"
										},

										{
											formatter : komentarFormat,
											title : "Komentari",
											hozAlign : "center",
											cellClick : function(e, cell) {
												window.location.href = "http://localhost:8080/WebProj/rest/apartman/komentari/"
														+ cell.getRow()
																.getData().id;
											}
										},
										{
											formatter : izmeniFormat,
											title : "Izmeni",
											hozAlign : "center",
											cellClick : function(e, cell) {
												window.location.href = "http://localhost:8080/WebProj/rest/apartman/"
														+ cell.getRow()
																.getData().id;
											},

										},

										{

											formatter : function(cell,
													formatterParams, onRendered) {
												if (cell.getRow().getData().aktivno == false) {
													return "<label>Aktiviraj</label>";
												} else {
													return "";
												}
											},
											title : "Aktivacija",
											hozAlign : "center",
											cellClick : function(e, cell) {
												$
														.ajax({
															type : 'PUT',
															url : "/WebProj/rest/apartman/"
																	+ cell
																			.getRow()
																			.getData().id
																	+ "/aktiviraj",
															contentType : 'application/json',

															success : function() {
																alert("Uspesno aktiviran")
															},
															error : function(
																	XMLHttpRequest,
																	textStatus,
																	errorThrown) {
																if (XMLHttpRequest.status == 403) {
																	alert("Greska pri aktivaciji")
																}
															}
														});
											}
										},
										{
											formatter : function(cell,
													formatterParams, onRendered) {
												if (cell.getRow().getData().aktivno == true) {
													return "<label>Deaktiviraj</label>";
												} else {
													return "";
												}
											},
											title : "Deaktivacija",
											hozAlign : "center",
											cellClick : function(e, cell) {
												$
														.ajax({
															type : 'PUT',
															url : "/WebProj/rest/apartman/"
																	+ cell
																			.getRow()
																			.getData().id
																	+ "/deaktiviraj",
															contentType : 'application/json',

															success : function() {
																alert("Uspesno deaktiviran")
															},
															error : function(
																	XMLHttpRequest,
																	textStatus,
																	errorThrown) {
																if (XMLHttpRequest.status == 403) {
																	alert("Greska pri deaktivaciji")
																}
															}
														});
											}
										},
										{
											formatter : brisanjeFormat,
											title : "Brisanje",
											hozAlign : "center",
											cellClick : function(e, cell) {
												$
														.ajax({
															type : 'DELETE',
															url : "/WebProj/rest/apartman/"
																	+ cell
																			.getRow()
																			.getData().id,
															contentType : 'application/json',

															success : function() {
																alert("Uspesno obrisan")
															},
															error : function(
																	XMLHttpRequest,
																	textStatus,
																	errorThrown) {
																if (XMLHttpRequest.status == 403) {
																	alert("Greska pri brisanju")
																}
															}
														});
											}
										}, ],
							});

				});

$(document).on(
		'submit',
		'#forma',
		function(e) {
			$("#errorDatumRezervacije").hide();
			
			var pocetniDatumRezervacije = $("#pocetniDatumRezervacije").val();
			var pocetniDatumRezervacije = $("#krajnjiDatumRezervacije").val();


			if (pocetniDatumRezervacije == "" && krajnjiDatumRezervacije != "") {
				$("#errorDatumRezervacije").show();
			} else if (pocetniDatumRezervacije != "" && krajnjiDatumRezervacije == "") {
				$("#errorDatumRezervacije").show();
			} else if (pocetniDatumRezervacije == "" && krajnjiDatumRezervacije == "") {
				$("#errorDatumRezervacije").show();
			} else {
				$.ajax({
					type : 'POST',
					url : "/WebProj/rest/rezervacija/nov",
					contentType : 'application/json',
					data : formToJSON(apartmanId, pocetniDatumRezervacije,
							brojNocenja, poruka),
					success : function() {
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
					}
				});
			}
		});

function formToJSON(apartmanId, pocetniDatumRezervacije, krajnjiDatumRezervacije) {
	return JSON.stringify({
		"pocetniDatumRezervacije" : pocetniDatumRezervacije,
		"krajnjiDatumRezervacije" : krajnjiDatumRezervacije,
	});
}
