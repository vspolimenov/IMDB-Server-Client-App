package com.java.network.imdb.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.java.network.imdb.services.ComandsExecutor;

public class ComandsExecutorTests {

	@Test
	public void executeComandTest() {
		assertEquals(
				"{" + '\n' + "Actors: Tony Shalhoub, Jason Gray-Stanford, Ted Levine, Traylor Howard" + '\n'
						+ "totalSeasons: 8" + '\n' + "}" + '\n',
				ComandsExecutor.executingComand("get-movie Monk --fields=[Actors]"));
		assertEquals(
				"{\"Response\":\"True\",\"totalSeasons\":\"4\",\"Episodes\":[{\"Episode\":\"1\",\"Released\":\"2017-02-01\",\"imdbID\":\"tt5540928\",\"Title\":\"Echoes\",\"imdbRating\":\"N/A\"},{\"Episode\":\"2\",\"Released\":\"2017-02-08\",\"imdbID\":\"tt5904036\",\"Title\":\"Episode #4.2\",\"imdbRating\":\"N/A\"},{\"Episode\":\"3\",\"Released\":\"N/A\",\"imdbID\":\"tt6009796\",\"Title\":\"Episode #4.3\",\"imdbRating\":\"N/A\"},{\"Episode\":\"4\",\"Released\":\"2017-02-22\",\"imdbID\":\"tt6031686\",\"Title\":\"Episode #4.4\",\"imdbRating\":\"N/A\"},{\"Episode\":\"5\",\"Released\":\"2017-03-01\",\"imdbID\":\"tt6035900\",\"Title\":\"Episode #4.5\",\"imdbRating\":\"N/A\"},{\"Episode\":\"6\",\"Released\":\"2017-03-08\",\"imdbID\":\"tt6075396\",\"Title\":\"Episode #4.6\",\"imdbRating\":\"N/A\"}],\"Title\":\"The 100\",\"Season\":\"4\"}"
						+ '\n',
				ComandsExecutor.executingComand("get-tv-series The 100 --season=4"));
		assertEquals("The poster is in local file system with name- Lucy" + '\n',
				ComandsExecutor.executingComand("get-movie-poster Lucy"));
	}
}
