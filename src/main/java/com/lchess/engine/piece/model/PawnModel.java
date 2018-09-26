package com.lchess.engine.piece.model;

import com.lchess.engine.board.Position;
import com.lchess.engine.piece.model.pojo.PieceMovementInfo;
import com.lchess.engine.piece.model.pojo.PieceMovementPath;

/**
 * Created by liad on 31/08/2018.
 */
public class PawnModel extends PieceModel {

    @Override
    public PieceMovementInfo getPieceMovementInfo(Position origin, PieceState pawnState) {

        PieceMovementInfo ret = new PieceMovementInfo();
        if (!pawnState.getClass().getSimpleName().equals("PawnState")){
            System.out.println(String.format("Error: pawnMovementInfo was requested for [%s]",pawnState.getClass().getSimpleName()));
            return null;
        }
        ret = getPawnGeneralAllowableMoves(origin, (PawnState) pawnState);

        return ret;
    }

    private PieceMovementInfo getPawnGeneralAllowableMoves(Position origin, PawnState pawnState) {
        PieceMovementInfo ret = new PieceMovementInfo();
        generateMoveForward1Path(origin , pawnState, ret);
        generateMoveForward2Path(origin , pawnState, ret);
        generateMoveAttackLeftPath(origin , pawnState, ret);
        generateMoveAttackRightPath(origin , pawnState, ret);

        return ret;
    }
    private void generateMoveForward1Path(Position origin, PawnState pawnState, PieceMovementInfo ret) {

        PieceMovementPath tempPath = new PieceMovementPath(origin);
        char tempXpos = origin.getPosition().getxPos();
        int tempYpos = origin.getPosition().getyPos();
        Position currentPosition = new Position(tempXpos, tempYpos);

        currentPosition  = PieceMovmentUtils.moveForward(currentPosition, pawnState.getColor(), 1);
        if (!pathManager.tryAddPositionToPath(tempPath, currentPosition).getSuccess()){
            return;
        }

        ret.tryAddPath(tempPath);
    }

    private void generateMoveForward2Path(Position origin, PawnState pawnState, PieceMovementInfo ret) {
        if (!pawnState.getFirstMove()){
            return;
        }
        for (int i =0 ; i < 2; i++){
            PieceMovementPath tempPath = new PieceMovementPath(origin);
            char tempXpos = origin.getPosition().getxPos();
            int tempYpos = origin.getPosition().getyPos();
            Position currentPosition = new Position(tempXpos, tempYpos);

            for (int j = 0 ; j <= i ; j++){
                currentPosition  = PieceMovmentUtils.moveForward(currentPosition, pawnState.getColor(), 1);
                if (!pathManager.tryAddPositionToPath(tempPath, currentPosition).getSuccess()){
                    return;
                }
            }
            ret.tryAddPath(tempPath);
        }
    }

    private void generateMoveAttackRightPath(Position origin, PawnState pawnState, PieceMovementInfo ret) {

        PieceMovementPath tempPath = new PieceMovementPath(origin);
        char tempXpos = origin.getPosition().getxPos();
        int tempYpos = origin.getPosition().getyPos();
        Position currentPosition = new Position(tempXpos, tempYpos);

        currentPosition  = PieceMovmentUtils.moveDiagonalUpRight(currentPosition, pawnState.getColor(), 1);
        if (!pathManager.tryAddPositionToPath(tempPath, currentPosition).getSuccess()){
            return;
        }

        ret.tryAddPath(tempPath);
    }

    private void generateMoveAttackLeftPath(Position origin, PawnState pawnState, PieceMovementInfo ret) {

        PieceMovementPath tempPath = new PieceMovementPath(origin);
        char tempXpos = origin.getPosition().getxPos();
        int tempYpos = origin.getPosition().getyPos();
        Position currentPosition = new Position(tempXpos, tempYpos);

        currentPosition  = PieceMovmentUtils.moveDiagonalUpLeft(currentPosition, pawnState.getColor(), 1);
        if (!pathManager.tryAddPositionToPath(tempPath, currentPosition).getSuccess()){
            return;
        }

        ret.tryAddPath(tempPath);
    }

}
