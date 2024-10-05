package pieces;

import java.awt.Graphics;
import java.util.LinkedList;

import chess.Board;

import typesChess.PieceType;
import typesChess.Positions;
import typesChess.TeamType;
import typesChess.TypeCell;

public class Bishop extends Pieces {
	private final TypeCell ALLIED = TypeCell.ALLIED;
	private final TypeCell ENEMY = TypeCell.ENEMY;
	private CheckKing checkKing = null;

	public Bishop(int axisX, int axisY, int SIZE_SQUARES, TeamType Team, PieceType Piece, Board board, byte PieceNumber) {
		super(axisX, axisY, SIZE_SQUARES, Team, Piece, board, PieceNumber);
		this.setImage("bishop.gif");
	}
	
	public void removePositions(LinkedList<Positions> positionsToDelete, boolean isKingTesting) {
		final int x = originAxisX;
		final int y = originAxisY;

		//posToDel === PositionToDelet
		positionsToDelete.stream().forEach(posToDel -> {
			final int AxisX = posToDel.axisX;
			final int AxisY = posToDel.axisY;
			final TypeCell CELL = posToDel.typeCell;
			
			if (AxisX > x && AxisY > y && CELL == ALLIED) {
				if(!isKingTesting) positions.removeIf(position -> position.axisX > x && position.axisY > y && position.axisX >= AxisX);
				else positions.removeIf(position -> position.axisX > x && position.axisY > y && position.axisX > AxisX);
			}else if (AxisX > x && AxisY > y && CELL == ENEMY) {
				positions.removeIf(position -> position.axisX > x && position.axisY > y && position.axisX > AxisX);
				positions.forEach(position -> { if(position.axisX > x && position.axisY > y && position.axisX >= AxisX) position.setTypeCell(ENEMY); });
			}

			if (AxisX < x && AxisY < y && CELL == ALLIED) {
				if(!isKingTesting) positions.removeIf(position -> position.axisX < x && position.axisY < y && position.axisX <= AxisX);
				else positions.removeIf(position -> position.axisX < x && position.axisY < y && position.axisX < AxisX);
			}else if (AxisX < x && AxisY < y && CELL == ENEMY) {
				positions.removeIf(position -> position.axisX < x && position.axisY < y && position.axisX < AxisX);
				positions.forEach(position -> { if(position.axisX < x && position.axisY < y && position.axisX <= AxisX) position.setTypeCell(ENEMY); });
			}

			if (AxisX < x && AxisY > y && CELL == ALLIED) {
				if(!isKingTesting) positions.removeIf(position -> position.axisX < x && position.axisY > y && position.axisY >= AxisY);
				else positions.removeIf(position -> position.axisX < x && position.axisY > y && position.axisY > AxisY);
			}else if (AxisX < x && AxisY > y && CELL == ENEMY) {
				positions.removeIf(position -> position.axisX < x && position.axisY > y && position.axisY > AxisY);
				positions.forEach(position -> { if(position.axisX < x && position.axisY > y && position.axisY >= AxisY) position.setTypeCell(ENEMY); });
			}

			if (AxisX > x && AxisY < y && CELL == ALLIED) {
				if(!isKingTesting) positions.removeIf(position -> position.axisX > x && position.axisY < y && position.axisY <= AxisY);
				else positions.removeIf(position -> position.axisX > x && position.axisY < y && position.axisY < AxisY);
			}else if (AxisX > x && AxisY < y && CELL == ENEMY) {
				positions.removeIf(position -> position.axisX > x && position.axisY < y && position.axisY < AxisY);
				positions.forEach(position -> { if(position.axisX > x && position.axisY < y && position.axisY <= AxisY) position.setTypeCell(ENEMY); });
			}
		});	
	}

	@Override
	public void checkPosition(boolean isKingTesting) {
		LinkedList<Positions> posToDel = new LinkedList<>();

		positions.removeIf(position -> position.axisX == originAxisX && position.axisY == originAxisY);

		board.pieces.stream().forEach(piece -> {
			positions.stream().forEach(position -> {
				if (piece.axisX == position.axisX && piece.axisY == position.axisY) {
					posToDel.add(new Positions(position.axisX, position.axisY, piece.Team == Team ? ALLIED : ENEMY));
				}
			});
		});

		removePositions(posToDel, isKingTesting);
	}

	public void getPath(boolean isKingTesting){
		int diagonalX, diagonalY;

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				diagonalX = Math.abs(row - originAxisX / SIZE_SQUARES);
				diagonalY = Math.abs(col - originAxisY / SIZE_SQUARES);

				if (diagonalX == diagonalY && row != originAxisX && col != originAxisY) {
					positions.add(new Positions(row * SIZE_SQUARES, col * SIZE_SQUARES, TypeCell.VOID));
				}
			}
		}

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
		} else {
			positions.stream().forEach(position -> {
				path.setColor(position.typeCell == TypeCell.VOID ? PATH : ENEMY_PIECE);
				path.fillRect(position.axisX, position.axisY, SIZE_SQUARES, SIZE_SQUARES);
			});
		}

	}

}
