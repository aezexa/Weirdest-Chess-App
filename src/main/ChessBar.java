package main;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ChessBar extends HBox {

    public ChessBar () {
        this.setLayoutX ( 0 );
        this.setLayoutY ( 100 );
        this.setPrefSize ( 600,100 );
        this.setMaxSize ( 600,100 );
        barScreen = new GridPane ();
        resetButton = new Button ( "Reset" );
        resetButton.setPrefWidth ( 200 );
        resetButton.setPrefHeight ( 50 );

        exitButton = new Button ( "Exit" );
        exitButton.setPrefWidth ( 200 );
        exitButton.setPrefHeight ( 50 );

        barScreen.setGridLinesVisible ( true );
        barScreen.setPrefSize ( 600,100 );
        barScreen.setStyle ( "-fx-background-color: #ffb34b; -fx-effect: innershadow(gaussian,rgba(0,0,0,4),75,0,5,0,10)" );
        barScreen.setSnapToPixel ( false );

        ColumnConstraints columnConstraints = new ColumnConstraints (  );
        columnConstraints.setPercentWidth ( 50 );

        barScreen.getColumnConstraints ().addAll ( columnConstraints,columnConstraints,columnConstraints );

        RowConstraints rowConstraints = new RowConstraints ( 50 );
        barScreen.getRowConstraints ().addAll ( rowConstraints , rowConstraints );

        turn = new Label ( "" );
        timer = new Label ( "" );
        barScreen.addColumn ( 0 , new Label ( "Turn" ) , turn );
        barScreen.addColumn ( 1 , resetButton , exitButton );
        barScreen.addColumn ( 2 , new Label ( "Time Left" ) , timer );

        for (Node child : barScreen.getChildren ( )) {
            GridPane.setHalignment ( child , HPos.CENTER );
            GridPane.setValignment ( child , VPos.CENTER );
            if (child instanceof Label)
                child.setStyle ( "-fx-font-size: 10pt; -fx-font-weight: bold; -fx-opacity: 1.0; -fx-font-family: 'Copperplate Gothic Bold'" );
        }

        getChildren ().add ( barScreen );

    }
    public Label turn;
    public Label timer;
    private GridPane barScreen;
    private Button resetButton;
    private Button exitButton;

    public Button getExitButton () {
        return exitButton;
    }
}
