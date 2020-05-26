package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static main.App.inGame;

public class ChessControl extends Control  {

    private static BorderPane mainBorderPane;
    private static ChessBar chessBar;
    private static ChessBoard chessBoard;

    public ChessControl ( ) {
        setSkin ( new SkinBase <> ( this ) {
        } );

        chessBar = new ChessBar (  );
        chessBoard = new ChessBoard (chessBar);
        FileInputStream input = null;
        try {
            input = new FileInputStream ( new File ( "." ).getCanonicalPath () + File.separator + "src" + File.separator + "resources" + File.separator + "board.png" );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        assert input != null;
        ImageView boardImage = new ImageView ( new Image (input) );
        boardImage.setFitWidth ( 600 );
        boardImage.setFitHeight ( 600 );
        boardImage.setX ( 0 );
        boardImage.setY ( 100 );
//        boardImage.setLayoutY ( 100 );
        boardImage.setTranslateY ( 50 );
        boardImage.setPreserveRatio ( true );
        getChildren ().addAll ( chessBar,chessBoard,boardImage );
//        centerPane.getChildren ().addAll ( chessBoard, boardImage );
//        centerPane.setPrefSize ( 600,600 );
//        centerPane.setCenterShape ( true );
//        chessBar.setAlignment ( Pos.BOTTOM_CENTER );
//        centerPane.setAlignment ( Pos.TOP_CENTER );
//        mainBorderPane.setCenter ( centerPane );
//        mainBorderPane.setTop ( chessBar );
//        getChildren ().addAll ( mainBorderPane );

        setOnMouseClicked ( event -> {
            System.out.println ( "x : " + (int) event.getX ()*8/600 + "\ny : " + (int) event.getY ()*8/600 );
        } );

        chessBar.getExitButton ().setOnAction ( event -> endGame () );

        setOnKeyPressed ( keyEvent -> {
            if (keyEvent.getCode () == KeyCode.ESCAPE )
                endGame ();
        } );

//        chessBar.getResetButton().setOnAction ( event -> chessBoard.resetGame() );

    }

    @Override
    public void resize ( double width , double height ) { //nmifhmm
        super.resize ( width , height - chessBarSize );
        chessBoard.setTranslateY( chessBarSize >> 1 );
        chessBoard.resize(width, height - chessBarSize);
        chessBar.resize(width, chessBarSize);
        chessBar.setTranslateY(-(chessBarSize >> 1));
    }

    private void endGame () {
        App.currentScene = App.appScene;
        App.currentStage.setScene ( App.appScene );
        App.setRoot ( "gameMenuScreen" );
        inGame = false;
    }


    private int chessBarSize = 200; //chie?
}
