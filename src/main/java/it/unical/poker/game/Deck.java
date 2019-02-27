package it.unical.poker.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import phe.Card;

/** Modella un mazzo da gioco.
 */
public class Deck implements Iterable<Card> {
	protected int currentCardIndex; 
	protected ArrayList<Card> cards; 
	protected int ncards = 52; 
	
	/** Inizializza un mazzo da gioco.
	 * 
	 */
	public Deck() {
		cards = new ArrayList<>();
		int[] suits = {Card.CLUBS, Card.HEARTS, Card.DIAMONDS, Card.SPADES};
		for (int suit: suits) {
			for (int rank = Card.DEUCE; rank <= Card.ACE; ++rank) {
				cards.add(new Card(rank, suit));
			}
		}
		
		currentCardIndex = 0; 
	}
	
	/** Rimuove (permanentemente) delle carte dal mazzo
	 * 
	 * @param cards
	 */
	public void strip(ArrayList<Card> seenCards) {		
		for (Card c: seenCards) {
			if (cards.remove(c)) {
				ncards -= 1; 
			}
		}
	}
	
	/** Rimuove (permanentemente) delle carte dal mazzo
	 * 
	 * @param seenCards
	 */
	public void strip(Card[] seenCards) {
		for (Card c: seenCards) {
			if (cards.remove(c)) {
				ncards -= 1; 
			}
		}
	}
	
	/** Mescola il mazzo.
	 * 
	 */
	public void shuffle() {		
		Collections.shuffle(cards);
		currentCardIndex = 0; 
	}
	
	/** Restituisce la carta in cima al mazzo.
	 * 
	 * @return
	 */
	public Card draw() {
		return cards.get(currentCardIndex++);
	}
	
	/** Restituisce le n carte in cima al mazzo
	 * 
	 * @param k
	 * @return
	 */
	public ArrayList<Card> drawAsList(int k) {
		ArrayList<Card> cards = new ArrayList<>();
		for (int i = 0; i < k; ++i) {
			cards.add(draw()); 
		}
		return cards; 
	}
	
	/** Restituisce le n carte in cima al mazzo.
	 * 
	 * @param n
	 * @return
	 */
	public Card[] draw(int n) {
		Card[] cards = new Card[n];
		for (int i = 0; i < n; ++i) {
			cards[i] = draw(); 
		}
		return cards; 
	}
	
	/** Restituisce le prime cinque carte del mazzo.
	 * 
	 * @return
	 */
	public Card[] drawHand() {
		return draw(5); 
	}
	
	/** Restituisce True se rimangono carte nel mazzo, False altrimenti.
	 * 
	 * @return
	 */
	public boolean empty() {
		return currentCardIndex >= ncards; 
	}

	@Override
	public Iterator<Card> iterator() {
		Iterator<Card> it = new Iterator<Card>() {

			@Override
			public boolean hasNext() {
				return !empty(); 
			}

			@Override
			public Card next() {
				return draw();
			}
			
		};
		
		return it; 
	}
}
