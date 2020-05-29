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

import static main.ChessControl.currentChessBoard;

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
        textGroup.prefWidth ( 300 );
        textGroup.prefHeight ( 400 );
        textArea = new TextArea (  );
        textArea.setBorder ( new Border(new BorderStroke(Color.DARKGOLDENROD,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths ( 10 ))));

        textArea.setPrefSize ( 300,400 );
        textArea.setEditable ( false );
        textArea.setStyle ( "-fx-font-size: 7.5pt; -fx-font-weight: bold; -fx-opacity: 1.0; -fx-font-family: 'Copperplate Gothic Bold'" );
        textGroup.getChildren ().addAll ( textArea );


        HBox goBackToTitle = new HBox (  );
        goBackToTitle.setLayoutY ( 500 );
        goBackToTitle.setPrefSize ( 300,100 );
        goBackToTitle.setStyle ( "-fx-background-color: #ffb34b; -fx-effect: innershadow(gaussian,rgba(0,0,0,4),75,0,5,0); -fx-border-color: goldenrod" +
                ";-fx-border-width: 10px;" );
        Label label2 = new Label ( "Go Back To" );
        label2.setStyle ( "-fx-font-size: 15pt; -fx-font-weight: bold; -fx-opacity: 1.0; -fx-font-family: 'Copperplate Gothic Bold'" );
        label2.setTextAlignment ( TextAlignment.CENTER );
        label2.setTranslateX ( 72 );
        label2.setTranslateY ( 25 );
        goBackToTitle.getChildren ().addAll ( label2 );

        Group textGroup2 = new Group (  );
        textGroup2.setLayoutY ( 600 );
        textGroup2.prefHeight ( 100 );
        textGroup2.prefWidth ( 250 );
        TextArea textArea2 = new TextArea (  );
        textArea2.setBorder ( new Border(new BorderStroke(Color.DARKGOLDENROD,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths ( 10 ))));

        textArea2.setPrefSize ( 250,100 );
        textArea2.setEditable ( true );
        textArea2.setStyle ( "-fx-font-size: 20pt; -fx-font-weight: bold; -fx-opacity: 1.0; -fx-font-family: 'Copperplate Gothic Bold';" );
        textArea2.setPromptText ( "Number Of Move" );

        Button button = new Button ( "undo" );
        button.setStyle ( "-fx-rotate: -90;-fx-font-family: 'Copperplate Gothic Bold'; -fx-font-size: 20; -fx-border-color: darkgoldenrod;-fx-border-width: 5px;" );
        button.setPrefSize ( 100,50 );
        button.setLayoutX ( 225 );
        button.setLayoutY ( 25 );

        button.setOnAction ( event -> {
            int stateNumberNow;
            try {
                stateNumberNow = Integer.parseInt ( textArea2.getText () );
                if (stateNumberNow < ChessBoard.states.size ()-1)
                    currentChessBoard.reset ( stateNumberNow );
                else
                    App.error ( "Can't Undo To This Stage" );
            } catch (NumberFormatException e) {
                App.error ( "It Should Be A Number" );
            }
            textArea2.setText ( "" );
        } );

        textGroup2.getChildren ().addAll ( textArea2 , button );


        getChildren ().addAll ( title , textGroup , goBackToTitle , textGroup2 );
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
