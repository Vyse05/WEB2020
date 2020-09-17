//Define some test data
$(window)
		.on(
				'load',
				function() {
					
				
						
						var url = window.location.pathname;
						var id = url.substring(url.lastIndexOf('/') + 1);
						
					
					

					var odobriFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Odobri</label>";
					};
					var ponistiFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Ponisti</label>";
					};

					// define Tabulator
					var table = new Tabulator("#example-table", {
						initialFilter : [ {
							field : "prikazatiKomentar",
							type : "like",
							value : "true"
						} ],
						height : "311px",
						ajaxURL : "../"+id+"/komentariKorisnik",
						layout : "fitDataTable",

						columns : [ {
							title : "Komentar",
							field : "komentar",
							hozAlign : "center",
							width : 800,
							formatter:"textarea",
							// sorter : "string",
							width : 800,
						}, {
							title : "Ocena",
							width : 100,
							vertAlign : "center",
							hozAlign : "center",
							field : "ocena",
							formatter:"star",
						// sorter : "number"
						},
						],
					});
					
					var table2 = new Tabulator(
							"#example-table2",
							{
								height : "311px",
								ajaxURL : "../"+id+"/komentariAdmin",
								layout : "fitDataTable",
								initialFilter : [ {
									field: "canEdit",
									type: "like",
									value: "true"
								} ],
								columns : [
										{
											title : "Komentar",
											field : "komentar",
											hozAlign : "center",
											width : 800,
											formatter : "textarea",
										// sorter : "string",
										},
										{
											title : "Ocena",
											width : 100,
											vertAlign : "center",
											hozAlign : "center",
											field : "ocena",
											formatter:"star",

										// sorter : "number"
										},
										{
											title : "Stanje Komentara",
											vertAlign : "center",
											hozAlign : "center",
											field : "prikazatiKomentar",
										// sorter : "number"
										},
										{
											formatter : function(cell,
													formatterParams, onRendered) {
												if (cell.getRow().getData().prikazatiKomentar == false) {
													return "<label>Odobri</label>";
												} else {
													return "";
												}
											},
											title : "Odobri",
											hozAlign : "center",
											cellClick : function(e, cell) {
												$
														.ajax({
															type : 'PUT',

															url : "/WebProj/rest/rezervacija/"
																	+ cell
																			.getRow()
																			.getData().id
																	+ "/odobriKomentar",
															success : function(
																	data) { // Vec
																			// Ulogovan
																alert("Komentar je Odobren");
															},
															error : function(
																	XMLHttpRequest,
																	textStatus,
																	errorThrown) {
																alert("Greska pri odobravanju komentara");

															}
														});
											}
										},
										{
											formatter : function(cell,
													formatterParams, onRendered) {
												if (cell.getRow().getData().prikazatiKomentar == true) {
													return "<label>Sakrij</label>";
												} else {
													return "";
												}
											},
											title : "Sakrij",
											hozAlign : "center",
											cellClick : function(e, cell) {
												$
														.ajax({
															type : 'PUT',
															url : "/WebProj/rest/rezervacija/"
																	+ cell
																			.getRow()
																			.getData().id
																	+ "/sakrijKomentar",
															success : function(
																	data) { // Vec
																			// Ulogovan
																alert("Komentar je Sakriven");
															},
															error : function(
																	XMLHttpRequest,
																	textStatus,
																	errorThrown) {
																alert("Greska pri sakrivanju komentara");

															}
														});
											}
										}, ],
							});
				});