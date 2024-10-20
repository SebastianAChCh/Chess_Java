package pieces;

import java.util.LinkedList;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import chess.Board;
import chess.ResetBoard;
import typesChess.PieceType;
import typesChess.Positions;
import typesChess.TeamType;
import typesChess.TypeCell;

public abstract class Pieces {
	public TeamType Team;
	public PieceType Piece;
	private BufferedImage image = null;
	public int axisX, axisY, SIZE_SQUARES;
	public LinkedList<Positions> positions = new LinkedList<>();
	public int originAxisX, originAxisY;
	public final Color PATH = new Color(0, 255, 72, 100);
	public final Color ENEMY_PIECE = new Color(255, 0, 0, 100);
	public boolean firstMove = false;
	public Board board;
	public byte PieceNumber;// I.e, if the piece is a bishop, then which one, the first or second one

	public Pieces(int axisX, int axisY, int SIZE_SQUARES, TeamType Team, PieceType Piece, Board board, byte PieceNumber) {
		this.Piece = Piece;
		this.Team = Team;
		this.axisX = axisX * SIZE_SQUARES;
		this.axisY = axisY * SIZE_SQUARES;
		this.originAxisX = this.axisX;
		this.originAxisY = this.axisY;
		this.SIZE_SQUARES = SIZE_SQUARES;
		this.board = board;
		this.PieceNumber = PieceNumber;
	}

	private BufferedImage getImage(String imagePath) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(getClass().getResourceAsStream(imagePath));
		} catch (Exception e) {
			System.out.println(e);
		}

		return img;
	}

	public void setImage(String piece) {
		switch (Team) {
			case WHITE -> image = getImage("/sprites/White/" + piece);
			case BLACK -> image = getImage("/sprites/Black/" + piece);
		}
	}

	public void drawPiece(Graphics piece) {
		piece.drawImage(image, axisX, axisY, SIZE_SQUARES, SIZE_SQUARES, null);
	}

	public List isPositionValid(Positions coords) {
		return positions.stream().filter(coord -> coord.axisX == coords.axisX && coord.axisY == coords.axisY).toList();
	}

	private Pieces getKing(TeamType team) {
		return board.pieces.stream().filter(piece -> piece.Piece == PieceType.KING && piece.Team == team).findFirst().get();
	}

	private void check(int axisX, int axisY) {
		board.pieceSelected.axisX = axisX;
		board.pieceSelected.axisY = axisY;
		board.pieceSelected.originAxisX = axisX;
		board.pieceSelected.originAxisY = axisY;
		board.pieceSelected.getPath(false);

		Pieces KING = getKing(board.pieceSelected.Team == TeamType.WHITE ? TeamType.BLACK : TeamType.WHITE);

		var result = board.pieceSelected.positions.stream().filter(piece -> piece.axisX == KING.axisX && piece.axisY == KING.axisY).toList();

		if (result.size() > 0) {
			JOptionPane.showMessageDialog(null, "Check");
			board.kingInCheck = board.pieceSelected.Team == TeamType.WHITE ? TeamType.BLACK : TeamType.WHITE;
			board.pieceAttackingKing = board.pieceSelected;
		}
	}

	private void checkMate() {
		Pieces KING = getKing(board.kingInCheck);
		assert KING != null;
		CheckKing checkKing = new CheckKing(board);
		checkKing.getPath(KING);
		if(board.sizePosArr > 0) return;
		board.enemyPieces.clear();

		//check which positions where king will move are attacked by other pieces
		if(board.sizePosArr > 0) return;
		checkKing.pieceProtectKing();
		if(!board.pieceProtectKing){
			int input = JOptionPane.showConfirmDialog(null, "Checkmate");
			if(input >= 0){
				ResetBoard resetBoard = new ResetBoard(board);
				resetBoard.init();
			}
		}
	}

	public void movePiece(Positions coords) {
		var isValid = isPositionValid(coords);

		if (isValid.size() > 0) {
			eatPiece(coords);
			axisX = coords.axisX;
			axisY = coords.axisY;
			originAxisX = axisX;
			originAxisY = axisY;
			firstMove = true;
			board.teamTurn = board.teamTurn == TeamType.WHITE ? TeamType.BLACK : TeamType.WHITE; // change current team's turn

			if(board.kingInCheck != null){
				board.kingInCheck = null;
				board.pieceAttackingKing = null;
			}

			/* Check if the current piece is putting in check to the king enemy */
			check(axisX, axisY);
			if(board.kingInCheck != null) checkMate();
		} else {
			axisX = originAxisX;
			axisY = originAxisY;
		}

		positions.clear();
		board.enemyPieces.clear();
		board.tempPositions.clear();
	}

	private void eatPiece(Positions coords) {
		positions.forEach(position -> {
			if (position.axisX == coords.axisX && position.axisY == coords.axisY
					&& position.typeCell == TypeCell.ENEMY) {
				board.pieces.removeIf(piece -> piece.axisX == position.axisX && piece.axisY == position.axisY);
			}
		});
	}

	public abstract void getPath(boolean isKingTesting);

	public abstract void checkPosition(boolean isKingTesting);

	public abstract void drawPath(Graphics path);
}
