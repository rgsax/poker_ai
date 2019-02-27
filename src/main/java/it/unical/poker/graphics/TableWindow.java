package it.unical.poker.graphics;

import it.unical.poker.game.Player;
import it.unical.poker.game.Table;
import it.unical.poker.game.states.ResetTableState;
import it.unical.poker.game.states.State;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class TableWindow extends BorderPane {
	Table table;
	
	private PlayerPane playerPane1;
	private PlayerPane playerPane2;
	
	private Label potTextLabel = new Label("Pot:");
	private Label potLabel = new Label();
	
	public TableWindow(Table table) {
		this.table = table;
		this.table.shuffleDeck();
		
		Player p1 = new Player("Alice", table);
		Player p2 = new Player("Bob", table);
		
		playerPane1 = new PlayerPane(p1);
		playerPane2 = new PlayerPane(p2);
		
		initGUI();
		initEH();
	}
	
	private void initGUI() {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(potTextLabel, potLabel);
		
		this.setCenter(box);
		this.setBottom(playerPane1);
		this.setTop(playerPane2);
	}
	
	private void initEH() {
		potLabel.textProperty().bind(table.getPot().asString());
		
		new AnimationTimer() {
			State state = new ResetTableState(table);
			
			@Override
			public void handle(long now) {
				
				state.process();
				state = state.next();
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("Bravo, hai rotto tutto!");
				}
				
				if(state == null)
					this.stop();
			}
		}.start();
	}
}
