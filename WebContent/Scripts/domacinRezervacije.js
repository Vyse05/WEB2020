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

					var odobriFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Odobri</label>";
					};
					var odbijFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Odbij</label>";
					};
					var zavrsiFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Zavrsi</label>";
					};

					var table = new Tabulator(
							"#domacin-apartman-table",
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
											title : "Gost",
											field : "gost",
										},
										{
											title : "Status",
											field : "status",
										},
										{
											formatter : function(cell,
													formatterParams, onRendered) {
												if (cell.getRow().getData().status == "KREIRANA") {
													return "<label>Odbij</label>";
												} else {
													return "";
												}
											},
											title : "Odbij Rezervaciju",
											hozAlign : "center",
											cellClick : function(e, cell) {

												$
														.ajax({
															type : 'PUT',

															url : "/WebProj/rest/rezervacija/"
																	+ cell
																			.getRow()
																			.getData().id
																	+ "/odbij",
															success : function(
																	data) {
																alert("odbij uspesno");
															},
															error : function(
																	XMLHttpRequest,
																	textStatus,
																	errorThrown) {
																alert("Greska pri odbij");

															}
														});
											}
										},
										{
											formatter : function(cell,
													formatterParams, onRendered) {
												if (cell.getRow().getData().status == "KREIRANA") {
													return "<label>Odobri</label>";
												} else {
													return "";
												}
											},
											title : "Odobri Rezervaciju",
											hozAlign : "center",
											cellClick : function(e, cell) {

												$
														.ajax({
															type : 'PUT',

															url : "/WebProj/rest/rezervacija/"
																	+ cell
																			.getRow()
																			.getData().id
																	+ "/prihvati",
															success : function(
																	data) {
																alert("prihvati uspesno");
															},
															error : function(
																	XMLHttpRequest,
																	textStatus,
																	errorThrown) {
																alert("Greska pri prihvati");

															}
														});
											}
										},
										{
											formatter : function(cell,
													formatterParams, onRendered) {
												if (cell.getRow().getData().status == "ZAVRŠENA") {
													return "<label>Zavrsi</label>";
												} else if (cell.getRow()
														.getData().status == "ODUSTANAK") {
													return "<label>Zavrsi</label>";

												} else if (cell.getRow()
														.getData().status == "ODBIJENA") {
													return "<label>Zavrsi</label>";
												} else {
													return "";
												}
											},
											title : "Zavrsi Rezervaciju",
											hozAlign : "center",
											cellClick : function(e, cell) {

												$
														.ajax({
															type : 'PUT',

															url : "/WebProj/rest/rezervacija/"
																	+ cell
																			.getRow()
																			.getData().id
																	+ "/zavrsi",
															success : function(
																	data) {
																alert("zavrsi uspesno");
															},
															error : function(
																	XMLHttpRequest,
																	textStatus,
																	errorThrown) {
																alert("Greska pri zavrsi");

															}
														});
											}
										}, ],
							});
				});