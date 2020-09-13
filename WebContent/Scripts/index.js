$(window).on(
		'load',
		function() {

			var minMaxFilterEditor = function(cell, onRendered, success,
					cancel, editorParams) {

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
			function minMaxFilterFunction(headerValue, rowValue, rowData,
					filterParams) {
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

				return true; // must return a boolean, true if it passes the
				// filter.
			}

			var table = new Tabulator("#example-table", {
				// data : tabledatabig,
				ajaxURL : "../WebProj/rest/apartman/svi", // ajax URL
				height : "311px",
				layout : "fitColumns",
				pagination : "local",
				paginationSize : 5,
				columns : [ {
					title : "Lokacija",
					field : "naseljenoMesto",
					headerFilter : "input"
				}, {
					title : "Cena",
					field : "cena",
					width : 200,
					sorter : "number",
					hozAlign : "center",
					headerFilter : minMaxFilterEditor,
					headerFilterFunc : minMaxFilterFunction,
					headerFilterLiveFilter : false
				}, {
					title : "Broj Soba",
					field : "brojSoba",
					sorter : "number",
					hozAlign : "center",
					width : 200,
					headerFilter : "number",
					headerFilterPlaceholder : "at least...",
					headerFilterFunc : ">="
				}, {
					title : "Broj Osoba",
					field : "brojGostiju",
					sorter : "number",
					hozAlign : "center",
					width : 200,
					headerFilter : "number",
					headerFilterPlaceholder : "at least...",
					headerFilterFunc : ">="
				}, {
					title : "Datum Dolaska",
					field : "dob",
					hozAlign : "center",
					sorter : "date",
					headerFilterFunc : ">="
				}, {
					title : "Datum Odlaska",
					field : "dob2",
					hozAlign : "center",
					sorter : "date",
					headerFilterFunc : ">="
				},  ],
				rowClick : function(e, row) {
					alert("Row " + row.getIndex() + " Context Clicked!!!!")
				},
			});
		});