package it.unical.poker.graphics;

import java.io.IOException;
import java.io.OutputStream;

import javafx.scene.control.TextArea;

public class Logger extends OutputStream {
	private TextArea textArea;
	
	public Logger(TextArea textArea) {
		this.textArea = textArea;
	}
	
	@Override
	public void write(int b) throws IOException {
		textArea.appendText(String.valueOf((char)b));
	}
}