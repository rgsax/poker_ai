package it.unical.poker.game;

import java.util.Arrays;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Table {
	int size; 
	BooleanProperty[] hasCalled; 
	BooleanProperty[] hasRaised; 
	BooleanProperty[] hasFolded; 
	BooleanProperty[] hasAllIn; 
	BooleanProperty[] hasChecked; 
	BooleanProperty[] hasQuit; 

	public IntegerProperty[] bets;
	public IntegerProperty[] chips; 

	IntegerProperty pot; 
	public IntegerProperty bet; 
	
	Deck deck = new Deck(); 
	
	IntegerProperty activePlayers; 
	
	public void shuffleDeck() {
		deck.shuffle(); 
	}
	
	public Table(int i) {
		size = i; 
		hasCalled = new SimpleBooleanProperty[size];
		hasRaised = new SimpleBooleanProperty[size];
		hasChecked = new SimpleBooleanProperty[size];
		hasFolded = new SimpleBooleanProperty[size];
		hasAllIn = new SimpleBooleanProperty[size];
		hasQuit = new SimpleBooleanProperty[size];  
		
		bets = new IntegerProperty[size];
		chips  = new IntegerProperty[size];
		
		for (int j = 0; j < size; ++j) {
			hasCalled[j] = new SimpleBooleanProperty(false);
			hasRaised[j] = new SimpleBooleanProperty(false);
			hasChecked[j] = new SimpleBooleanProperty(false);
			hasFolded[j] = new SimpleBooleanProperty(false);
			hasAllIn[j] = new SimpleBooleanProperty(false);
			hasQuit[j] = new SimpleBooleanProperty(false);
			
			bets[j] = new SimpleIntegerProperty(0);
			chips[j] = new SimpleIntegerProperty(1000);
		}
						
		pot = new SimpleIntegerProperty(0);
		bet = new SimpleIntegerProperty(0);
		
		// BET BINDING
		bet.bind(new IntegerBinding() {
			{for (int k = 0; k <size; ++k) {
				super.bind(bets[k]);
			}}
			@Override
			protected int computeValue() {
				return Arrays.stream(bets).map(ip -> ip.intValue()).max(Integer::compareTo).get(); 
			}
		});
		activePlayers = new SimpleIntegerProperty(0);
		activePlayers.bind(new IntegerBinding() {		
			{
				for (int k = 0; k < size; ++k) {
					super.bind(hasCalled[k]);
					super.bind(hasChecked[k]);
					super.bind(hasFolded[k]);
					super.bind(hasQuit[k]);
				}
			}
			
			@Override
			protected int computeValue() {
				int s = 0; 
				for (int k = 0; k < size; ++k) {
					if (hasCalled[k].get() || hasChecked[k].get() || hasFolded[k].get() 
							|| hasQuit[k].get()) continue;
					++s; 
				}
				return s; 
			}
		});
	}
}
