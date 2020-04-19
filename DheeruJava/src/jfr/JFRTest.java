package jfr;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Recording;

public class JFRTest {

	@Label("MyEvent")
	@Description("Helps programmer getting started")
	static class Info extends Event {
		@Label("Message")
		String message;
	}

	public static void main(String[] args) throws IOException {
		try (var recording = new Recording()) {
			recording.enable(Info.class).withoutThreshold().withStackTrace();
			recording.start();
			var info = new Info();
			info.message = "Start";
			info.begin();
			testMethod();
			Thread.sleep(5000);
			info.commit();
			recording.stop();
			recording.dump(Path.of("flight_recorder.jfr"));
			System.out.println("End of execution");
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}

	private static void testMethod() {
		List<Person> persons = new LinkedList<>();
		for (int i = 1; i <= 100000; i++) {
			var person = new Person();
			person.setFirstName("Name" + i);
			person.setLastName("Surname" + i);
			person.setAge((int)(Math.random() * 100));
			persons.add(person);
		}

		persons.stream().map(p -> p.getAge()).forEach(age -> System.out.println(age));
	}
}
