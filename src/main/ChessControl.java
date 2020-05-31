package main;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static main.App.*;
import static main.App.playImage;

public class ChessControl extends Control {

    private static BorderPane mainBorderPane;
    private static ChessBar chessBar;
    private static MoveBar moveBar;
    public static ChessBoard currentChessBoard;

    public ChessControl ( ) {
        setSkin ( new SkinBase <> ( this ) {
        } );

        AnchorPane anchorPane = new AnchorPane (  );

        chessBar = new ChessBar (  );
        moveBar = new MoveBar ();
        currentChessBoard = new ChessBoard (chessBar, moveBar);

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
        boardImage.setPreserveRatio ( true );

        anchorPane.getChildren ().addAll ( boardImage , chessBar , moveBar, currentChessBoard );
        getChildren ().addAll ( anchorPane );

        chessBar.getExitButton ().setOnAction ( event -> endGame () );

        setOnKeyPressed ( keyEvent -> {
            if (keyEvent.getCode () == KeyCode.ESCAPE )
                endGame ();
        } );

        chessBar.getForfeitButton ().setOnAction ( event -> {
            currentChessBoard.endGameScreen ( "What a loser! " + currentChessBoard.getOppositeTurnUser ().getName () + " won!" );
            currentChessBoard.getOppositeTurnUser ().addWins ();
            currentChessBoard.getOppositeTurnUser ().addScore ( 2 );
            currentChessBoard.getTurnUser ( ).addLosses ( );
            currentChessBoard.getTurnUser ( ).addScore ( -1 );
            endGame ();
        });

    }

    @Override
    public void resize ( double width , double height ) { //nmifhmm
        super.resize ( width , height - chessBarSize );
        currentChessBoard.setTranslateY( chessBarSize >> 1 );
        currentChessBoard.resize(width, height - chessBarSize);
        chessBar.resize(width, chessBarSize);
        chessBar.setTranslateY(-(chessBarSize >> 1));
    }

    public static void endGame () {
        App.currentScene = App.appScene;
        App.currentStage.setScene ( App.appScene );
        App.setRoot ( "gameMenuScreen" );
        inGame = false;
    }

    private int chessBarSize = 200;
}
