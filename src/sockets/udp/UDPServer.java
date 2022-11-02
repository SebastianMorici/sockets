package sockets.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class UDPServer extends Thread{

	public static final int PORT = 4000;
	public static final String IP = "192.168.100.204";
	
	private DatagramSocket socket;
//	private boolean running;
//	private byte[] buffer = new byte[256];
	
	

	public UDPServer() throws SocketException {
		socket = new DatagramSocket(PORT);
	}

	public void run() {
//		running = true;
		System.out.println("Server started. Listening in port " + PORT + "... Press CTRL+C to stop.");
		while (true) {
			DatagramPacket packetRequest = new DatagramPacket(new byte[128], 128);
			try {
				socket.receive(packetRequest);
				InetAddress clientAddress = packetRequest.getAddress();
				int clientPort = packetRequest.getPort();
				String received = new String(packetRequest.getData(), 0, packetRequest.getLength());
				
				
				byte[] bufferResponse = new Date().toString().getBytes();
				DatagramPacket packetResponse = new DatagramPacket(bufferResponse, bufferResponse.length, clientAddress, clientPort);
				if (received.equalsIgnoreCase("end")) {
					bufferResponse = "Bye".getBytes();
					socket.send(new DatagramPacket(bufferResponse, bufferResponse.length, clientAddress, clientPort));
//					running = false;
					continue;
				}
				
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
