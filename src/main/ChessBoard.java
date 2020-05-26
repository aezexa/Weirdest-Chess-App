package main;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static main.App.inGame;

public class ChessBoard extends Pane {
    public ChessBoard (ChessBar chessBar) {
        this.setLayoutX ( 0 );
        this.setLayoutY ( 100 );
//        this.setTranslateY ( 100 );
        this.setPrefHeight ( 600.0 );
        this.setPrefWidth ( 600.0 );
//
//
//        initializeGame ();
//
//        FileInputStream input = null;
//        try {
//            input = new FileInputStream ( new File ( "." ).getCanonicalPath () + File.separator + "src" + File.separator + "resources" + File.separator + "board.jpg" );
//        } catch (IOException e) {
//            e.printStackTrace ( );
//        }
//        assert input != null;
//        Image image = new Image (input);
//        ImageView boardImage = new ImageView ( image );
//        boardImage.setFitHeight ( 600.0 );
//        boardImage.setFitWidth ( 600.0 );
//        boardImage.setPreserveRatio ( true );
//
////        chessBoard = new ChessBoard (  );
//
////        centerPane.getChildren ().addAll ( boardImage , chessBoard ); felan image ndare


    }

    private void initializeGame () {

        inGame = true;
        board = new Tile[8][8];

        int tileNum = 8;

        for (int i = 0; i < tileNum; i++)
            for (int j = 0; j < tileNum; j++) {
                board[i][j] = new Tile ( i , j );
                board[i][j].resize ( tileWidth, tileHeight );
//                board[i][j].setPrefSize ( tileWidth, tileHeight );
//                chessBoard.add ( board[i][j],i,j );
            }
    }

    private BorderPane mainBorderPane;
    private final int BORDER_WIDTH = 600;
    private final int BORDER_HEIGHT = 600;
    private final int tileNum = 8;
    private final double tileWidth = (double) BORDER_WIDTH / tileNum;
    private final double tileHeight = (double) BORDER_HEIGHT / tileNum;
    private Tile[][] board;
}

class Tile extends Group {
    private final int positionX;
    private final int positionY;

    public Tile ( int x , int y ) {
        positionX = x;
        positionY = y;
        setOnMouseClicked ( e -> System.out.println ( positionX + " " + positionY ) );
    }
}