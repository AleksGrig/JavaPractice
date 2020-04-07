package enums;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CreditCardParser {

	private enum CreditCard
	{
		VISA(List.of("4"), Set.of(16)),
		Amex(List.of("34", "37"), Set.of(15)),
		Discovery(List.of("60", "65"), Set.of(16)),
		JCB(List.of("35"), Set.of(16, 17, 18, 19));
		
		private List<String> prefixes;
		private Set<Integer> lengths;
		
		private CreditCard(List<String> prefixes, Set<Integer> lengths) {
			this.lengths = lengths;
			this.prefixes = prefixes;
		}

		public boolean instanceOf(String code) {
			code = code.replace(" ", "");
			if(lengths.contains(code.length())) {
				for(String prefix : prefixes) {
					if(code.startsWith(prefix)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(checkCardType("4544 4444 4444 4444"));
	}

	private static String checkCardType(String code) {
		var result =  Arrays.asList(CreditCard.values())
			.stream()
			.filter(card -> card.instanceOf(code))
			.findFirst()
			.map(card -> card.name());
		return result.isPresent() ? result.get() : "Match not found";
	}

}
