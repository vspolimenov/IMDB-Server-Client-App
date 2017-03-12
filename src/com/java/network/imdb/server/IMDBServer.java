package com.java.network.imdb.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.java.network.imdb.services.ComandsExecutor;

public class IMDBServer implements Runnable {

	private int serverPort = 39999;
	private ServerSocketChannel serverSocket = null;
	private boolean isStopped = false;

	// TODO optimization:
	// public static HashSet<String> moviesInFileSystem;

	public IMDBServer(int port) {
		this.serverPort = port;

		// TODO optimization:
		// moviesInFileSystem = new HashSet<String>();
	}

	@Override
	public void run() {

		openServerSocket();
		while (!isStopped()) {
			SocketChannel clientSocket = null;

			try {
				clientSocket = serverSocket.accept();
				{
					ByteBuffer buffer = ByteBuffer.allocate(100);
					int numBytes = clientSocket.read(buffer);

					if (numBytes == -1) {
						System.out.println("broken socket channel");
						return;
					} else if (numBytes > 0) {
						buffer.flip();
						String response = new String(buffer.array());
						response = response.substring(0, response.lastIndexOf(" t"));
						System.out.println(response);
						
						final String executed = ComandsExecutor.executingComand(response);
						buffer.clear();
						
						ByteBuffer buf = ByteBuffer.allocate(executed.length() + 2);
						buf.put((executed + " t").getBytes());
						buf.flip();
						while (buf.hasRemaining()) {
						try {
							clientSocket.write(buf);
							clientSocket.close();
						} catch (IOException e) {
							System.out.println("Problem with sending response");
							return;
						}
					}
				}
			} }catch (IOException e) {
				System.out.println("Broken client!");
				return;
			}
		}

		System.out.println("Server Stopped.");
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop() {
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

	private void openServerSocket() {
		try {
			serverSocket = ServerSocketChannel.open();
			serverSocket.socket().bind(new InetSocketAddress(39999));

		} catch (IOException e) {
			throw new RuntimeException("Cannot open current port", e);
		}
	}

}