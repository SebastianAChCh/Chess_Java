package pieces;

import java.awt.Graphics;

import chess.Board;
import typesChess.PieceType;
import typesChess.Positions;
import typesChess.TeamType;
import typesChess.TypeCell;

public class Knight extends Pieces {

	private CheckKing checkKing = null;

	public Knight(int axisX, int axisY, int SIZE_SQUARES, TeamType Team, PieceType Piece, Board board, byte PieceNumber) {
		super(axisX, axisY, SIZE_SQUARES, Team, Piece, board, PieceNumber);
		this.setImage("knight.gif");
	}


	@Override
	public void checkPosition(boolean isKingTesting) {
		if(!isKingTesting){
			board.pieces.stream().forEach(piece -> positions
					.removeIf(position -> piece.axisX == position.axisX && piece.axisY == position.axisY ? piece.Team == Team : false));

			board.pieces.stream().forEach(piece -> positions.stream().forEach(position -> {
				if(piece.axisX == position.axisX && piece.axisY == position.axisY) position.setTypeCell(TypeCell.ENEMY);
			}));
		}
	}

	@Override
	public void getPath(boolean isKingTesting){
		int x, y;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				x = originAxisX / SIZE_SQUARES;
				y = originAxisY / SIZE_SQUARES;
				
				if ((row - x == 2 || row - x == -2) && (col - y == 1 || col - y == -1))
				positions.add(new Positions(row*SIZE_SQUARES, col * SIZE_SQUARES, TypeCell.VOID));
				
				if ((row - x == 1 || row - x == -1) && (col - y == 2 || col - y == -2))
				positions.add(new Positions(row*SIZE_SQUARES, col * SIZE_SQUARES, TypeCell.VOID));
			}
		}
		
		positions.removeIf(position -> position.axisX == originAxisX && position.axisY == originAxisY);
		checkPosition(isKingTesting);
	}

	@Override
	public void drawPath(Graphics path) {
		if (positions.isEmpty()) {
			getPath(false);
			checkKing = new CheckKing(board);
			checkKing.pieceCanMove(this);
			if(board.kingInCheck != null){
				checkKing.isKingInCheck(this);
			}
		}else {
			positions.forEach(position -> {
				path.setColor(position.typeCell == TypeCell.VOID ? PATH : ENEMY_PIECE);
				path.fillRect(position.axisX, position.axisY, SIZE_SQUARES, SIZE_SQUARES);
			});
		}
	}

}
