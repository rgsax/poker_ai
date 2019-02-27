package it.unical.poker.game.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.unical.poker.game.Player;
import it.unical.poker.game.Table;
import phe.Hand;

public class ShowdownState extends State {
	public ShowdownState(Table table) {
		super(table);
	}
	
	@Override
	public void process() {		
		Map<Player, Integer> playerPoints = new HashMap<Player, Integer>();
		
		for(Player player : table.getPlayers()) {
			if(!player.getHasFolded().get())
				playerPoints.put(player, Hand.evaluate(player.getCards()));
		}
		
		int min = playerPoints.values().stream().min(Integer::compare).get().intValue();
		ArrayList<Player> winners = new ArrayList<>();
		
		for(Player player : playerPoints.keySet()) {
			if(playerPoints.get(player).equals(min)) {
				winners.add(player);
			}
		}
		
		int potSplit = table.getPot().get() / winners.size();
		int rem = table.getPot().get() - potSplit * winners.size();
		
		for(Player player : winners) {
			int currentSplit = potSplit;
			if(rem > 0) {
				currentSplit += rem;
				rem = 0;
			}
			
			table.chips[player.getId().get()].set(player.getChips().get() + currentSplit);
		}
		
		table.getPot().set(0);		
	}
	
	@Override
	public State next() {
		super.next();
		return null;
	}
}
