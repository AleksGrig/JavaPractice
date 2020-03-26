package networking.tcp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class FirstSimpleSocket {

	public static void main(String[] args) throws Exception {

		Socket socket = new Socket("java-course.ru", 80);
		socket.setSoTimeout(2000);

		StringBuilder command = new StringBuilder("GET /haiku.html HTTP/1.1");
		command.append(System.lineSeparator());
		command.append("Host: java-course.ru").append(System.lineSeparator());
		command.append("Connection: close").append(System.lineSeparator());
		command.append(System.lineSeparator());

		OutputStream os = socket.getOutputStream();
		os.write(command.toString().getBytes());

		InputStream is = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "cp1251"));
		String line = br.readLine();
		while (line != null) {
			System.out.println(line);
			try {
				line = br.readLine();
			} catch (SocketTimeoutException ex) {
				ex.printStackTrace(System.out);
				break;
			}
		}
		os.close();
		br.close();
		socket.close();
	}
}

