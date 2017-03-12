package com.java.network.imdb.tests;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.junit.Test;

import com.java.network.imdb.services.ComandsHandler;

public class ComandsHandlerTests {

	@Test
	public void getMoviePosterTest() {

		try {
			assertTrue("Server should save the poster of Titanic",
					ComandsHandler.getMoviePoster("get-movie-poster Titanic"));
			assertFalse("Wrong movie name, should return false",
					ComandsHandler.getMoviePoster("get-movie-poster Titanica"));
			assertFalse("Movie is not in file system, should return false",
					ComandsHandler.getMoviePoster("get-movie-poster Nemo"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getMovieTitleTest() {

		assertEquals("Monk", ComandsHandler.getTitle("get-movie Monk --fields=[Actors]"));
		assertEquals("Rocky", ComandsHandler.getTitle("get-movie Rocky"));
		assertEquals("Nemo", ComandsHandler.getTitle("get-movie Nemo"));
		assertEquals("4", ComandsHandler.getSeason("get-tv-series The Vampire Diaries --season=4"));

	}

	@Test
	public void getMovies() {
		ComandsHandler handler = new ComandsHandler();
		assertNotNull("List of movies should't be NULL", handler.getMovies("get-movies --order=[asc]"));
		assertNotNull("List of movies should't be NULL",
				handler.getMovies("get-movies --order=[asc] --actors=[Hayden Christensen]"));
	}

}
