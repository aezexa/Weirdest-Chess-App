package main;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Translate;

public abstract class Piece extends Group{


	protected User owner;

	protected int row;
	protected int column;

	protected String name;

	protected ImageView imageView = new ImageView();

	protected boolean isFirstTime;

	protected boolean canMoveInCheck;

	// Diagonal elements
	private int biggerRow;
	private int biggerColumn;
	private int smallerRow;
	private int smallerColumn;

	// Straightforward elements
	private int bigger;
	private int smaller;

	public Piece(User owner, int row, int column) {
		this.owner = owner;
		this.row = row;
		this.column = column;
		isFirstTime = true;
		canMoveInCheck = false;
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

	public boolean canMove (int rowStart, int rowEnd, int columnStart, int columnEnd, ChessBoard.Tile[][] board) {
		return false;
	}

	public User getOwner () {
		return owner;
	}

	protected boolean isWhite () {
		return owner == User.getWhiteUser ();
	}

	protected boolean isBlack () {
		return owner == User.getBlackUser ();
	}

	protected boolean canMoveDiagonally ( int rowStart , int rowEnd , int columnStart , int columnEnd , ChessBoard.Tile[][] board ) {

		//move restrictions
		if ( rowStart == rowEnd || columnStart == columnEnd || Math.abs ( rowStart - rowEnd ) != Math.abs ( columnStart - columnEnd ) )
			return false;

		//see if the path is empty for moving
		if ( bishopSorterGetHarmonious ( rowStart , rowEnd , columnStart , columnEnd ) ) {
			for (int i = smallerRow + 1, j = smallerColumn + 1; i < biggerRow && j < biggerColumn; i++ , j++)
				if ( !board[i][j].isEmpty () )
					return false;
		} else {
			for (int i = smallerRow + 1, j = biggerColumn - 1; i < biggerRow && j > smallerColumn; i++ , j--)
				if ( !board[i][j].isEmpty () )
					return false;
		}
		return true;
	}

	private boolean bishopSorterGetHarmonious ( int firstRow , int secondRow , int firstColumn , int secondColumn ) {
		if ( firstRow > secondRow ) {
			biggerRow = firstRow;
			smallerRow = secondRow;
			if ( firstColumn > secondColumn ) {
				biggerColumn = firstColumn;
				smallerColumn = secondColumn;
				return true;
			} else {
				biggerColumn = secondColumn;
				smallerColumn = firstColumn;
				return false;
			}
		} else {
			biggerRow = secondRow;
			smallerRow = firstRow;
			if ( firstColumn > secondColumn ) {
				biggerColumn = firstColumn;
				smallerColumn = secondColumn;
				return false;
			} else {
				biggerColumn = secondColumn;
				smallerColumn = firstColumn;
				return true;
			}
		}
	}

	protected boolean canMoveStraightforward (  int rowStart , int rowEnd , int columnStart , int columnEnd , ChessBoard.Tile[][] board  ) {

		//move restrictions
		if ( rowEnd != rowStart && columnEnd != columnStart )
			return false;

		//see if the path is empty for moving
		if ( rowEnd == rowStart ) {
			sorter ( columnStart , columnEnd );
			for (int i = smaller + 1; i < bigger; i++)
				if ( !board[rowStart][i].isEmpty () )
					return false;
		} else {
			sorter ( rowStart , rowEnd );
			for (int i = smaller + 1; i < bigger; i++)
				if ( !board[i][columnStart].isEmpty () )
					return false;
		}
		return true;
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

	public ImageView getImageView () {
		return (imageView);
	}


}
