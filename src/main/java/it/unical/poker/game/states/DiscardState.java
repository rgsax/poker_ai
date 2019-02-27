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
		System.out.println("player" + player.getId());
		//if(player instanceof DLVPlayer)
		//	player.discard();
	}
	
	@Override
	public void onExit() {
		table.softReset();
		super.onExit();
	}
	
	@Override
	public State next() {
		if(currentPlayerIterator.hasNext()) {
			return this;
		}
		
		onExit();
		if(table.getActivePlayers().get() > 1)
			return new BettingState2(table);
		
		return new ShowdownState(table);
	}
}
