package main;

import javafx.scene.Group;
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

    public void setAreaString (String text) {
        textArea.setText ( text );
    }


}
