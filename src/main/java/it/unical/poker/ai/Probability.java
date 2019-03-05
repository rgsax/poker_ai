package it.unical.poker.ai;

import it.unical.poker.game.Deck;
import phe.Card;
import phe.Hand;

public class Probability {
	public static double simulateHands(Card[] hand, int N) {
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
	
	private Probability() { }
}
