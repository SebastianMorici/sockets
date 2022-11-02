package sockets.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

class TCPClientHandler extends Thread {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public TCPClientHandler(Socket socket) {
		this.clientSocket = socket;
	}

	public void run() {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.equalsIgnoreCase("end")) {
					out.println("Bye");
					break;
				}
				out.println(new Date().toString() + ". " + (Thread.activeCount()-1) + " clients connected right now.");
			}
			in.close();
			out.close();
			clientSocket.close();
			System.out.println("One client disconnected");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
