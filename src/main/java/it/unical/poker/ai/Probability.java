package it.unical.poker.ai;

import it.unical.poker.game.Deck;
import phe.Card;
import phe.Hand;

public class Probability {
	private static int total_hands = 2598960;
	public static double montecarloSimulateHands(Card[] hand, int N) {
		Deck deck = new Deck();
		deck.strip(hand);
		
		double wins = 0.0;
		
		for(int i = 0 ; i < N ; ++i) {
			deck.shuffle();
			Card[] cards = deck.draw(5);
			
			if(Hand.evaluate(hand) < Hand.evaluate(cards))
				++wins;
		}
		
		return wins / N;
	}
	
	// http://suffe.cool/poker/evaluator.html
	public static double approximateProbability(Card[] hand) {
		double p = (double) countBeatenHands(hand) / Probability.total_hands;
		System.out.println("Probailità: " + p);
		return p; 
	}
	
	public static double approximateProbabilityNoHC(Card[] hand) {
		int beaten = countBeatenHands(hand); 
		if (beaten - 1302540 <= 0) return 0.0; 
		
		double p = (double) (beaten - 1302540) / Probability.total_hands; 
		System.out.println("PROBABILIT À (non coppie): " + p);
		return p; 
	}
	

	// http://suffe.cool/poker/evaluator.html + vedi phe.Hand::evaluate
	private static int countBeatenHands(Card[] hand) {
		/*if (val > 6185)
			return (HandRank.HIGH_CARD); // 1277 high card
		if (val > 3325)
			return (HandRank.ONE_PAIR); // 2860 one pair
		if (val > 2467)
			return (HandRank.TWO_PAIR); // 858 two pair
		if (val > 1609)
			return (HandRank.THREE_OF_A_KIND); // 858 three-kind
		if (val > 1599)
			return (HandRank.STRAIGHT); // 10 straights
		if (val > 322)
			return (HandRank.FLUSH); // 1277 flushes
		if (val > 166)
			return (HandRank.FULL_HOUSE); // 156 full house
		if (val > 10)
			return (HandRank.FOUR_OF_A_KIND); // 156 four-kind
		return (HandRank.STRAIGHT_FLUSH); // 10 straight-flushes
		 */
		
		// max[_]	è il punteggio MINIMO per il tipo di punto _
		// min[_]	è il punteggio MASSIMO per i tipo di punto _
		// [_]		è il numero di mani per il tipo di punto _ (con permutazioni, con combinazioni di semi)
		
		int minHighcard = 7500;
		int maxHighcard = 6186;
		int highcards = 1302540; 
		
		int minOnePair = 6185; 
		int maxOnePair = 3326; 
		int onePair = 1098240; 
		
		int minTwoPair = 3325; 
		int maxTwoPair = 2468;  
		int twoPair = 123552; 
		
		int minThree = 2467; 
		int maxThree = 1610; 
		int threeKind = 54912; 
		
		int minStraight = 1609; 
		int maxStraight = 1600; 
		int straight = 10200; 
		
		int minFlush = 1599; 
		int maxFlush = 323; 
		int flush = 5108; 
		
		int minHouse = 322; 
		int maxHouse = 167; 
		int fhouse = 3744; 
		
		int minFour = 166; 
		int maxFour = 11; 
		int four = 624; 
		
		int minSflush = 10;
		int maxSflush = 1;
		int straightFlushes = 40; 
		
		int mins[] = {minHighcard, minOnePair, minTwoPair, minThree,  minStraight, minFlush, minHouse, minFour, minSflush};
		int maxs[] = {maxHighcard, maxOnePair, maxTwoPair, maxThree,  maxStraight, maxFlush, maxHouse, maxFour, maxSflush};
		int freq[] = {highcards,   onePair,    twoPair,    threeKind, straight,    flush,    fhouse,   four,    straightFlushes};
				
		int handValue = Hand.evaluate(hand); 
		int pt = 0; 
		int beatenHands = 0; 
		
		System.out.println("handvalue: " + handValue + " maxs: " + maxs[pt]);
		
		// il punteggio della mia mano è inferiore al min del range per una determinata categorie
		// di punti -> batto la frequenza di quella mano
		while (handValue <= maxs[pt]) {
			beatenHands += freq[pt]; 
			//System.out.println("BEAT: " + freq[pt]);
			++pt;
		}
		
		// pt è l'indice del punto che ho in mano.
		// Batto esattamente mins[pt]-handValue mani _distinte_, non tengo conto della frequenza
		//System.out.println("Beating: " + (mins[pt] - handValue));
		
		// Stimo la frequenza dei punti che batto come (#mani_distinte / #mani_totali * freq)
		beatenHands += (int) ((double) (mins[pt] - handValue) / (double) (mins[pt] - maxs[pt]) * freq[pt]);
		
		return beatenHands; 
	}
	
	public static void main(String[] args) {
		double p = approximateProbability( Hand.fromString("2h 2d 2c 4d 9s") );
		double q = approximateProbability( Hand.fromString("8h 8d 4c 4h Ts") );
		System.out.println(String.format("p: %.5f q: %.5f ", p, q));
	}
	
	
	private Probability() { }
}
