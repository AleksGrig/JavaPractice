package networking.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class QuoteClient {

	public static void main(String[] args) throws IOException {
		System.setProperty("java.net.preferIPv4Stack", "true");

		try (DatagramSocket socket = new DatagramSocket()) {
			byte[] buf = new byte[256];
			InetAddress address = InetAddress.getByName("localhost");
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
			socket.send(packet);
			packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);

			String received = new String(packet.getData(), 0, packet.getLength());
			System.out.println("Quote of the Moment: " + received);
		}
		System.out.println("Finish");
	}
}

