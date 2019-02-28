package it.unical.poker.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import phe.Card;


@Id("in_hand_HELPER")
public class CardAdapter {
	static {
		try {
			System.out.println("CARD ADAPTER REGISTRATO");
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
	public CardAdapter(Card c) {
		int cardSuit = c.getSuit(); 
		int cardValue = c.getRank();
		
		switch (cardSuit) {
		case Card.HEARTS:
			suit = "hearts"; 
			break;
		case Card.CLUBS:
			suit = "clubs"; 
			break; 
		case Card.SPADES:
			suit = "spades"; 
			break;
		case Card.DIAMONDS:
			suit = "diamonds"; 
			break; 
		}
		
		value = cardValue += 2; 
	}
	
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
	
	public CardAdapter() { 
		
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
