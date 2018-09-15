package com.lchess.engine.piece.model;

import com.lchess.engine.board.Position;
import com.lchess.engine.piece.model.pojo.PieceMovementInfo;
import com.lchess.engine.piece.model.pojo.PieceMovementPath;
import com.lchess.engine.piece.view.PieceTypeEnum;

/**
 * Created by liad on 15/09/2018.
 */
public class KingModel extends PieceModel {

    @Override
    public PieceMovementInfo getPieceMovementInfo(Position origin, PieceState kingState) {
        PieceMovementInfo ret = new PieceMovementInfo();
        if (!kingState.getPieceType().equals(PieceTypeEnum.KING)){
            System.out.println(String.format("Error: kingMovementInfo was requested for [%s]",kingState.getClass().getSimpleName()));
            return null;
        }
        ret = getKingGeneralAllowableMoves(origin, (KingState) kingState);
        return ret;
    }

    private PieceMovementInfo getKingGeneralAllowableMoves(Position origin, KingState kingState) {
        PieceMovementInfo ret = new PieceMovementInfo();
        generateForwaredMove(origin, kingState, ret);
        generateBackMove(origin, kingState, ret);
        generateLeftMove(origin, kingState, ret);
        generateRightMove(origin, kingState, ret);

        generateUpLeftkMove(origin, kingState, ret);
        generateDownLeftkMove(origin, kingState, ret);
        generateUpRightMove(origin, kingState, ret);
        generateDownRightMove(origin, kingState, ret);
        return ret;
    }

    private void generateForwaredMove(Position origin, KingState kingState, PieceMovementInfo pieceMovementInfo){
        PieceMovementPath tempPath = new PieceMovementPath(origin);
        Position currentPosition;

        currentPosition  = PieceMovmentUtils.moveForward(origin, kingState.getColor(), 1);
        if (!pathManager.tryAddPositionToPath(tempPath, currentPosition)){
            return;
        }
        pieceMovementInfo.tryAddPath(tempPath);
    }

    private void generateLeftMove(Position origin, KingState kingState, PieceMovementInfo pieceMovementInfo){
        PieceMovementPath tempPath = new PieceMovementPath(origin);
        Position currentPosition;

        currentPosition  = PieceMovmentUtils.moveLeft(origin, kingState.getColor(), 1);
        if (!pathManager.tryAddPositionToPath(tempPath, currentPosition)){
            return;
        }
        pieceMovementInfo.tryAddPath(tempPath);
    }

    private void generateRightMove(Position origin, KingState kingState, PieceMovementInfo pieceMovementInfo){
        PieceMovementPath tempPath = new PieceMovementPath(origin);
        Position currentPosition;

        currentPosition  = PieceMovmentUtils.moveRight(origin, kingState.getColor(), 1);
        if (!pathManager.tryAddPositionToPath(tempPath, currentPosition)){
            return;
        }
        pieceMovementInfo.tryAddPath(tempPath);
    }

    private void generateBackMove(Position origin, KingState kingState, PieceMovementInfo pieceMovementInfo){
        PieceMovementPath tempPath = new PieceMovementPath(origin);
        Position currentPosition;

        currentPosition  = PieceMovmentUtils.moveBack(origin, kingState.getColor(), 1);
        if (!pathManager.tryAddPositionToPath(tempPath, currentPosition)){
            return;
        }
        pieceMovementInfo.tryAddPath(tempPath);
    }

    private void generateUpLeftkMove(Position origin, KingState kingState, PieceMovementInfo pieceMovementInfo){
        PieceMovementPath tempPath = new PieceMovementPath(origin);
        Position currentPosition;

        currentPosition  = PieceMovmentUtils.moveDiagonalUpLeft(origin, kingState.getColor(), 1);
        if (!pathManager.tryAddPositionToPath(tempPath, currentPosition)){
            return;
        }
        pieceMovementInfo.tryAddPath(tempPath);
    }

    private void generateUpRightMove(Position origin, KingState kingState, PieceMovementInfo pieceMovementInfo){
        PieceMovementPath tempPath = new PieceMovementPath(origin);
        Position currentPosition;

        currentPosition  = PieceMovmentUtils.moveDiagonalUpRight(origin, kingState.getColor(), 1);
        if (!pathManager.tryAddPositionToPath(tempPath, currentPosition)){
            return;
        }
        pieceMovementInfo.tryAddPath(tempPath);
    }

    private void generateDownLeftkMove(Position origin, KingState kingState, PieceMovementInfo pieceMovementInfo){
        PieceMovementPath tempPath = new PieceMovementPath(origin);
        Position currentPosition;

        currentPosition  = PieceMovmentUtils.moveDiagonalDownLeft(origin, kingState.getColor(), 1);
        if (!pathManager.tryAddPositionToPath(tempPath, currentPosition)){
            return;
        }
        pieceMovementInfo.tryAddPath(tempPath);
    }

    private void generateDownRightMove(Position origin, KingState kingState, PieceMovementInfo pieceMovementInfo){
        PieceMovementPath tempPath = new PieceMovementPath(origin);
        Position currentPosition;

        currentPosition  = PieceMovmentUtils.moveDiagonalDownRight(origin, kingState.getColor(), 1);
        if (!pathManager.tryAddPositionToPath(tempPath, currentPosition)){
            return;
        }
        pieceMovementInfo.tryAddPath(tempPath);
    }


}
