package it.unical.poker.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	List<Player> players = new ArrayList<Player>();
	
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
					super.bind(hasAllIn[k]);
				}
			}
			
			@Override
			protected int computeValue() {
				int s = 0; 
				for (int k = 0; k < size; ++k) {
					if (hasCalled[k].get() || hasChecked[k].get() || hasFolded[k].get() 
							|| hasQuit[k].get() || hasAllIn[k].get()) continue;
					++s; 
				}
				return s; 
			}
		});
	}
	
	public int getNotFoldedPlayers() {
		int folded = 0;
		
		for(BooleanProperty bp : hasFolded)
			if(!bp.get())
				++folded;
		
		return folded;
	}

	public IntegerProperty getPot() {
		return pot;
	}

	public int getSize() {
		return size;
	}

	public BooleanProperty[] getHasCalled() {
		return hasCalled;
	}

	public BooleanProperty[] getHasRaised() {
		return hasRaised;
	}

	public BooleanProperty[] getHasFolded() {
		return hasFolded;
	}

	public BooleanProperty[] getHasAllIn() {
		return hasAllIn;
	}

	public BooleanProperty[] getHasChecked() {
		return hasChecked;
	}

	public BooleanProperty[] getHasQuit() {
		return hasQuit;
	}

	public IntegerProperty[] getBets() {
		return bets;
	}

	public IntegerProperty[] getChips() {
		return chips;
	}

	public IntegerProperty getBet() {
		return bet;
	}

	public Deck getDeck() {
		return deck;
	}

	public IntegerProperty getActivePlayers() {
		return activePlayers;
	}

	public void hardReset() {
		for(int i = 0 ; i < size ; ++i) {
			hasCalled[i].set(false);
			hasChecked[i].set(false);
			hasRaised[i].set(false);
			hasFolded[i].set(false);
			hasAllIn[i].set(false);
			bets[i].set(0);
		}
		
		pot.set(0);
	}

	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void rackBets() {
		int total = 0;
		for(int i = 0 ; i < size ; ++i) {
			total += bets[i].get();
			bets[i].set(0);
		}
		
		pot.set(pot.get() + total);
	}
}
