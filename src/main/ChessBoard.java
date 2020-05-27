package main;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;

import static main.App.*;

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

        //0 means white
        for (int i = 0; i < 8; i++) {
            board[1][i].setPiece ( new Pawn (1 , 1 , i) );
            board[6][i].setPiece ( new Pawn ( 0 , 6 , i ) );
        }

        //Rooks
        board[0][0].setPiece ( new Rook ( 1 , 0 ,0 ) );
        board[0][7].setPiece ( new Rook ( 1 , 0 ,7 ) );
        board[7][0].setPiece ( new Rook ( 0 , 7 ,0 ) );
        board[7][7].setPiece ( new Rook ( 0 , 7 ,7 ) );

        //Knight
        board[0][1].setPiece ( new Knight ( 1 ,0,1 ) );
        board[0][6].setPiece ( new Knight ( 1,0,6 ) );
        board[7][1].setPiece ( new Knight ( 0,7,1 ) );
        board[7][6].setPiece ( new Knight ( 0,7,6 ) );

        //Bishop
        board[0][2].setPiece ( new Bishop ( 1,0,2 ) );
        board[0][5].setPiece ( new Bishop ( 1,0,5 ) );
        board[7][2].setPiece ( new Bishop ( 0,7,2 ) );
        board[7][5].setPiece ( new Bishop ( 0,7,5 ) );

        //King
        board[0][3].setPiece ( new King ( 1,0,3 ) );
        board[7][3].setPiece ( new King ( 0,7,3 ) );

        //Queen
        board[0][4].setPiece ( new Queen ( 1,0,4 ) );
        board[7][4].setPiece ( new Queen ( 0,7,4 ) );

        for (int row = 0; row < 8; row++)
            for (int column = 0; column < 8; column++)
                if (row == 0 || row == 1 || row == 6 || row == 7)
                    getChildren ().add ( board[row][column].getPiece ().getImageView () );

        setOnMouseClicked ( mouseEvent -> {
            selectedColumn = (int) (mouseEvent.getX ()/tileWidth);
            selectedRow = (int) (mouseEvent.getY ()/tileHeight);
            unhighlightTiles ();
            board[selectedRow][selectedColumn].highlightTile ( Color.BLACK );
        } );
    }

    public void unhighlightTiles () {
        for (int row = 0; row < 8; row++)
        {
            for (int column = 0; column < 8; column++)
            {
                if (board[row][column].getRectangle().getStroke() != null)
//                if (!board[column][row].getRectangle ().getStyle ().equals ( "-fx-border-color: transparent" ))
                    board[row][column].unhighlightTile ();
            }
        }
    }

    private BorderPane mainBorderPane;
    private final int BORDER_WIDTH = 600;
    private final int BORDER_HEIGHT = 600;
    private final int tileNum = 8;
    public final double tileWidth = (double) BORDER_WIDTH / tileNum;
    public final double tileHeight = (double) BORDER_HEIGHT / tileNum;
    private Tile[][] board;
    private int selectedColumn;
    private int selectedRow;

    static class Tile extends Pane {
        private final int rowPosition;
        private final int columnPosition;
        private Rectangle rectangle;
        private Translate translate;
        private boolean isHighlighted = false;
        private final int tileLength = 75;
        private Piece piece;

        public void highlightTile ( Color color ) {
//            this.setStyle ( "-fx-border-color: #8e09ff;-fx-border-radius: 10;-fx-border-width: 3" );

//            this.setBorder ( new Border(new BorderStroke(color,
//                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)) );

            rectangle.setStrokeType( StrokeType.INSIDE );
            rectangle.setStrokeWidth(3);
            rectangle.setStroke(color);
            DropShadow ds = new DropShadow ( 20 , Color.AQUA );
            if (piece != null) {
                piece.getImageView ( ).requestFocus ( );
                piece.getImageView ( ).setEffect ( ds );
            }

//
        if (color == Color.GREEN)
            isHighlighted = true;
        }

        public void unhighlightTile () {
//            this.setStyle ( "-fx-border-color: transparent" );
            rectangle.setStroke(null);
            if (piece != null)
                piece.getImageView ().setEffect ( null );
            isHighlighted = false;
        }

        public Rectangle getRectangle () {
            return rectangle;
        }

        public void setPiece ( Piece piece ) {
            this.piece = piece;
        }

        public Piece getPiece () {
            return piece;
        }

        public Tile ( int row, int column ) {
            columnPosition = column;
            rowPosition = row;
            rectangle = new Rectangle ( tileLength,tileLength );
            this.setHeight ( tileLength );
            this.setWidth ( tileLength );;
            this.setLayoutX ( column*tileLength );
            this.setLayoutY ( row*tileLength );
            rectangle.maxHeight ( tileLength );
            rectangle.maxWidth ( tileLength );
            rectangle.setFill ( Color.TRANSPARENT );
            rectangle.getTransforms ().add ( translate = new Translate (  ) );
            getChildren ().add ( rectangle );
        }
    }
}

