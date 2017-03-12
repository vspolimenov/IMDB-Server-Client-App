package com.java.network.imdb.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class FileWorkManager {

	public static String readFromFile(String filename) {

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}

			String everything = sb.toString();
			return everything;
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	public static List<JSONObject> getAllMoviesFromDataBase() {

		String current = null;

		try {
			current = new java.io.File(".").getCanonicalPath();
		} catch (IOException e) {
			System.out.println("Cannot find directory!");
			return null;
		}

		File folder = new File(current);
		File[] listOfFiles = folder.listFiles();

		List<JSONObject> movies = new ArrayList<JSONObject>();

		for (int i = 0; i < listOfFiles.length; i++) {
			File file = listOfFiles[i];
			if (file.isFile() && file.getName().endsWith(".txt")) {
				String content = FileWorkManager.readFromFile(file.getName());
				try {
					movies.add(new JSONObject(content));
				} catch (JSONException e) {
					System.out.println("Wrong JSON expression!");
				}
			}
		}
		return movies;
	}
}
