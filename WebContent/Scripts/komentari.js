//Define some test data
$(window)
		.on(
				'load',
				function() {

					var cheeseData = [

							{
								id : 1,
								komentar : "sgagsa gasgasgasgas asgasg asga sg asg  asgas g asgasgasgasg"
										+ "sgagsa gasgasgasgas asgasg asga sg asg  asgas g asgasgasgasg"
										+ "sgagsa gasgasgasgas asgasg asga sg asg  asgas g asgasgasgasg"
										+ "sgagsa gasgasgasgas asgasg asga sg asg  asgas g asgasgasgasg"
										+ "sgagsa gasgasgasgas asgasg asga sg asg  asgas g asgasgasgasg"
										+ "sgagsa gasgasgasgas asgasg asga sg asg  asgas g aewtwetwetwesgasg",
								rating : "5"
							},

					]

					// define Tabulator
					var table = new Tabulator("#example-table", {
					    height:"311px",
						data : cheeseData,
					    layout:"fitDataTable",

						columns : [ {
							title : "Komentar",
							field : "komentar",
							hozAlign : "center",
							width: 800,
							formatter:"textarea",
							sorter : "string",
						}, {
							title : "Ocena",
							width: 100,
							vertAlign : "center",
							hozAlign : "center",
							field : "rating",
							sorter : "number"
						} ],
					});
				});