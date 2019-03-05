package it.unical.poker.game.states;

import java.util.Iterator;

import it.unical.poker.game.DLVPlayer;
import it.unical.poker.game.Player;
import it.unical.poker.game.Table;
import phe.Hand;

public class DiscardState extends State {
	protected Iterator<Player> currentPlayerIterator;
	protected Player player;
	
	public DiscardState(Table table ) {
		super(table);
		currentPlayerIterator = table.getPlayers().iterator();
		player = currentPlayerIterator.next();
	}
	
	@Override
	public void process() {
		if(player instanceof DLVPlayer) {
			System.out.println(player.getName().get());
			System.out.println("before: " + Hand.toString(player.getCards()));
			player.discard();
			System.out.println("after: " + Hand.toString(player.getCards()));
		}
	}
	
	@Override
	public Player getCurrentPlayer() {
		return player;
	}
	
	@Override
	public void onExit() {
		table.softReset();
		super.onExit();
	}
	
	@Override
	public State next() {
		if(currentPlayerIterator.hasNext()) {
			player = currentPlayerIterator.next();
			return this;
		}
		
		onExit();
		if(table.getActivePlayers().get() > 1)
			return new BettingState2(table);
		
		return new ShowdownState(table);
	}
}
