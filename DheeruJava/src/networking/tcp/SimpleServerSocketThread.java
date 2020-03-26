package networking.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServerSocketThread {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		int port = 19999;
		System.out.println("Try to bind to port:" + port);
		ServerSocket server = new ServerSocket(port, 200);
		System.out.println("Server socket is opened");
		while (true) {
			Socket socket = server.accept();
			System.out.println("Connection is accepted");
			new SocketThread(socket).start();
		}

	}

	private static class SocketThread extends Thread {

		private Socket socket;

		public SocketThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String str;
				while ((str = br.readLine()) != null) {
					System.out.println(str);
					if (str.equals("bye")) {
						pw.println("bye");
						break;
					} else {
						pw.println("Server answers:" + str);
					}
				}
				pw.close();
				br.close();

				socket.close();
			} catch (Exception ex) {
				ex.printStackTrace(System.out);
			}
		}
	}
}

