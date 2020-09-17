$(document).on('submit', '#forma', function(e) {
	$("#errorSnimljeno").hide();
	$("#snimljeno").hide();
	$("#errorNaziv").hide();

	e.preventDefault();

	var naziv = $("#naziv").val();

	if (naziv == "") {
		$("#errorNaziv").show();
	} else {
		$.ajax({
			type : 'POST',
			url : "/WebProj/rest/sadrzaj/nov",
			contentType : 'application/json',
			data : formToJSON(naziv),
			success : function() {
				$("#snimljeno").show();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#errorSnimljeno").show();
			}
		});
	}

});

function formToJSON(naziv) {
	return JSON.stringify({
		"naziv" : naziv
	});
}

$(window).on(
		'load',
		function() {
			var table = new Tabulator("#example-table", {

				height : "311px",
				ajaxURL : "../sadrzaj/svi",
				layout : "fitDataTable",

				columns : [
						{
							title : "ID",
							field : "id",
						},
						{
							title : "Naziv",
							field : "naziv",
						},
						{
							title : "Izmeni Sadrzaj",
							formatter : function(cell, formatterParams,
									onRendered) {
								return "<label>Izmeni</label>";
							},
							hozAlign : "center",
							cellClick : function(e, cell) {
								window.location.href = "/WebProj/rest/sadrzaj/"
										+ cell.getRow().getData().id
										+ "/izmeni";
							}

						},
						{
							formatter : function(cell, formatterParams,
									onRendered) {
								if (cell.getRow().getData().obrisan == false) {
									return "<label>Obrisi</label>";
								} else {
									return "";
								}
							},
							title : "Obrisi Sadrzaj",
							hozAlign : "center",
							cellClick : function(e, cell) {
								window.location.href = "/WebProj/rest/sadrzaj/"
										+ cell.getRow().getData().id
										+ "/izmeni";
							}
						}, ],
			});

		});