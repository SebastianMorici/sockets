package sockets.tcp;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer {

	public static final int PORT = 5000;
	public static final String IP = "192.168.100.204";
	private ServerSocket serverSocket;

	public void start(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server started. Listening in port " + port);
			while (true) {
				new TCPClientHandler(serverSocket.accept()).start();
				System.out.println("New client connected, total: " + (Thread.activeCount()-1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			stop();
		}
	}
	
	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
    public static void main(String[] args) {
    	TCPServer server = new TCPServer();
        server.start(PORT);
    }
}
