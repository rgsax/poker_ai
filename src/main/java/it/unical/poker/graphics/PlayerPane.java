package it.unical.poker.graphics;

import java.util.ArrayList;

import it.unical.poker.game.Player;
import it.unical.poker.game.states.BettingState;
import it.unical.poker.game.states.BettingState2;
import it.unical.poker.game.states.DiscardState;
import it.unical.poker.game.states.State;
import javafx.beans.binding.IntegerBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import phe.Card;
import phe.Hand;

public class PlayerPane extends HBox {
	private Player player;
	private Button call = new Button("Call");
	private Button check = new Button("Check");
	private Button raise = new Button("Raise");
	private Button fold = new Button("Fold");
	private Button allin = new Button("Allin");
	private Button discard = new Button("Discard");
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
		
		call.setOnMouseClicked( event -> { 
			player.call(); 
			((TableWindow)this.getParent()).resumeTimer();
		});
		
		check.setOnMouseClicked(event -> {
			player.check();
			((TableWindow)this.getParent()).resumeTimer();
		});
		
		raise.setOnMouseClicked(event -> {
			player.raise();
			((TableWindow)this.getParent()).resumeTimer();
		});
		
		fold.setOnMouseClicked(event -> {
			player.fold();
			((TableWindow)this.getParent()).resumeTimer();
		});
		
		allin.setOnMouseClicked(event -> {
			player.allIn();
			((TableWindow)this.getParent()).resumeTimer();
		});
		
		discard.setOnMouseClicked(event -> {
			Card[] cards = player.getCards();
			System.out.println(Hand.toString(cards));
			
			ArrayList<Integer> selectedCards = new ArrayList<>();
			for(int i = 0 ; i < 5 ; ++i) {
				if(cardImages[i].isSelected())
					selectedCards.add(i);
			}
			
			player.drawHand(selectedCards);
			
			cards = player.getCards();
			System.out.println(Hand.toString(cards));
			for(int i = 0 ; i < 5 ; ++i) {
				if(selectedCards.contains(i))
					cardImages[i].setImage(CardImage.getCardImage(cards[i].toString()));
				
				cardImages[i].setSelected(false);
			}
			
			((TableWindow)this.getParent()).resumeTimer();
		});
		
//		for(int i = 0 ; i < 5 ; ++i) {
//			cardImages[i].disableProperty().bind(...);
//		}
	}

	private void initGUI() {
		this.setSpacing(5);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10));
		
		
		HBox upperBox = new HBox(5);
		upperBox.setAlignment(Pos.CENTER);
		
		upperBox.getChildren().addAll(new Label(player.getName().get()), chipsTextLabel, chipsLabel);
		
		Card[] cards = player.getCards();
		if(cards != null)
			for(int i = 0 ; i < 5 ; ++i) {
				cardImages[i] = new CardView();
				if(cards[i] != null)
					cardImages[i].setImage(CardImage.getCardImage(cards[i].toString()));
				
				cardImages[i].setPreserveRatio(true);
				cardImages[i].setFitWidth(80);
				
				upperBox.getChildren().add(cardImages[i]);
			}
		
		upperBox.getChildren().addAll(betTextLabel, betLabel);
		
		HBox bettingLowerBox = new HBox(5);
		bettingLowerBox.visibleProperty()
			.bind(State.STRING_STATE.isEqualTo(BettingState.class.getSimpleName())
					.or(State.STRING_STATE
							.isEqualTo(BettingState2.class.getSimpleName())));
		
		bettingLowerBox.getChildren().addAll(fold, check, call, raise, raiseSlider, raiseLabel, allin);
		bettingLowerBox.setAlignment(Pos.CENTER);
		
		HBox discardLowerBox = new HBox(5);
		discardLowerBox.visibleProperty()
			.bind(State.STRING_STATE.isEqualTo(DiscardState.class.getSimpleName()));
		
		discardLowerBox.getChildren().add(discard);
		discardLowerBox.setAlignment(Pos.CENTER);
		
		VBox finalBox = new VBox(10);
		finalBox.getChildren().addAll(upperBox, bettingLowerBox, discardLowerBox);
		
		this.getChildren().add(finalBox);
	}
	
	public void refreshAllCards() {
		Card[] cards = player.getCards();
		if(cards != null)
			for(int i = 0 ; i < 5 ; ++i) {
				if(cards[i] != null)
					cardImages[i].setImage(CardImage.getCardImage(cards[i].toString()));
			}
	}
}
