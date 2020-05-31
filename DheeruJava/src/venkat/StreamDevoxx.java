package venkat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// If you have one-to-one function use map to go from Stream<T> to Stream<R>
// If you have one-to-many function use map to go from Stream<T> to Stream<Collection<R>>
// If you have one-to-many function use flatMap to go from Stream<T> to Stream<R>
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

// Exception handling is an imperative style of programming concept
// In Functional programming we deal with stream of data - dataflow 

public class StreamDevoxx {
	
	static class Book implements Comparable<Book> {
		private long isbn;
		private String title;
		private double rating;
		private double price;
		private String source;

		public Book(long isbn, String title, double rating, double price, String source) {
			this.isbn = isbn;
			this.title = title;
			this.rating = rating;
			this.price = price;
			this.source = source;
		}

		public long getIsbn() {
			return isbn;
		}

		public String getTitle() {
			return title;
		}

		public double getRating() {
			return rating;
		}

		public double getPrice() {
			return price;
		}

		public String getSource() {
			return source;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (isbn ^ (isbn >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Book other = (Book) obj;
			if (isbn != other.isbn) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return "Book [isbn=" + isbn + ", title=" + title + ", rating=" + rating + ", price=" + price + ", source="
					+ source + "]";
		}

		@Override
		public int compareTo(Book o) {
			return Long.valueOf(isbn).compareTo(o.getIsbn());
		}

	}
	
	static List<Book> getFromAmazon(String keyword) {
		Book b1 = new Book(9780596009201L, "Java 1", 4.0, 25.0, "Amazon");
		Book b2 = new Book(9780596009202L, "Java 2", 4.1, 25.0, "Amazon");
		Book b3 = new Book(9780596009203L, "Java 3", 4.5, 25.0, "Amazon");
		Book b4 = new Book(9780596009204L, "Java 4", 4.5, 25.0, "Amazon");
		Book b5 = new Book(9780596009205L, "Java 5", 3.9, 30.0, "Amazon");
		Book b6 = new Book(9780596009206L, "Java 6", 4.8, 40.0, "Amazon");
		Book b7 = new Book(9780596009207L, "Java 7", 4.9, 40.0, "Amazon");
		Book b8 = new Book(9780596009208L, "Java 8", 4.7, 50.0, "Amazon");
		Book b9 = new Book(9780596009209L, "Java 9", 4.3, 50.0, "Amazon");
		Book b10 = new Book(9780596009210L, "Java 10", 4.5, 60.0, "Amazon");

		return Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b7, b8, b9, b10);
	}

	static List<Book> getFromBarnesAndNoble(String keyword) {
		Book b1 = new Book(9780596009201L, "Java 1", 3.9, 20.0, "B&N");
		Book b2 = new Book(9780596009202L, "Java 2", 4.1, 20.0, "B&N");
		Book b3 = new Book(9780596009203L, "Java 3", 4.6, 15.0, "B&N");
		Book b4 = new Book(9780596009204L, "Java 4", 4.5, 20.0, "B&N");
		Book b5 = new Book(9780596009211L, "Java 11", 4.9, 40.0, "B&N");
		Book b6 = new Book(9780596009212L, "Java 12", 4.9, 55.0, "B&N");
		Book b7 = new Book(9780596009213L, "Java 13", 4.9, 60.0, "B&N");

		return Arrays.asList(b1, b2, b3, b4, b5, b6, b7);
	}

	public static void main(String[] args) {
		List<Book> books = new ArrayList<>();
		books.addAll(getFromAmazon("java"));
		books.addAll(getFromBarnesAndNoble("java"));
		
		// Create comma separated list of book names, in upper case, with rating higher than 4.5
		String list = books.stream()
			.filter(b -> b.getRating() >= 4.5)
			.map(Book::getTitle)
			.map(String::toUpperCase)
			.collect(Collectors.joining(", ")); 
		System.out.println(list);
		
		// Partition depending on rating
		Map<Boolean, List<Book>> partition = books.stream()
			.collect(Collectors.partitioningBy(b -> b.getRating() >= 4.5, Collectors.toList()));
		System.out.println(partition);
		
		// Counting 
		Map<Double, Long> count = books.stream()
				.collect(Collectors.groupingBy(Book::getRating, Collectors.counting()));
		System.out.println(count);
		
		// Same with Integer instead Long
		Map<Double, Integer> countInt = books.stream()
				.collect(Collectors.groupingBy(Book::getRating, 
						Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
		System.out.println(countInt);
		
		// Cheapest book
		books.stream()
				.min((b1, b2) -> b1.getPrice() >= b2.getPrice() ? 
						(b1.getPrice() > b2.getPrice() ? 1 : 0) : -1)
				.ifPresentOrElse(System.out::println, () -> System.out.println("Not found"));
		
		// Another approach
		System.out.println(books.stream()
				.collect(Collectors.minBy(Comparator.comparing(Book::getPrice))));
		
		// Elicit name out of cheapest book
		String title = books.stream()
				.collect(Collectors.collectingAndThen(Collectors.minBy(Comparator.comparing(Book::getPrice)), 
						book -> book.map(Book::getTitle).orElse("Not found")));
		System.out.println(title);
		
		// map/mapping - map inside stream, mapping inside reduce operation
		// filter/filtering - filter inside stream, filtering inside reduce
		System.out.println(books.stream()
				.collect(Collectors.groupingBy(Book::getPrice, Collectors.mapping(
						Book::getTitle, Collectors.filtering(t -> t.contains("Java 1"), Collectors.toList())))));
		
		// grouping, mapping - (Function, Collector)
		// collectingAndThen - (Collector, Function)
		// teeing - (Collector, Collector, operation(BiFunction))
		
		
		// flatMap
		List<Integer> numbers = List.of(1, 2, 3);
		
		// one-to-one function
		System.out.println(numbers.stream()
			.map(e -> e * 2) // Stream<T>.map(FoneToone) ==> Stream<R>
			.collect(Collectors.toList()));
		
		// one-to-many function
		System.out.println(numbers.stream()
				.map(e -> List.of(e-1, e+2)) // Stream<T>.map(FoneTomany) ==> Stream<List<R>>
				.collect(Collectors.toList()));
		
		// If instead of Stream<List<R>> we need Stream<R>
		// 	Stream
		//			.map(Function<T, R>) ==> Stream<R>
		//			.flatMap(Function<T, Stream<R>>) ==> Stream<R>
		System.out.println(numbers.stream()
				.flatMap(e -> List.of(e-1, e+2).stream())
				.collect(Collectors.toList()));
		
		// Collecting title's characters
		System.out.println(books.stream()
			.map(Book::getTitle)
			.flatMap(t -> Stream.of(t.split("")))
			.filter(c -> !c.equals(" "))
			.collect(Collectors.toList()));
		
		// FlatMapping inside Collector
		System.out.println(books.stream()
				.collect(Collectors.groupingBy(Book::getPrice, TreeMap::new, // Grouping by price into TreeMap, sorted by price
						Collectors.mapping(b ->b.getTitle().toUpperCase(), // Converting title to upper case
						Collectors.flatMapping(t -> Stream.of(t.split("")),  // Convrting titles to stream of characters
						Collectors.filtering(c -> !c.equals(" "),  // Removing spaces from stream
						Collectors.toCollection(TreeSet::new))))))); // Collecting stream into TreeSet, unique, sorted, upper-cased chars 

		// Sorting
		books.stream()
			.sorted(Comparator.comparing(Book::getPrice).thenComparing(Book::getRating))
			.forEach(System.out::println);
	}
}
