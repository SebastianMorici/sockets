package sockets.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UDPClient {

	private static final String SERVER_IP = "192.168.100.204";
	private static final int SERVER_PORT = 4000;
	// Socket que se utiliza para enviar y recibir datagramas ("DatagramPacket").
	private DatagramSocket socket;
	private InetAddress address;

	public UDPClient() throws UnknownHostException, SocketException {
		socket = new DatagramSocket();
		address = InetAddress.getByName(SERVER_IP);
	}

	public String sendMessage(String msg) {
		// Se convierte el mensaje en una cadena de bytes y se agrega al "bufferRequest"
		byte[] bufferRequest = msg.getBytes();
		// Se crea el datagrama a enviar que contiene el mensaje (convertido en bytes), la dirección IP del servidor (address) y el puerto.
		DatagramPacket packetRequest = new DatagramPacket(bufferRequest, bufferRequest.length, address, SERVER_PORT);
		// Buffer destinado a la recepción del mensaje del servidor.
		byte[] bufferResponse = new byte[512];
		// Contendrá el datagrama enviado por el servidor.
		DatagramPacket packetResponse = new DatagramPacket(bufferResponse, bufferResponse.length);
		try {
			// Envía el datagrama al servidor a través de "packetRequest"
			socket.send(packetRequest);
			// Espera hasta que recibe un datagrama del servidor y se almacena en "packetResponse"
			socket.receive(packetResponse);
			// Se extraen los datos del datagrama (ya que vienen como cadena de bytes)
			String received = new String(packetResponse.getData(), 0, packetResponse.getLength());
			return received;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void close() {
		socket.close();
	}

	public static void main(String[] args) {
		try {
			UDPClient client = new UDPClient();
			System.out.println("Hello");
			Scanner scan = new Scanner(System.in);
			String consoleInput;
			while (true) {
				// Lee los datos introducidos por consola.
				consoleInput = scan.next();
				String response = client.sendMessage(consoleInput);
				System.out.println(response);
				if (response.equals("Bye")) {
					break;
				}

			}
			scan.close();
			client.close();
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}
	}
}
