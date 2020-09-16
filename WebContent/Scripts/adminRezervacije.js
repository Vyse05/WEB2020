$(window)
		.on(
				'load',
				function() {
				
					var potvrdiFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Otkazi</label>";
					};
					var otkaziFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Otkazi</label>";
					};
					var table = new Tabulator(
							"#admin-rezervacije-table",
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
										}, ],
							});
					//table.setFilter("gostId", "=", "username"); 
				});