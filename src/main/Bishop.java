package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bishop extends Piece{

//	private int xPos;
//	private int yPos;
//	private int type;
	private Image image;
//	private ImageView imageView = new ImageView();

	public Bishop ( User user, int row, int column) {
		super(user, row, column);
		name = "Bishop";
//		this.type = type;
//		this.row = row;
//		this.column = column;
		// TODO Auto-generated constructor stub
		if ( user == User.getWhiteUser () )
			image = new Image ( "/resources/White_Bishop.png" );
		else
			image = new Image ( "/resources/Black_Bishop.png" );
		imageView.setImage ( image );
		super.setImageProperty ( );

	}

	public Bishop (Bishop bishop) {
		super(bishop.owner , bishop.row, bishop.column);
		name = bishop.name;
		image = bishop.image;
		imageView.setImage ( image );
		super.setImageProperty ();
	}
	
	@Override
	public ImageView getImageView () {
		return (imageView);
	}
	
//	@Override
//	public void SelectPiece(ChessBoard chessBoard) {
//		int y = this.yPos + 1;
//		chessBoard.colorSquare(this.xPos, this.yPos, true);
//		if (chessBoard.checkState && !this.isASavior)
//			return;
//		if (gameLogic.horizontalProtection(chessBoard, this.xPos, this.yPos, this.type) || gameLogic.verticalProtection(chessBoard, this.xPos, this.yPos, this.type))
//			return;
//		if (!gameLogic.slashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type))
//		{
//			for(int x = this.xPos + 1; x < chessBoard.getBoardWidth() && y < chessBoard.getBoardHeight(); x++, y++)
//			{
//				if (chessBoard.getBoardPosition(x, y) == 0)
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
//							chessBoard.colorSquare(x, y, false);
//					}
//					else
//						chessBoard.colorSquare(x, y, false);
//				}
//				else if (chessBoard.getBoardPosition(x, y) == this.type)
//					break;
//				else
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
//							chessBoard.colorSquare(x, y, false);
//					}
//					else
//						chessBoard.colorSquare(x, y, false);
//					break;
//				}
//			}
//			y = this.yPos - 1;
//			for(int x = this.xPos - 1; x >= 0 && y >= 0; x--, y--)
//			{
//				if (chessBoard.getBoardPosition(x, y) == 0)
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
//							chessBoard.colorSquare(x, y, false);
//					}
//					else
//						chessBoard.colorSquare(x, y, false);
//				}
//				else if (chessBoard.getBoardPosition(x, y) == this.type)
//					break;
//				else
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
//							chessBoard.colorSquare(x, y, false);
//					}
//					else
//						chessBoard.colorSquare(x, y, false);
//					break;
//				}
//			}
//		}
//		if (!gameLogic.backslashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type))
//		{
//			y = this.yPos + 1;
//			for (int x = this.xPos - 1; x >= 0 && y < chessBoard.getBoardHeight(); x--, y++)
//			{
//				if (chessBoard.getBoardPosition(x, y) == 0)
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
//							chessBoard.colorSquare(x, y, false);
//					}
//					else
//						chessBoard.colorSquare(x, y, false);
//				}
//				else if (chessBoard.getBoardPosition(x, y) == this.type)
//					break;
//				else
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
//							chessBoard.colorSquare(x, y, false);
//					}
//					else
//						chessBoard.colorSquare(x, y, false);
//					break;
//				}
//			}
//			y = this.yPos - 1;
//			for (int x = this.xPos + 1; x < chessBoard.getBoardWidth() && y >= 0; x++, y--)
//			{
//				if (chessBoard.getBoardPosition(x, y) == 0)
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
//							chessBoard.colorSquare(x, y, false);
//					}
//					else
//						chessBoard.colorSquare(x, y, false);
//				}
//				else if (chessBoard.getBoardPosition(x, y) == this.type)
//					break;
//				else
//				{
//					if (chessBoard.checkState)
//					{
//						if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
//							chessBoard.colorSquare(x, y, false);
//					}
//					else
//						chessBoard.colorSquare(x, y, false);
//					break;
//				}
//			}
//		}
//	}
}
