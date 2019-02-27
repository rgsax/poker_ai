package it.unical.poker.game.states;

import it.unical.poker.game.Player;
import it.unical.poker.game.Table;

public class DealCardsState extends State {
	
	public DealCardsState(Table table) {
		super(table);
	}
	
	@Override
	public void process() {
		table.shuffleDeck();
		for(Player player : table.getPlayers()) {
			player.drawHand();
		}
		super.process();
	}
	
	@Override
	public State next() {
		super.next();
	
		return null;
	}
}
