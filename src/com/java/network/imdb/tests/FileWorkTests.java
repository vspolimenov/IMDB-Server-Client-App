package com.java.network.imdb.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.java.network.imdb.services.FileWorkManager;

public class FileWorkTests {

	@Test
	public void readFromFileTest() {
		assertEquals("az" + System.lineSeparator(),
				FileWorkManager.readFromFile("C:\\Users\\stan\\workspace\\IMDBInfo\\test.txt"));
		assertEquals("az" + System.lineSeparator() + "sym" + System.lineSeparator(),
				FileWorkManager.readFromFile("C:\\Users\\stan\\workspace\\IMDBInfo\\test2.txt"));
	}
}
