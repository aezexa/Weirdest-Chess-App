package main;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

public class ChessBar extends HBox {

    public ChessBar () {
        barScreen = new GridPane ();
        resetButton = new Button ( "Reset" );
        exitButton = new Button ( "Exit" );

        barScreen.setGridLinesVisible ( false );
        barScreen.setPrefSize ( 600,600 );
//        barScreen.setVgap ( 10 );
//        barScreen.setHgap ( 10 );
//        barScreen.setPadding ( new Insets ( 10,10,10,10 ) );
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
