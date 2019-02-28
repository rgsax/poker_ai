package it.unical.poker.game.states;

import it.unical.poker.game.Table;

public class BettingState2 extends BettingState {
	public BettingState2(Table table) {
		super(table);
	}
	
	@Override
	public State next() {
		if(table.getActivePlayers().get() > 0 && table.getNotFoldedPlayers().get() > 1) {
			if(currentPlayerIterator.hasNext())
				return this;
			
			currentPlayerIterator = table.getPlayers().iterator();
			return this;
		}
		
		this.onExit();
		
		return new ShowdownState(table);
	}
}
