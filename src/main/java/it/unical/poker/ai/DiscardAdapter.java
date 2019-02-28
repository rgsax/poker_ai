package it.unical.poker.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import phe.Card;

@Id("discard_HELPER")
public class DiscardAdapter {
	
	@Param(0)
	int value; 
	
	@Param(1)
	String suit; 
	
	// Atom -> Card 
	public Card getCard() {
		int cardSuit = 0; 
		if (suit.equals("hearts")) {
			cardSuit = Card.HEARTS;
		} else if (suit.equals("diamonds")) {
			cardSuit = Card.DIAMONDS; 
		} else if (suit.equals("clubs")) {
			cardSuit = Card.CLUBS; 
		} else if (suit.equals("spades")) {
			cardSuit = Card.SPADES; 
		}
		
		int cardValue = value -= 2; 
		
		return new Card(cardValue, cardSuit); 
	}
	
	public DiscardAdapter() { 
		
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
