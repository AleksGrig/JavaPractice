package networking.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer {

	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 1000);
		server.createContext("/", new MyHandler());
		server.createContext("/test1", new MyHandler1());
		server.createContext("/test2", new MyHandler2());
		server.start();
	}

	static class MyHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			String response = "Welcome Real's HowTo test page";
			t.sendResponseHeaders(200, response.getBytes().length);
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class MyHandler1 implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			String response = "Handler 1";
			t.sendResponseHeaders(200, response.getBytes().length);
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class MyHandler2 implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			String response = "Handler 2";
			t.sendResponseHeaders(200, response.getBytes().length);
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}

