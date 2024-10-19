package pieces;

import chess.Board;
import typesChess.PieceType;
import typesChess.Positions;
import typesChess.TeamType;
import typesChess.TypeCell;

import java.util.LinkedList;

public class CheckKing {

	private Board board;

	public CheckKing(Board board){
		this.board = board;
	}

	private void ROOK(Pieces enemyPiece, Pieces KING, Pieces currentPiece){
		int rowKing = (enemyPiece.originAxisX - KING.axisX);
		int colKing = (enemyPiece.originAxisY - KING.axisY);

		int row = (enemyPiece.originAxisX - currentPiece.originAxisX);
		int col = (enemyPiece.originAxisY - currentPiece.originAxisY);

		// both, the current piece and the king are not in the same line, otherwise do not return
		if (!(row == 0 && rowKing == 0) && !(col == 0 && colKing == 0)) return;

		// check if there is another piece that can protect the king, for example, the rook wants to be moved and behind there's a pawn that can
		// protect the king
		var otherPiece = board.pieces.stream().filter(pieceBoard -> {
			//check also if the piece is between the king and the enemy piece
			if((row == 0 && rowKing == 0)) return (pieceBoard.axisX == currentPiece.originAxisX && pieceBoard.Team == currentPiece.Team) && (
				(pieceBoard.axisX > KING.axisX && pieceBoard.axisX < enemyPiece.axisX) || (pieceBoard.axisX < KING.axisX && pieceBoard.axisX > enemyPiece.axisX)) &&
				pieceBoard.Piece != PieceType.KING;
			else return (pieceBoard.axisY == currentPiece.originAxisY && pieceBoard.Team == currentPiece.Team)  && (
				(pieceBoard.axisY > KING.axisY && pieceBoard.axisY < enemyPiece.axisY) || (pieceBoard.axisY < KING.axisY && pieceBoard.axisY > enemyPiece.axisY)) &&
				pieceBoard.Piece != PieceType.KING;
		}).toList();

		if (otherPiece.size() > 0) return;

		// remove positions where the piece is not in the same column or row
		currentPiece.positions.removeIf(currentPiecePosition -> (row == 0 && rowKing == 0) ? currentPiecePosition.axisX != KING.axisX : currentPiecePosition.axisY != KING.axisY);
	}

	private void BISHOP(Pieces enemyPiece, Pieces KING, Pieces currentPiece){
		int diagonalXKing = Math.abs(enemyPiece.originAxisX - KING.axisX);
		int diagonalYKing = Math.abs(enemyPiece.originAxisY - KING.axisY);

		int diagonalX = Math.abs(enemyPiece.originAxisX - currentPiece.originAxisX);
		int diagonalY = Math.abs(enemyPiece.originAxisY - currentPiece.originAxisY);

		// check if the piece and king are not in the same diagonal
		if (!(diagonalX == diagonalY && diagonalXKing == diagonalYKing)) return;

		// check if there is another piece that can protect the king, for example, the rook wants to be moved and behind there's a pawn that can
		// protect the king
		var otherPiece = board.pieces.stream().filter(pieceBoard -> {
			int tempDiagonalX = Math.abs(pieceBoard.originAxisX - currentPiece.originAxisX);
			int tempDiagonalY = Math.abs(pieceBoard.originAxisY - currentPiece.originAxisY);

			int tempDiagonalXKing = Math.abs(pieceBoard.originAxisX - KING.axisX);
			int tempDiagonalYKing = Math.abs(pieceBoard.originAxisY - KING.axisY);

			//the piece is in the same row or column of the current piece and king and it's not the current piece and belongs to the same team and it's not the king
			boolean isInSameDiagonal = tempDiagonalY == tempDiagonalX && tempDiagonalXKing == tempDiagonalYKing && pieceBoard.Team == currentPiece.Team
			&& (pieceBoard.Piece != currentPiece.Piece || pieceBoard.PieceNumber != currentPiece.PieceNumber)
			&& pieceBoard.Piece != PieceType.KING;

			//check also if the piece is between the king and the enemy
			return isInSameDiagonal && (
			(pieceBoard.axisX > KING.axisX && pieceBoard.axisY > KING.axisY && pieceBoard.axisX < enemyPiece.axisX && pieceBoard.axisY < enemyPiece.axisY) ||
			(pieceBoard.axisX < KING.axisX && pieceBoard.axisY < KING.axisY && pieceBoard.axisX > enemyPiece.axisX && pieceBoard.axisY > enemyPiece.axisY) ||
			(pieceBoard.axisX < KING.axisX && pieceBoard.axisY > KING.axisY && pieceBoard.axisX > enemyPiece.axisX && pieceBoard.axisY < enemyPiece.axisY) || 
			(pieceBoard.axisX > KING.axisX && pieceBoard.axisY < KING.axisY && pieceBoard.axisX < enemyPiece.axisX && pieceBoard.axisY > enemyPiece.axisY));
		}).toList();

		if (otherPiece.size() > 0) return;

		currentPiece.positions.removeIf(position -> {
			int tempDiagonalX = Math.abs(position.axisX - enemyPiece.axisX);
			int tempDiagonalY = Math.abs(position.axisY - enemyPiece.axisY);
			int tempDiagonalXKing = Math.abs(position.axisX - KING.axisX);
			int tempDiagonalYKing = Math.abs(position.axisY - KING.axisY);

			if(position.axisX == enemyPiece.axisX && position.axisY == enemyPiece.axisY) return false;

			return !((tempDiagonalX == tempDiagonalY && tempDiagonalXKing == tempDiagonalYKing) && (
				(position.axisX > KING.axisX && position.axisY > KING.axisY && position.axisX < enemyPiece.axisX && position.axisY < enemyPiece.axisY)||
				(position.axisX < KING.axisX && position.axisY < KING.axisY && position.axisX > enemyPiece.axisX && position.axisY > enemyPiece.axisY)||
				(position.axisX < KING.axisX && position.axisY > KING.axisY && position.axisX > enemyPiece.axisX && position.axisY < enemyPiece.axisY)||
				(position.axisX > KING.axisX && position.axisY < KING.axisY && position.axisX < enemyPiece.axisX && position.axisY > enemyPiece.axisY)
			));
		});

	}

	private void QUEEN(Pieces enemyPiece, Pieces KING, Pieces currentPiece){
		int rowKing = (enemyPiece.originAxisX - KING.axisX);
		int colKing = (enemyPiece.originAxisY - KING.axisY);

		int row = (enemyPiece.originAxisX - currentPiece.originAxisX);
		int col = (enemyPiece.originAxisY - currentPiece.originAxisY);

		int diagonalXKing = Math.abs(enemyPiece.originAxisX - KING.axisX);
		int diagonalYKing = Math.abs(enemyPiece.originAxisY - KING.axisY);

		int diagonalX = Math.abs(enemyPiece.originAxisX - currentPiece.originAxisX);
		int diagonalY = Math.abs(enemyPiece.originAxisY - currentPiece.originAxisY);
	
		if (diagonalX == diagonalY && diagonalXKing == diagonalYKing) {
			System.out.println("diagonal " + currentPiece.positions.size());
			// check if there is another piece that can protect the king, for example, the rook wants to be moved and behind there's a pawn that can
			// protect the king
			var otherPiece = board.pieces.stream().filter(pieceBoard -> {
				int tempDiagonalX = Math.abs(pieceBoard.originAxisX - currentPiece.originAxisX);
				int tempDiagonalY = Math.abs(pieceBoard.originAxisY - currentPiece.originAxisY);
	
				int tempDiagonalXKing = Math.abs(pieceBoard.originAxisX - KING.axisX);
				int tempDiagonalYKing = Math.abs(pieceBoard.originAxisY - KING.axisY);
	
				//the piece is in the same row or column of the current piece and king and it's not the current piece and belongs to the same team and it's not the king
				boolean isInSameDiagonal = tempDiagonalY == tempDiagonalX && tempDiagonalXKing == tempDiagonalYKing && pieceBoard.Team == currentPiece.Team
				&& (pieceBoard.Piece != currentPiece.Piece || pieceBoard.PieceNumber != currentPiece.PieceNumber)
				&& pieceBoard.Piece != PieceType.KING;
	
				//check also if the piece is between the king and the enemy
				return isInSameDiagonal && (
				(pieceBoard.axisX > KING.axisX && pieceBoard.axisY > KING.axisY && pieceBoard.axisX < enemyPiece.axisX && pieceBoard.axisY < enemyPiece.axisY) ||
				(pieceBoard.axisX < KING.axisX && pieceBoard.axisY < KING.axisY && pieceBoard.axisX > enemyPiece.axisX && pieceBoard.axisY > enemyPiece.axisY) ||
				(pieceBoard.axisX < KING.axisX && pieceBoard.axisY > KING.axisY && pieceBoard.axisX > enemyPiece.axisX && pieceBoard.axisY < enemyPiece.axisY) || 
				(pieceBoard.axisX > KING.axisX && pieceBoard.axisY < KING.axisY && pieceBoard.axisX < enemyPiece.axisX && pieceBoard.axisY > enemyPiece.axisY));
			}).toList();

			if (otherPiece.size() > 0) return;

			currentPiece.positions.removeIf(position -> {
				int tempDiagonalX = Math.abs(position.axisX - enemyPiece.axisX);
				int tempDiagonalY = Math.abs(position.axisY - enemyPiece.axisY);
				int tempDiagonalXKing = Math.abs(position.axisX - KING.axisX);
				int tempDiagonalYKing = Math.abs(position.axisY - KING.axisY);
	
				if(position.axisX == enemyPiece.axisX && position.axisY == enemyPiece.axisY) return false;
	
				return !((tempDiagonalX == tempDiagonalY && tempDiagonalXKing == tempDiagonalYKing) && (
					(position.axisX > KING.axisX && position.axisY > KING.axisY && position.axisX < enemyPiece.axisX && position.axisY < enemyPiece.axisY)||
					(position.axisX < KING.axisX && position.axisY < KING.axisY && position.axisX > enemyPiece.axisX && position.axisY > enemyPiece.axisY)||
					(position.axisX < KING.axisX && position.axisY > KING.axisY && position.axisX > enemyPiece.axisX && position.axisY < enemyPiece.axisY)||
					(position.axisX > KING.axisX && position.axisY < KING.axisY && position.axisX < enemyPiece.axisX && position.axisY > enemyPiece.axisY)
				));
			});
		}else if((row == 0 && rowKing == 0) || (col == 0 && colKing == 0)){
			System.out.println("vertical" + " " + currentPiece.positions.size());
		// check if there is another piece that can protect the king, for example, the rook wants to be moved and behind there's a pawn that can
		// protect the king
		var otherPiece = board.pieces.stream().filter(pieceBoard -> {
			//check also if the piece is between the king and the enemy piece
			if((row == 0 && rowKing == 0)) return (pieceBoard.axisX == currentPiece.originAxisX && pieceBoard.Team == currentPiece.Team) && (
				(pieceBoard.axisX > KING.axisX && pieceBoard.axisX < enemyPiece.axisX) || (pieceBoard.axisX < KING.axisX && pieceBoard.axisX > enemyPiece.axisX)) &&
				pieceBoard.Piece != PieceType.KING;
			else return (pieceBoard.axisY == currentPiece.originAxisY && pieceBoard.Team == currentPiece.Team)  && (
				(pieceBoard.axisY > KING.axisY && pieceBoard.axisY < enemyPiece.axisY) || (pieceBoard.axisY < KING.axisY && pieceBoard.axisY > enemyPiece.axisY)) &&
				pieceBoard.Piece != PieceType.KING;
		}).toList();

		if (otherPiece.size() > 0) return;

		// remove positions where the piece is not in the same column or row
		currentPiece.positions.removeIf(currentPiecePosition -> (row == 0 && rowKing == 0) ? currentPiecePosition.axisX != KING.axisX : currentPiecePosition.axisY != KING.axisY);
		}
	}

	public void pieceCanMove(Pieces currentPiece) {
		Pieces KING = getKing(currentPiece.Team);

		board.pieces.forEach(enemyPiece -> {
			if (enemyPiece.Piece == PieceType.ROOK && enemyPiece.Team != currentPiece.Team) {
				ROOK(enemyPiece, KING, currentPiece);
			}
			
			if (enemyPiece.Piece == PieceType.BISHOP && enemyPiece.Team != currentPiece.Team) {
				BISHOP(enemyPiece, KING, currentPiece);
			}

			if (enemyPiece.Piece == PieceType.QUEEN && enemyPiece.Team != currentPiece.Team) {
				QUEEN(enemyPiece, KING, currentPiece);
			}
		});
	}

	private void positionUnderAttack(LinkedList<Positions> positions) {
		board.sizePosArr = positions.size();
		board.pieces.forEach(piece -> {
			//verify if the piece is in the enemy pieces list, which already were tested if they could attack the king
			var isInEnemyList = board.enemyPieces.stream().anyMatch(enemyPiece -> piece.Team == enemyPiece.Team
					&& piece.Piece == enemyPiece.Piece && piece.PieceNumber == enemyPiece.PieceNumber);
 
			if (piece.Team == board.teamTurn || isInEnemyList || piece.Piece == PieceType.KING) return;

			piece.getPath(true);
			positions.forEach(kingPosition -> {
				var isPositionKing = piece.positions.stream().anyMatch(position -> position.axisX == kingPosition.axisX && position.axisY == kingPosition.axisY);
				if (isPositionKing) board.sizePosArr--;
			});
			piece.positions.clear();
			board.enemyPieces.add(piece);
		});
	}
	
	private void checkPosition(LinkedList<Positions> positions,  Pieces KING) {
		board.pieces.stream().forEach(piece -> positions
				.removeIf(position -> piece.axisX == position.axisX && piece.axisY == position.axisY ? piece.Team == KING.Team : false));

		board.pieces.stream().forEach(piece -> positions.stream().forEach(position -> {
			if (piece.axisX == position.axisX && piece.axisY == position.axisY)
				position.setTypeCell(TypeCell.ENEMY);
		}));

		positionUnderAttack(positions);
		positions.removeIf(position -> board.tempPositions.stream().anyMatch(
				tempPosition -> tempPosition.axisX == position.axisX && tempPosition.axisY == position.axisY));
	}

	public void getPath(Pieces KING) {
		LinkedList<Positions> positions = new LinkedList<>();
		int x, y;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				x = Math.abs(row - KING.originAxisX / KING.SIZE_SQUARES);
				y = Math.abs(col - KING.originAxisY / KING.SIZE_SQUARES);
				if (x == 1 && y == 1 || x == 0 && y == 1 || x == 1 && y == 0)
					positions.add(new Positions(row * KING.SIZE_SQUARES, col * KING.SIZE_SQUARES, TypeCell.VOID));
			}
		}

		positions.removeIf(position -> position.axisX == KING.originAxisX && position.axisY == KING.originAxisY);
		checkPosition(positions, KING);
	}

	private Pieces getKing(TeamType team) {
		return board.pieces.stream().filter(piece -> piece.Piece == PieceType.KING && piece.Team == team).findFirst().get();
	}

	//this method will remove the positions from the selected piece where the enemy piece cannot be eaten
	private void deletePositions(Pieces currentPiece, Pieces enemyPiece){

		if(currentPiece.Piece == PieceType.BISHOP){
			if(currentPiece.originAxisX > enemyPiece.originAxisX && currentPiece.originAxisY > enemyPiece.originAxisY)
				currentPiece.positions.removeIf(posDel -> !(posDel.axisX > enemyPiece.axisX && posDel.axisY > enemyPiece.axisY));
			else if(currentPiece.originAxisX < enemyPiece.originAxisX && currentPiece.originAxisY < enemyPiece.originAxisY)
				currentPiece.positions.removeIf(posDel -> !(posDel.axisX < enemyPiece.axisX && posDel.axisY < enemyPiece.axisY));
			else if(currentPiece.originAxisX < enemyPiece.originAxisX && currentPiece.originAxisY > enemyPiece.originAxisY)
				currentPiece.positions.removeIf(posDel -> !(posDel.axisX < enemyPiece.axisX && posDel.axisY > enemyPiece.axisY));
			else if(currentPiece.originAxisX > enemyPiece.originAxisX && currentPiece.originAxisY < enemyPiece.originAxisY)
				currentPiece.positions.removeIf(posDel -> !(posDel.axisX > enemyPiece.axisX && posDel.axisY < enemyPiece.axisY));

		}else if(currentPiece.Piece == PieceType.KNIGHT){
			currentPiece.positions.removeIf(position -> position.axisX != enemyPiece.axisX && position.axisY != enemyPiece.axisY);

		}else if(currentPiece.Piece == PieceType.PAWN){
			currentPiece.positions.removeIf(position -> position.axisX != enemyPiece.axisX && position.axisY != enemyPiece.axisY);

		}else if(currentPiece.Piece == PieceType.QUEEN){
			//ROOK
			if(currentPiece.originAxisX > enemyPiece.axisX) currentPiece.positions.removeIf(posDel -> !(posDel.axisX > enemyPiece.axisX));
			else if(currentPiece.originAxisX < enemyPiece.axisX) currentPiece.positions.removeIf(posDel -> !(posDel.axisX < enemyPiece.axisX));
			else if(currentPiece.originAxisY > enemyPiece.axisY) currentPiece.positions.removeIf(posDel -> !(posDel.axisY > enemyPiece.axisY));
			else if(currentPiece.originAxisY < enemyPiece.axisY) currentPiece.positions.removeIf(posDel -> !(posDel.axisY < enemyPiece.axisY));
			//BISHOP
			if(currentPiece.originAxisX > enemyPiece.originAxisX && currentPiece.originAxisY > enemyPiece.originAxisY)
				currentPiece.positions.removeIf(posDel -> !(posDel.axisX > enemyPiece.axisX && posDel.axisY > enemyPiece.axisY));
			else if(currentPiece.originAxisX < enemyPiece.originAxisX && currentPiece.originAxisY < enemyPiece.originAxisY)
				currentPiece.positions.removeIf(posDel -> !(posDel.axisX < enemyPiece.axisX && posDel.axisY < enemyPiece.axisY));
			else if(currentPiece.originAxisX < enemyPiece.originAxisX && currentPiece.originAxisY > enemyPiece.originAxisY)
				currentPiece.positions.removeIf(posDel -> !(posDel.axisX < enemyPiece.axisX && posDel.axisY > enemyPiece.axisY));
			else if(currentPiece.originAxisX > enemyPiece.originAxisX && currentPiece.originAxisY < enemyPiece.originAxisY)
				currentPiece.positions.removeIf(posDel -> !(posDel.axisX > enemyPiece.axisX && posDel.axisY < enemyPiece.axisY));

		}else if(currentPiece.Piece == PieceType.ROOK){
			if(currentPiece.originAxisX > enemyPiece.axisX) currentPiece.positions.removeIf(posDel -> !(posDel.axisX > enemyPiece.axisX));
			else if(currentPiece.originAxisX < enemyPiece.axisX) currentPiece.positions.removeIf(posDel -> !(posDel.axisX < enemyPiece.axisX));
			else if(currentPiece.originAxisY > enemyPiece.axisY) currentPiece.positions.removeIf(posDel -> !(posDel.axisY > enemyPiece.axisY));
			else if(currentPiece.originAxisY < enemyPiece.axisY) currentPiece.positions.removeIf(posDel -> !(posDel.axisY < enemyPiece.axisY));
		}
	}

	public void canEatEnemy(Pieces currentPiece, Pieces enemy){
		currentPiece.positions.forEach(position -> {
			//can eat the enemy piece
			if((position.axisX == board.pieceAttackingKing.axisX && position.axisY == board.pieceAttackingKing.axisY))
				deletePositions(currentPiece, enemy);
		});
	}

	//if the king is found in check, then verify if the piece can be moved at the position where the enemy is attacking the king
	public void isKingInCheck(Pieces currentPiece){
		final Pieces enemy = board.pieceAttackingKing;
		final Pieces KING = getKing(currentPiece.Team);

		if(board.pieceAttackingKing.Piece == PieceType.BISHOP){
			currentPiece.positions.removeIf(position -> {
				int tempDiagonalX = Math.abs(position.axisX - enemy.axisX);
				int tempDiagonalY = Math.abs(position.axisY - enemy.axisY);
				int tempDiagonalXKing = Math.abs(position.axisX - KING.axisX);
				int tempDiagonalYKing = Math.abs(position.axisY - KING.axisY);

				if(position.axisX == enemy.axisX && position.axisY == enemy.axisY) return false;

				return !((tempDiagonalX == tempDiagonalY && tempDiagonalXKing == tempDiagonalYKing) && (
					(position.axisX > KING.axisX && position.axisY > KING.axisY && position.axisX < enemy.axisX && position.axisY < enemy.axisY)||
					(position.axisX < KING.axisX && position.axisY < KING.axisY && position.axisX > enemy.axisX && position.axisY > enemy.axisY)||
					(position.axisX < KING.axisX && position.axisY > KING.axisY && position.axisX > enemy.axisX && position.axisY < enemy.axisY)||
					(position.axisX > KING.axisX && position.axisY < KING.axisY && position.axisX < enemy.axisX && position.axisY > enemy.axisY)
				));
			});

			board.pieceProtectKing = currentPiece.positions.size() > 0;
		}else if(board.pieceAttackingKing.Piece == PieceType.KNIGHT){
			canEatEnemy(currentPiece, enemy);
			board.pieceProtectKing = currentPiece.positions.size() > 0;
		}else if(board.pieceAttackingKing.Piece == PieceType.PAWN){
			canEatEnemy(currentPiece, enemy);
			board.pieceProtectKing = currentPiece.positions.size() > 0;
		}else if(board.pieceAttackingKing.Piece == PieceType.ROOK){
			boolean rowKing = board.pieceAttackingKing.axisX == KING.axisX;
			boolean colKing = board.pieceAttackingKing.axisY == KING.axisY;

			currentPiece.positions.removeIf(position -> {
				if(position.axisX == enemy.axisX && position.axisY == enemy.axisY) return false;

				if(rowKing && enemy.axisY > KING.axisY)
					return (position.axisX != enemy.axisX) || position.axisX == enemy.axisX && enemy.axisY < KING.axisY;
				else if(rowKing && enemy.axisY < KING.axisY)
					return (position.axisX != enemy.axisX)  || position.axisX == enemy.axisX && enemy.axisY > KING.axisY;
				else if(colKing && enemy.axisX > KING.axisX)
					return (position.axisY != enemy.axisY) || position.axisY == enemy.axisY && enemy.axisX < KING.axisX;
				else if(colKing && enemy.axisX < KING.axisX) 
					return (position.axisY != enemy.axisY) || position.axisY == enemy.axisY && enemy.axisX > KING.axisX;

				return false;
			});

			board.pieceProtectKing = currentPiece.positions.size() > 0;
		}else if(board.pieceAttackingKing.Piece == PieceType.QUEEN){
			//BISHOP
			final int IS_IN_DIAGONAL_X_KING = Math.abs(enemy.originAxisX - KING.originAxisX);
			final int IS_IN_DIAGONAL_Y_KING = Math.abs(enemy.originAxisY - KING.originAxisY);
			if(IS_IN_DIAGONAL_X_KING == IS_IN_DIAGONAL_Y_KING){
				currentPiece.positions.removeIf(currentPiecePosition -> {
					int tempDiagonalX = Math.abs(currentPiecePosition.axisX - enemy.axisX);
					int tempDiagonalY = Math.abs(currentPiecePosition.axisY - enemy.axisY);
					int tempDiagonalXKing = Math.abs(currentPiecePosition.axisX - KING.axisX);
					int tempDiagonalYKing = Math.abs(currentPiecePosition.axisY - KING.axisY);
		
					if(currentPiecePosition.axisX == enemy.axisX && currentPiecePosition.axisY == enemy.axisY) return false;
		
					return !((tempDiagonalX == tempDiagonalY && tempDiagonalXKing == tempDiagonalYKing) && (
						(currentPiecePosition.axisX > KING.axisX && currentPiecePosition.axisY > KING.axisY && currentPiecePosition.axisX < enemy.axisX && currentPiecePosition.axisY < enemy.axisY)||
						(currentPiecePosition.axisX < KING.axisX && currentPiecePosition.axisY < KING.axisY && currentPiecePosition.axisX > enemy.axisX && currentPiecePosition.axisY > enemy.axisY)||
						(currentPiecePosition.axisX < KING.axisX && currentPiecePosition.axisY > KING.axisY && currentPiecePosition.axisX > enemy.axisX && currentPiecePosition.axisY < enemy.axisY)||
						(currentPiecePosition.axisX > KING.axisX && currentPiecePosition.axisY < KING.axisY && currentPiecePosition.axisX < enemy.axisX && currentPiecePosition.axisY > enemy.axisY)
					));
				});
				return;
			}
			//ROOK
			boolean rowKing = board.pieceAttackingKing.axisX == KING.axisX;
			boolean colKing = board.pieceAttackingKing.axisY == KING.axisY;
			
			currentPiece.positions.removeIf(position -> {
				if(position.axisX == enemy.axisX && position.axisY == enemy.axisY) return false;

				if(rowKing && enemy.axisY > KING.axisY)
					return (position.axisX != enemy.axisX) || position.axisX == enemy.axisX && enemy.axisY < KING.axisY;
				else if(rowKing && enemy.axisY < KING.axisY)
					return (position.axisX != enemy.axisX)  || position.axisX == enemy.axisX && enemy.axisY > KING.axisY;
				else if(colKing && enemy.axisX > KING.axisX)
					return (position.axisY != enemy.axisY) || position.axisY == enemy.axisY && enemy.axisX < KING.axisX;
				else if(colKing && enemy.axisX < KING.axisX) 
					return (position.axisY != enemy.axisY) || position.axisY == enemy.axisY && enemy.axisX > KING.axisX;

				return false;
			});

			board.pieceProtectKing = currentPiece.positions.size() > 0;
		}
	}

	public void pieceProtectKing() {
		board.pieces.forEach(piece -> {
			if(!board.pieceProtectKing) isKingInCheck(piece);
		});
	}
}
