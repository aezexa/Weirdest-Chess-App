package main;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Translate;

//class declaration - abstract because we will not want to create a Piece object but we would
//like to specify the private fields that all pieces should have in addition to their behaviours
public abstract class Piece extends Group{

	// Piece can be either white (1) or black (2)
	protected int type;
	// Position of the piece on the board
	protected int row;
	protected int column;
	// Name of the piece
	protected String name;
	// ImageView
	protected ImageView imageView = new ImageView();
	// Position 
	protected Translate pos;
	// GameLogic
//	protected GameLogic gameLogic = new GameLogic();
	// True if it's the first time that the Piece is used.
	protected boolean isFirstTime;
	// Variable to know if the piece can move in a check situation
	protected boolean isASavior = false;
	
	public Piece(int type, int row, int column) {
		this.type = type;
		this.row = row;
		this.column = column;
		isFirstTime = true;
	}

	public void setImageProperty() {
		imageView.setFitHeight ( 60 );
		imageView.setFitWidth ( 60 );
		imageView.setLayoutX ( 75* column + 7.5 );
		imageView.setLayoutY ( 75* row + 7.5 );
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setCache(true);
	}
	
	// Select method: When a piece is selected by a first click
	// Highlight all the available position where the piece can go
	public void SelectPiece( ChessBruh board) {
	}
	
	// Move method: When a piece is already selected and that the player click on a highlighted position
	// Change the position of the piece and update the board
	public void MovePiece( ChessBruh chessBruh , int x, int y) {
		chessBruh.setBoard(this.row , this.column , 0);
		chessBruh.setPiece(this.row , this.column , null);
		if (!chessBruh.checkState && this.canCastle( chessBruh )!=0){
			if(this.canCastle( chessBruh )==1){
				chessBruh.setBoard(x-1, y, this.type);
				chessBruh.setPiece(x-1, y, this);
				this.row = x - 1;
				chessBruh.setBoard(5, y, chessBruh.getPiece(7, y).type);
				chessBruh.setPiece(5, y, chessBruh.getPiece(7, y));
				chessBruh.getPiece(7, y).row = 7;
				chessBruh.setBoard(7, y, 0);
				chessBruh.setPiece(7, y, null);
			}
			if(this.canCastle( chessBruh )==2){
				chessBruh.setBoard(x+2, y, this.type);
				chessBruh.setPiece(x+2, y, this);
				this.row = x + 2;
				chessBruh.setBoard(3, y, chessBruh.getPiece(0, y).type);
				chessBruh.setPiece(3, y, chessBruh.getPiece(0, y));
				chessBruh.getPiece(3, y).row = 3;
				chessBruh.setBoard(0, y, 0);
				chessBruh.setPiece(0, y, null);
			}
			if(this.canCastle( chessBruh )==3){
				chessBruh.setBoard(x-1, y, this.type);
				chessBruh.setPiece(x-1, y, this);
				this.row = x - 1;
				chessBruh.setBoard(5, y, chessBruh.getPiece(7, y).type);
				chessBruh.setPiece(5, y, chessBruh.getPiece(7, y));
				chessBruh.getPiece(5, y).row = 5;
				chessBruh.setBoard(7, y, 0);
				chessBruh.setPiece(7, y, null);
			}
			if(this.canCastle( chessBruh )==4){
				chessBruh.setBoard(x+2, y, this.type);
				chessBruh.setPiece(x+2, y, this);
				this.row = x + 2;
				chessBruh.setBoard(3, y, chessBruh.getPiece(0, y).type);
				chessBruh.setPiece(3, y, chessBruh.getPiece(0, y));
				chessBruh.getPiece(3, y).row = 3;
				chessBruh.setBoard(0, y, 0);
				chessBruh.setPiece(0, y, null);
			}
		}
		else{
			this.row = x;
			this.column = y;
			if ( chessBruh.getPiece(x, y) != null)
				chessBruh.getPiece(x, y).capture( chessBruh );
			chessBruh.setBoard(x, y, this.type);
			chessBruh.setPiece(x, y, this);
			if (this.name == "Pawn" && ((this.type == 1 && this.column == 0) || (this.type == 2 && this.column == 7)))
			{
				chessBruh.createPromotePiece(this);
				if (this.type == 1)
					chessBruh.playerOnePawn--;
				else
					chessBruh.playerTwoPawn--;
			}
		}
	}
	
	// Return the image of the piece
	public ImageView getImageView () {
		return (imageView);
	}
	
	public void centerImage() {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }
	
	// Capture method: When a piece is captured by another one
	public void capture( ChessBruh chessBruh ) {
		if (this.type == 1)
		{
			if (this.name == "Rook")
				chessBruh.playerOneRook--;
			else if (this.name == "Knight")
				chessBruh.playerOneKnight--;
			else if (this.name == "Queen")
				chessBruh.playerOneQueen--;
			else if (this.name == "Pawn")
				chessBruh.playerOnePawn--;
			else if (this.name == "Bishop" && (this.row + this.column) % 2 != 0)
				chessBruh.playerOneBishopDarkSquare--;
			else if (this.name == "Bishop" && (this.row + this.column) % 2 == 0)
				chessBruh.playerOneBishopLightSquare--;
		}
		else
		{
			if (this.name == "Rook")
				chessBruh.playerTwoRook--;
			else if (this.name == "Knight")
				chessBruh.playerTwoKnight--;
			else if (this.name == "Queen")
				chessBruh.playerTwoQueen--;
			else if (this.name == "Pawn")
				chessBruh.playerTwoPawn--;
			else if (this.name == "Bishop" && (this.row + this.column) % 2 == 0)
				chessBruh.playerTwoBishopLightSquare--;
			else if (this.name == "Bishop" && (this.row + this.column) % 2 != 0)
				chessBruh.playerTwoBishopDarkSquare--;
		}
		chessBruh.getChildren().remove(this.getImageView ());
	}
	
	public int canCastle( ChessBruh chessBruh ){
		return 0;
	}

	public void resize(double width, double height) {
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
	}

	// overridden version of the relocate method
	public void relocate(double x, double y) {
		imageView.setTranslateX(x);
		imageView.setTranslateY(y);	
		centerImage();
	}
	
	public void resetPiece()
	{
		this.isFirstTime = true;
		this.isASavior = false;
	}

	public boolean isFirstTime() {
		return isFirstTime;
	}

	public void setFirstTime(boolean isFirstTime) {
		this.isFirstTime = isFirstTime;
	}
	
	public int getX(){
		return this.row;
	}
	
	public int getY(){
		return this.column;
	}
	

}
