package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Queen extends Piece {
	
	private Image image;
	
	public Queen ( User user, int row, int column) {
		super(user, row, column);
		name = "Queen";
		if ( user == User.getWhiteUser () )
			image = new Image ( "/resources/White_Queen.png" );
		else
			image = new Image ( "/resources/Black_Queen.png" );
		imageView.setImage ( image );
		super.setImageProperty ( );
	}

	public Queen (Queen queen) {
		super(queen.owner , queen.row, queen.column);
		name = queen.name;
		image = queen.image;
		imageView.setImage ( image );
		super.setImageProperty ();
	}

	@Override
	public boolean canMove ( int rowStart , int rowEnd , int columnStart , int columnEnd , ChessBoard.Tile[][] board ) {
		return canMoveDiagonally ( rowStart, rowEnd, columnStart, columnEnd, board ) ||
				canMoveStraightforward ( rowStart, rowEnd, columnStart, columnEnd, board );
	}

}
