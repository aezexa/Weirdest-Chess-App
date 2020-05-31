package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bishop extends Piece {

	private Image image;

	public Bishop ( User user , int row , int column ) {
		super ( user , row , column );
		name = "Bishop";
		if ( user == User.getWhiteUser ( ) )
			image = new Image ( "/resources/White_Bishop.png" );
		else
			image = new Image ( "/resources/Black_Bishop.png" );
		imageView.setImage ( image );
		super.setImageProperty ( );

	}

	public Bishop ( Bishop bishop ) {
		super ( bishop.owner , bishop.row , bishop.column );
		name = bishop.name;
		image = bishop.image;
		imageView.setImage ( image );
		super.setImageProperty ( );
	}

	@Override
	public boolean canMove ( int rowStart , int rowEnd , int columnStart , int columnEnd , ChessBoard.Tile[][] board ) {
		return canMoveDiagonally ( rowStart , rowEnd , columnStart , columnEnd , board );
	}

}