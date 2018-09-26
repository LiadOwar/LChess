package com.lchess.engine.piece.model;

import com.lchess.engine.board.*;
import com.lchess.engine.piece.model.pojo.PieceMovementPath;

/**
 * Created by liad on 31/08/2018.
 */
public class PathManagerImpl implements PathManager {
    private static BoardManager boardManager = BoardManager.getInstanse();
    private static Board board;

    public PathManagerImpl() {
        board = boardManager.getBoard();
    }

    @Override
    public AddPositionToPathResult tryAddPositionToPath(PieceMovementPath pieceMovementPath, Position position) {
        AddPositionToPathResult result = new AddPositionToPathResult();
        if (BoardUtils.isOutOfBoard(position)) {
            result.setSuccess(false);
        }
        else {
            result = boardManager.checkIfCanMoveToPosition(pieceMovementPath, position);
            if (result.getSuccess()){
                pieceMovementPath.addPositionToPath(position);
            }
        }
        return result;

    }
}




