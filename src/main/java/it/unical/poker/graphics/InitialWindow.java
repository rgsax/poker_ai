package it.unical.poker.graphics;

import it.unical.poker.game.Player;
import it.unical.poker.game.Table;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InitialWindow extends GridPane {
	private Label usernameLabel = new Label("utente");
	private TextField usernameField = new TextField("utente");
	private PlayerPanel dlvPlayer;
	private Button playButton = new Button("Play");
	
	private Table table;
	
	public InitialWindow(Table table) {
		dlvPlayer = new PlayerPanel("bob", table);
		
		this.table = table;
		
		initGUI();
		initEH();
	}
	
	public void initGUI() {
		this.setPadding(new Insets(10));
		this.setHgap(5);
		this.setVgap(5);
		this.setAlignment(Pos.CENTER_LEFT);
		
		this.add(usernameLabel, 0, 0);
		this.add(usernameField, 1, 0);
		this.add(playButton, 0, 2);
		this.add(dlvPlayer, 0, 3);
	}
	
	private void initEH() {
		playButton.setOnMouseClicked(event -> {
					
			Main.initGame(new Player(usernameField.getText(), table), dlvPlayer.createPlayer());
		});
	}
}
