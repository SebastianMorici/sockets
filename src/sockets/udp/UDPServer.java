package sockets.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class UDPServer extends Thread {

	private static final int PORT = 4000;

	// Socket que se utiliza para enviar y recibir datagramas ("DatagramPacket").
	private DatagramSocket socket;

	public UDPServer() throws SocketException {
		socket = new DatagramSocket(PORT);
	}

	public void run() {
		System.out.println("Server started. Listening in port " + PORT + "... Press CTRL+C to stop.");
		while (true) {
			// Contendrá el datagrama enviado por el cliente.
			DatagramPacket packetRequest = new DatagramPacket(new byte[128], 128);
			try {
				// Espera hasta que recibe un datagrama del cliente y se almacena en
				// "packetRequest"
				socket.receive(packetRequest);
				// Se obtiene la dirección IP del cliente a través del datagrama que se recibió
				InetAddress clientAddress = packetRequest.getAddress();
				// Se obtiene el PUERTO del cliente a través del datagrama que se recibió
				int clientPort = packetRequest.getPort();
				// Se extraen los datos del datagrama (ya que vienen como cadena de bytes)
				String received = new String(packetRequest.getData(), 0, packetRequest.getLength());

				// Buffer destinado al envío del mensaje al cliente. Se transforma el mensaje
				// (en este caso, la fecha) en una cadena de bytes.
				byte[] bufferResponse = new Date().toString().getBytes();
				// Se crea el datagrama a enviar que contiene el mensaje (convertido en bytes),
				// la dirección IP del cliente y el puerto del cliente.
				DatagramPacket packetResponse = new DatagramPacket(bufferResponse, bufferResponse.length, clientAddress,
						clientPort);
				if (received.equalsIgnoreCase("end")) {
					// Si el cliente nos envía "end", devolvemos "Bye".
					bufferResponse = "Bye".getBytes();
					// Se envía la respuesta a través de un nuevo datagrama. (Se reutiliza "bufferResponse")
					socket.send(new DatagramPacket(bufferResponse, bufferResponse.length, clientAddress, clientPort));
					continue;
				}

				// Se envía el datagrama al cliente a través de packetResponse
				socket.send(packetResponse);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		try {
			new UDPServer().start();

		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

}
