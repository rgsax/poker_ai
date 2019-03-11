package it.unical.poker.game;

import java.io.File;
import java.util.List;

import it.unical.poker.ai.BettingStrategy;
import it.unical.poker.ai.DiscardStrategy;

public class DLVPlayer extends Player {
	private File discardStrategy;
	private File bettingStrategy;
	
	public DLVPlayer(String s, Table t, File discardStrategy, File bettingStrategy) {
		super(s, t);
		
		this.discardStrategy = discardStrategy;
		this.bettingStrategy = bettingStrategy;
	}
	
	@Override
	public void discard() {
		DiscardStrategy ds = new DiscardStrategy(discardStrategy);
		List<Integer> is = ds.chooseWhatToDiscard(cards); 
		
		drawHand(is);
	}
	
	@Override
	public void bet() {
		BettingStrategy bs = new BettingStrategy(bettingStrategy);	
		String action = bs.evaluate(this, t);
		
		if ( action.equals("fold")) {
			fold(); 
		} else if (action.equals("call")) {
			call(); 
		} else if (action.equals("check")) {
			check();  
		} else if (action.equals("raise")) {
			this.raise();
		} else if (action.equals("allin")) {
			allIn();  
		}
	}
}
