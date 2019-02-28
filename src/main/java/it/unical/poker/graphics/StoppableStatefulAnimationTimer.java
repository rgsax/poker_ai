package it.unical.poker.graphics;

import it.unical.poker.game.states.BettingState;
import it.unical.poker.game.states.DealCardsState;
import it.unical.poker.game.states.DiscardState;
import it.unical.poker.game.states.ResetTableState;
import it.unical.poker.game.states.State;
import javafx.animation.AnimationTimer;

public class StoppableStatefulAnimationTimer extends AnimationTimer {
	State state;
	TableWindow tableWindow;
	
	public StoppableStatefulAnimationTimer(TableWindow tableWindow) {
		super();
		
		this.tableWindow = tableWindow;
		state = new ResetTableState(tableWindow.getTable());
	}	
	
	@Override
	public void handle(long now) {
		state.process();
		
		if((state instanceof BettingState || state instanceof DiscardState)) {
			this.stop();
		}
		else {
			if((state instanceof DealCardsState))
				tableWindow.refreshPlayersCards();
			state = state.next();
		}	
		
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
