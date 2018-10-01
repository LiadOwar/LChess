package com.lchess.engine.piece.model;

import com.lchess.engine.board.Position;
import com.lchess.engine.piece.model.pojo.PieceMovementInfo;
import com.lchess.engine.piece.model.pojo.PieceMovementPath;
import com.lchess.engine.piece.view.PieceTypeEnum;

/**
 * Created by liad on 01/09/2018.
 */
public class BishopModel extends PieceModel {
    @Override
    public PieceMovementInfo getPieceMovementInfo(Position origin, PieceState bishopState) {
        PieceMovementInfo ret = new PieceMovementInfo();
        if (!bishopState.getPieceType().equals(PieceTypeEnum.BISHOP)){
            System.out.println(String.format("Error: bishopMovementInfo was requested for [%s]",bishopState.getClass().getSimpleName()));
            return null;
        }
        ret = getBishopGeneralAllowableMoves(origin, (BishopState)bishopState);
        return ret;
    }

    private PieceMovementInfo getBishopGeneralAllowableMoves(Position origin, BishopState bishopState) {
        PieceMovementInfo ret = new PieceMovementInfo();
        generateDiagonalUpLeftPath(origin, bishopState, ret);
        generateDiagonalDownLeftPath(origin, bishopState, ret);
        generateDiagonalDownRightPath(origin, bishopState, ret);
        generateDiagonalUpRightPath(origin, bishopState, ret);
        return ret;
    }

    private void generateDiagonalUpLeftPath(Position origin, BishopState bishopState, PieceMovementInfo ret) {

        for (int i =0 ; i < 7; i++){
            PieceMovementPath tempPath = new PieceMovementPath(origin);
            char tempXpos = origin.getCoordinate().getxPos();
            int tempYpos = origin.getCoordinate().getyPos();
            Position currentPosition = new Position(tempXpos, tempYpos);
            AddPositionToPathResult result;
            for (int j = 0 ; j <= i ; j++){
                currentPosition  = PieceMovmentUtils.moveDiagonalUpLeft(currentPosition, bishopState.getColor(), 1);
                result = pathManager.tryAddPositionToPath(tempPath, currentPosition);
                if (!result.getSuccess()){
                    return;
                }
                if (result.getLastPositionOccupiedByEnemyPiece()){
                    ret.tryAddPath(tempPath);
                    return;
                }
            }
            ret.tryAddPath(tempPath);
        }
    }

    private void generateDiagonalDownLeftPath(Position origin, BishopState bishopState, PieceMovementInfo ret) {
        for (int i =0 ; i < 7; i++){
            PieceMovementPath tempPath = new PieceMovementPath(origin);
            char tempXpos = origin.getCoordinate().getxPos();
            int tempYpos = origin.getCoordinate().getyPos();
            Position currentPosition = new Position(tempXpos, tempYpos);

            AddPositionToPathResult result;
            for (int j = 0 ; j <= i ; j++){
                currentPosition  = PieceMovmentUtils.moveDiagonalDownLeft(currentPosition, bishopState.getColor(), 1);
                result = pathManager.tryAddPositionToPath(tempPath, currentPosition);
                if (!result.getSuccess()){
                    return;
                }
                if (result.getLastPositionOccupiedByEnemyPiece()){
                    ret.tryAddPath(tempPath);
                    return;
                }
            }
            ret.tryAddPath(tempPath);
        }
    }

    private void generateDiagonalDownRightPath(Position origin, BishopState bishopState, PieceMovementInfo ret) {
        for (int i =0 ; i < 7; i++){
            PieceMovementPath tempPath = new PieceMovementPath(origin);
            char tempXpos = origin.getCoordinate().getxPos();
            int tempYpos = origin.getCoordinate().getyPos();
            Position currentPosition = new Position(tempXpos, tempYpos);

            AddPositionToPathResult result;
            for (int j = 0 ; j <= i ; j++){
                currentPosition  = PieceMovmentUtils.moveDiagonalDownRight(currentPosition, bishopState.getColor(), 1);
                result = pathManager.tryAddPositionToPath(tempPath, currentPosition);
                if (!result.getSuccess()){
                    return;
                }
                if (result.getLastPositionOccupiedByEnemyPiece()){
                    ret.tryAddPath(tempPath);
                    return;
                }
            }
            ret.tryAddPath(tempPath);
        }
    }

    private void generateDiagonalUpRightPath(Position origin, BishopState bishopState, PieceMovementInfo ret) {
        for (int i =0 ; i < 7; i++){
            PieceMovementPath tempPath = new PieceMovementPath(origin);
            char tempXpos = origin.getCoordinate().getxPos();
            int tempYpos = origin.getCoordinate().getyPos();
            Position currentPosition = new Position(tempXpos, tempYpos);

            AddPositionToPathResult result;
            for (int j = 0 ; j <= i ; j++){
                currentPosition  = PieceMovmentUtils.moveDiagonalUpRight(currentPosition, bishopState.getColor(), 1);
                result = pathManager.tryAddPositionToPath(tempPath, currentPosition);
                if (!result.getSuccess()){
                    return;
                }
                if (result.getLastPositionOccupiedByEnemyPiece()){
                    ret.tryAddPath(tempPath);
                    return;
                }
            }
            ret.tryAddPath(tempPath);
        }
    }

}
