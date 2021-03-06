$(window)
		.on(
				'load',
				function() {

					var navigacija = '<ul id="servisni"></ul>'
							+ '<ul id="korisnicki"></ul>';
					$('#header').html(navigacija);

					$
							.ajax({
								type : 'GET',
								url : '/WebProj/rest/korisnik/info',
								dataType : "json",
								success : function(data) {
									$('#korisnicki')
											.append(
													'<li><a href="/WebProj">Home</a></li>&nbsp&nbsp;');
									$('#korisnicki')
											.append(
													'<li><a href="/WebProj/rest/korisnik/profil">Profil</a></li>&nbsp;&nbsp;');
									$('#korisnicki')
											.append(
													'<li><a onclick="logout()" href="#">Log out</a></li>&nbsp;&nbsp;');
									
									if (data.uloga == 'Domaćin') {
										$('#korisnicki')
												.append(
														'<li><a href="/WebProj/rest/apartman/nov">Nov Apartman</a>&nbsp;&nbsp;</li>');
										$('#korisnicki')
										.append(
												'<li><a href="/WebProj/rest/rezervacija/domacinRezervacije">Rezervacije</a>&nbsp;&nbsp;</li>');
										$('#korisnicki')
										.append(
												'<li><a href="/WebProj/rest/korisnik/korisniciDomacin">Gosti</a>&nbsp;&nbsp;</li>');
									}
									if (data.uloga == 'Administrator') {
										$('#korisnicki')
												.append(
														'<li><a href="/WebProj/rest/sadrzaj/nov">Nov Sadrzaj</a>&nbsp;&nbsp;</li>');
										$('#korisnicki')
												.append(
														'<li><a href="/WebProj/rest/korisnik/korisnici">Korisnici</a>&nbsp;&nbsp;</li>');
										$('#korisnicki')
										.append(
												'<li><a href="/WebProj/rest/rezervacija/adminRezervacije">Sve Rezervacije</a></li>&nbsp;&nbsp;');
									}

									if (data.uloga == 'Korisnik') {
										$('#korisnicki')
												.append(
												'<li><a href="/WebProj/rest/rezervacija/gostRezervacije">Moje Rezervacije</a></li>&nbsp;&nbsp;');
										
									}
								},
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									if (XMLHttpRequest.status == 403) {
										$('#korisnicki')
												.append(
														'<li><a href="/WebProj">Home</a>&nbsp&nbsp;</li>&nbsp&nbsp;');
										$('#korisnicki')
												.append(
														'<li><a href="/WebProj/rest/korisnik/registracija">Registracija</a></li>&nbsp&nbsp;');
										$('#korisnicki')
												.append(
														'<li><a href="/WebProj/rest/korisnik/profil">Profil</a></li>&nbsp&nbsp;');
										$('#korisnicki')
												.append(
														'<li><a href="/WebProj/rest/korisnik/login">Log in</a></li>&nbsp;&nbsp;');
									}
								}
							});
				});

function logout() {
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