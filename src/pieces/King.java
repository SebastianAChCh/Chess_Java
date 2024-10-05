package pieces;

import java.awt.Graphics;

import chess.Board;

import typesChess.PieceType;
import typesChess.Positions;
import typesChess.TeamType;
import typesChess.TypeCell;

public class King extends Pieces {

	public King(int axisX, int axisY, int SIZE_SQUARES, TeamType Team, PieceType Piece, Board board, byte PieceNumber) {
		super(axisX, axisY, SIZE_SQUARES, Team, Piece, board, PieceNumber);
		this.setImage("King.gif");
	}

	public void positionUnderAttack(boolean isKingTesting) {
		board.pieces.forEach(piece -> {
			var isInEnemyList = board.enemyPieces.stream().anyMatch(enemyPiece -> piece.Team == enemyPiece.Team
					&& piece.Piece == enemyPiece.Piece && piece.PieceNumber == enemyPiece.PieceNumber);

			if (piece.Team == board.teamTurn || isInEnemyList || piece.Piece == PieceType.KING) return;
			
			piece.getPath(isKingTesting);
			positions.forEach(kingPosition -> {
				var isPositionKing = piece.positions.stream()
						.anyMatch(position -> position.axisX == kingPosition.axisX && position.axisY == kingPosition.axisY);
				if (isPositionKing) {
					board.tempPositions.add(new Positions(kingPosition.axisX, kingPosition.axisY, null));
				}
			});
			//delete the position, otherwise, if any enemy piece is moved it will be able to be moved over allied pieces
			piece.positions.clear();
			//now the piece has been tested, added to this list to avoid tested again
			board.enemyPieces.add(piece);
		});
	}

	@Override
	public void checkPosition(boolean isKingTesting /*LinkedList<Positions> positions,  Pieces KING*/) {

		board.pieces.stream().forEach(piece -> positions
				.removeIf(position -> piece.axisX == position.axisX && piece.axisY == position.axisY ? piece.Team == Team : false));

		board.pieces.stream().forEach(piece -> positions.stream().forEach(position -> {
			if (piece.axisX == position.axisX && piece.axisY == position.axisY)
				position.setTypeCell(TypeCell.ENEMY);
		}));

		positionUnderAttack(isKingTesting);
		positions.removeIf(position -> board.tempPositions.stream().anyMatch(
				tempPosition -> tempPosition.axisX == position.axisX && tempPosition.axisY == position.axisY));
	}

	public void getPath(boolean isKingTesting /*,Pieces KING */) {
		//LinkedList<Positions> positions = new LinkedList<>();
		int x, y;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				x = Math.abs(row - originAxisX / SIZE_SQUARES);
				y = Math.abs(col - originAxisY / SIZE_SQUARES);
				if (x == 1 && y == 1 || x == 0 && y == 1 || x == 1 && y == 0)
					positions.add(new Positions(row * SIZE_SQUARES, col * SIZE_SQUARES, TypeCell.VOID));
			}
		}

		positions.removeIf(position -> position.axisX == originAxisX && position.axisY == originAxisY);
		checkPosition(isKingTesting/**,positions, KING */);
	}

	@Override
	public void drawPath(Graphics path) {
		if (positions.isEmpty()) {
			getPath(true);
		} else {
			positions.stream().forEach(position -> {
				path.setColor(position.typeCell == TypeCell.VOID ? PATH : ENEMY_PIECE);
				path.fillRect(position.axisX, position.axisY, SIZE_SQUARES, SIZE_SQUARES);
			});
		}
	}

}
