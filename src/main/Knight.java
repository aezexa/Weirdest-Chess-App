package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Knight extends Piece{

	private Image image;
	
	public Knight ( User user, int row, int column) {
		super(user, row, column);
		name = "Knight";
		if ( user == User.getWhiteUser () )
			image = new Image ( "/resources/White_Knight.png" );
		else
			image = new Image ( "/resources/Black_Knight.png" );
		imageView.setImage ( image );
		super.setImageProperty ( );
	}

	public Knight (Knight knight) {
		super(knight.owner , knight.row, knight.column);
		name = knight.name;
		image = knight.image;
		imageView.setImage ( image );
		super.setImageProperty ();
	}

	@Override
	public boolean canMove (int rowStart, int rowEnd, int columnStart, int columnEnd, ChessBoard.Tile[][] board) {
		return (Math.abs ( rowStart - rowEnd ) == 2 && Math.abs ( columnStart - columnEnd ) == 1)
				|| (Math.abs ( rowStart - rowEnd ) == 1 && Math.abs ( columnStart - columnEnd ) == 2);
	}

}
