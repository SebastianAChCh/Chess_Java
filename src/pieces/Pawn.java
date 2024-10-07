package pieces;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import chess.Board;
import typesChess.PieceType;
import typesChess.Positions;
import typesChess.TeamType;
import typesChess.TypeCell;

public class Pawn extends Pieces {

	private CheckKing checkKing = null;

	public Pawn(int axisX, int axisY, int SIZE_SQUARES, TeamType Team, PieceType Piece, Board board, byte PieceNumber) {
		super(axisX, axisY, SIZE_SQUARES, Team, Piece, board, PieceNumber);
		this.setImage("pawn.gif");
	}

	@Override
	public void checkPosition(boolean isKingTesting) {
		positions.removeIf(position -> position.axisX == originAxisX && position.axisY == originAxisY);

		board.pieces.forEach(piece -> {
			List<Positions> toRemove = new ArrayList<>();
			
			positions.forEach(position -> {

                /* Check if the position has an allied piece that cannot be eaten.
                * or if the position is of type VOID, so as not to allow the current pawn to move over an allied piece.
                * to move over an allied piece.
                */
				if (position.axisX == piece.axisX && position.axisY == piece.axisY && position.typeCell == TypeCell.VOID && Team == TeamType.BLACK) {
					positions.stream()
						.filter(pos -> pos.axisX >= piece.axisX && pos.axisY >= piece.axisY && pos.typeCell == TypeCell.VOID)
						.forEach(toRemove::add);
				} else if (position.axisX == piece.axisX && position.axisY == piece.axisY && position.typeCell == TypeCell.VOID && Team == TeamType.WHITE) {
					positions.stream()
						.filter(pos -> pos.axisX <= piece.axisX && pos.axisY <= piece.axisY && pos.typeCell == TypeCell.VOID)
						.forEach(toRemove::add);
				}

				if (position.axisX == piece.axisX && position.axisY == piece.axisY && piece.Team == Team) {
					toRemove.add(position);
				}
			});

			positions.removeAll(toRemove);
		});


		if(!isKingTesting){
			// check if the piece is not attacking a enemy piece
			positions.removeIf(position -> board.pieces
					.stream()
					.noneMatch(piece -> piece.axisX == position.axisX && piece.axisY == position.axisY
							&& position.typeCell == TypeCell.ENEMY)
					&& position.typeCell == TypeCell.ENEMY);
		}
	}

	public void getPath(boolean isKingTesting) {
		if (!firstMove && this.Team == TeamType.BLACK) {
			positions.add(new Positions(originAxisX, ((originAxisY / SIZE_SQUARES) + 1) * SIZE_SQUARES, TypeCell.VOID));
			positions.add(new Positions(originAxisX, ((originAxisY / SIZE_SQUARES) + 2) * SIZE_SQUARES, TypeCell.VOID));

			// Positions to eat pieces
			positions.add(new Positions(((originAxisX / SIZE_SQUARES) - 1) * SIZE_SQUARES, ((originAxisY / SIZE_SQUARES) + 1) * SIZE_SQUARES, TypeCell.ENEMY));
			positions.add(new Positions(((originAxisX / SIZE_SQUARES) + 1) * SIZE_SQUARES, ((originAxisY / SIZE_SQUARES) + 1) * SIZE_SQUARES, TypeCell.ENEMY));
			checkPosition(isKingTesting);
		} else if (this.Team == TeamType.BLACK) {
			positions.add(new Positions(originAxisX, ((originAxisY / 100) + 1) * 100, TypeCell.VOID));

			// Positions to eat pieces
			positions.add(new Positions(((originAxisX / SIZE_SQUARES) - 1) * SIZE_SQUARES, ((originAxisY / SIZE_SQUARES) + 1) * SIZE_SQUARES, TypeCell.ENEMY));
			positions.add(new Positions(((originAxisX / SIZE_SQUARES) + 1) * SIZE_SQUARES, ((originAxisY / SIZE_SQUARES) + 1) * SIZE_SQUARES, TypeCell.ENEMY));
			checkPosition(isKingTesting);
		}

		if (!firstMove && this.Team == TeamType.WHITE) {
			positions.add(new Positions(originAxisX, ((originAxisY / SIZE_SQUARES) - 1) * SIZE_SQUARES, TypeCell.VOID));
			positions.add(new Positions(originAxisX, ((originAxisY / SIZE_SQUARES) - 2) * SIZE_SQUARES, TypeCell.VOID));

			// Positions to eat pieces
			positions.add(new Positions(((originAxisX / SIZE_SQUARES) - 1) * SIZE_SQUARES, ((originAxisY / SIZE_SQUARES) - 1) * SIZE_SQUARES, TypeCell.ENEMY));
			positions.add(new Positions(((originAxisX / SIZE_SQUARES) + 1) * SIZE_SQUARES, ((originAxisY / SIZE_SQUARES) - 1) * SIZE_SQUARES, TypeCell.ENEMY));
			checkPosition(isKingTesting);
		} else if (this.Team == TeamType.WHITE) {
			positions.add(new Positions(originAxisX, ((originAxisY / SIZE_SQUARES) - 1) * SIZE_SQUARES, TypeCell.VOID));
			// Positions to eat pieces
			positions.add(new Positions(((originAxisX / SIZE_SQUARES) - 1) * SIZE_SQUARES, ((originAxisY / SIZE_SQUARES) - 1) * SIZE_SQUARES, TypeCell.ENEMY));
			positions.add(new Positions(((originAxisX / SIZE_SQUARES) + 1) * SIZE_SQUARES, ((originAxisY / SIZE_SQUARES) - 1) * SIZE_SQUARES, TypeCell.ENEMY));

			checkPosition(isKingTesting);
		}
	}

	@Override
	public void drawPath(Graphics path) {
		if(positions.isEmpty()){
			getPath(false);
			checkKing = new CheckKing(board);
			checkKing.pieceCanMove(this);
			if(board.kingInCheck != null){
				checkKing.isKingInCheck(this);
			}
		}else if (!positions.isEmpty()) {
			positions.stream().forEach(position -> {
				path.setColor(position.typeCell == TypeCell.VOID ? PATH : ENEMY_PIECE);
				path.fillRect(position.axisX, position.axisY, SIZE_SQUARES, SIZE_SQUARES);
			});
		}
	}

}
