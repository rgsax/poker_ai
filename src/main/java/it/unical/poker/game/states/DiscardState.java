package it.unical.poker.game.states;

import java.util.Iterator;

import it.unical.poker.game.Player;
import it.unical.poker.game.Table;

public class DiscardState extends State {
	protected Iterator<Player> currentPlayerIterator;
	
	public DiscardState(Table table ) {
		super(table);
		currentPlayerIterator = table.getPlayers().iterator();
	}
	
	@Override
	public void process() {
		Player player = currentPlayerIterator.next();
		//player.discard();
	}
	
	@Override
	public State next() {
		if(currentPlayerIterator.hasNext())
			return this;
		return new BettingState2(table);
	}
}
