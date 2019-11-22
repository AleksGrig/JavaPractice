package exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;

public class HttpConnect {

	public static void send(int destination, String data, String partner) throws IOException {
		System.out.println("\nInside send ...");
		if (destination < 0 || destination > 1) {
			throw new IllegalArgumentException(); // Unchecked exception
		}
		if (destination == 0) {
			throw new FileNotFoundException();
		} else if (destination == 1) {
			throw new IOException();
		}

		System.out.println("\nEnd of send ...");
	}
}

