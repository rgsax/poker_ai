package it.unical.poker.game.states;

import it.unical.poker.game.Player;
import it.unical.poker.game.Table;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class State {
	protected Table table;
	public static StringProperty STRING_STATE = new SimpleStringProperty();

	public State(Table table) {
		this.table = table;
		onEnter();
		
		STRING_STATE.set(this.getClass().getSimpleName());
	}
	
	public void onEnter() {
		//System.out.println("Entering on " + this.getClass().getSimpleName());
	}


	public void onExit() {
		//System.out.println("Exiting from " + this.getClass().getSimpleName());
	}

	public void process() {
		//System.out.println("Processing for " + this.getClass().getSimpleName());
	}
	
	public State next() {
		//System.out.println("Calling next for " + this.getClass().getSimpleName());
		return null;
	}
	
	public Player getCurrentPlayer() {
		return null;
	}
}
