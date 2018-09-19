package com.lchess.engine.piece.model;

import com.lchess.engine.board.Position;
import com.lchess.engine.piece.model.pojo.PieceMovementInfo;
import com.lchess.engine.piece.model.pojo.PieceMovementPath;
import com.lchess.engine.piece.view.PieceTypeEnum;

/**
 * Created by liad on 20/09/2018.
 */
public class TurretModel extends PieceModel {
    @Override
    public PieceMovementInfo getPieceMovementInfo(Position origin, PieceState turretState) {
        PieceMovementInfo ret = new PieceMovementInfo();
        if (!turretState.getPieceType().equals(PieceTypeEnum.TURRET)){
            System.out.println(String.format("Error: turretMovementInfo was requested for [%s]",turretState.getClass().getSimpleName()));
            return null;
        }
        ret = getTurretGeneralAllowableMoves(origin, (TurretState)turretState);
        return ret;
    }

    private PieceMovementInfo getTurretGeneralAllowableMoves(Position origin, TurretState turretState) {
        PieceMovementInfo ret = new PieceMovementInfo();
        generateForwardPath(origin, turretState, ret);
        generateBackwardsPath(origin, turretState, ret);
        generateLeftPath(origin, turretState, ret);
        generateRightath(origin, turretState, ret);

        return ret;
    }

    private void generateForwardPath(Position origin, TurretState turretState, PieceMovementInfo ret) {
        for (int i = 0; i < 7; i++) {
            PieceMovementPath tempPath = new PieceMovementPath(origin);
            char tempXpos = origin.getPosition().getxPos();
            int tempYpos = origin.getPosition().getyPos();
            Position currentPosition = new Position(tempXpos, tempYpos);

            for (int j = 0; j <= i; j++) {
                currentPosition = PieceMovmentUtils.moveForward(currentPosition, turretState.getColor(), 1);
                if (!pathManager.tryAddPositionToPath(tempPath, currentPosition)) {
                    return;
                }
            }
            ret.tryAddPath(tempPath);
        }
    }

    private void generateBackwardsPath(Position origin, TurretState turretState, PieceMovementInfo ret) {
        for (int i = 0; i < 7; i++) {
            PieceMovementPath tempPath = new PieceMovementPath(origin);
            char tempXpos = origin.getPosition().getxPos();
            int tempYpos = origin.getPosition().getyPos();
            Position currentPosition = new Position(tempXpos, tempYpos);

            for (int j = 0; j <= i; j++) {
                currentPosition = PieceMovmentUtils.moveBack(currentPosition, turretState.getColor(), 1);
                if (!pathManager.tryAddPositionToPath(tempPath, currentPosition)) {
                    return;
                }
            }
            ret.tryAddPath(tempPath);
        }
    }

    private void generateLeftPath(Position origin, TurretState turretState, PieceMovementInfo ret) {
        for (int i = 0; i < 7; i++) {
            PieceMovementPath tempPath = new PieceMovementPath(origin);
            char tempXpos = origin.getPosition().getxPos();
            int tempYpos = origin.getPosition().getyPos();
            Position currentPosition = new Position(tempXpos, tempYpos);

            for (int j = 0; j <= i; j++) {
                currentPosition = PieceMovmentUtils.moveLeft(currentPosition, turretState.getColor(), 1);
                if (!pathManager.tryAddPositionToPath(tempPath, currentPosition)) {
                    return;
                }
            }
            ret.tryAddPath(tempPath);
        }
    }

    private void generateRightath(Position origin, TurretState turretState, PieceMovementInfo ret) {
        for (int i = 0; i < 7; i++) {
            PieceMovementPath tempPath = new PieceMovementPath(origin);
            char tempXpos = origin.getPosition().getxPos();
            int tempYpos = origin.getPosition().getyPos();
            Position currentPosition = new Position(tempXpos, tempYpos);

            for (int j = 0; j <= i; j++) {
                currentPosition = PieceMovmentUtils.moveRight(currentPosition, turretState.getColor(), 1);
                if (!pathManager.tryAddPositionToPath(tempPath, currentPosition)) {
                    return;
                }
            }
            ret.tryAddPath(tempPath);
        }
    }
}
