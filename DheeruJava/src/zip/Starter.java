package zip;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Starter {

	public static void main(String[] args) throws IOException {
		readZip();
		saveZip();
	}

	private static void saveZip() throws IOException {
		var zipOutputStream = new ZipOutputStream(new FileOutputStream("save.zip"));
		var zipEntry1 = new ZipEntry("save1.txt");
		zipOutputStream.putNextEntry(zipEntry1);
		System.out.println("Saving save1.txt...");
		zipOutputStream.write("Save string1".getBytes());
		var zipEntry2 = new ZipEntry("save2.txt");
		zipOutputStream.putNextEntry(zipEntry2);
		System.out.println("Saving save2.txt...");
		zipOutputStream.write("Save string1".getBytes());
		zipOutputStream.close();
	}

	private static void readZip() throws IOException {
		ZipFile zipFile = new ZipFile("temp.zip");
		for (Enumeration<? extends ZipEntry> iterator = zipFile.entries(); iterator.hasMoreElements();) {
			var zipEntry = iterator.nextElement();
			System.out.println("Entry: " + zipEntry.getName());
			if (!zipEntry.isDirectory()) {
				var bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(zipEntry)));
				for(String line; (line = bufferedReader.readLine()) != null; System.out.println(line));
				System.out.println("EOF---------------------->>>");
			}
		}
		
		System.out.println("\n");
		zipFile.stream().filter(zEntry -> !zEntry.isDirectory()).forEach(zEntry -> System.out.println(zEntry.getName()));
		zipFile.close();
	}
}
