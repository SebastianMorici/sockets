package sockets.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UDPClient {

	private DatagramSocket socket;
	private InetAddress address;
//	private byte[] buffer = new byte[512];

	public UDPClient() throws UnknownHostException, SocketException {
		socket = new DatagramSocket();
		address = InetAddress.getByName(UDPServer.IP);
	}

	public String sendMessage(String msg) {
		byte[] bufferRequest = msg.getBytes();
		DatagramPacket packetRequest = new DatagramPacket(bufferRequest, bufferRequest.length, address, UDPServer.PORT);
		byte[] bufferResponse = new byte[512];
		DatagramPacket packetResponse = new DatagramPacket(bufferResponse, bufferResponse.length);
		try {
			socket.send(packetRequest);
			socket.receive(packetResponse);
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
