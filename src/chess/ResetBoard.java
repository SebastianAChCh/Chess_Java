package chess;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;
import typesChess.PieceType;
import typesChess.TeamType;

public class ResetBoard {
    private Board board;

    public ResetBoard(Board board){
        this.board = board;
    }

    public void init(){
        resetArrays();
        resetPieces();
        resetVariables();
    }

    public void resetArrays(){
        board.enemyPieces.clear();
        board.pieces.clear();
        board.tempPositions.clear();
    }

    public void resetVariables(){
        board.pieceAttackingKing = null;
        board.kingInCheck = null;
        board.pieceSelected = null;
        board.sizePosArr = 0;
        board.teamTurn = TeamType.BLACK;
        board.pieceProtectKing = false;
    }

    public void resetPieces(){
		board.pieces.add(new Pawn(7,6, board.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, board, (byte) 1));
		board.pieces.add(new Pawn(6,6, board.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, board, (byte) 2));
		board.pieces.add(new Pawn(5,6, board.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, board, (byte) 3));
		board.pieces.add(new Pawn(4,6, board.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, board, (byte) 4));
		board.pieces.add(new Pawn(3,6, board.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, board, (byte) 5));
		board.pieces.add(new Pawn(2,6, board.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, board, (byte) 6));
		board.pieces.add(new Pawn(1,6, board.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, board, (byte) 7));
		board.pieces.add(new Pawn(0,6, board.SIZE_SQUARES, TeamType.WHITE, PieceType.PAWN, board, (byte) 8));
		board.pieces.add(new Rook(7,7, board.SIZE_SQUARES, TeamType.WHITE, PieceType.ROOK, board, (byte) 1));
		board.pieces.add(new Rook(0,7, board.SIZE_SQUARES, TeamType.WHITE, PieceType.ROOK, board, (byte) 2));
		board.pieces.add(new Knight(6,7, board.SIZE_SQUARES, TeamType.WHITE, PieceType.KNIGHT, board, (byte) 1));
		board.pieces.add(new Knight(1,7, board.SIZE_SQUARES, TeamType.WHITE, PieceType.KNIGHT, board, (byte) 2));
		board.pieces.add(new Bishop(5,7, board.SIZE_SQUARES, TeamType.WHITE, PieceType.BISHOP, board, (byte) 1));
		board.pieces.add(new Bishop(2,7, board.SIZE_SQUARES, TeamType.WHITE, PieceType.BISHOP, board, (byte) 2));
		board.pieces.add(new King(3,7, board.SIZE_SQUARES, TeamType.WHITE, PieceType.KING, board, (byte) 1));
		board.pieces.add(new Queen(4,7, board.SIZE_SQUARES, TeamType.WHITE, PieceType.QUEEN, board, (byte) 1));

	
		board.pieces.add(new Pawn(7,1, board.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, board, (byte) 1));
		board.pieces.add(new Pawn(6,1, board.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, board, (byte) 2));
		board.pieces.add(new Pawn(5,1, board.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, board, (byte) 3));
		board.pieces.add(new Pawn(4,1, board.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, board, (byte) 4));
		board.pieces.add(new Pawn(3,1, board.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, board, (byte) 5));
		board.pieces.add(new Pawn(2,1, board.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, board, (byte) 6));
		board.pieces.add(new Pawn(1,1, board.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, board, (byte) 7));
		board.pieces.add(new Pawn(0,1, board.SIZE_SQUARES, TeamType.BLACK, PieceType.PAWN, board, (byte) 8));
		board.pieces.add(new Rook(3,4, board.SIZE_SQUARES, TeamType.BLACK, PieceType.ROOK, board, (byte) 1));
		board.pieces.add(new Rook(0,0, board.SIZE_SQUARES, TeamType.BLACK, PieceType.ROOK, board, (byte) 2));
		board.pieces.add(new Knight(6,0, board.SIZE_SQUARES, TeamType.BLACK, PieceType.KNIGHT, board, (byte) 1));
		board.pieces.add(new Knight(1,0, board.SIZE_SQUARES, TeamType.BLACK, PieceType.KNIGHT, board, (byte) 2));
		board.pieces.add(new Bishop(5,0, board.SIZE_SQUARES, TeamType.BLACK, PieceType.BISHOP, board, (byte) 1));
		board.pieces.add(new Bishop(2,0, board.SIZE_SQUARES, TeamType.BLACK, PieceType.BISHOP, board, (byte) 2));
		board.pieces.add(new King(3,0, board.SIZE_SQUARES, TeamType.BLACK, PieceType.KING, board, (byte) 1));
		board.pieces.add(new Queen(4,0, board.SIZE_SQUARES, TeamType.BLACK, PieceType.QUEEN, board, (byte) 1));

    }

}
