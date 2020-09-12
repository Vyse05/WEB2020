package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DAL<T extends Removable> {
	private List<T> entities = new ArrayList<T>();
	private String path;

	final Class<T> typeParameterClass;

	public DAL(Class<T> typeParameterClass, String path) {
		this.typeParameterClass = typeParameterClass;
		this.path = path;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
			read(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private void read(BufferedReader in) {
		String line;
		Integer id = 0;
		try {
			while ((line = in.readLine()) != null) {
				line = line.trim();

				if (line.equals(""))
					continue;

				T novEntitet = typeParameterClass.getConstructor(Integer.class, String.class).newInstance(id, line);

				entities.add(novEntitet);
				id++;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<T> get() {
		return entities;
	}

	public void add(T entity) {
		entities.add(entity);
		entity.setId(entities.size() - 1);
		write(entity);
	}

	public void remove(T entity) {
		entity.setRemoved(true);
		refresh();
	}

	private void write(T entity) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(path, true), StandardCharsets.UTF_8)));
			out.println(entity.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void refresh() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)));

			for (T entity : entities) {
				out.println(entity.toString());
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
