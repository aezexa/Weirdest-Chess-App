package main;

import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private int limit;
    boolean hasMoved;
    boolean hasUsedUndo;
    boolean prevOppositeTurnOwnership;
    boolean hadKill;
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
        limit = GameMenuController.limit;

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
        limit = chessBoard.limit;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Tile ( chessBoard.board[i][j] );
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

        chessBar.setTurn ( whiteUser.getName () );


        int tileNum = 8;

        for (int i = 0; i < tileNum; i++)
            for (int j = 0; j < tileNum; j++) {
                board[i][j] = new Tile ( i , j );
                getChildren ().add ( board[i][j] );
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

//        getWhiteUser ().setRemainingUndo ( 2 );
//        getBlackUser ().setRemainingUndo ( 2 );

        states.add ( new ChessBoard ( this ) );

        mouseDragOption ();
//        mouseClickOption ();


    }

    public void reset (int stateNumber) {
        unhighlightTiles ();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                getChildren ().remove ( board[i][j] );
                if (board[i][j].getPiece () != null)
                    getChildren ().remove ( board[i][j].getPiece ().getImageView () );
                board[i][j] = states.get ( stateNumber ).board[i][j];
                if (board[i][j].getPiece () != null)
                    getChildren ().add ( board[i][j].getPiece ().getImageView () );
                getChildren ().add ( board[i][j] );
            }
        }
        int count = states.size () - stateNumber - 1;
        while (count != 0) {
            states.remove ( states.size ( ) - 1 );
            count--;
        }

        updateUndoHistory ( stateNumber );
        board[0][0].rectangle.setStroke ( Color.BLACK );

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
            isPieceSelected = true;
            startRow = endRow;
            startColumn = endColumn;
        }
    }

    boolean hasSelectErrors ( int row , int column ) {

        if ( board[row][column].getPiece () == null ) {
            return true;
        }
        if ( board[row][column].getPiece ().getOwner () == getOppositeTurnUser () ) {
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

            //setting booleans
            prevOppositeTurnOwnership = false;
            hadKill = false;

            //appending to history
            moveString = "(" + states.size () + ") " + getTurnUser ().getName () + " : " + getStartTile ().getPiece ().name + " " +
                    getStartTile ().getName ( startRow, startColumn ) +
                    " to " +
                    getEndTile ().getName ( endRow , endColumn );

            chessMove.play ();
        } else {

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
                kingDiedGameOver ();
            }

            getChildren ().remove ( getEndTile ().getPiece ().imageView );
            chessHit.play ();

        }

        history.add ( moveString );

        moveBar.setAreaString ( makeHistoryString () );

        //setting booleans
        hasMoved = true;

        //changing board
        getEndTile ().setPiece ( getStartTile ().getPiece () );
        getStartTile ().setPiece ( null );

        unhighlightTiles ();
        getEndTile ().highlightTile ( Color.BLACK , true );
        getEndTile ().getPiece ().getImageView ().setLayoutX ( 75* endColumn + 7.5 );
        getEndTile ().getPiece ().getImageView ().setLayoutY ( 75* endRow + 7.5 );

        getEndTile ().getPiece ().row = endRow;
        getEndTile ().getPiece ().column = endColumn;

        //change ownership
//        changeOwnershipOfTile ( );
    }

    private boolean hasMoveErrors ( ) {
        if ( !isDifferentColor (endRow,endColumn) || !getStartTile ().getPiece ().canMove ( startRow , endRow , startColumn , endColumn, board )) {
            return true;
        }
        return false;
    }

    boolean isDifferentColor (int i, int j) {
        if (board[i][j].getPiece () == null)
            return true;
        return getStartTile ().getPiece ().getOwner () != board[i][j].getPiece ().getOwner ();
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
            startColumn = (int) (mouseEvent.getX ()/tileWidth);
            startRow = (int) (mouseEvent.getY ()/tileHeight);

            unhighlightTiles ();

            board[startRow][startColumn].highlightTile ( Color.BLACK , true );

            if (!board[startRow][startColumn].isEmpty () && board[startRow][startColumn].getPiece ().getOwner () == getTurnUser ())
                highlightPossibleTiles ();

        } );

        setOnMouseDragged( mouseEvent -> {
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
            if (isPieceHere ()) {
                if ( isPieceSelected && !isSameTile ( ) && !hasMoveErrors ( )) {
                    acceptDrag ( );
                    limit--;
                    if (limit == 0)
                        limitOver ();
                }
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

            endColumn = (int) (mouseEvent.getX ()/tileWidth);
            endRow = (int) (mouseEvent.getY ()/tileHeight);

            selectPiece ();


            if (isPieceSelected) {
                chessMove.play ();
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
            getEndTile ().highlightTile ( Color.BLACK , true );
            selectPiece ();

            if (isPieceSelected) {
                chessMove.play ();
            }
            unhighlightTiles ();

            board[startRow][startColumn].highlightTile ( Color.BLACK , true );

            if (!board[startRow][startColumn].isEmpty ())
                highlightPossibleTiles ();
            startColumn = (int) (mouseEvent.getX ()/tileWidth);
            startRow = (int) (mouseEvent.getY ()/tileHeight);
        } );

    }

    void highlightPossibleTiles () {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isDifferentColor (i,j) && board[startRow][startColumn].getPiece ().canMove ( startRow, i, startColumn, j , board)) {
                    board[i][j].highlightTile ( Color.GREEN , false );
                }
            }
        }
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
        String message = user.getName () + " ran out of time! "+ getOppositeTurnUser ().getName ()+" won!";
        getOppositeTurnUser ().addWins ();
        getOppositeTurnUser ().addScore ( 3 );
        getTurnUser ().addLosses ();
        endGameScreen (message);

    }

    void limitOver () {
        timer.timeline.stop ();
        String message = "This is a draw!";
        getTurnUser ().addDraws ();
        getTurnUser ().addScore ( 1 );
        getOppositeTurnUser ().addDraws ();
        getOppositeTurnUser ().addScore ( 1 );
        endGameScreen ( message );
    }

    void kingDiedGameOver () {
        timer.timeline.stop ();
        String message = getOppositeTurnUser ().getName ()+"'s king died! " + getTurnUser ().getName () + " won!";
        getTurnUser ().addWins ();
        getTurnUser ().addScore ( 3 );
        getOppositeTurnUser ().addLosses ();
        endGameScreen ( message );
    }

    public void endGameScreen(String message) {
        AnchorPane gameOver = new AnchorPane (  );

        BoxBlur bb = new BoxBlur (  );
        bb.setWidth ( 5 );
        bb.setHeight ( 5 );
        bb.setIterations ( 3 );
        ImageView imageView = new ImageView ( new Image ( "/resources/gameOver.jpg" ,885,498,false,false) );
        imageView.setEffect ( bb );
        imageView.setPreserveRatio ( true );

        GridPane gridPane = new GridPane ();
        gridPane.setLayoutX ( 0 );
        gridPane.setLayoutY ( 350 );
        gridPane.setMinSize ( 885 , 102 );

        Text text = new Text ( message );
        text.setFont ( Font.font ( "Comic Sans MS" , 20 ) );
        text.setFill ( Color.WHITESMOKE );

        Text text1 = new Text ( "Wanna Play Again? :D" );
        text1.setFont ( Font.font ( "Comic Sans MS" , 20 ) );
        text1.setFill ( Color.WHITESMOKE );

        Button button = new Button ( "No" );
        button.setTextAlignment ( TextAlignment.CENTER );
        button.setDefaultButton ( true );

        ColumnConstraints columnConstraints = new ColumnConstraints (  );
        columnConstraints.setMinWidth ( 885 );

        gridPane.getColumnConstraints ().addAll ( columnConstraints );

        RowConstraints rowConstraints = new RowConstraints (  );
        rowConstraints.setPrefHeight ( 25 );

        RowConstraints rowConstraints1 = new RowConstraints (  );
        rowConstraints1.setPrefHeight ( 50 );

        gridPane.getRowConstraints ().addAll ( rowConstraints , rowConstraints, rowConstraints1 );

        gridPane.addColumn ( 0 , text, text1, button );
        GridPane.setHalignment ( text, HPos.CENTER );
        GridPane.setHalignment ( text1, HPos.CENTER );
        GridPane.setHalignment ( button, HPos.CENTER );

        gameOver.getChildren ().addAll ( imageView , gridPane );
        Stage registrationCompletedStage = new Stage (  );
        registrationCompletedStage.setTitle ( "Game Over!" );
        registrationCompletedStage.setScene ( new Scene ( gameOver , 885, 498 ) );
        registrationCompletedStage.initModality ( Modality.APPLICATION_MODAL );
        registrationCompletedStage.show ();

        registrationCompletedStage.setOnCloseRequest ( event -> {
            ChessControl.endGame ();
        } );

        button.setOnAction ( event -> {
            registrationCompletedStage.close ();
            ChessControl.endGame ();
        } );
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
            rectangle.maxHeight ( tileLength );
            rectangle.maxWidth ( tileLength );
            rectangle.setFill ( Color.TRANSPARENT );
            rectangle.getTransforms ().add ( translate = new Translate (  ) );
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

        public void highlightTile ( Color color , boolean haveShadow ) {

            rectangle.setStrokeType( StrokeType.INSIDE );
            rectangle.setStrokeWidth(3);
            rectangle.setStroke( color );

            if (haveShadow) {
                DropShadow ds = new DropShadow ( 20 , Color.AQUA );
                if ( piece != null ) {
                    piece.getImageView ( ).requestFocus ( );
                    piece.getImageView ( ).setEffect ( ds );
                }
            }
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

