package functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.htmlcleaner.HtmlCleaner;

/**
 * Demonstrates:
 * 
 * Function & Predicate (including Chaining & Compose) Bi-Function as an
 * alternate to Predicate
 * 
 * Consumer & Supplier Show API too (including primitive functional interfaces)
 * !!
 * 
 * Method References TODO: Constructor References
 * 
 * @author Dheeru Mundluru
 *
 */
public class FunctionalInterfaceDemo {

	public static void main(String[] args) {

		String doc1 = "<html><body>One of the most common uses of <i>streams</i> is to represent queries over data in collections</body></html>";
		String doc2 = "<html><body>Information integration systems provide valuable services to users by integrating information from a number of autonomous, heterogeneous and distributed Web sources</body></html>";
		String doc3 = "<html><body>Solr is the popular, blazing fast open source enterprise search platform from the Apache Lucene</body></html>";
		String doc4 = "<html><body>Java 8 goes one more step ahead and has developed a streams API which lets us think about parallelism</body></html>";

		List<String> documents = new ArrayList<>(Arrays.asList(doc1, doc2, doc3, doc4));

		List<String> targetDocuments = new ArrayList<>();

		for (String doc : documents) {
			// boolean isTargetDoc = filter(doc, d -> d.contains("stream"));

			// 7d: Method References (objectRef::instanceMethod)
			// Function<String, Boolean> function = doc::contains;

			// 4. Implementing Predicate via BiFunction. 'c' is hard-coded in Predicate
			// example
			BiFunction<String, String, Boolean> biFunction = (d, c) -> d.contains(c);

			// 7c: Method References (ClassName::instanceMethod)
			// BiFunction<String, String, Boolean> biFunction = String::contains;
			if (biFunction.apply(doc, "stream")) {
				// if (function.apply("stream")) {
				// if (isTargetDoc) {

				// 1. Passing lambda directly
				doc = transform(doc, d -> Indexer.stripHtmlTags(d));
				doc = transform(doc, d -> Indexer.removeStopwords(d));

				// 7a. Method References (ClassName::staticMethod)
				// doc = transform(doc, Indexer::removeStopwords);

				// 2. Assigning lambda to a variable and then passing as argument
				// Function<String, String> htmlCleaner = d -> Indexer.stripHtmlTags(d);
				// doc = transform(doc, htmlCleaner);
				// Function<String, String> stopwordRemover = d -> Indexer.removeStopwords(d);
				// doc = transform(doc, stopwordRemover);

				// 3. Compose: andThen & compose return composed functions
				// Function<String, String> docProcessor = htmlCleaner.andThen(stopwordRemover);
				// doc = transform(doc, docProcessor);

				// System.out.println(doc);

				targetDocuments.add(doc);
			}
		}

		// Consumer & Supplier Demo
		System.out.println("\nConsumer & Supplier Demo ... ");
		// 5. Consumer
		System.out.println("\nConsumer Demo ... ");
		targetDocuments.forEach(d -> System.out.println(d));

		// 6. Supplier (Deferred Execution). Logging framework ~ java.util.logging uses
		// this
		System.out.println("\nSupplier Demo ... ");
		for (String doc : targetDocuments) {
			try {
				if (doc.length() > 80) {
					throw new Exception("Oversized document!!!");
				}
			} catch (Exception e) {
				// NOTE: doc is being captured from enclosing scope
				print(() -> e.getMessage() + " ~ " + doc);

				// 7c. Method References (ClassName::instanceMethod)
				String resizedDoc = resize(doc, 0, 80, (d, start, end) -> d.substring(start, end));
				// String resizedDoc = resize(doc, 0, 80, String::substring);
				// System.out.println("Resized doc: " + resizedDoc);
			}
		}

		// 7b: Method References (objectRef::instanceMethod)
		System.out.println("\nMethod References Demo ...");
		targetDocuments.forEach(System.out::println);

		// Constructor References

		// Typical scenario
		Supplier<String> supplier = String::new; // () -> new String();
		System.out.println("\nsupplier.get: " + supplier.get());

		Function<String, String> function = String::new; // s -> new String(s);
		System.out.println("\nfunction.apply: " + function.apply("Java"));

		BiFunction<Integer, Float, HashMap> biFunction = HashMap::new; // (c, lf) -> new HashMap(c, lf);
		System.out.println("\nbiFunction.apply: " + biFunction.apply(100, 0.75f));

		Consumer<String> consumer = String::new;
		consumer.accept("Java");
		// Create own interface if existing functional interfaces are not useful!
	}

	private static String resize(String doc, int start, int end,
			TriFunction<String, Integer, Integer, String> operator) {
		doc = operator.apply(doc, start, end);
		return doc;
	}

	private static boolean errorFlag = true;
	private static void print(Supplier<String> supplier) {
		if (errorFlag) {
			System.out.println(supplier.get());
		}
	}

	static boolean filter(String doc, Predicate<String> filter) {
		return filter.test(doc);
	}

	/*
	 * static String transform(String doc, UnaryOperator<String> transformer) {
	 * return transformer.apply(doc); }
	 */
	static String transform(String doc, Function<String, String> transformer) {
		return transformer.apply(doc);
	}

}

class Indexer {

	private static List<String> stopWords = Arrays.asList("of", "the", "a", "is", "to", "in", "and");

	static String stripHtmlTags(String doc) {
		return new HtmlCleaner().clean(doc).getText().toString();
	}

	static String removeStopwords(String doc) {

		StringBuilder sb = new StringBuilder();
		for (String word : doc.split(" ")) {
			if (!stopWords.contains(word)) {
				sb.append(word).append(" ");
			}
		}

		return sb.toString();
	}

}

@FunctionalInterface
interface TriFunction<S, U, V, R> {
	R apply(S s, U u, V v);
}