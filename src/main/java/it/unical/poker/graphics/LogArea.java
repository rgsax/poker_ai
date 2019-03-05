package it.unical.poker.graphics;

import java.io.PrintStream;

import javafx.scene.control.TextArea;

public class LogArea extends TextArea {
	public LogArea() {
		System.setOut(new PrintStream(new Logger(this)));
		this.setMaxWidth(500);
		this.setEditable(false);
	}
}