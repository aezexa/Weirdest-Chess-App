package main;

import javafx.animation.Timeline;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.App.*;
import static main.User.*;

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
    boolean hasUsedUndo;
    boolean prevOppositeTurnOwnership;
    boolean hadKill;
    boolean movesUnlimited;
    boolean gameOver;
    boolean isCheckMate;
    ChessBar chessBar;
    MoveBar moveBar;
    User whiteUser;
    User blackUser;
    public static ArrayList<ChessBoard> states;
    public ArrayList<String> history;
    Timer timer;

    public ChessBoard (ChessBar chessBar, MoveBar moveBar) {
        this.setLayoutX ( 0 );
        this.setLayoutY ( 0 );
        this.setPrefHeight ( 600.0 );
        this.setPrefWidth ( 600.0 );
        this.chessBar = chessBar;
        this.moveBar = moveBar;
        whiteUser = getWhiteUser ();
        blackUser = getBlackUser ();
        movesUnlimited = (GameMenuController.limit == 0);

        initializeGame ();
        
        timer = new Timer ( this );
        whiteUser.setTimer ( 3600 );
        blackUser.setTimer ( 3600 );
        timer.timeline.setCycleCount ( Timeline.INDEFINITE );
        timer.timeline.play ();
    }

    public ChessBoard (ChessBoard chessBoard) {
        board = new Tile[8][8];
        setWhiteUser ( chessBoard.whiteUser );
        setBlackUser ( chessBoard.blackUser );
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard.board[i][j] != null)
                    board[i][j] = new Tile ( chessBoard.board[i][j] );
                else
                    board[i][j] = new Tile ( i , j );
            }
        }
    }

    private void initializeGame () {

        inGame = true;
        board = new Tile[8][8];
        history = new ArrayList <> (  );
        states = new ArrayList <> (  );


        whiteUser.setTurn ( true );
        User.getBlackUser ().setTurn ( false );
//        setFirstOwnership ( );

        chessBar.setTurn ( whiteUser.getName () );


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

        //Images
        for (int row = 0; row < 8; row++)
            for (int column = 0; column < 8; column++)
                if (row == 0 || row == 1 || row == 6 || row == 7)
                    getChildren ().add ( board[row][column].getPiece ().getImageView () );

        getWhiteUser ().setRemainingUndo ( 2 );
        getBlackUser ().setRemainingUndo ( 2 );

        states.add ( new ChessBoard ( this ) );

        mouseDragOption ();



    }

    public void reset (int stateNumber) {
        unhighlightTiles ();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getPiece () != null)
                    getChildren ().remove ( board[i][j].getPiece ().getImageView () );
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = states.get ( stateNumber ).board[i][j];
                if (board[i][j].getPiece () != null)
                    getChildren ().add ( board[i][j].getPiece ().getImageView () );
            }
        }
        int count = states.size () - stateNumber - 1;
        while (count != 0) {
            states.remove ( states.size ( ) - 1 );
            count--;
        }

        updateUndoHistory ( stateNumber );


        whiteUser.setTurn ( states.size ()%2 != 0 );
        blackUser.setTurn ( states.size ()%2 == 0 );
        chessBar.setTurn ( getTurnUser ().getName () );
        moveBar.setAreaString ( makeHistoryString () );

    }

    private void updateUndoHistory (int stateNumber) {
        if (stateNumber == 0)
            while (history.size () > 0)
                history.remove ( history.size () -1 );
        else {
            while ( getHistoryStateNumber ( history.get ( history.size ( ) - 1 ) ) != stateNumber )
                history.remove ( history.size ( ) - 1 );
        }
    }

    private int getHistoryStateNumber (String string) {
        Matcher matcher = Pattern.compile ( "\\((.*)\\).*" ).matcher ( string );
        if ( matcher.matches () )
            return Integer.parseInt ( matcher.group ( 1 ) );
        return 0;
    }

//    private static void setFirstOwnership () {
//        for (int row = 0; row <= 1; row++)
//            for (int column = 0; column < 8; column++) {
//                whiteUser.setUserOwnsSquare ( 7 - row , column , true );
//                User.getBlackUser ().setUserOwnsSquare ( row , column , true );
//            }
//        for (int row = 2; row < 8; row++)
//            for (int column = 0; column < 8; column++) {
//                whiteUser.setUserOwnsSquare ( 7 - row , column , false );
//                User.getBlackUser ().setUserOwnsSquare ( row , column , false );
//            }
//    }

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
//            if ( board[endRow][endColumn].getPiece ().getOwner () == getWhiteUser () ) {
            isPieceSelected = true;
            startRow = endRow;
            startColumn = endColumn;
//            }
        }
    }

    boolean hasSelectErrors ( int row , int column ) {

        if ( board[row][column].getPiece () == null ) {
            System.out.println ( "no piece on this spot" );
            return true;
        }
        if ( board[row][column].getPiece ().getOwner () == getOppositeTurnUser () ) {
            System.out.println ( "you can only select one of your pieces" );
            return true;
        }
        return false;
    }

    User getTurnUser () {
        if ( whiteUser.isTurn ( ) )
            return whiteUser;
        return User.getBlackUser ();
    }

    User getOppositeTurnUser () {
        if ( whiteUser.isTurn ( ) )
            return User.getBlackUser ();
        return whiteUser;
    }

    private void changeTurns () {
        if ( whiteUser.isTurn ( ) ) {
            whiteUser.setTurn ( false );
            User.getBlackUser ().setTurn ( true );
        } else {
            whiteUser.setTurn ( true );
            User.getBlackUser ().setTurn ( false );
        }
        chessBar.setTurn ( getTurnUser ().getName () );
    }

    void moveActions () {

        //if we had a kill
        String moveString;

        if ( board[endRow][endColumn].getPiece () == null ) { //if we didn't have a kill
            System.out.println ( "moved" );

            //setting booleans
            prevOppositeTurnOwnership = false;
            hadKill = false;

            //appending to history
            moveString = "(" + states.size () + ") " + getTurnUser ().getName () + " : " + getStartTile ().getPiece ().name + " " +
                    getStartTile ().getName ( startRow, startColumn ) +
                    " to " +
                    getEndTile ().getName ( endRow , endColumn );
        } else {
            System.out.println ( "rival piece destroyed" );

            //setting booleans
            prevOppositeTurnOwnership = true;
            hadKill = true;

            //appending to history
            moveString = "(" + states.size () + ") " + getTurnUser ().getName () + " : " + getStartTile ().getPiece ().name + " " +
                    getStartTile ().getName ( startRow, startColumn ) +
                    " to " +
                    getEndTile ().getName ( endRow , endColumn ) +
                    " destroyed " + getEndTile ().getPiece ().name;

            //if king had been hit
            if ( board[endRow][endColumn].getPiece () instanceof King ) {
                gameOver = true;
            }

            getChildren ().remove ( getEndTile ().getPiece ().imageView );

        }

        history.add ( moveString );

        moveBar.setAreaString ( makeHistoryString () );

        //setting booleans
        hasMoved = true;

        //changing board
        getEndTile ().setPiece ( getStartTile ().getPiece () );
        getStartTile ().setPiece ( null );

        getEndTile ().highlightTile ( Color.BLACK );
        getEndTile ().getPiece ().getImageView ().setLayoutX ( 75* endColumn + 7.5 );
        getEndTile ().getPiece ().getImageView ().setLayoutY ( 75* endRow + 7.5 );

        getEndTile ().getPiece ().row = endRow;
        getEndTile ().getPiece ().column = endColumn;

        //change ownership
//        changeOwnershipOfTile ( );
    }

    private boolean hasMoveErrors ( ) {
        if ( !isDifferentColor () || !getStartTile ().getPiece ().canMove ( startRow , endRow , startColumn , endColumn, board )) {
            System.out.println ( "cannot move to the spot" );
            return true;
        }
        return false;
    }

//    private void undo () {
//        if ( !hasUndoErrors ( ) ) {
//            System.out.println ( "undo completed" );
//
//            //variables
//            hasUsedUndo = true;
//            hasMoved = false;
//            kingHasBeenHit = false;
//
//            //selected piece is undo-ed
//            if ( selectedRow == endRowBefore && selectedColumn == endColumnBefore ) {
//                selectedRow = startRowBefore;
//                selectedColumn = endColumnBefore;
//            }
//
//            getTurnUser ( ).reduceUndo ( ); //reduce Undo
//            undoOwnership ( endRowBefore , endColumnBefore , startRowBefore , startColumnBefore ); //undo ownership
//            board[startRowBefore - 1][startColumnBefore - 1] = startNameBefore; //change piece location
//            board[endRowBefore - 1][endColumnBefore - 1] = endNameBefore; //change piece location
//
//            //change move history
//            getTurnUser ( ).removeFromMoveHistory ( );
//            allUsersMoveHistory.remove ( allUsersMoveHistory.size ( ) - 1 );
//
//            //change kill history
//            if ( hadKill ) {
//                getOppositeTurnUser ( ).removeFromKillHistory ( );
//                allUsersKillHistory.remove ( allUsersKillHistory.size ( ) - 1 );
//            }
//        }
//    }

    boolean hasUndoErrors () {
        if ( getTurnUser ( ).getRemainingUndo ( ) == 0 ) {
            System.out.println ( "you cannot undo anymore" );
            return true;
        } else if ( !hasMoved ) {
            System.out.println ( "you must move before undo" );
            return true;
        } else if ( hasUsedUndo ) {
            System.out.println ( "you have used your undo for this turn" );
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

        moveActions ( );
        changeTurns ( );

        states.add ( new ChessBoard ( this ) );
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
//            System.out.println ( "Event on Source: mouse dragged + " + endRow + " " + endColumn );
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
            System.out.println ( "end row : " + endRow );
            System.out.println ( "end column : " + endColumn );
            System.out.println ( "end piece : " + getEndTile ().getPiece () );
            selectPiece ();

            if (isPieceSelected) {
                System.out.println ( "start row : " + startRow );
                System.out.println ( "start column : " + startColumn );
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

    String makeHistoryString () {
        StringBuilder stringBuilder = new StringBuilder (  );
        if (history.size () == 0)
            return "";
        stringBuilder.append ( history.get ( 0 ) );
        for (int i = 1; i < history.size ( ); i++) {
            stringBuilder.append ( "\n" ).append ( history.get ( i ) );
        }
        return stringBuilder.toString ();
    }

    void timerOver (User user) {
        timer.timeline.stop();
        if (user == getWhiteUser ())
        {
//            statusBar.whitePlayerAlert.setText("White player run out of time");
//            statusBar.winner.setText("Black player won !");
        }
        else if (user == getBlackUser ())
        {
//            statusBar.blackPlayerAlert.setText("Black player run out of time");
//            statusBar.winner.setText("White player won !");
        }
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

        public Tile (Tile tile) {
            columnPosition = tile.columnPosition;
            rowPosition = tile.rowPosition;
            this.setHeight ( tileLength );
            this.setWidth ( tileLength );
            rectangle = new Rectangle ( tileLength,tileLength );
//            rectangle = tile.rectangle;
            this.setLayoutX ( columnPosition*tileLength );
            this.setLayoutY ( rowPosition*tileLength );
            getChildren ().add ( rectangle );
            if (tile.getPiece () instanceof Pawn)
                this.setPiece ( new Pawn ( (Pawn) tile.getPiece () ) );
            if (tile.getPiece () instanceof King)
                this.setPiece ( new King ( (King) tile.getPiece () ) );
            if (tile.getPiece () instanceof Queen)
                this.setPiece ( new Queen ( (Queen) tile.getPiece () ) );
            if (tile.getPiece () instanceof Bishop)
                this.setPiece ( new Bishop ( (Bishop) tile.getPiece () ) );
            if (tile.getPiece () instanceof Knight)
                this.setPiece ( new Knight ( (Knight) tile.getPiece () ) );
            if (tile.getPiece () instanceof Rook)
                this.setPiece ( new Rook ( (Rook) tile.getPiece () ) );
        }

        public void highlightTile ( Color color ) {
            rectangle.setStrokeType( StrokeType.INSIDE );
            rectangle.setStrokeWidth(3);
            rectangle.setStroke(Color.TRANSPARENT);
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
            String columnString = Character.toString ((char) (column + 65));
            return columnString + rowString;
        }

        public boolean isEmpty () {
            return piece == null;
        }
    }

    public ChessBar getChessBar () {
        return chessBar;
    }
}

