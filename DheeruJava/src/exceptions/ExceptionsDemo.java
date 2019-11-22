package exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExceptionsDemo {

	public static void main(String[] args) {
		System.out.println("\nInside main ...");
//		try {
			share();
//		} catch (FileNotFoundException e) { // TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		System.out.println("\nEnd of main ...");
	}

	private static void share() {
		System.out.println("\nInside share ...");

		try {
			HttpConnect.send(0, "hello", "http://www.goodsnips.com");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// throw e;
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\nEnd of share ...");
	}

}

