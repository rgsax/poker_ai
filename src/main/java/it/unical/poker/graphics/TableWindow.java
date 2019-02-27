package it.unical.poker.graphics;

import it.unical.poker.game.Player;
import it.unical.poker.game.Table;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class TableWindow extends BorderPane {
	Table table;
	
	private StoppableStatefulAnimationTimer timer;
	
	private PlayerPane playerPane1;
	private PlayerPane playerPane2;
	private PlayerPane playerPane3;
	
	private Label potTextLabel = new Label("Pot:");
	private Label potLabel = new Label();
	
	public TableWindow(Table table) {
		this.table = table;
		this.table.shuffleDeck();
		
		Player p1 = new Player("Alice", table);
		Player p2 = new Player("Bob", table);
		Player p3 = new Player("Carlotta", table);
		
		playerPane1 = new PlayerPane(p1);
		playerPane2 = new PlayerPane(p2);
		playerPane3 = new PlayerPane(p3);
		
		
		timer = new StoppableStatefulAnimationTimer(table);
		
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
		this.setLeft(playerPane3);
	}
	
	private void initEH() {
		potLabel.textProperty().bind(table.getPot().asString());

		timer.start();
	}
	
	public void resumeTimer() {
		timer.resume();
	}
}
