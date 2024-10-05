package chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import mouseEvents.ChessEvents;
import pieces.*;
import typesChess.PieceType;
import typesChess.Positions;
import typesChess.TeamType;


public class Board extends JPanel{
	public int SIZE_SQUARES = 100, WIDTH, HEIGHT;
	private ChessEvents chessEvents;
	public ArrayList<Pieces> pieces = new ArrayList<>(32);
	public Pieces pieceSelected = null;
	public TeamType teamTurn = TeamType.BLACK;
	public ArrayList<Pieces> enemyPieces = new ArrayList<>(18);
	public TeamType kingInCheck = null;
	public LinkedList<Positions> tempPositions = new LinkedList<>();
	public int sizePosArr = 0;
	public Pieces pieceAttackingKing = null;
	public boolean pieceProtectKing = false;
	
	public Board(int WIDTH, int HEIGHT) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.chessEvents = new ChessEvents(this);
		init();
	}

	public void init() {
		this.setPreferredSize(new Dimension(HEIGHT, WIDTH));
		this.setVisible(true);
		this.setFocusable(true);
		this.addMouseListener(chessEvents);
		this.addMouseMotionListener(chessEvents);
		setPieces();
	}

	public void paint(Graphics board) {
		Color cell1 = new Color(255, 206, 159);
		Color cell2 = new Color(210, 139, 72);

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(i % 2 == 0) board.setColor(j % 2 == 0 ? cell1 : cell2);
				else board.setColor(j % 2 == 0 ? cell2 : cell1);

				board.fillRect(i*SIZE_SQUARES, j*SIZE_SQUARES, SIZE_SQUARES, SIZE_SQUARES);
			}
		}

		//draw the pieces on the board
		pieces.stream().forEach(piece -> piece.drawPiece(board));

		//invoke the function to draw the path of the piece selected and if it's its turn
		if(pieceSelected != null && pieceSelected.Team == teamTurn) {
			pieces.stream()
			.filter(piece -> piece.originAxisX == pieceSelected.originAxisX && piece.originAxisY == pieceSelected.originAxisY)
			.forEach(piece -> piece.drawPath(board));
		}

		repaint();
	}
	
	public void setPieces() {
		pieces.add(new Pawn(7,6, this.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, this, (byte) 1));
		pieces.add(new Pawn(6,6, this.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, this, (byte) 2));
		pieces.add(new Pawn(5,6, this.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, this, (byte) 3));
		pieces.add(new Pawn(4,6, this.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, this, (byte) 4));
		pieces.add(new Pawn(3,6, this.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, this, (byte) 5));
		pieces.add(new Pawn(2,6, this.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, this, (byte) 6));
		pieces.add(new Pawn(1,6, this.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, this, (byte) 7));
		pieces.add(new Pawn(0,6, this.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, this, (byte) 8));
		pieces.add(new Rook(7,7, this.SIZE_SQUARES, TeamType.WHITE, PieceType.ROOK, this, (byte) 1));
		pieces.add(new Rook(0,7, this.SIZE_SQUARES, TeamType.WHITE, PieceType.ROOK, this, (byte) 2));
		pieces.add(new Knight(6,7, this.SIZE_SQUARES, TeamType.WHITE, PieceType.KNIGHT, this, (byte) 1));
		pieces.add(new Knight(1,7, this.SIZE_SQUARES, TeamType.WHITE, PieceType.KNIGHT, this, (byte) 2));
		pieces.add(new Bishop(5,7, this.SIZE_SQUARES, TeamType.WHITE, PieceType.BISHOP, this, (byte) 1));
		pieces.add(new Bishop(2,7, this.SIZE_SQUARES, TeamType.WHITE, PieceType.BISHOP, this, (byte) 2));
		pieces.add(new King(3,7, this.SIZE_SQUARES, TeamType.WHITE, PieceType.KING, this, (byte) 1));
		pieces.add(new Queen(4,7, this.SIZE_SQUARES, TeamType.WHITE, PieceType.QUEEN, this, (byte) 1));

	
		pieces.add(new Pawn(7,1, this.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, this, (byte) 1));
		pieces.add(new Pawn(6,1, this.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, this, (byte) 2));
		pieces.add(new Pawn(5,1, this.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, this, (byte) 3));
		pieces.add(new Pawn(4,1, this.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, this, (byte) 4));
		pieces.add(new Pawn(3,1, this.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, this, (byte) 5));
		pieces.add(new Pawn(2,1, this.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, this, (byte) 6));
		pieces.add(new Pawn(1,1, this.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, this, (byte) 7));
		pieces.add(new Pawn(0,1, this.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, this, (byte) 8));
		pieces.add(new Rook(7,0, this.SIZE_SQUARES, TeamType.BLACK, PieceType.ROOK, this, (byte) 1));
		pieces.add(new Rook(0,0, this.SIZE_SQUARES, TeamType.BLACK, PieceType.ROOK, this, (byte) 2));
		pieces.add(new Knight(6,0, this.SIZE_SQUARES, TeamType.BLACK, PieceType.KNIGHT, this, (byte) 1));
		pieces.add(new Knight(1,0, this.SIZE_SQUARES, TeamType.BLACK, PieceType.KNIGHT, this, (byte) 2));
		pieces.add(new Bishop(5,0, this.SIZE_SQUARES, TeamType.BLACK, PieceType.BISHOP, this, (byte) 1));
		pieces.add(new Bishop(2,0, this.SIZE_SQUARES, TeamType.BLACK, PieceType.BISHOP, this, (byte) 2));
		pieces.add(new King(3,0, this.SIZE_SQUARES, TeamType.BLACK, PieceType.KING, this, (byte) 1));
		pieces.add(new Queen(4,0, this.SIZE_SQUARES, TeamType.BLACK, PieceType.QUEEN, this, (byte) 1));
	}

}
