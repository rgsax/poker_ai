package it.unical.poker.graphics;

import it.unical.poker.game.DLVPlayer;
import it.unical.poker.game.Player;
import it.unical.poker.game.Table;
import it.unical.poker.game.states.ShowdownState;
import it.unical.poker.game.states.State;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TableWindow extends BorderPane {
	Table table;
	
	private StoppableStatefulAnimationTimer timer;
	
	private PlayerPane playerPane1;
	private PlayerPane playerPane2;
	
	private Label potTextLabel = new Label("Pot:");
	private Label potLabel = new Label();
	private Label betTextLabel = new Label("Bet:");
	private Label betLabel = new Label();
	
	public TableWindow(Table table) {
		this.table = table;
		
		Player p1 = new Player("Alice", table);
		Player p2 = new DLVPlayer("Bob", table);
		
		playerPane1 = new PlayerPane(p1);
		playerPane2 = new PlayerPane(p2);
		
		
		timer = new StoppableStatefulAnimationTimer(this);
		
		initGUI();
		initEH();
	}
	
	public Table getTable() {
		return table;
	}
	
	private void initGUI() {
		HBox box1 = new HBox(5);
		box1.setAlignment(Pos.CENTER);
		box1.getChildren().addAll(potTextLabel, potLabel);
		
		HBox box2 = new HBox(5);
		box2.setAlignment(Pos.CENTER);
		box2.getChildren().addAll(betTextLabel, betLabel);
		
		VBox box = new VBox(5);
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(box1, box2);
		
		this.setCenter(box);
		this.setBottom(playerPane1);
		this.setTop(playerPane2);
		
		this.setLeft(new LogArea());
	}
	
	private void initEH() {
		potLabel.textProperty().bind(table.getPot().asString());
		betLabel.textProperty().bind(table.getBet().asString());
		
		this.setOnKeyPressed(event -> {
			if(event.getCode().equals(KeyCode.ENTER) && State.STRING_STATE.get().equals(ShowdownState.class.getSimpleName()))
				resumeTimer();
		});
		
		timer.start();
	}
	
	public void refreshPlayersCards() {
		playerPane1.refreshAllCards();
		playerPane2.refreshAllCards();
	}
	
	public void resumeTimer() {
		timer.resume();
	}
}
