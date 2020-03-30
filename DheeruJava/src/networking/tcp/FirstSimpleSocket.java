package networking.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class FirstSimpleSocket {

	public static void main(String[] args) {
		try (var socket = new Socket("java-course.ru", 80);
				var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "cp1251"))) {
			socket.setSoTimeout(2000);

			var command = new StringBuilder("GET /haiku.html HTTP/1.1");
			command.append(System.lineSeparator());
			command.append("Host: java-course.ru").append(System.lineSeparator());
			command.append("Connection: close").append(System.lineSeparator());
			command.append(System.lineSeparator());
			
			socket.getOutputStream().write(command.toString().getBytes());

			for (String line; (line = bufferedReader.readLine()) != null; System.out.println(line));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
