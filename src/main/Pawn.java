package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pawn extends Piece {

	private Image image;
	int bigger;
	int smaller;

	public Pawn ( User user , int row , int column ) {
		super ( user , row , column );
		name = "Pawn";
		if ( user == User.getWhiteUser ( ) )
			image = new Image ( "/resources/White_Pawn.png" );
		else
			image = new Image ( "/resources/Black_Pawn.png" );
		imageView.setImage ( image );
		super.setImageProperty ( );
	}

	public Pawn ( Pawn pawn ) {
		super ( pawn.owner , pawn.row , pawn.column );
		name = pawn.name;
		image = pawn.image;
		imageView.setImage ( image );
		super.setImageProperty ( );
	}

	@Override
	public boolean canMove ( int rowStart , int rowEnd , int columnStart , int columnEnd , ChessBoard.Tile[][] board ) {

		//move restrictions
		if ( Math.abs ( rowEnd - rowStart ) > 2 || Math.abs ( columnEnd - columnStart ) > 1 || rowEnd == rowStart )
			return false;

		//the way it moves
		if ( isWhite ( ) ) {
			if ( rowEnd > rowStart )
				return false;
		} else if ( isBlack ( ) ) {
			if ( rowEnd < rowStart )
				return false;
		}

		if ( columnStart != columnEnd ) {
			//wants to hit
			if ( Math.abs ( rowEnd - rowStart ) == 2 )
				return false;
			if ( board[rowEnd][columnEnd].isEmpty ( ) )
				return false;
			return true;
		} else {
			//wants to move forward
			sorter ( rowStart , rowEnd );
			if ( isWhite ( ) ) {
				if ( rowStart == 6 && Math.abs ( rowEnd - rowStart ) == 2 ) { //two squares
					return board[rowStart - 1][columnStart].isEmpty ( ) &&
							board[rowStart - 2][columnStart].isEmpty ( );
				} else if ( Math.abs ( rowEnd - rowStart ) == 1 ) { //one square
					return board[rowStart - 1][columnStart].isEmpty ( );
				}
			} else if ( isBlack ( ) ) {
				if ( rowStart == 1 && Math.abs ( rowStart - rowEnd ) == 2 ) { //two squares
					return board[rowStart + 1][columnStart].isEmpty ( ) &&
							board[rowStart + 2][columnStart].isEmpty ( );

				} else if ( Math.abs ( rowStart - rowEnd ) == 1 ) { //one square
					return board[rowStart + 1][columnStart].isEmpty ( );

				}
			}
		}
		return false;
	}

	private void sorter ( int first , int second ) {
		if ( first > second ) {
			bigger = first;
			smaller = second;
		} else {
			smaller = first;
			bigger = second;
		}

	}

}