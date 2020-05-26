package main;

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
        this.setPrefHeight ( 600.0 );
        this.setPrefWidth ( 600.0 );

        initializeGame ();

    }

    private void initializeGame () {

        inGame = true;
        board = new Tile[8][8];

        int tileNum = 8;

        for (int i = 0; i < tileNum; i++)
            for (int j = 0; j < tileNum; j++) {
                board[i][j] = new Tile ( i , j );
//                board[i][j].resize ( tileWidth, tileHeight );
                getChildren ().add ( board[i][j] );
//                board[i][j].setPrefSize ( tileWidth, tileHeight );
//                chessBoard.add ( board[i][j],i,j );
            }

        setOnMouseClicked ( mouseEvent -> {
            selectedX = (int) (mouseEvent.getX ()/tileWidth);
            selectedY = (int) (mouseEvent.getY ()/tileHeight);
            System.out.println ( board[selectedX][selectedY].getRectangle () );
            unhighlightWindow ();
            board[selectedX][selectedY].highlightWindow ( Color.GREEN );
        } );


    }

    public void unhighlightWindow () {
        for (int y = 0; y < 7; y++)
        {
            for (int x = 0; x < 7; x++)
            {
                if (board[x][y].getRectangle().getStroke() != null)
                    board[x][y].unhighlightWindow ();
            }
        }
    }

    private BorderPane mainBorderPane;
    private final int BORDER_WIDTH = 600;
    private final int BORDER_HEIGHT = 600;
    private final int tileNum = 8;
    private final double tileWidth = (double) BORDER_WIDTH / tileNum;
    private final double tileHeight = (double) BORDER_HEIGHT / tileNum;
    private Tile[][] board;
    private int selectedX;
    private int selectedY;

    static class Tile extends Pane {
        private final int positionX;
        private final int positionY;
        private Rectangle rectangle;
        private Translate translate;
        private boolean isHighlighted = false;

        public void highlightWindow ( Color color ) {
//            this.setStyle ( "-fx-border-color: #8e09ff;" );
//            this.setBorder ( new Border(new BorderStroke(color,
//                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)) );

        rectangle.setStrokeType( StrokeType.INSIDE );
        rectangle.setStrokeWidth(4);
        rectangle.setStroke(color);
//        if (color == Color.GREEN)
//            isHighlighted = true;
        }

        public void unhighlightWindow () {
            rectangle.setStroke(null);
            isHighlighted = false;
        }

        public Rectangle getRectangle () {
            return rectangle;
        }

        public Tile ( int x , int y ) {
            positionX = x;
            positionY = y;
            rectangle = new Rectangle ( 75,75 );
            this.setHeight ( 75 );
            this.setWidth ( 75 );;
            this.setLayoutX ( x*75 );
            this.setLayoutY ( y*75 );
            rectangle.maxHeight ( 75 );
            rectangle.maxWidth ( 75 );
            rectangle.setFill ( Color.TRANSPARENT );
            rectangle.getTransforms ().add ( translate = new Translate (  ) );
            getChildren ().add ( rectangle );
        }
    }
}

