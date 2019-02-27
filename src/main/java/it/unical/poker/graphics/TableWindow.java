package it.unical.poker.graphics;

import it.unical.poker.game.Player;
import it.unical.poker.game.Table;
import javafx.scene.layout.BorderPane;

public class TableWindow extends BorderPane {
	private PlayerPane playerPane1;
	private PlayerPane playerPane2;
	
	public TableWindow(Table table) {		
		Player p1 = new Player("alice", table);
		Player p2 = new Player("bob", table);
		
		playerPane1 = new PlayerPane(p1);
		playerPane2 = new PlayerPane(p2);
		
		initGUI();
		initEH();
	}
	
	private void initGUI() {
		this.setBottom(playerPane1);
		this.setTop(playerPane2);
	}
	
	private void initEH() {
		
	}
}
