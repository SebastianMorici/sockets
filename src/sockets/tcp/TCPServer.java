package sockets.tcp;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer {

	private static final int PORT = 5000;
	private ServerSocket serverSocket;

	public void start(int port) {
		try {
			// Crea el socket y se le asigna un puerto
			serverSocket = new ServerSocket(port);
			System.out.println("Server started. Listening in port " + port + "... Press CTRL+C to stop.");
			while (true) {
				// El server se queda "escuchando" al socket para saber cuando un cliente solicite una conexión.
				// Cuando llega un cliente, se lo acepta y se inicia el hilo encargado de manejar la conexión con ese cliente.
				// Este ciclo se repite infinitamente, creando hilos para cada cliente que solicite una conexión.
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
