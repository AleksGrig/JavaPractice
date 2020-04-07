package enums;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreditCardParser {

	public enum CreditCard
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

		private boolean instanceOf(String code) {
			if(lengths.contains(code.length())) {
				for(String prefix : prefixes) {
					if(code.startsWith(prefix)) {
						return true;
					}
				}
			}
			return false;
		}
		
		public static String checkCardType(String code) {
			var result =  Arrays.asList(CreditCard.values()).stream()
				.filter(card -> card.instanceOf(code.replace(" ", "")))
				.findFirst()
				.map(card -> card.name());
			return result.isPresent() ? result.get() : "Match not found";
		}
	}

	public static void main(String[] args) {
		var creditCards = List.of("6544 4444 4444 4444", "4444 4444 4444 4444", 
				"6044 4444 4444 4444", "3444 4444 4444 444", "3744 4444 4444 4444", "3544 4444 4444 4444 444");
		var cardTypes = creditCards.stream()
			.collect(Collectors.toMap(card -> card, card -> CreditCard.checkCardType(card)));
		System.out.println(cardTypes);
	}

}
