package networking.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServerSocket {
	public static void main(String[] args) throws Exception {

		int port = 19999;
		System.out.println("Try to bind to port:" + port);
		ServerSocket server = new ServerSocket(port);
		System.out.println("Server socket is opened");
		while (true) {
			Socket socket = server.accept();
			System.out.println("Connection is accepted");

			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String str;
			while ((str = br.readLine()) != null) {
				System.out.println(str);
				if (str.equals("bye")) {
					pw.println("bye");
					break;
				} else {
					pw.println("Server is answering:" + str);
				}
			}
			Thread.sleep(1000);
			pw.close();
			br.close();
			server.close();
			socket.close();
		}
	}
}

