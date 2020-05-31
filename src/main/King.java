package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class King extends Piece {

	private Image image;

	public King ( User user , int row , int column ) {
		super ( user , row , column );
		name = "King";
		if ( user == User.getWhiteUser ( ) )
			image = new Image ( "/resources/White_King.png" );
		else
			image = new Image ( "/resources/Black_King.png" );
		imageView.setImage ( image );
		super.setImageProperty ( );
	}

	public King ( King king ) {
		super ( king.owner , king.row , king.column );
		name = king.name;
		image = king.image;
		imageView.setImage ( image );
		super.setImageProperty ( );
	}

	@Override
	public boolean canMove ( int rowStart , int rowEnd , int columnStart , int columnEnd , ChessBoard.Tile[][] board ) {
		return Math.abs ( rowEnd - rowStart ) <= 1 && Math.abs ( columnEnd - columnStart ) <= 1;
	}

}
