package it.unical.poker.game.states;

import it.unical.poker.game.Table;

public abstract class State {
	protected Table table;

	public State(Table table) {
		this.table = table;
		onEnter();
	}
	
	public void onEnter() {
		System.out.println("Entering on " + this.getClass().getSimpleName());
	}


	public void onExit() {
		System.out.println("Exiting from " + this.getClass().getSimpleName());
	}

	public void process() {
		System.out.println("Processing for " + this.getClass().getSimpleName());
	}
	
	public State next() {
		System.out.println("Calling next for " + this.getClass().getSimpleName());
		return null;
	}
}
