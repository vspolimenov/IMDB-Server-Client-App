package com.java.network.imdb.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.java.network.imdb.services.UrlFixer;

public class UrlFixerTests {

	@Test
	public void createUrlTest() {
		assertEquals("http://www.omdbapi.com/?t=Rambo&y=&plot=short&r=json", UrlFixer.createGetMovieURL("Rambo"));
		assertEquals("http://www.omdbapi.com/?t=Rambo+is+Back&y=&plot=short&r=json",
				UrlFixer.createGetMovieURL("Rambo is Back"));
		assertEquals("http://www.omdbapi.com/?t=Rambo&y=&plot=short&r=json", UrlFixer.createGetMovieURL("Rambo"));
		assertEquals("http://www.omdbapi.com/?t=Tom%26Jerry&y=&plot=short&r=json",
				UrlFixer.createGetMovieURL("Tom&Jerry"));
		assertEquals("http://www.omdbapi.com/?t=Tom%26Jerry+2+and+Tom%26Jerry++3&y=&plot=short&r=json",
				UrlFixer.createGetMovieURL("Tom&Jerry 2 and Tom&Jerry  3"));

	}

	@Test
	public void fixInputTest() {
		assertEquals("Tom%26Jerry", UrlFixer.fixString("Tom&Jerry"));
		assertEquals("Tom%26Jerry%3A+New+Adventure", UrlFixer.fixString("Tom&Jerry: New Adventure"));
	}

	@Test
	public void createTvSeriesUrlTest() {
		assertEquals("http://www.omdbapi.com/?t=The+Vampire+Diaries&Season=4",
				UrlFixer.createGetTvSeriesURL("The Vampire Diaries", "4"));
		assertEquals("http://www.omdbapi.com/?t=Lost&Season=2", UrlFixer.createGetTvSeriesURL("Lost", "2"));
	}
}
