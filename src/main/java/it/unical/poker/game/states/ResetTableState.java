package it.unical.poker.game.states;

import it.unical.poker.game.Table;

public class ResetTableState extends State {
	
	public ResetTableState(Table table) {
		super(table);
	}

	@Override
	public void process() {		
		table.hardReset();
		super.process();
	}
	
	@Override
	public State next() {
		super.next();
		
		return new DealCardsState(table);
	}
}
