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
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							//TODO
							}
						});
					
					var potvrdiFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
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
						if (test == 5){
							return "<label>Oceni</label>";

						}
						else return "<label></label>";
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
											formatter : potvrdiFormat,
											title : "Potvrdi Apartman",
											hozAlign : "center",
											cellClick : function(e, cell) {
												alert("Treba da potvrdi rezervaciju: "
														+ cell.getRow()
																.getData().id)
											}
										},
										{
											formatter : otkaziFormat,
											title : "Otkazi Apartman",
											hozAlign : "center",
											cellClick : function(e, cell) {
												alert("Treba da otkaze rezervaciju: "
														+ cell.getRow()
																.getData().id)
											}
										},
										{
											formatter : OstaviKomentarFormat,
											title : "Oceni",
											hozAlign : "center",
											cellClick : function(e, cell) {
												alert("Treba da otkaze rezervaciju: "
														+ cell.getRow()
																.getData().id)
												window.location.href = "http://localhost:8080/WebProj/rest/rezervacija/ostaviKomentar/"+ cell.getRow().getData().id;
											}
										},
										],
							});
					//table.setFilter("gostId", "=", "gostId");
				});