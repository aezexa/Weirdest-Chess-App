package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pawn extends Piece{

//	private int xPos;
//	private int yPos;
//	private int type;
	private Image image;
//	private ImageView imageView = new ImageView();
	int bigger;
	int smaller;
	boolean canBeEnPassanted;
	
	public Pawn ( User user, int row, int column) {
		super(user, row, column);
		name = "Pawn";
		// TODO Auto-generated constructor stub
		// TODO tester les paramètres 
		if ( user == User.getWhiteUser () )
			image = new Image ( "/resources/White_Pawn.png" );
		else
			image = new Image ( "/resources/Black_Pawn.png" );
		imageView.setImage ( image );
		super.setImageProperty ( );
	}

	public Pawn (Pawn pawn) {
		super(pawn.owner , pawn.row, pawn.column);
		name = pawn.name;
		image = pawn.image;
		imageView.setImage ( image );
		super.setImageProperty ();
	}
	
	@Override
	public ImageView getImageView () {
		return (imageView);
	}

	@Override
	public boolean canMove ( int rowStart , int rowEnd , int columnStart , int columnEnd, ChessBoard.Tile[][] board ) {
		boolean isFirst = isFirstTime;
		isFirstTime = false;

		//move restrictions
		if ( Math.abs ( rowEnd - rowStart ) > 2 || Math.abs ( columnEnd - columnStart ) > 1 || rowEnd == rowStart )
			return false;

		//the way it moves
		if ( isWhite () ) {
			if ( rowEnd > rowStart )
				return false;
		} else if ( isBlack () ) {
			if ( rowEnd < rowStart )
				return false;
		}

		if ( columnStart != columnEnd ) {
			//wants to hit
			if ( Math.abs ( rowEnd - rowStart ) == 2 )
				return false;
//			if (canEnPassant ()) { //en passant
//				return true;
//			}
			if (board[rowEnd][columnEnd].isEmpty ())
				return false;
			return true;
		} else {
			//wants to move forward
			sorter ( rowStart , rowEnd );
			if ( isWhite () ) {
				if ( rowStart == 6 && Math.abs ( rowEnd - rowStart ) == 2 ) { //two squares
					canBeEnPassanted = true;
					return board[rowStart - 1][columnStart].isEmpty () &&
							board[rowStart - 2][columnStart].isEmpty ();
				} else if ( Math.abs ( rowEnd - rowStart ) == 1 ) { //one square
					return board[rowStart - 1][columnStart].isEmpty ();
				}
			} else if ( isBlack () ) {
				if ( rowStart == 1 && Math.abs ( rowStart - rowEnd ) == 2 ) { //two squares
					canBeEnPassanted = true;
					return board[rowStart + 1][columnStart].isEmpty () &&
							board[rowStart + 2][columnStart].isEmpty ();

				} else if ( Math.abs ( rowStart - rowEnd ) == 1 ) { //one square
					return board[rowStart + 1][columnStart].isEmpty ();

				}
			}
		}
		return false;
	}

//	private boolean canEnPassant () {
//		return true;
//	}

	private void sorter ( int first , int second ) {
		if ( first > second ) {
			bigger = first;
			smaller = second;
		} else {
			smaller = first;
			bigger = second;
		}

	}

	//	@Override
//	public void SelectPiece(ChessBoard chessBoard) {
//		chessBoard.colorSquare(this.xPos, this.yPos, true);
//		if (chessBoard.checkState && !this.isASavior)
//			return;
//		if (gameLogic.horizontalProtection(chessBoard, this.xPos, this.yPos, this.type))
//			return;
//		if (this.type == 1)
//		{
//			if (!gameLogic.slashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type) && !gameLogic.backslashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type))
//			{
//				if (this.yPos - 1 >= 0 && chessBoard.getBoardPosition(this.xPos, this.yPos - 1) == 0)
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, this.xPos, this.yPos - 1, this.type))
//							chessBoard.colorSquare(this.xPos, this.yPos - 1, false);
//					}
//					else
//						chessBoard.colorSquare(this.xPos, this.yPos - 1, false);
//				}
//				if (this.isFirstTime == true && chessBoard.getBoardPosition(this.xPos, this.yPos - 2) == 0)
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, this.xPos, this.yPos - 2, this.type))
//							chessBoard.colorSquare(this.xPos, this.yPos - 2, false);
//					}
//					else
//						chessBoard.colorSquare(this.xPos, this.yPos - 2, false);
//				}
//			}
//			if (!gameLogic.verticalProtection(chessBoard, this.xPos, this.yPos, this.type))
//			{
//				if (!gameLogic.slashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type))
//				{
//					if (this.yPos - 1 >= 0 && this.xPos - 1 >= 0 && chessBoard.getBoardPosition(this.xPos - 1, this.yPos - 1) != this.type && chessBoard.getBoardPosition(this.xPos - 1, this.yPos - 1) != 0)
//					{
//						if (chessBoard.checkState)
//						{
//							if (gameLogic.isThisProtecting(chessBoard, this.xPos - 1, this.yPos - 1, this.type))
//								chessBoard.colorSquare(this.xPos - 1, this.yPos - 1, false);
//						}
//						else
//							chessBoard.colorSquare(this.xPos - 1, this.yPos - 1, false);
//					}
//				}
//				if (!gameLogic.backslashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type))
//				{
//					if (this.yPos - 1 >= 0 && this.xPos + 1 < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(this.xPos + 1, this.yPos - 1) != this.type && chessBoard.getBoardPosition(this.xPos + 1, this.yPos - 1) != 0)
//					{
//						if (chessBoard.checkState)
//						{
//							if (gameLogic.isThisProtecting(chessBoard, this.xPos + 1, this.yPos - 1, this.type))
//								chessBoard.colorSquare(this.xPos + 1, this.yPos - 1, false);
//						}
//						else
//							chessBoard.colorSquare(this.xPos + 1, this.yPos - 1, false);
//					}
//				}
//			}
//		}
//		else if (this.type == 2)
//		{
//			if (!gameLogic.slashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type) && !gameLogic.backslashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type))
//			{
//				if (this.yPos + 1 < chessBoard.getBoardHeight() && chessBoard.getBoardPosition(this.xPos, this.yPos + 1) == 0)
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, this.xPos, this.yPos + 1, this.type))
//							chessBoard.colorSquare(this.xPos, this.yPos + 1, false);
//					}
//					else
//						chessBoard.colorSquare(this.xPos, this.yPos + 1, false);
//				}
//				if (this.isFirstTime == true && chessBoard.getBoardPosition(this.xPos, this.yPos + 2) == 0)
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, this.xPos, this.yPos + 2, this.type))
//							chessBoard.colorSquare(this.xPos, this.yPos + 2, false);
//					}
//					else
//						chessBoard.colorSquare(this.xPos, this.yPos + 2, false);
//				}
//			}
//			if (!gameLogic.verticalProtection(chessBoard, this.xPos, this.yPos, this.type))
//			{
//				if (!gameLogic.backslashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type))
//				{
//					if (this.yPos + 1 < chessBoard.getBoardHeight() && this.xPos - 1 >= 0 && chessBoard.getBoardPosition(this.xPos - 1, this.yPos + 1) != this.type && chessBoard.getBoardPosition(this.xPos - 1, this.yPos + 1) != 0)
//					{
//						if (chessBoard.checkState)
//						{
//							if (gameLogic.isThisProtecting(chessBoard, this.xPos - 1, this.yPos + 1, this.type))
//								chessBoard.colorSquare(this.xPos - 1, this.yPos + 1, false);
//						}
//						else
//							chessBoard.colorSquare(this.xPos - 1, this.yPos + 1, false);
//					}
//				}
//				if (!gameLogic.slashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type))
//				{
//					if (this.yPos + 1 < chessBoard.getBoardHeight() && this.xPos + 1 < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(this.xPos + 1, this.yPos + 1) != this.type && chessBoard.getBoardPosition(this.xPos + 1, this.yPos + 1) != 0)
//					{
//						if (chessBoard.checkState)
//						{
//							if (gameLogic.isThisProtecting(chessBoard, this.xPos + 1, this.yPos + 1, this.type))
//								chessBoard.colorSquare(this.xPos + 1, this.yPos + 1, false);
//						}
//						else
//							chessBoard.colorSquare(this.xPos + 1, this.yPos + 1, false);
//					}
//				}
//			}
//		}
//	}
}
