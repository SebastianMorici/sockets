package sockets.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

class TCPClientHandler extends Thread {
	private Socket clientSocket;
	// Atributo que sirve para ENVIAR mensajes al cliente
	private PrintWriter out;
	// Atributo que sirve para LEER mensajes del cliente
	private BufferedReader in;

	public TCPClientHandler(Socket socket) {
		this.clientSocket = socket;
	}

	public void run() {
		try {
			// Se inicializa la variable para poder enviar datos. Envía datos desde el socket del servidor al socket del cliente.
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			// Se inicializa la variable para poder leer datos. Lee los datos que el socket del cliente envió al socket del servidor.
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.equalsIgnoreCase("end")) {
					// Si el cliente envía "end", devuelve "Bye" y termina la conexión con ese cliente.
					out.println("Bye");
					break;
				}
				// Ante cualquier entrada, envía la fecha y la cantidad de cliente conectados en ese momento.
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
