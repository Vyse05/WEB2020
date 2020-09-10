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
			$('#korisnicki').append('<a onclick="logout()" href="#">Log out</a>&nbsp;&nbsp;');
			$('#korisnicki').append('<a href="/WebProj/rest/korisnik/profil">Profil</a>&nbsp;&nbsp;');
			if(data.uloga == 'Domaćin'){
				$('#korisnicki').append('<a href="/WebProj/rest/apartman/nov">Nov Apartman</a>&nbsp;&nbsp;');				
			}
			if(data.uloga == 'Korisnik'){
				$('#korisnicki').append('<a href="/WebProj/rest/sadrzaj/nov">Nov Sadrzaj</a>&nbsp;&nbsp;');				
			}
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

function logout(){
	$.ajax({
		type : 'POST',
		url : '/WebProj/rest/korisnik/logout',
		success : function() {
			window.location.href = "/WebProj/index.html";
		},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			window.location.href = "/WebProj/index.html";
		}
	});	  
  }