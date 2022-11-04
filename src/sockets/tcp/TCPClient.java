package sockets.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class TCPClient {
	private static final String SERVER_IP = "192.168.100.204";
	private static final int SERVER_PORT = 5000;
	
	private Socket clientSocket;
	// Atributo que sirve para ENVIAR mensajes al servidor
	private PrintWriter out;
	// Atributo que sirve para LEER mensajes del servidor
	private BufferedReader in;

	public void startConnection(String ip, int port) throws IOException {
		// Se envía una solicitud de conexión. El socket se crea solo si el servidor acepta la solicitud.
		clientSocket = new Socket(ip, port);
		// Se inicializa la variable para poder enviar datos. Envía datos desde el socket del cliente al socket del servidor.
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		// Se inicializa la variable para poder leer datos. Lee los datos que el socket del servidor envió al socket del cliente.
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public String sendMessage(String msg) throws IOException {
		// Se envía el mensaje al servidor. Se 
		out.println(msg);
		// Se guarda la respuesta del servidor.
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
			tCPClient.startConnection(SERVER_IP, SERVER_PORT);
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
