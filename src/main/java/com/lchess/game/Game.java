package com.lchess.game;

import com.lchess.engine.board.Board;
import com.lchess.engine.board.Position;
import com.lchess.engine.piece.view.PieceColorEnum;

/**
 * Created by liad on 01/09/2018.
 */
public interface Game {
    void startGame();
    void movePiece(Position origin, Position destination);
    PieceColorEnum getCurrentTurn();

}