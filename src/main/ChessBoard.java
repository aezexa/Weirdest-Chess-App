package main;

import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;


import static main.App.inGame;

public class ChessBoard extends Pane {
    public ChessBoard (ChessBar chessBar) {
        this.setLayoutX ( 0 );
        this.setLayoutY ( 0 );
//        this.setTranslateY ( 50 );
        this.setPrefHeight ( 600.0 );
        this.setPrefWidth ( 600.0 );

        this.setBorder ( new Border(new BorderStroke(Color.BROWN,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)) );

        initializeGame ();

    }

    private void initializeGame () {

        inGame = true;
        board = new Tile[8][8];

        int tileNum = 8;

        for (int i = 0; i < tileNum; i++)
            for (int j = 0; j < tileNum; j++) {
                board[i][j] = new Tile ( i , j );
                board[i][j].resize ( tileWidth, tileHeight );
                getChildren ().add ( board[i][j] );
//                board[i][j].setPrefSize ( tileWidth, tileHeight );
//                chessBoard.add ( board[i][j],i,j );
            }

        setOnMouseClicked ( mouseEvent -> System.out.println ( mouseEvent.getX () + " " + mouseEvent.getY () ) );
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
    private Rectangle rectangle;
    private Translate translate;
    private boolean isHighlighted = false;

    public void highlightWindow ( Color color ) {
        rectangle.setStrokeType( StrokeType.INSIDE );
        rectangle.setStrokeWidth(4);
        rectangle.setStroke(color);
        if (color == Color.GREEN)
            isHighlighted = true;
    }

    public Tile ( int x , int y ) {
        positionX = x;
        positionY = y;
        rectangle = new Rectangle (  );
        rectangle.getTransforms ().add ( translate = new Translate (  ) );
        getChildren ().add ( rectangle );
        setOnMouseClicked ( e -> {
            System.out.println ( positionX + " " + positionY );
            highlightWindow ( Color.GREEN );
        } );
    }
}