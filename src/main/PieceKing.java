package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PieceKing extends Piece{


//	private int xPos;
//	private int yPos;
//	private int type;
	private Image image;
//	private ImageView imageView = new ImageView();
	
	public PieceKing(int type, int xPos, int yPos) {
		super(type, xPos, yPos);
		name = "King";
		// TODO Auto-generated constructor stub
		
		if(type==1){
			image = new Image("file:src/ChessPiece/White_King.png");
			imageView.setImage(image);
			imageView.fitHeightProperty();
			imageView.fitWidthProperty();
	        imageView.setPreserveRatio(true);
	        imageView.setSmooth(true);
	        imageView.setCache(true);
		}
		else{
			image = new Image("file:src/ChessPiece/Black_King.png");
			imageView.setImage(image);
			imageView.fitHeightProperty();
			imageView.fitWidthProperty();
	        imageView.setPreserveRatio(true);
	        imageView.setSmooth(true);
	        imageView.setCache(true);
		}
	}
	
	@Override
	public ImageView getImage() {
		return (imageView);
	}
	
//	@Override
//	public void SelectPiece(ChessBoard chessBoard) {
//		int x = this.xPos;
//		int y = this.yPos;
//		chessBoard.colorSquare(this.xPos, this.yPos, true);
//		for (y = this.yPos - 1; y <= this.yPos + 1; y++)
//		{
//			for (x = this.xPos - 1; x <= this.xPos + 1; x++)
//			{
//				if(y >= 0 && y < chessBoard.getBoardHeight() && x >= 0 && x < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(x, y) != this.type)
//				{
//					if (!chessBoard.checkState)
//						this.canCastle(chessBoard);
//					// Check si echec et mat sur cette case
//					if (!gameLogic.isCheck(chessBoard, x, y, this.type, true))
//						chessBoard.colorSquare(x, y, false);
//				}
//			}
//		}
//		// Mouvement Roque (castling)
//		// cliquer sur l'autre pièce pour faire le roque
//		// use canCastle
//
//	}
	
	
	public int canCastle( ChessBruh chessBruh ){
		int canCastle =0;
		//Black 
		//shortCastle (5 and 6 empty) 
		if(type==2 && this.isFirstTime && chessBruh.getBoardPosition(5, 0) == 0 && chessBruh.getBoardPosition(6, 0) == 0 && chessBruh.getPiece(7, 0) != null && chessBruh.getPiece(7, 0).isFirstTime){
			canCastle = 1;
			chessBruh.colorSquare(7, 0, false);
		}
		//longCastle (1 2 3 empty)
		if(type==2 && this.isFirstTime && chessBruh.getBoardPosition(1, 0) == 0 && chessBruh.getBoardPosition(2, 0) == 0 && chessBruh.getBoardPosition(3, 0) == 0 && chessBruh.getPiece(0, 0) != null && chessBruh.getPiece(0, 0).isFirstTime){
			canCastle = 2;
			chessBruh.colorSquare(0, 0, false);
		}
		// White
		//shortCastle (5 and 6 empty) 
		if(type==1 && this.isFirstTime && chessBruh.getBoardPosition(5, 7) == 0 && chessBruh.getBoardPosition(6, 7) == 0 && chessBruh.getPiece(7, 7) != null && chessBruh.getPiece(7, 7).isFirstTime){
			canCastle = 3;
			chessBruh.colorSquare(7, 7, false);
		}
		//longCastle (1 2 3 empty)
		if(type==1 && this.isFirstTime && chessBruh.getBoardPosition(1, 7) == 0 && chessBruh.getBoardPosition(2, 7) == 0 && chessBruh.getBoardPosition(3, 7) == 0 && chessBruh.getPiece(0, 7) != null && chessBruh.getPiece(0, 7).isFirstTime){
			canCastle = 4;
			chessBruh.colorSquare(0, 7, false);
		}
		return canCastle; 
	}
}
