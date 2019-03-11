package it.unical.poker.graphics;

import it.unical.poker.game.Player;
import it.unical.poker.game.Table;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	static Stage stage;
	static Table table;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		table = new Table(2);
		
		
		Scene scene = new Scene(new InitialWindow(table), 800, 600);
		primaryStage.setScene(scene);
		
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static void initGame(Player player1, Player player2) {
		stage.getScene().setRoot(new TableWindow(table, player1, player2));
	}
}
