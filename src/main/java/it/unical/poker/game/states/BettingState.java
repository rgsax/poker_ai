package it.unical.poker.game.states;

import java.util.Iterator;

import it.unical.poker.game.DLVPlayer;
import it.unical.poker.game.Player;
import it.unical.poker.game.Table;

public class BettingState extends State {
	
	protected Iterator<Player> currentPlayerIterator;
	protected Player player;
	
	public BettingState(Table table) {
		super(table);
		
		currentPlayerIterator = table.getPlayers().iterator();
		player = currentPlayerIterator.next();
	}
	
	@Override
	public Player getCurrentPlayer() {
		return player;
	}
	
	@Override
	public void process() {
		if(player instanceof DLVPlayer) {
			player.bet();
			player.setHasAlreadyBet(true);
		}
		
		
		super.process();
	}
	
	@Override
	public void onExit() {
		table.rackBets();
		super.onExit();
	}
	
	@Override
	public State next() {
		if(table.getNotFoldedPlayers().get() == 1) {
			this.onExit();
			return new ShowdownState(table);
		}
		
		if(table.getActivePlayers().get() > 0) {
			if(currentPlayerIterator.hasNext()) {
				player = currentPlayerIterator.next();
				return this;
			}
				
			currentPlayerIterator = table.getPlayers().iterator();
			player = currentPlayerIterator.next();
			return this;
		}
		
		this.onExit();
		return new DiscardState(table);
	}
}
