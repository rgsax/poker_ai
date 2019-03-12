package it.unical.poker.game;

import java.util.List;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import phe.Card;

public class Player {
	boolean hasAlreadyBet = false; 
	
	public boolean isHasAlreadyBet() {
		return hasAlreadyBet;
	}

	public void setHasAlreadyBet(boolean hasAlreadyBet) {
		this.hasAlreadyBet = hasAlreadyBet;
	}

	public Card[] getCards() {
		return cards;
	}

	public void setCards(Card[] cards) {
		this.cards = cards;
	}

	protected static int ID = 0;  
	public Table t; 
	
	StringProperty name = new SimpleStringProperty();
	IntegerProperty id = new SimpleIntegerProperty();
	
	public IntegerProperty chips; 
	public IntegerProperty bet; 
	
	BooleanProperty hasCalled;
	BooleanProperty hasChecked; 
	BooleanProperty hasRaised;
	BooleanProperty hasAllIn; 
	BooleanProperty hasFolded; 
	BooleanProperty hasQuit; 
	
	public BooleanProperty canCall; 
	public BooleanProperty canCheck;
	public BooleanProperty canAllIn; 
	public BooleanProperty canFold; 
	
	public BooleanProperty canRaise;
	public IntegerProperty raiseAmount; 
	IntegerProperty toCall; 
	
	Card[] cards = new Card[5];
	
	public void allIn() {
		System.out.println(name.get() + " goes all-in!");
		t.hasAllIn[id.get()].set(true);
		t.bets[id.get()].set(t.bets[id.get()].get() + chips.get());
		t.chips[id.get()].set(0);
		
		for (int i = 0; i < t.size; ++i) {
			if (t.bets[i].lessThan(t.bet).getValue()) {
				t.hasCalled[i].set(false);
				t.hasChecked[i].set(false);
			}
		}
		
		t.hasCalled[id.get()].set(true);
		t.hasChecked[id.get()].set(true);
		t.hasRaised[id.get()].set(true);
	}
	
	public void check() {
		System.out.println(name.get() + " cheking at " + bet.intValue());
		t.hasChecked[id.get()].set(true);
	}
	
	public void call() {
		System.out.println(name.get() + " calling for " + toCall.intValue());
		t.chips[id.get()].set( t.chips[id.get()].subtract(toCall).intValue() );
		t.bets[id.get()].set(t.bet.intValue());
		
		t.hasChecked[id.get()].set(true);
		t.hasCalled[id.get()].set(true);
	}
	
	public void fold() {
		System.out.println(name.get() + " folding ");
		t.hasFolded[id.get()].set(true); 
	}
	
	public void raise() {
		call(); 
		System.out.println(name.get() + " raising for " + (toCall.intValue()+raiseAmount.intValue()));
		
		// Sistema le bet
		t.bets[id.get()].set(t.bets[id.get()].add(raiseAmount).intValue());
		t.chips[id.get()].set(t.chips[id.get()].get() - raiseAmount.get());
		
		
		// Rilascia i call/check
		for (int i = 0; i < t.size; ++i) {
			if (t.bets[i].lessThan(t.bet).getValue()) {
				t.hasCalled[i].set(false);
				t.hasChecked[i].set(false);
			}
		}
		
		t.hasCalled[id.get()].set(true);
		t.hasChecked[id.get()].set(true);
		t.hasRaised[id.get()].set(true);
	}
	
	public void drawHand() {
		Card[] tmp = t.deck.draw(5);
		for(int i = 0 ; i < 5 ; ++i)
			cards[i] = tmp[i];
	}
	
	public void discard() { }
	public void bet() { }
	
	public void drawHand(List<Integer> indexes) {
		for(Integer index : indexes) {
			cards[index] = t.deck.draw();
		}
	}
	
	public Player(String s, Table t) {
		this.t = t; 
		name.set(s);
		id.set(ID++);
		
		chips = new SimpleIntegerProperty();
		chips.bind(t.chips[id.get()]);
		
		bet = new SimpleIntegerProperty();
		bet.bind(t.bets[id.get()]);
		
		hasCalled = new SimpleBooleanProperty();
		hasCalled.bind(t.hasCalled[id.get()]);
		
		hasChecked = new SimpleBooleanProperty();
		hasChecked.bind(t.hasChecked[id.get()]);
		
		hasRaised = new SimpleBooleanProperty();
		hasRaised.bind(t.hasRaised[id.get()]);
		
		hasFolded = new SimpleBooleanProperty();
		hasFolded.bind(t.hasFolded[id.get()]);
		
		hasAllIn = new SimpleBooleanProperty();
		hasAllIn.bind(t.hasAllIn[id.get()]);
		
		hasQuit = new SimpleBooleanProperty();
		hasQuit.bind(t.hasQuit[id.get()]);
		
		raiseAmount = new SimpleIntegerProperty(1); 
		// Bindare da fuori...!
		
		toCall = new SimpleIntegerProperty();
		toCall.bind(new IntegerBinding() {
			{
				super.bind(t.bet);
				super.bind(bet);
			}
			@Override
			protected int computeValue() {
				return t.bet.subtract(bet).getValue().intValue();
			}
		});
		
		canCall = new SimpleBooleanProperty();
		canCall.bind(chips.greaterThan(toCall).and(bet.lessThan(t.bet))
				.and(hasFolded.not())
				.and(hasAllIn.not())
				.and(hasCalled.not()));
		
		canCheck = new SimpleBooleanProperty();
		canCheck.bind(bet.isEqualTo(t.bet)
				.and(hasChecked.not())
				.and(hasFolded.not()
				.and(hasAllIn.not())
				.and(t.getNotFoldedPlayers().greaterThan(1))));
		
		canRaise = new SimpleBooleanProperty();
		canRaise.bind(t.hasRaised[id.get()].not().and(chips.greaterThan(toCall.add(raiseAmount)))
				.and(raiseAmount.greaterThan(0))
				.and(hasFolded.not())
				.and(hasRaised.not()));
		
		canFold = new SimpleBooleanProperty();
		canFold.bind(t.getNotFoldedPlayers().greaterThan(1)
				.and(hasFolded.not())
				.and(hasAllIn.not()));
		
		canAllIn = new SimpleBooleanProperty();
		canAllIn.bind(hasFolded.not()
				.and(hasAllIn.not())
				.and(t.getNotFoldedPlayers().greaterThan(1)));
	
		t.addPlayer(this);
	}

	public StringProperty getName() {
		return name;
	}

	public IntegerProperty getId() {
		return id;
	}

	public IntegerProperty getChips() {
		return chips;
	}

	public IntegerProperty getBet() {
		return bet;
	}

	public BooleanProperty getHasCalled() {
		return hasCalled;
	}

	public BooleanProperty getHasChecked() {
		return hasChecked;
	}

	public BooleanProperty getHasRaised() {
		return hasRaised;
	}

	public BooleanProperty getHasAllIn() {
		return hasAllIn;
	}

	public BooleanProperty getHasFolded() {
		return hasFolded;
	}

	public BooleanProperty getHasQuit() {
		return hasQuit;
	}

	public BooleanProperty getCanCall() {
		return canCall;
	}

	public BooleanProperty getCanCheck() {
		return canCheck;
	}

	public BooleanProperty getCanAllIn() {
		return canAllIn;
	}

	public BooleanProperty getCanFold() {
		return canFold;
	}

	public BooleanProperty getCanRaise() {
		return canRaise;
	}

	public IntegerProperty getRaiseAmount() {
		return raiseAmount;
	}
	
	public void payAnte() {
		System.out.println("Player " + name.get() + " is paying " + t.ante.get());
		
		t.chips[id.get()].set(t.chips[id.get()].get() - t.ante.get());
		t.bets[id.get()].set(t.bets[id.get()].get() + t.ante.get());
		
	}

	public IntegerProperty getToCall() {
		return toCall;
	}
}
