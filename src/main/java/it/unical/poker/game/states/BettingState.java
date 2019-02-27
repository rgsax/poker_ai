package it.unical.poker.game.states;

import java.util.Iterator;

import it.unical.poker.game.Player;
import it.unical.poker.game.Table;

public class BettingState extends State {
	
	protected Iterator<Player> currentPlayerIterator;
	
	public BettingState(Table table) {
		super(table);
		
		currentPlayerIterator = table.getPlayers().iterator();
	}
	
	@Override
	public void process() {
		Player player = currentPlayerIterator.next();
//		if(player instanceof DLVPlayer)
//			player.doAction();
		
		super.process();
	}
	
	@Override
	public void onExit() {
		table.rackBets();
		super.onExit();
	}
	
	@Override
	public State next() {
		if(table.getActivePlayers().get() > 0) {
			if(currentPlayerIterator.hasNext())
				return this;
			
			if(table.getNotFoldedPlayers().get() == 1) {
				this.onExit();
				return new ShowdownState(table);
			}
				
			currentPlayerIterator = table.getPlayers().iterator();
			return this;
		}
		
		this.onExit();
		return new DiscardState(table);
	}
}
