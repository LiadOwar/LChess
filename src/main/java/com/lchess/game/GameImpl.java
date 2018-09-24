package com.lchess.game;

import com.lchess.engine.board.Board;
import com.lchess.engine.board.BoardManager;
import com.lchess.engine.board.Position;
import com.lchess.engine.board.Tile;
import com.lchess.engine.piece.model.PieceState;
import com.lchess.engine.piece.view.PieceColorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by liad on 01/09/2018.
 */
@Component
public class GameImpl implements Game {
    @Autowired
    private BoardManager boardManager;
    private static Board board;

    private PieceColorEnum turn;
    @Override
    public void startGame() {
        boardManager = BoardManager.getInstanse();
        boardManager.resetBoard();
        board = boardManager.getBoard();

        turn = PieceColorEnum.WHITE;
    }

    public GameImpl() {
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    @Override
    public void movePiece(Position origin, Position destination) {

        if (checkTurn(origin) == false){
            System.out.println(String.format("cannot move from [%s] to [%s] - wrong turn", origin, destination));
            return;
        }
        Boolean isCompleted = boardManager.tryMovePieceOnTheBoard(origin, destination);
        if (isCompleted){
            nextTurn();
        }
        else {
            System.out.println(String.format("cannot move from [%s] to [%s] - path not allow", origin, destination));
        }
    }

    private boolean checkTurn(Position origin) {
        Tile tileFromPosition = boardManager.getTileFromPosition(board, origin);
        PieceState pieceState = tileFromPosition.getPieceState();
        if (pieceState == null){
            return false;
        }
        PieceColorEnum color = pieceState.getColor();
        if (!color.equals(turn)){
            return false;
        }
        return true;
    }

    @Override
    public PieceColorEnum getCurrentTurn() {
        return turn;
    }

    private void nextTurn(){
        if (turn.equals(PieceColorEnum.WHITE)){
            turn = PieceColorEnum.BLACK;
        }
        else if (turn.equals(PieceColorEnum.BLACK)){
            turn = PieceColorEnum.WHITE;
        }
    }
}
