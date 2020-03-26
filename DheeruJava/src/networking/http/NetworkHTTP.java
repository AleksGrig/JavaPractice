package networking.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkHTTP {

	public static void main(String[] args) {
		NetworkHTTP n = new NetworkHTTP();
		n.demoHTTP();
	}

	private void demoHTTP() {
		try {
			URL serverAddress = new URL("http://java-course.ru:80/");
			HttpURLConnection connection = (HttpURLConnection) serverAddress.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setDoOutput(false);
//            connection.setReadTimeout(10000);
//            connection.connect();

			try {
				BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line).append(System.lineSeparator());
				}

				System.out.println(sb.toString());

			} catch (IOException e) {
				e.printStackTrace(System.out);
			} finally {
				connection.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
	}
}

