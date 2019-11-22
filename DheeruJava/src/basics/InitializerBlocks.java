package basics;

public class InitializerBlocks {

	public InitializerBlocks() {
		System.out.println("Inside no-arg constructor...");
	}

	// Instance initializer
	{
		System.out.println("Inside instance initializer..."); // Copied into beginning of each constructor
	}

	public static void main(String[] args) {
		new InitializerBlocks();
	}

}
