$(window)
		.on(
				'load',
				function() {
					
					var domacinFormat = function(cell, formatterParams,
							onRendered) { // plain
						// text
						// value
						return "<label>Promeni</label>";
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
							"#tabela-korisnika",
							{
								ajaxURL : "../korisnik/svi", // ajax URL
								height : "311px",
								layout : "fitColumns",
								pagination : "local",
								paginationSize : 5,
								columns : [
										{
											title : "Korisnicko ime",
											field : "korisnickoIme",
											headerFilter : "input"
										},
										{
											title : "Ime",
											field : "ime",
											headerFilter : "input"
										},
										{
											title : "Prezime",
											field : "prezime",
											headerFilter : "input"
										},
										{
											title : "Pol",
											field : "pol",
											headerFilter : "input"
										},
										{
											title : "Uloga",
											field : "uloga",
											headerFilter : "input"
										},
										{
											title : "ID",
											field : "id",
											sorter : "number",
											headerFilter : "number",
											headerFilterFunc : "=",
										},
										{
											formatter : function(cell,
													formatterParams, onRendered) {
												if (cell.getRow().getData().uloga == "Korisnik") {
													return "<label>Promeni</label>";
												} else {
													return "";
												}
											},
											title : "Konvertuj",
											hozAlign : "center",
											cellClick : function(e, cell) {
												$.ajax({
													type : 'PUT',
													url : "/WebProj/rest/korisnik/" + cell.getRow().getData().id +"/domacin",
													success : function(data) { 
														alert("Konvertovanje uspesno");
													},
													error : function(XMLHttpRequest, textStatus, errorThrown) {
														alert("Korisnik je vec domacin");
													}
													});

											}
										}, ],

							});
				});