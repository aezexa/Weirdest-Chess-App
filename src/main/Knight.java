package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Knight extends Piece{

//	private int xPos;
//	private int yPos;
//	private int type;
	private Image image;
//	private ImageView imageView = new ImageView(); 
	
	public Knight ( User user, int row, int column) {
		super(user, row, column);
		name = "Knight";
		// TODO Auto-generated constructor stub
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
	public ImageView getImageView () {
		return (imageView);
	}

	@Override
	public boolean canMove (int rowStart, int rowEnd, int columnStart, int columnEnd) {
		return (Math.abs ( rowStart - rowEnd ) == 2 && Math.abs ( columnStart - columnEnd ) == 1)
				|| (Math.abs ( rowStart - rowEnd ) == 1 && Math.abs ( columnStart - columnEnd ) == 2);
	}

	//	@Override
//	public void SelectPiece(ChessBoard chessBoard) {
//		int x = 0;
//		chessBoard.colorSquare(this.xPos, this.yPos, true);
//		if (chessBoard.checkState && !this.isASavior)
//			return;
//		if (gameLogic.verticalProtection(chessBoard, this.xPos, this.yPos, this.type) || gameLogic.horizontalProtection(chessBoard, this.xPos, this.yPos, this.type) ||
//			gameLogic.slashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type) || gameLogic.backslashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type))
//			return;
//		for (int y = -2; y <= 2; y++)
//		{
//			if (y != 0)
//			{
//				x = y % 2 == 0 ? 1 : 2;
//				if (this.yPos + y >= 0 && this.yPos + y < chessBoard.getBoardHeight() && this.xPos - x >= 0 && this.xPos - x < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(this.xPos - x, this.yPos + y) != this.type)
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, this.xPos - x, this.yPos + y, this.type))
//							chessBoard.colorSquare(this.xPos - x, this.yPos + y, false);
//					}
//					else
//						chessBoard.colorSquare(this.xPos - x, this.yPos + y, false);
//				}
//				if (this.yPos + y >= 0 && this.yPos + y < chessBoard.getBoardHeight() && this.xPos + x >= 0 && this.xPos + x < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(this.xPos + x, this.yPos + y) != this.type)
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, this.xPos + x, this.yPos + y, this.type))
//							chessBoard.colorSquare(this.xPos + x, this.yPos + y, false);
//					}
//					else
//						chessBoard.colorSquare(this.xPos + x, this.yPos + y, false);
//				}
//			}
//		}
//	}
}
