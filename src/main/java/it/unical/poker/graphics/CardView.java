package it.unical.poker.graphics;

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
	}
	
	public void setPreserveRatio(boolean value) {
		view.setPreserveRatio(value);
	}
	
	public void setFitWidth(double value) {
		view.setFitWidth(value);
	}
	
	private void initEH() {
		this.setOnMouseClicked( event -> {
			if(!selected)
				this.getStyleClass().add("selected_card");
			else
				this.getStyleClass().remove(0);
			
			selected = !selected;
		});
	}
}
