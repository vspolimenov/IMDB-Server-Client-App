package com.java.network.imdb.server;

public class testClass {

	public static void main(String[] args) {

		Thread server = new Thread(new IMDBServer(39999));
		server.start();
	}

}
