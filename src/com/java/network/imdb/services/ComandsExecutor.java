package com.java.network.imdb.services;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ComandsExecutor {

	private static String executeGetMovieComand(String input) {

		String filename = ComandsHandler.getTitle(input) + ".txt";
		ComandsHandler request;

		String movieInfo = null;
		Path path = Paths.get(filename);
		if (Files.exists(path)) {

			// TODO optimization:
			// if(IMDBServer.moviesInFileSystem.contains(filename)) {
			// String movieInfo = null;

			movieInfo = FileWorkManager.readFromFile(filename);
		} else {

			request = new ComandsHandler(UrlFixer.createGetMovieURL(ComandsHandler.getTitle(input)));

			JSONObject json = request.getMovie();

			if (json == null) {
				System.out.println("Cannot find this movie!");
				return "Cannot find this movie!";
			}
			try {
				if (json.get("Response").equals("False")) {
					System.out.println("Movie cannot be found!");
					return "Movie cannot be found!";
				}
			} catch (JSONException e2) {
				System.out.println("Movie cannot be found!");
				return "Movie cannot be found!";
			}
			try (FileWriter file = new FileWriter(json.getString("Title").replaceAll(":", "") + ".txt")) {

				movieInfo = json.toString();
				file.write(movieInfo);

				// TODO optimization:
				// IMDBServer.moviesInFileSystem.add(json.getString("Title").replaceAll(":",
				// "") + ".txt");

			} catch (IOException e) {
				System.out.println("Cannot create current file!");
				return "Cannot create current file!";

			} catch (JSONException e1) {
				System.out.println("Wrong movie name!");
				return "Wrong movie name!";
			}

		}
		return constructMovieInfo(input, movieInfo);
	}

	private static String constructMovieInfo(String input, String movieInfo) {

		List<String> fields = null;
		if (input.contains("--fields=[") == false) {
			String[] allFields = { "Released", "Metascore", "imdbID", "Plot", "Director", "Title", "Actors",
					"imdbRating", "imdbVotes", "Response", "Runtime", "Type", "Awards", "Year", "Language", "Rated",
					"Poster", "Country", "Genre", "Writer" };
			fields = Arrays.asList(allFields);
		} else {
			fields = Arrays.asList(ComandsHandler.getFields(input));
		}
		StringBuilder info = new StringBuilder();
		try {
			JSONObject json = new JSONObject(movieInfo);

			info.append("{\n");

			for (String field : fields) {
				info.append(field);
				info.append(": ");
				info.append(json.getString(field));
				info.append('\n');
			}

			if (json.getString("Type").equals("series")) {

				info.append("totalSeasons");
				info.append(": ");
				info.append(json.getString("totalSeasons"));
				info.append('\n');
			}
			info.append("}");
		} catch (JSONException e) {
			System.out.println("Wrong field!");
			return "Wrong field!";
		}

		System.out.println(info);
		System.out.println();
		return info.toString() + '\n';
	}

	private static String executeGetMoviesComand(String input) {
		ComandsHandler request;

		request = new ComandsHandler();
		List<JSONObject> movies = request.getMovies(input);
		StringBuilder moviesAsString = new StringBuilder();
		for (JSONObject movie : movies) {
			System.out.println(movie.toString());
			moviesAsString.append(movie.toString());
			moviesAsString.append('\n');
		}
		System.out.println();
		return moviesAsString.toString();

	}

	private static String executeGetMoviePosterComand(String input) {

		try {
			ComandsHandler.getMoviePoster(input);
			System.out.println("The poster is in local file system with name- " + ComandsHandler.getTitle(input));
			System.out.println();
			return "The poster is in local file system with name- " + ComandsHandler.getTitle(input) + '\n';
		} catch (JSONException e) {
			System.out.println("Current movie doesn't excist in file system!");
			return "Current movie doesn't excist in file system!";
		}

	}

	private static String executeGetTvSeriesComand(String input) {

		ComandsHandler request;
		String season = ComandsHandler.getSeason(input);
		String url = UrlFixer.createGetTvSeriesURL(ComandsHandler.getTitle(input), season);

		request = new ComandsHandler(url);

		JSONObject json = request.getMovie();

		System.out.println(json.toString());
		System.out.println();
		return json.toString() + '\n';
	}

	public static String executingComand(String input) {

		if (input.length() < 10) {
			System.out.println("Wrong command!");
			return "Wrong command!";
		}
		if (input.substring(0, 10).equals("get-movie ")) {
			return executeGetMovieComand(input);
		} else if (input.substring(0, 10).equals("get-movies")) {
			return executeGetMoviesComand(input);
		} else if (input.contains("get-movie-poster")) {
			return executeGetMoviePosterComand(input);
		} else if (input.contains("get-tv-series")) {
			return executeGetTvSeriesComand(input);
		} else {
			System.out.println("Wrong command!");
			return "Wrong command!";
		}
	}
}
