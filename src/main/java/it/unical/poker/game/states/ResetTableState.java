package it.unical.poker.game.states;

import java.util.Collections;

import it.unical.poker.game.Table;

public class ResetTableState extends State {
	private static int GAME_COUNT = 0;
	
	public ResetTableState(Table table) {
		super(table);
		++GAME_COUNT;
	}

	@Override
	public void process() {
		if(GAME_COUNT == 3) {
			GAME_COUNT = 0;
			table.increaseAnte();
		}
		
		table.hardReset();
		
		Collections.rotate(table.getPlayers(), 1);
		
		super.process();
	}
	
	@Override
	public State next() {
		super.next();
		
		return new DealCardsState(table);
	}
}
