package it.unical.poker.game;

import java.io.File;
import java.util.List;

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
}
