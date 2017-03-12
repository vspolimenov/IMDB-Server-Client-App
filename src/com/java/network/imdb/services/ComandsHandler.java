package com.java.network.imdb.services;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONException;
import org.json.JSONObject;

public class ComandsHandler {

	private String urlToRead;

	public ComandsHandler(String urlToRead) {
		this.urlToRead = urlToRead;
	}

	public ComandsHandler() {
	}

	public static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static boolean getMoviePoster(String input) throws JSONException {
		String movieName = ComandsHandler.getTitle(input);

		JSONObject movie = new JSONObject(FileWorkManager.readFromFile(movieName + ".txt"));
		String imageUrl = movie.getString("Poster");
		try {
			saveImage(imageUrl, movieName + ".jpeg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;

	}

	public static void saveImage(String imageUrl, String imageName) throws IOException {

		URL url = new URL(imageUrl);

		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(imageName);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

	public JSONObject getMovie() {

		try {
			URL url = new URL(this.urlToRead);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),
					Charset.forName(java.nio.charset.StandardCharsets.UTF_8.name())));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);

			rd.close();
			return json;
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		} catch (JSONException e) {
		}
		return null;
	}

	public List<JSONObject> getMovies(String input) {

		List<JSONObject> movies = FileWorkManager.getAllMoviesFromDataBase();

		if (input.contains("--genres=[")) {
			String[] genres = getGenres(input);
			Stream<JSONObject> op = movies.stream().filter(x -> {
				try {
					for (String genre : genres) {
						if (x.getString("Genre").contains(genre)) {
							return true;
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return false;
			});

			movies = op.collect(Collectors.toList());

		}

		if (input.contains("--actors=[")) {
			String[] actors = getActors(input);

			Stream<JSONObject> op = movies.stream().filter(x -> {
				try {
					for (String actor : actors) {
						if (x.getString("Actors").contains(actor)) {
							return true;
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return false;
			});

			movies = op.collect(Collectors.toList());
		}

		if (input.contains("get-movies --order=[asc]")) {
			Collections.sort(movies, new Comparator<JSONObject>() {

				@Override

				public int compare(JSONObject a, JSONObject b) {

					String criteria = "imdbRating";
					String valA = null;
					String valB = null;

					try {
						valA = (String) a.get(criteria);
						valB = (String) b.get(criteria);
					} catch (JSONException e) {
						e.printStackTrace();
					}

					return valA.compareTo(valB);
				}
			});
		} else {

			Collections.sort(movies, new Comparator<JSONObject>() {

				@Override

				public int compare(JSONObject a, JSONObject b) {

					String criteria = "imdbRating";
					String valA = null;
					String valB = null;

					try {
						valA = (String) a.get(criteria);
						valB = (String) b.get(criteria);
					} catch (JSONException e) {
						e.printStackTrace();
					}

					return -valA.compareTo(valB);
				}
			});
		}
		return movies;
	}

	private String[] getGenres(String input) {
		input = input.substring(input.indexOf("--genres=[") + "--genres=[".length());
		input = input.substring(0, input.indexOf("]"));

		return input.split(", ");
	}

	public static String[] getFields(String input) {
		input = input.substring(input.indexOf("--fields=[") + "--fields=[".length());
		input = input.substring(0, input.indexOf("]"));

		return input.split(", ");
	}

	public static String getTitle(String input) {

		if (input.indexOf("get-movie ") != -1)
			input = input.substring(input.indexOf("get-movie ") + "get-movie ".length());
		if (input.indexOf(" --fields=[") != -1)
			input = input.substring(0, input.indexOf(" --fields=["));
		if (input.indexOf(" --season=") != -1)
			input = input.substring(0, input.indexOf(" --season"));
		if (input.indexOf("-poster") != -1)
			input = input.substring(input.indexOf("-poster ") + "-poster ".length());
		if (input.indexOf("get-tv-series ") != -1)
			input = input.substring(input.indexOf("get-tv-series ") + "get-tv-series ".length());

		return input;
	}

	public static String getSeason(String input) {
		input = input.substring(input.indexOf("--season=") + "--season=".length());

		return input;
	}

	private String[] getActors(String input) {
		input = input.substring(input.indexOf("--actors=[") + "--actors=[".length());
		input = input.substring(0, input.indexOf("]"));
		return input.split(", ");
	}
}
