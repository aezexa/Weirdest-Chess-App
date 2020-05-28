package main;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class MoveBar extends AnchorPane {

    TextArea textArea;

    MoveBar () {
        setLayoutX ( 600 );
        setLayoutY ( 0 );
        setPrefSize ( 300,700 );
        setMaxSize ( 300 , 700 );
        setStyle ( "-fx-background-color: sandybrown" );


        HBox title = new HBox (  );
        title.setPrefSize ( 300,100 );;
        title.setStyle ( "-fx-background-color: #ffb34b; -fx-effect: innershadow(gaussian,rgba(0,0,0,4),75,0,5,0); -fx-border-color: goldenrod" +
                ";-fx-border-width: 10px;" );
        Label label = new Label ( "Move History" );
        label.setStyle ( "-fx-font-size: 15pt; -fx-font-weight: bold; -fx-opacity: 1.0; -fx-font-family: 'Copperplate Gothic Bold'" );
        label.setTranslateX ( 65 );
        label.setTranslateY ( 25 );
        label.setTextAlignment ( TextAlignment.CENTER );
        title.getChildren ().addAll ( label );


        Group textGroup = new Group (  );
        textGroup.setLayoutY ( 100 );
        textGroup.prefHeight ( 600 );
        textGroup.prefWidth ( 300 );
        textArea = new TextArea (  );
        textArea.setBorder ( new Border(new BorderStroke(Color.DARKGOLDENROD,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths ( 10 ))));

        textArea.setPrefSize ( 300,600 );
        textArea.setEditable ( false );
        textArea.setStyle ( "-fx-font-size: 7.5pt; -fx-font-weight: bold; -fx-opacity: 1.0; -fx-font-family: 'Copperplate Gothic Bold'" );
        textGroup.getChildren ().addAll ( textArea );

        getChildren ().addAll ( title , textGroup );
    }

    public String getAreaString () {
        return textArea.getText ();
    }

    public void setAreaString (String text) {
        textArea.setText ( text );
    }

//    public ChessBar () {
//        this.setLayoutX ( 0 );
//        this.setLayoutY ( 100 );
//        this.setPrefSize ( 600,100 );
//        this.setMaxSize ( 600,100 );
//        barScreen = new GridPane ();
//        resetButton = new Button ( "Reset" );
//        resetButton.setPrefWidth ( 200 );
//        resetButton.setPrefHeight ( 50 );
//        resetButton.setOnMouseEntered(e -> resetButton.setBorder ( new Border (new BorderStroke ( Color.BLACK,
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths ( 3 ))) ));
//        resetButton.setOnMouseExited(e -> resetButton.setBorder ( new Border(new BorderStroke(Color.TRANSPARENT,
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)) ));
//
//
//        exitButton = new Button ( "Exit" );
//        exitButton.setPrefWidth ( 200 );
//        exitButton.setPrefHeight ( 50 );
//        exitButton.setCancelButton ( true );
//        exitButton.setOnMouseEntered(e -> exitButton.setBorder ( new Border(new BorderStroke(Color.BLACK,
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths ( 3 ))) ));
//        exitButton.setOnMouseExited(e -> exitButton.setBorder ( new Border(new BorderStroke(Color.TRANSPARENT,
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)) ));
//
//        barScreen.setGridLinesVisible ( true );
//        barScreen.setPrefSize ( 600,100 );
//        barScreen.setStyle ( "-fx-background-color: #ffb34b; -fx-effect: innershadow(gaussian,rgba(0,0,0,4),75,0,5,0,10)" );
//        barScreen.setSnapToPixel ( false );
//
//        ColumnConstraints columnConstraints = new ColumnConstraints (  );
//        columnConstraints.setPercentWidth ( 50 );
//        ColumnConstraints columnConstraintsButton = new ColumnConstraints (  );
//        columnConstraintsButton.setPercentWidth ( 20 );
//
//        barScreen.getColumnConstraints ().addAll ( columnConstraints,columnConstraintsButton,columnConstraints );
//
//        RowConstraints rowConstraints = new RowConstraints ( 50 );
//        barScreen.getRowConstraints ().addAll ( rowConstraints , rowConstraints );
//
//        turn = new Label ( "" );
//        timer = new Label ( "" );
//        barScreen.addColumn ( 0 , new Label ( "Turn" ) , turn );
//        barScreen.addColumn ( 1 , resetButton , exitButton );
//        barScreen.addColumn ( 2 , new Label ( "Time Left" ) , timer );
//
//        for (Node child : barScreen.getChildren ( )) {
//            GridPane.setHalignment ( child , HPos.CENTER );
//            GridPane.setValignment ( child , VPos.CENTER );
//            child.setStyle ( "-fx-font-size: 10pt; -fx-font-weight: bold; -fx-opacity: 1.0; -fx-font-family: 'Copperplate Gothic Bold'" );
//        }
//
//        getChildren ().add ( barScreen );
//
//    }
}
