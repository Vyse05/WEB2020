$(window)
		.on(
				'load',
				function() {

					var gostId;
					$.ajax({
						type : 'GET',
						url : '/WebProj/rest/korisnik/info',
						dataType : "json",
						success : function(data) {
							gostID = data.id;
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							// TODO
						}
					});

					var potvrdiFormat = function(cell, formatterParams,
							onRendered) {

						return "<label>Potvrdi</label>";
					};
					var otkaziFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Otkazi</label>";
					};
					var test = 5;

					var OstaviKomentarFormat = function(cell, formatterParams,
							onRendered) { // plain
						if (test == 5) {
							return "<label>Oceni</label>";

						} else
							return "<label></label>";
					};

					var table = new Tabulator(
							"#korisnik-apartman-table",
							{
								ajaxURL : "../rezervacija/svi", // ajax
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
											field : "apartman",
										},
										{
											title : "Pocetni Datum",
											field : "pocetniDatumRezervacije",
											sorter : "number",
										},
										{
											title : "Broj Nocenja",
											field : "brojNocenja",
											sorter : "number",
											hozAlign : "center",
										},
										{
											title : "Cena",
											field : "ukupnaCena",
											sorter : "number",
											hozAlign : "center",
										},
										{
											title : "Poruka",
											field : "poruka",
										},
										{
											title : "Status",
											field : "status"
										},

									
										{
											formatter : function(cell,
													formatterParams, onRendered) {
												if (cell.getRow().getData().status == "KREIRANA") {
													return "<label>Otkazi</label>";
												} else {
													return "";
												}
											},
											title : "Otkazi Apartman",
											hozAlign : "center",
											cellClick : function(e, cell) {
											
												$
														.ajax({
															type : 'PUT',

															url : "/WebProj/rest/rezervacija/"
																	+ cell
																			.getRow()
																			.getData().id
																	+ "/odustani",
															success : function(
																	data) {
																alert("Odustanak uspesan");
															},
															error : function(
																	XMLHttpRequest,
																	textStatus,
																	errorThrown) {
																alert("Greska pri odustajanju20");

															}
														});
											}
										},
										{
											formatter : function(cell,
													formatterParams, onRendered) {
												if (cell.getRow().getData().status == "ODBIJENA") {
													return "<label>Komentarisi</label>";
												} else if (cell.getRow()
														.getData().status == "ZAVRŠENA") {
													return "<label>Komentarisi</label>";
												} else {
													return "";
												}
											},
											title : "Komentarisi",
											hozAlign : "center",
											cellClick : function(e, cell) {
												window.location.href = "http://localhost:8080/WebProj/rest/rezervacija/ostaviKomentar/"
														+ cell.getRow()
																.getData().id;
											}
										}, ],

							});
					// table.setFilter("gostId", "=", "gostId");
				});