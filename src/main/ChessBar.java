package main;

import javafx.geometry.HPos;
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
        forfeitButton = new Button ( "Forfeit" );
        forfeitButton.setPrefWidth ( 200 );
        forfeitButton.setPrefHeight ( 50 );
        forfeitButton.setOnMouseEntered( e -> forfeitButton.setBorder ( new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths ( 3 ))) ));
        forfeitButton.setOnMouseExited( e -> forfeitButton.setBorder ( new Border(new BorderStroke(Color.TRANSPARENT,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)) ));


        exitButton = new Button ( "Exit" );
        exitButton.setPrefWidth ( 200 );
        exitButton.setPrefHeight ( 50 );
        exitButton.setCancelButton ( true );
        exitButton.setOnMouseEntered(e -> exitButton.setBorder ( new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths ( 3 ))) ));
        exitButton.setOnMouseExited(e -> exitButton.setBorder ( new Border(new BorderStroke(Color.TRANSPARENT,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)) ));

        barScreen.setGridLinesVisible ( true );
        barScreen.setPrefSize ( 600,100 );
        barScreen.setStyle ( "-fx-background-color: #ffb34b; -fx-effect: innershadow(gaussian,rgba(0,0,0,4),75,0,5,0,10)" );
        barScreen.setSnapToPixel ( false );

        ColumnConstraints columnConstraints = new ColumnConstraints (  );
        columnConstraints.setPercentWidth ( 50 );
        ColumnConstraints columnConstraintsButton = new ColumnConstraints (  );
        columnConstraintsButton.setPercentWidth ( 20 );

        barScreen.getColumnConstraints ().addAll ( columnConstraints,columnConstraintsButton,columnConstraints );

        RowConstraints rowConstraints = new RowConstraints ( 50 );
        barScreen.getRowConstraints ().addAll ( rowConstraints , rowConstraints );

        turn = new Label ( "" );
        timer = new Label ( "" );
        barScreen.addColumn ( 0 , new Label ( "Turn" ) , turn );
        barScreen.addColumn ( 1 , forfeitButton , exitButton );
        barScreen.addColumn ( 2 , new Label ( "Time Left" ) , timer );

        for (Node child : barScreen.getChildren ( )) {
            GridPane.setHalignment ( child , HPos.CENTER );
            GridPane.setValignment ( child , VPos.CENTER );
            child.setStyle ( "-fx-font-size: 10pt; -fx-font-weight: bold; -fx-opacity: 1.0; -fx-font-family: 'Copperplate Gothic Bold'" );
        }

        getChildren ().add ( barScreen );

    }

    public void setTurn (String username) {
        turn.setText ( username );
    }

    public void setTimer (String text) {
        timer.setText ( text );
    }

    public Label turn;
    public Label timer;
    private GridPane barScreen;
    private Button forfeitButton;
    private Button exitButton;

    public Button getExitButton () {
        return exitButton;
    }

    public Button getForfeitButton () {
        return forfeitButton;
    }
}
