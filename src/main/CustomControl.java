package main;

import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;

public class CustomControl extends Control {
	
	//similar to previous custom controlls but must handle more
	//complex mouse interactions and key interactions
	public CustomControl(){
		setSkin(new CustomControlSkin(this));

		chessBruh = new ChessBruh ();
		getChildren().addAll( chessBruh );
		
		setOnMouseClicked( event -> {
			// TODO Auto-generated method stub
			chessBruh.selectPiece(event.getX(), event.getY() - (statusBarSize / 2));
		} );

		// Add a key listener that will reset the game
		setOnKeyPressed( event -> {
			if (event.getCode() == KeyCode.SPACE)
				chessBruh.resetGame();
		} );
		
	}
	
	public void resize(double width, double height){
		super.resize(width, height - statusBarSize);
		chessBruh.setTranslateY(statusBarSize / 2);
		chessBruh.resize(width, height - statusBarSize);
	}
	
	private ChessBruh chessBruh;
	private int statusBarSize = 100;	
}
