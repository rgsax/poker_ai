package it.unical.poker.graphics;

import java.util.HashMap;

import javafx.scene.image.Image;

public class CardImage {
	private static HashMap<String, Image> cards = new HashMap<>();
	
	private CardImage() { }
	
	public static Image getCardImage(String card) {
		card = card.toLowerCase();
		
		if(!cards.containsKey(card)) {
			Image img = new Image("cards/" + card + ".png");
			cards.put(card, img);
		}
		
		return cards.get(card);
	}
}