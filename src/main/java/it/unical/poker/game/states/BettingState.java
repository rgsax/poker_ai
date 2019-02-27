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
		
		//player.doAction();
		
		super.process();
	}
	
	@Override
	public void onExit() {
		table.rackBets();
		super.onExit();
	}
	
	@Override
	public State next() {
		if(currentPlayerIterator.hasNext())
			return this;
		
		if(table.getActivePlayers().get() > 0) {
			currentPlayerIterator = table.getPlayers().iterator();
			return this;
		}
		
		this.onExit();
		
		if(table.getNotFoldedPlayers() == 1)
			return new ShowdownState(table);
		
		return new DiscardState(table);
	}
}
