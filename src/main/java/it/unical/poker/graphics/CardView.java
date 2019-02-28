package it.unical.poker.graphics;

import it.unical.poker.game.states.DiscardState;
import it.unical.poker.game.states.State;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class CardView extends GridPane {
	ImageView view = new ImageView();
	boolean selected = false;
	
	public CardView() {
		initGUI();
		initEH();
	}

	public CardView(Image image) {
		view.setImage(image);
		
		initGUI();
		initEH();
	}
	
	private void initGUI() {
		this.getStylesheets().add("css/style.css");
		this.getChildren().add(view);		
	}

	public void setImage(Image image) {
		view.setImage(image);
		setSelected(false);
	}
	
	public void setPreserveRatio(boolean value) {
		view.setPreserveRatio(value);
	}
	
	public void setFitWidth(double value) {
		view.setFitWidth(value);
	}
	
	private void initEH() {
		this.setOnMouseClicked( event -> {
			setSelected(!selected);
		});
		
		this.disableProperty()
			.bind(State.STRING_STATE.isNotEqualTo(DiscardState.class.getSimpleName()));
	}

	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		if(this.selected != selected) {
			if(selected)
				this.getStyleClass().add("selected_card");
			else
				this.getStyleClass().remove(0);
			
			this.selected = selected;
		}
	}
}
