$(window).on('load', function(){
	
	var url = window.location.pathname;
	var id = url.substring(url.lastIndexOf('/') + 1);
	
	$.ajax({
		type : 'GET',
		url : '/WebProj/rest/rezervacija/'+id+'/info',
		dataType : "json",
		success : function(data) {
			$("#apartman").text(data.apartman);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//TODO
			}
		});
});