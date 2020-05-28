package main;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;

import static main.App.*;
import static main.User.getWhiteUser;
import static main.User.getBlackUser;

public class ChessBoard extends Pane {

    private BorderPane mainBorderPane;
    private final int BORDER_WIDTH = 600;
    private final int BORDER_HEIGHT = 600;
    private final int tileNum = 8;
    public final double tileWidth = (double) BORDER_WIDTH / tileNum;
    public final double tileHeight = (double) BORDER_HEIGHT / tileNum;
    private Tile[][] board;
    private int endRow;
    private int endColumn;
    boolean isPieceSelected;
    private int startRow;
    private int startColumn;
    boolean hasMoved;
    boolean prevOppositeTurnOwnership;
    boolean hadKill;
    boolean gameOver;
    boolean isCheckMate;
    ChessBar chessBar;
    MoveBar moveBar;

    public ChessBoard (ChessBar chessBar, MoveBar moveBar) {
        this.setLayoutX ( 0 );
        this.setLayoutY ( 0 );
        this.setPrefHeight ( 600.0 );
        this.setPrefWidth ( 600.0 );
        this.chessBar = chessBar;
        this.moveBar = moveBar;
        initializeGame ();

    }

    private void initializeGame () {

        inGame = true;
        board = new Tile[8][8];

        User.setWhiteUser ( new User ( "Alireza", "1" ) );
        User.setBlackUser ( new User ( "Alireza's Enemy", "1" ) );
        User.getWhiteUser ().setTurn ( true );
        User.getBlackUser ().setTurn ( false );
        setFirstOwnership ( );

        chessBar.setTurn ( User.getWhiteUser ().getName () );


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
            board[1][i].setPiece ( new Pawn ( getBlackUser () , 1 , i) );
            board[6][i].setPiece ( new Pawn ( getWhiteUser () , 6 , i ) );
        }

        //Rooks
        board[0][0].setPiece ( new Rook ( getBlackUser () , 0 ,0 ) );
        board[0][7].setPiece ( new Rook ( getBlackUser () , 0 ,7 ) );
        board[7][0].setPiece ( new Rook ( getWhiteUser () , 7 ,0 ) );
        board[7][7].setPiece ( new Rook ( getWhiteUser () , 7 ,7 ) );

        //Knight
        board[0][1].setPiece ( new Knight ( getBlackUser () ,0,1 ) );
        board[0][6].setPiece ( new Knight ( getBlackUser (),0,6 ) );
        board[7][1].setPiece ( new Knight ( getWhiteUser (),7,1 ) );
        board[7][6].setPiece ( new Knight ( getWhiteUser (),7,6 ) );

        //Bishop
        board[0][2].setPiece ( new Bishop ( getBlackUser (),0,2 ) );
        board[0][5].setPiece ( new Bishop ( getBlackUser (),0,5 ) );
        board[7][2].setPiece ( new Bishop ( getWhiteUser (),7,2 ) );
        board[7][5].setPiece ( new Bishop ( getWhiteUser (),7,5 ) );

        //King
        board[0][3].setPiece ( new King ( getBlackUser (),0,3 ) );
        board[7][3].setPiece ( new King ( getWhiteUser (),7,3 ) );

        //Queen
        board[0][4].setPiece ( new Queen ( getBlackUser (),0,4 ) );
        board[7][4].setPiece ( new Queen ( getWhiteUser (),7,4 ) );

        for (int row = 0; row < 8; row++)
            for (int column = 0; column < 8; column++)
                if (row == 0 || row == 1 || row == 6 || row == 7)
                    getChildren ().add ( board[row][column].getPiece ().getImageView () );



//        movesUnlimited = (moveLimit == 0);
//        isSelected = false; //selected ints and undo variables are automatically handled in functions
//        hadKill = false;
//        hasMoved = false;
//        hasUsedUndo = false;
//        prevOppositeTurnOwnership = false;
//        allUsersMoveHistory.clear ( );
//        allUsersKillHistory.clear ( );
//        gameLeaveKey = false;
//        kingHasBeenHit = false;


//        Variables.whiteUser.setRemainingUndo ( 2 );
//        Variables.blackUser.setRemainingUndo ( 2 );

        mouseDragOption ();

    }

    private static void setFirstOwnership () {
        for (int row = 0; row <= 1; row++)
            for (int column = 0; column < 8; column++) {
                User.getWhiteUser ().setUserOwnsSquare ( 7 - row , column , true );
                User.getBlackUser ().setUserOwnsSquare ( row , column , true );
            }
        for (int row = 2; row < 8; row++)
            for (int column = 0; column < 8; column++) {
                User.getWhiteUser ().setUserOwnsSquare ( 7 - row , column , false );
                User.getBlackUser ().setUserOwnsSquare ( row , column , false );
            }
    }

    public void unhighlightTiles () {
        for (int row = 0; row < 8; row++)
        {
            for (int column = 0; column < 8; column++)
            {
                if (board[row][column].getRectangle().getStroke() != null)
                    board[row][column].unhighlightTile ();
            }
        }
    }

    public void selectPiece () {
        isPieceSelected = false;
        if ( !hasSelectErrors ( endRow , endColumn ) ) {
            if ( getTurnUser ( ).userOwnsSquare ( endRow , endColumn ) ) {
                isPieceSelected = true;
                startRow = endRow;
                startColumn = endColumn;
            }
        }
    }

    boolean hasSelectErrors ( int row , int column ) {

        if ( getOppositeTurnUser ( ).userOwnsSquare ( row , column ) ) {
            System.out.println ( "you can only select one of your pieces" );
            return true;
        } else if ( !getTurnUser ( ).userOwnsSquare ( row , column ) ) {
            System.out.println ( "no piece on this spot" );
            return true;
        }
        return false;
    }

    User getTurnUser () {
        if ( User.getWhiteUser ().isTurn ( ) )
            return User.getWhiteUser ();
        return User.getBlackUser ();
    }

    User getOppositeTurnUser () {
        if ( User.getWhiteUser ().isTurn ( ) )
            return User.getBlackUser ();
        return User.getWhiteUser ();
    }

    private void changeTurns () {
        if ( User.getWhiteUser ().isTurn ( ) ) {
            User.getWhiteUser ().setTurn ( false );
            User.getBlackUser ().setTurn ( true );
        } else {
            User.getWhiteUser ().setTurn ( true );
            User.getBlackUser ().setTurn ( false );
        }
        chessBar.setTurn ( getTurnUser ().getName () );
    }

    void moveActions () {

        //if we had a kill
        if ( getOppositeTurnUser ( ).userOwnsSquare ( endRow , endColumn ) ) {
            System.out.println ( "rival piece destroyed" );

            //setting booleans
            prevOppositeTurnOwnership = true;
            hadKill = true;

            //appending to history
            String moveString = getTurnUser ().getName () + "'s " + getStartTile ().getPiece ().name + " " +
                    getStartTile ().getName ( startRow, startColumn ) +
                    " to " +
                    getEndTile ().getName ( endRow , endColumn ) +
                    " destroyed " + getEndTile ().getPiece ().name;

            moveBar.setAreaString ( moveBar.getAreaString ( ) + "\n" + moveString );

            //if king had been hit
            if ( board[endRow][endColumn].getPiece () instanceof King ) {
                gameOver = true;
            }

        } else { //if we didn't have a kill
            System.out.println ( "moved" );

            //setting booleans
            prevOppositeTurnOwnership = false;
            hadKill = false;

            //appending to history
            String moveString = getTurnUser ().getName () + "'s " + getStartTile ().getPiece ().name + " " +
                    getStartTile ().getName ( startRow, startColumn ) +
                    " to " +
                    getEndTile ().getName ( endRow , endColumn );

            moveBar.setAreaString ( moveBar.getAreaString ( ) + "\n" + moveString );
        }

        //setting booleans
        hasMoved = true;

        //changing board
        getEndTile ().setPiece ( getStartTile ().getPiece () );
        getStartTile ().setPiece ( null );

        getEndTile ().highlightTile ( Color.BLACK );
        getEndTile ().getPiece ().getImageView ().setLayoutX ( 75* endColumn + 7.5 );
        getEndTile ().getPiece ().getImageView ().setLayoutY ( 75* endRow + 7.5 );

        //change ownership
        changeOwnershipOfTile ( );
    }

    private boolean hasMoveErrors ( ) {
        if ( !getStartTile ().getPiece ().canMove ( startRow , endRow , startColumn , endColumn ) || !isDifferentColor () ) {
            System.out.println ( "cannot move to the spot" );
            return true;
        }
        return false;
    }

    boolean isDifferentColor () {
        if (getEndTile ().getPiece () == null)
            return true;
        return getStartTile ().getPiece ().getOwner () != getEndTile ().getPiece ().getOwner ();
    }

    private void changeOwnershipOfTile ( ) {
        getTurnUser ( ).setUserOwnsSquare ( startRow , startColumn , false );
        getTurnUser ( ).setUserOwnsSquare ( endRow , endColumn , true );
        getOppositeTurnUser ( ).setUserOwnsSquare ( endRow , endColumn , false );
    }

    void acceptDrag() {
        moveActions ();
        changeTurns ();
    }

    void declineDrag() {
        getStartTile ().getPiece ().getImageView ().setLayoutX ( 75* startColumn + 7.5 );
        getStartTile ().getPiece ().getImageView ().setLayoutY ( 75* startRow + 7.5 );
    }

    void beginDrag() {
        setOnDragDetected( mouseEvent -> {
            startFullDrag();
            System.out.println ( "Event on Source: drag detected + " + endRow + " " + endColumn );
            startColumn = (int) (mouseEvent.getX ()/tileWidth);
            startRow = (int) (mouseEvent.getY ()/tileHeight);
        } );

        setOnMouseDragged( mouseEvent -> {
            endColumn = (int) (mouseEvent.getX ()/tileWidth);
            endRow = (int) (mouseEvent.getY ()/tileHeight);
            System.out.println ( "Event on Source: mouse dragged + " + endRow + " " + endColumn );
            mouseEvent.setDragDetect(false);
            if (isPieceHere ()) {
                getStartTile ().getPiece ( ).getImageView ( ).setLayoutX ( mouseEvent.getX ( ) - 30 );
                getStartTile ().getPiece ( ).getImageView ( ).setLayoutY ( mouseEvent.getY ( ) - 30 );
            }
        } );

        setOnMouseReleased( mouseEvent -> {
            endColumn = (int) (mouseEvent.getX ()/tileWidth);
            endRow = (int) (mouseEvent.getY ()/tileHeight);
            setMouseTransparent(false);
            System.out.println ( "Event on Source: mouse released + " + endRow + " " + endColumn );
            System.out.println ( "\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014" );
            unhighlightTiles ();
            if (isPieceHere ()) {
                if ( isPieceSelected && !isSameTile ( ) && !hasMoveErrors ( ))
                    acceptDrag ( );
                else
                    declineDrag ( );
            }
        } );
    }

    boolean isSameTile () {
        return endColumn == startColumn && endRow == startRow;
    }

    boolean isPieceHere () {
        return getStartTile ().getPiece () != null;
    }

    void mouseDragOption () {

        setOnMousePressed ( mouseEvent -> {

            setMouseTransparent(true);
            System.out.println ( "Event on Source: mouse pressed" );

            endColumn = (int) (mouseEvent.getX ()/tileWidth);
            endRow = (int) (mouseEvent.getY ()/tileHeight);
            unhighlightTiles ();
            getEndTile ().highlightTile ( Color.BLACK );
            System.out.println ( "selected row : " + endRow );
            System.out.println ( "selected column : " + endColumn );
            System.out.println ( "selected piece : " + getEndTile ().getPiece () );
            selectPiece ();

            if (isPieceSelected) {
                System.out.println ( "selected row to move : " + startRow );
                System.out.println ( "selected column to move : " + startColumn );
            }
            mouseEvent.setDragDetect ( true );
        } );

        if (getEndTile ().getPiece () != null)
            beginDrag ();

    }

    void mouseClickOption () {
        setOnMouseClicked ( mouseEvent -> {

            endColumn = (int) (mouseEvent.getX ()/tileWidth);
            endRow = (int) (mouseEvent.getY ()/tileHeight);
            unhighlightTiles ();
            getEndTile ().highlightTile ( Color.BLACK );
            System.out.println ( "selected row : " + endRow );
            System.out.println ( "selected column : " + endColumn );
            System.out.println ( "selected piece : " + getEndTile ().getPiece () );
            selectPiece ();

            if (isPieceSelected) {
                System.out.println ( "selected row to move : " + startRow );
                System.out.println ( "selected column to move : " + startColumn );
            }

        } );
        System.out.println ( "\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014" );

    }

    Tile getStartTile () {
        return board[startRow][startColumn];
    }

    Tile getEndTile () {
        return board[endRow][endColumn];
    }





    static class Tile extends Pane {

        private final int rowPosition;
        private final int columnPosition;
        private Rectangle rectangle;
        private Translate translate;
        private boolean isHighlighted = false;
        private final int tileLength = 75;
        private Piece piece;



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

        public void highlightTile ( Color color ) {
            rectangle.setStrokeType( StrokeType.INSIDE );
            rectangle.setStrokeWidth(3);
            rectangle.setStroke(color);
            DropShadow ds = new DropShadow ( 20 , Color.AQUA );
            if (piece != null) {
                piece.getImageView ( ).requestFocus ( );
                piece.getImageView ( ).setEffect ( ds );
            }

        if (color == Color.GREEN)
            isHighlighted = true;
        }

        public void unhighlightTile () {
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

        public String getName (int row, int column) {
            String rowString = String.valueOf ( 8 - row );
            char columnString = (char) (column + 17);
            return rowString + columnString;
        }
    }


}

