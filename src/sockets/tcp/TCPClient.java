package sockets.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class TCPClient {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public void startConnection(String ip, int port) throws IOException {
		clientSocket = new Socket(ip, port);
		// Enviar mensajes al servidor
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		// Recibir mensajes desde el servidor
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public String sendMessage(String msg) throws IOException {
		out.println(msg);
		String response = in.readLine();
		return response;
	}

	public void stopConnection() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}

	public static void main(String[] args) {
		TCPClient tCPClient = new TCPClient();
		try {
			// Ip del servidor y puerto donde está escuchando el servidor
			tCPClient.startConnection(TCPServer.IP, TCPServer.PORT);
			System.out.println("Hello");
			Scanner scan = new Scanner(System.in);
			String consoleInput;
			while (true) {
				consoleInput = scan.next();
				String response = tCPClient.sendMessage(consoleInput);
				System.out.println(response);
				if (response.equals("Bye")) {
					break;
				}
			}
			scan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
