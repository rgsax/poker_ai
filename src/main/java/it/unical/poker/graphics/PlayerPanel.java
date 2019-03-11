package it.unical.poker.graphics;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;

import it.unical.poker.game.DLVPlayer;
import it.unical.poker.game.Player;
import it.unical.poker.game.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class PlayerPanel extends HBox {
	private TextField playerNameField = new TextField();
	private ComboBox<File> dlvBettingStrategy = new ComboBox<>();
	private ComboBox<File> dlvDiscardStrategy = new ComboBox<>();
	
	private Table table;
	
	public PlayerPanel(String defaultName, Table table) {
		playerNameField.setText(defaultName);
		
		this.table = table;
		
		initGUI();
		initEH();
	}
	
	public PlayerPanel() {
		playerNameField.setText("_giocatore_");
	}
	
	private void initGUI() {
		initDiscardStrategies();
		initBettingStrategies();
		
		ObservableList<Node> children = this.getChildren();
		children.add(playerNameField);
		children.add(dlvDiscardStrategy);
		children.add(dlvBettingStrategy);
	}
	
	private void initEH() {
		//
	}
	
	private void initDiscardStrategies() {
		ObservableList<File> files = FXCollections.observableArrayList();
		
		URL encodingsPath = getClass().getClassLoader().getResource("encodings");
		
		File[] filteredFiles = new File(encodingsPath.getFile()).listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().matches("^discard_.+[.]lp$");
			}
		});
		
		for(File f : filteredFiles) {
			System.out.println(f.getName());
		}
		
		files.addAll(filteredFiles);
		dlvDiscardStrategy.setItems(files);
		dlvDiscardStrategy.getSelectionModel().select(0);
	}
	
	private void initBettingStrategies() {
		ObservableList<File> files = FXCollections.observableArrayList();
		
		URL encodingsPath = getClass().getClassLoader().getResource("encodings");
		
		File[] filteredFiles = new File(encodingsPath.getFile()).listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().matches("^bet_.+[.]lp$");
			}
		});
		
		for(File f : filteredFiles) {
			System.out.println(f.getName());
		}
		
		files.addAll(filteredFiles);
		dlvBettingStrategy.setItems(files);
		dlvBettingStrategy.getSelectionModel().select(0);
	}
	
	public Player createPlayer() {
		String name = playerNameField.getText();
		if(name.equals(""))
			name = "giocatore";
		return new DLVPlayer(name, table, dlvDiscardStrategy.getValue(), dlvBettingStrategy.getValue());
	}
}
