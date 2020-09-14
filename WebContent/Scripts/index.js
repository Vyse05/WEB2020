$(window)
		.on(
				'load',
				function() {

					var rezervacijaFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Rezervisi</label>";
					};
					var komentarFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Procitaj</label>";
					};
					komentarFormat

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
							"#example-table",
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
											title : "Datum Dolaska",
											field : "dob",
											hozAlign : "center",
											sorter : "date",
											headerFilterFunc : ">="
										},
										{
											title : "Datum Odlaska",
											field : "dob2",
											hozAlign : "center",
											sorter : "date",
											headerFilterFunc : ">="
										},
										{
											formatter : rezervacijaFormat,
											title : "Rezervacija",
											hozAlign : "center",
											cellClick : function(e, cell) {
												alert("Redirefting to: "
														+ "http://localhost:8080/WebProj/rest/rezervacija/nov/"
															+ cell.getRow()
															.getData().id);
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
												alert("Printing row data for: "
														+ cell.getRow()
																.getData().id)
											}
										}, ],
							});

				});