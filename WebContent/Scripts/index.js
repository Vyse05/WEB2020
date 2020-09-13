$(window).on(
		'load',
		function() {

			var tabledatabig = [ {
				id : 1,
				location : "Oli Bob",
				price : 12,
				gender : "male",
				rooms : 1,
				people : 1,
				col : "red",
				dob : "14/04/1984",
				car : 1,
				lucky_no : 5
			}, {
				id : 2,
				location : "Mary May",
				price : 1,
				gender : "female",
				rooms : 2,
				people : 2,
				col : "blue",
				dob : "14/05/1982",
				car : true,
				lucky_no : 10
			}, {
				id : 3,
				location : "Christine Lobowski",
				price : 42,
				gender : "female",
				rooms : 0,
				people : 3,
				col : "green",
				dob : "22/05/1982",
				car : "true",
				lucky_no : 12
			}, {
				id : 4,
				location : "Brendon Philips",
				price : 100,
				gender : "male",
				rooms : 1,
				people : 4,
				col : "orange",
				dob : "01/08/1980",
				lucky_no : 18
			}, {
				id : 5,
				location : "Margret Marmajuke",
				price : 16,
				gender : "female",
				rooms : 5,
				people : 5,
				col : "yellow",
				dob : "31/01/1999",
				lucky_no : 33
			}, {
				id : 6,
				location : "Frank Harbours",
				price : 38,
				gender : "male",
				rooms : 4,
				people : 6,
				col : "red",
				dob : "12/05/1966",
				car : 1,
				lucky_no : 2
			}, {
				id : 7,
				location : "Jamie Newhart",
				price : 23,
				gender : "male",
				rooms : 3,
				people : 1,
				col : "green",
				dob : "14/05/1985",
				car : true,
				lucky_no : 63
			}, {
				id : 8,
				location : "Gemma Jane",
				price : 60,
				gender : "female",
				rooms : 0,
				people : 2,
				col : "red",
				dob : "22/05/1982",
				car : "true",
				lucky_no : 72
			}, {
				id : 9,
				location : "Emily Sykes",
				price : 42,
				gender : "female",
				rooms : 1,
				people : 3,
				col : "maroon",
				dob : "11/11/1970",
				lucky_no : 44
			}, {
				id : 10,
				location : "James Newman",
				price : 73,
				gender : "male",
				rooms : 5,
				people : 4,
				col : "red",
				dob : "22/03/1998",
				lucky_no : 9
			}, {
				id : 11,
				location : "Martin Barryman",
				price : 20,
				gender : "male",
				rooms : 5,
				people : 5,
				col : "violet",
				dob : "04/04/2001"
			} ];

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