package com.java.network.imdb.client;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class IMDBClient implements Runnable {

	private Socket clientSocket = null;
	private String clientName = null;
	private String serverText = null;

	public IMDBClient(String serverText, String clientName) {
		this.clientName = clientName;
		this.serverText = serverText;
	}

	@Override
	public void run() {

		SocketChannel socketChannel = null;
		try {
			socketChannel = SocketChannel.open();
		} catch (IOException e1) {
			System.out.println("broken socket channel");
			return;
		}
		try {
			socketChannel.connect(new InetSocketAddress("localhost", 39999));
		} catch (IOException e) {
			System.out.println("broken socket channel");
			return;
		}

		ByteBuffer buf = ByteBuffer.allocate(serverText.length() + 2);
		buf.put((this.serverText + " t").getBytes());
		buf.flip();
		while (buf.hasRemaining()) {
			try {
				socketChannel.write(buf);
			} catch (IOException e) {
				System.out.println("broken socket channel");
				return;
			}
		}

		ByteBuffer buffer = ByteBuffer.allocate(100000);
		try {
			int numBytes = socketChannel.read(buffer);

			if (numBytes == -1) {
				System.out.println("broken socket channel");
				return;
			} else if (numBytes > 0) {
				buffer.flip();
				String response = new String(buffer.array());
				response = response.substring(0, response.lastIndexOf(" t"));
				System.out.println(this.clientName + ": ");
				System.out.println(response);

			}
		} catch (IOException e1) {
			System.out.println("broken socket channel");
			return;
		}
		try {
			socketChannel.close();

		} catch (IOException e) {
			System.out.println("broken socket channel");
			return;
		}
	}
}
