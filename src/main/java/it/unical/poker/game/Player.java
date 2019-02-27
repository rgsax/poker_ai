package it.unical.poker.game;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {
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
	
	public void allIn() {
		System.out.println(name.get() + " goes all-in!");
		t.hasAllIn[id.get()].set(true);
		t.bets[id.get()].set(t.bets[id.get()].get() + chips.get());
		t.chips[id.get()].set(0);
	}
	
	public void check() {
		System.out.println(name.get() + " cheking at " + bet.intValue());
		t.hasChecked[id.get()].set(true);
	}
	
	public void call() {
		System.out.println(name.get() + " calling for " + toCall.intValue());
		t.chips[id.get()].set( t.chips[id.get()].subtract(toCall).intValue() );
		t.bets[id.get()].set(t.bet.intValue());
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
				.and(hasFolded.not().and(hasAllIn.not())));
		
		canCheck = new SimpleBooleanProperty();
		canCheck.bind(bet.isEqualTo(t.bet)
				.and(hasFolded.not()
				.and(hasAllIn.not())));
		
		canRaise = new SimpleBooleanProperty();
		canRaise.bind(t.hasRaised[id.get()].not().and(chips.greaterThan(toCall.add(raiseAmount)))
				.and(raiseAmount.greaterThan(0))
				.and(hasFolded.not())
				.and(hasRaised.not()));
		
		canFold = new SimpleBooleanProperty();
		canFold.bind(t.activePlayers.greaterThan(1)
				.and(hasFolded.not())
				.and(hasAllIn.not()));
		
		canAllIn = new SimpleBooleanProperty();
		canAllIn.bind(hasFolded.not().and(hasAllIn.not())); 
	}
}
