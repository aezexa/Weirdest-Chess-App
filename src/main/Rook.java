package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Rook extends Piece {

	private Image image;


	public Rook ( User user, int row, int column) {
		super(user, row, column);
		name = "Rook";
		if ( user == User.getWhiteUser () )
			image = new Image ( "/resources/White_Rook.png" );
		else
			image = new Image ( "/resources/Black_Rook.png" );
		imageView.setImage ( image );
		super.setImageProperty ( );
	}

	public Rook (Rook rook) {
		super(rook.owner , rook.row, rook.column);
		name = rook.name;
		image = rook.image;
		imageView.setImage ( image );
		super.setImageProperty ();
	}

	@Override
	public boolean canMove ( int rowStart , int rowEnd , int columnStart , int columnEnd , ChessBoard.Tile[][] board ) {
		return canMoveStraightforward ( rowStart, rowEnd, columnStart, columnEnd, board );
	}

}