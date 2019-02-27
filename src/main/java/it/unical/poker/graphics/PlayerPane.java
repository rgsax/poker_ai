package it.unical.poker.graphics;

import it.unical.poker.game.Player;
import javafx.beans.binding.IntegerBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import phe.Card;

public class PlayerPane extends HBox {
	private Player player;
	private Button call = new Button("Call");
	private Button check = new Button("Check");
	private Button raise = new Button("Raise");
	private Button fold = new Button("Fold");
	private Button allin = new Button("Allin");
	private Slider raiseSlider = new Slider();
	private Label raiseLabel = new Label();
	private Label chipsTextLabel = new Label("Chips:");
	private Label chipsLabel = new Label();
	private Label betTextLabel = new Label("Bet:");
	private Label betLabel = new Label();
	private CardView[] cardImages = new CardView[5];
	
	public PlayerPane(Player player) {
		this.player = player;
		
		initGUI();
		initEH();
	}

	private void initEH() {
		chipsLabel.textProperty().bind(player.chips.asString());
		call.disableProperty().bind(player.canCall.not());
		check.disableProperty().bind(player.canCheck.not());
		raise.disableProperty().bind(player.canRaise.not());
		fold.disableProperty().bind(player.canFold.not());
		allin.disableProperty().bind(player.canAllIn.not());
		player.raiseAmount.bind(raiseSlider.valueProperty());
		betLabel.textProperty().bind(player.bet.asString());
		
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
		
//		for(int i = 0 ; i < 5 ; ++i) {
//			cardImages[i].disableProperty().bind(...);
//		}
	}

	private void initGUI() {
		this.setSpacing(5);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10));
		
		Card[] cards = player.getCards();
		
		HBox upperBox = new HBox(5);
		upperBox.setAlignment(Pos.CENTER);
		for(int i = 0 ; i < 5 ; ++i) {
			cardImages[i] = new CardView();
			cardImages[i].setImage(CardImage.getCardImage(cards[i].toString()));
			
			cardImages[i].setPreserveRatio(true);
			cardImages[i].setFitWidth(80);
			
			upperBox.getChildren().add(cardImages[i]);
		}
		
		HBox lowerBox = new HBox(5);
		
		lowerBox.getChildren().addAll(chipsTextLabel, chipsLabel, fold, check, call, raise, raiseSlider, raiseLabel, allin, betTextLabel, betLabel);
		lowerBox.setAlignment(Pos.CENTER);
		
		VBox finalBox = new VBox(10);
		finalBox.getChildren().addAll(upperBox, lowerBox);
		
		this.getChildren().add(finalBox);
	}
	
	

}
