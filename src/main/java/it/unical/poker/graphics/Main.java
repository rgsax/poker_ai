package it.unical.poker.graphics;

import it.unical.poker.game.Table;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Table table = new Table(2);
		
		
		Scene scene = new Scene(new TableWindow(table), 800, 600);
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
