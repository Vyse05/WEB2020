//Define some test data
$(window)
		.on(
				'load',
				function() {

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
					    height:"311px",
						ajaxURL : "../rezervacija/svi",
					    layout:"fitDataTable",

						columns : [ {
							title : "Komentar",
							field : "komentar",
							hozAlign : "center",
							width: 800,
							//formatter:"textarea",
							//sorter : "string",
						}, {
							title : "Ocena",
							width: 100,
							vertAlign : "center",
							hozAlign : "center",
							field : "ocena",
							//sorter : "number"
						} ],
					});
					
					var table2 = new Tabulator("#example-table2", {
					    height:"311px",
						ajaxURL : "../rezervacija/svi",
					    layout:"fitDataTable",

						columns : [ {
							title : "Komentar",
							field : "komentar",
							hozAlign : "center",
							width: 800,
							formatter:"textarea",
							//sorter : "string",
						}, {
							title : "Ocena",
							width: 100,
							vertAlign : "center",
							hozAlign : "center",
							field : "ocena",
							//sorter : "number"
						},{
							title : "Stanje Komentara",
							vertAlign : "center",
							hozAlign : "center",
							field : "prikazatiKomentar",
							//sorter : "number"
						},
						{
							formatter : odobriFormat,
							title : "Odobri",
							hozAlign : "center",
							cellClick : function(e, cell) {
								$.ajax({
									type : 'PUT', 
									
									url : "/WebProj/rest/rezervacija/" + cell.getRow().getData().id +"/odobriKomentar",
									success : function(data) { // Vec Ulogovan
										alert("Komentar je Odobren");
									},
									error : function(XMLHttpRequest, textStatus, errorThrown) {
										alert("Greska pri odobravanju komentara");

									}
									});
							}
						},
						{
							formatter : ponistiFormat,
							title : "Ponisti",
							hozAlign : "center",
							cellClick : function(e, cell) {
								$.ajax({
									type : 'PUT',    
									url : "/WebProj/rest/rezervacija/" + cell.getRow().getData().id +"/sakrijKomentar",
									success : function(data) { // Vec Ulogovan
										alert("Komentar je Sakriven");
									},
									error : function(XMLHttpRequest, textStatus, errorThrown) {
										alert("Greska pri sakrivanju komentara");

									}
									});
							}
						},
						],
					});
				});