package it.unical.poker.game;

import java.io.File;
import java.util.List;

import it.unical.poker.ai.BettingStrategy;
import it.unical.poker.ai.DiscardStrategy;

public class DLVPlayer extends Player {
	public DLVPlayer(String s, Table t) {
		super(s, t);
	}
	
	@Override
	public void discard() {
		DiscardStrategy ds = new DiscardStrategy(new File(DiscardStrategy.class.getClassLoader().getResource("encodings/discard_pokerNew.lp").getFile()));
		List<Integer> is = ds.chooseWhatToDiscard(cards); 
		
		drawHand(is);
	}
	
	@Override
	public void bet() {
		BettingStrategy bs = new BettingStrategy(new File(BettingStrategy.class.getClassLoader().getResource("encodings/randomPlayer.dlv").getFile()));	
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
