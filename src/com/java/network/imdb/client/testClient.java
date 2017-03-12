package com.java.network.imdb.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class testClient {

	public static void main(String[] Args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter comand ");
		String comand = sc.nextLine();
		Thread client = new Thread(new IMDBClient(comand, "Gosho"));
		Thread client1 = new Thread(new IMDBClient("get-movie Inception", "Ivan"));
		client.start();
	//	client1.start();

		List<Integer> numbers = new ArrayList<Integer>();

		numbers.add(2);
		numbers.add(5);
		numbers.add(7);
		numbers.add(55);
		numbers.add(6);
		numbers.add(27);

		Integer op = numbers.stream().filter(x -> x % 2 == 0).reduce(0, (a, b) -> a + b);
		
		// System.out.println(op);

	}
}