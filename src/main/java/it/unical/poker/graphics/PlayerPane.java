package it.unical.poker.graphics;

import it.unical.poker.game.Player;
import javafx.beans.binding.IntegerBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class PlayerPane extends HBox {
	private Player player;
	private Button call = new Button("Call");
	private Button check = new Button("Check");
	private Button raise = new Button("Raise");
	private Button fold = new Button("Fold");
	private Button allin = new Button("Allin");
	private Slider raiseSlider = new Slider();
	private Label raiseLabel = new Label();
	
	public PlayerPane(Player player) {
		this.player = player;
		
		initGUI();
		initEH();
	}

	private void initEH() {
		call.disableProperty().bind(player.canCall.not());
		check.disableProperty().bind(player.canCheck.not());
		raise.disableProperty().bind(player.canRaise.not());
		fold.disableProperty().bind(player.canFold.not());
		allin.disableProperty().bind(player.canAllIn.not());
		player.raiseAmount.bind(raiseSlider.valueProperty());
		
		raiseSlider.setMin(0);
		raiseSlider.maxProperty().bind(player.chips);
		
		raiseLabel.textProperty().bind(new IntegerBinding() {
			
			{
				super.bind(raiseSlider.valueProperty());
			}
			
			@Override
			protected int computeValue() {
				return (int)raiseSlider.getValue();
			}
		}.asString());
		
		call.setOnMouseClicked( event -> player.call() );
		check.setOnMouseClicked( event -> player.check() );
		raise.setOnMouseClicked( event -> player.raise() );
		fold.setOnMouseClicked( event -> player.fold() );
		allin.setOnMouseClicked( event -> player.allIn() );
	}

	private void initGUI() {
		this.setSpacing(5);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10));
		
		this.getChildren().addAll(fold, check, call, raise, raiseSlider, raiseLabel, allin);
	}
	
	

}
