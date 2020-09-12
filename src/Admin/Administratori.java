package Admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import Model.Korisnik;

public class Administratori {
	public static List<Korisnik> get(ServletContext application) {
		List<Korisnik> administratori = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("administratori.txt"), "UTF-8"));

			String line;
			Integer id = 0;
			try {
				while ((line = in.readLine()) != null) {
					line = line.trim();

					if (line.equals(""))
						continue;

					Korisnik novaLokacija = new Korisnik(id, line);

					administratori.add(novaLokacija);
					id++;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		application.setAttribute("administratori", administratori);
		return administratori;
	}
}
