$(window).on('load', function(){
    
	var navigacija = 
    	'<a href="/WebProj">Home</a>&nbsp&nbsp;'
        +'<span id="servisni"></span>'
        +'<span id="korisnicki"></span>';
	$('#header').html(navigacija);
	
	$.ajax({
		type : 'GET',
		url : '/WebProj/rest/korisnik/info',
		dataType : "json",
		success : function(data) {
			$('#korisnicki').append('<a href="/WebProj/rest/korisnik/profil">Profil</a>&nbsp;&nbsp;');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if(XMLHttpRequest.status==403)
				{
					$('#korisnicki').append(
							'<a href="/WebProj/rest/korisnik/registracija">Registracija</a>&nbsp;&nbsp;'+
							'<a href="/WebProj/rest/korisnik/login">Log in</a>&nbsp;&nbsp;');
				}
			}
		});
});