package it.unical.poker.graphics;

import it.unical.poker.game.Table;
import it.unical.poker.game.states.BettingState;
import it.unical.poker.game.states.DiscardState;
import it.unical.poker.game.states.ResetTableState;
import it.unical.poker.game.states.State;
import javafx.animation.AnimationTimer;

public class StoppableStatefulAnimationTimer extends AnimationTimer {
	State state;
	
	public StoppableStatefulAnimationTimer(Table table) {
		super();
		state = new ResetTableState(table);
	}	
	
	@Override
	public void handle(long now) {
		state.process();
		
		if((state instanceof BettingState || state instanceof DiscardState)) {
			this.stop();
		}
		else
			state = state.next();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Bravo, hai rotto tutto!");
		}
		
		if(state == null)
			this.stop();
	}
	
	public void resume() {
		state = state.next();
		start();
	}

}
