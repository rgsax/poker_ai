package it.unical.poker.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import phe.Card;


@Id("")
public class CardAdapter {
	static {
		try {
			ASPMapper.getInstance().registerClass(CardAdapter.class);
		} catch (ObjectNotValidException | IllegalAnnotationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Param(0)
	int value; 
	
	@Param(1)
	String suit; 
	
	// Card -> Atom 
	CardAdapter(Card c) {
		int cardSuit = c.getSuit(); 
		int cardValue = c.getRank();
		
		switch (cardSuit) {
		case Card.HEARTS:
			suit = "HEARTS"; 
			break;
		case Card.CLUBS:
			suit = "CLUBS"; 
			break; 
		case Card.SPADES:
			suit = "SPADES"; 
			break;
		case Card.DIAMONDS:
			suit = "DIAMONDS"; 
			break; 
		}
		
		value = cardValue += 2; 
	}
	
	// Atom -> Card 
	public Card getCard() {
		int cardSuit = 0; 
		if (suit.equals("HEARTS")) {
			cardSuit = Card.HEARTS;
		} else if (suit.equals("DIAMONDS")) {
			cardSuit = Card.DIAMONDS; 
		} else if (suit.equals("CLUBS")) {
			cardSuit = Card.CLUBS; 
		} else if (suit.equals("SPADES")) {
			cardSuit = Card.SPADES; 
		}
		
		int cardValue = value -= 2; 
		
		return new Card(cardValue, cardSuit); 
	}
	
	CardAdapter() { 
		
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
