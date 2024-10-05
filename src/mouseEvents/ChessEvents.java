package mouseEvents;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import chess.Board;
import typesChess.Positions;
import typesChess.TypeCell;

public class ChessEvents extends MouseAdapter{

	private Board board;
	private boolean pressed = false;
	private int x, y;
	
	
	public ChessEvents(Board board) {
		this.board = board;
	}

	@Override
	public void mousePressed(MouseEvent e) {		
		pressed = true;
		x = (e.getX()/100) * 100;
		y = (e.getY()/100) * 100;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(pressed) {
			for(int i = 0; i < board.pieces.size(); i++) {
				if(board.pieces.get(i).axisX== x && board.pieces.get(i).axisY== y) {
					board.pieceSelected = board.pieces.get(i);
					board.pieces.get(i).axisX = e.getX();
					board.pieces.get(i).axisY = e.getY();
					x = e.getX();
					y = e.getY();
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		for(int i = 0; i < board.pieces.size(); i++) {
			if(board.pieceSelected == null) break;
			if(board.pieces.get(i).originAxisX == board.pieceSelected.originAxisX && board.pieces.get(i).originAxisY == board.pieceSelected.originAxisY) {
				board.pieces.get(i).movePiece(new Positions((x/100)*100,(y/100)*100, TypeCell.VOID));
			}
		}
		
		board.pieceSelected = null;
	}
	
}
