package com.java.network.imdb.services;

public class UrlFixer {

	public static String createGetMovieURL(String movieTitle) {

		StringBuilder url = new StringBuilder();

		url.append("http://www.omdbapi.com/?t=");

		movieTitle = fixString(movieTitle);

		url.append(movieTitle);
		url.append("&y=&plot=short&r=json");

		// System.out.println(url);

		return url.toString();
	}

	public static String createGetTvSeriesURL(String title, String season) {
		StringBuilder url = new StringBuilder();

		url.append("http://www.omdbapi.com/?t=");
		url.append(fixString(title));
		url.append("&Season=");
		url.append(season);
		System.out.println(url);
		return url.toString();
	}

	public static String fixString(String input) {

		input = input.replaceAll(" ", "+");
		input = input.replaceAll(":", "%3A");
		input = input.replaceAll("&", "%26");

		return input;
	}

}
