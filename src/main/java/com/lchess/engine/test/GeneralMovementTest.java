package com.lchess.engine.test;

import com.lchess.common.Logger;
import com.lchess.engine.board.*;
import com.lchess.engine.piece.model.*;
import com.lchess.engine.piece.model.pojo.PieceMovementInfo;
import com.lchess.engine.piece.model.pojo.PieceMovementPath;
import com.lchess.engine.piece.view.PieceColorEnum;
import com.lchess.engine.test.utils.TestUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by liad on 31/08/2018.
 */
public class GeneralMovementTest {
    private static Logger logger;
    private static PieceModel model = new PawnModel();
    private static TestUtils testUtils;
    private static BoardManager boardManager = BoardManager.getInstanse();

    private static Boolean whitePawnGeneralMovementTest(){
        String methodName = "whitePawnGeneralMovementTest";

        Position origin = new Position(new ChessCoordinate('A', 2));
        PieceState whitePawnState = new PawnState(PieceColorEnum.WHITE);
        PieceMovementInfo generalAllowableMoves = model.getPieceMovementInfo(origin, whitePawnState);

        Position[] expectedPositionsForward = {
                new Position(new ChessCoordinate('A', 2)),
                new Position(new ChessCoordinate('A', 3))
        };
        PieceMovementPath expectedPathForward = testUtils.generatePath(expectedPositionsForward);

        Position[] expectedPositionsForwardFirstMove = {
                new Position(new ChessCoordinate('A', 2)),
                new Position(new ChessCoordinate('A', 3)),
                new Position(new ChessCoordinate('A', 4))
        };
        PieceMovementPath expectedPathForwardFirstMove = testUtils.generatePath(expectedPositionsForwardFirstMove);


        Set<PieceMovementPath> allowedPaths = generalAllowableMoves.getAllowedPaths();
        int allowabelPathNum = 2;
        int allowablePathTested = 0;
        for (PieceMovementPath path : allowedPaths){
            if (!TestUtils.isNumOfPathsCorrect(allowabelPathNum, generalAllowableMoves )){
                logger.errorMsg(methodName, String.format("allowed Paths num [%s], generated paths [%s] ", allowabelPathNum, generalAllowableMoves.getAllowedPaths().size()));
                return false;
            }
            ArrayList<Position> tempPath = path.getPath();
            for (Position position : tempPath){
                if (BoardUtils.isOutOfBoard(position)){
                   logger.errorMsg(methodName, String.format("position [%s] is outOfBoard", position));
                    return false;
                }
            }
            if (TestUtils.isSamePath(path, expectedPathForwardFirstMove)){
                allowablePathTested++;
            }
            if (TestUtils.isSamePath(path, expectedPathForward)){
                allowablePathTested++;
            }
        }
        if (allowabelPathNum != allowablePathTested){
            logger.errorMsg(methodName, String.format("allowed Paths num [%s], tested paths num [%s] ", allowabelPathNum, allowablePathTested));
            return false;
        }
        TestUtils.printAllowablePaths(generalAllowableMoves);
        return true;

    }

    private static Boolean pawnOutOfBoundTest(PieceColorEnum colorEnum){
        String methodName = "pawnOutOfBoundTest";
        Position origin = new Position(new ChessCoordinate('A', 8));
        PawnState pawnState = new PawnState(colorEnum);
        switch (colorEnum) {
            case WHITE:
                origin = new Position(new ChessCoordinate('A', 8));
            break;
            case BLACK:
                origin = new Position(new ChessCoordinate('A', 1));
                break;
        }
        pawnState.setFirstMove(false);
        PieceMovementInfo generalAllowableMoves = model.getPieceMovementInfo(origin, pawnState);
        int size = generalAllowableMoves.getAllowedPaths().size();
        if (size > 0){
            logger.errorMsg(methodName, String.format("pawn [%s] at position [%s] expect [%s] allowed moves, got [%s] ",
                    colorEnum.toString(), origin,1, size));
            return false;
        }

        TestUtils.printPieceState(pawnState);
        TestUtils.printAllowablePaths(generalAllowableMoves);
        return true;

    }

    private static Boolean moveWhitePawnForward1MoveTest(){
        String methodName = "moveWhitePawnForward1MoveTest";
        Board board = boardManager.getBoard();

        Position origin = new Position('A', 2);
        Position destination = new Position('A', 3);
        return pieceMovementOnBoardTest(methodName, PieceColorEnum.WHITE, board, origin, destination);
    }

    private static Boolean moveBlackPawnForward2MoveTest(){
        String methodName = "moveBlackPawnForward2MoveTest";
        Board board = boardManager.getBoard();

        Position origin = new Position('A', 7);
        Position destination = new Position('A', 5);
        return pieceMovementOnBoardTest(methodName, PieceColorEnum.BLACK, board, origin, destination);
    }

    private static Boolean moveBlackPawnForward3MoveTest(){
        String methodName = "moveBlackPawnForward3MoveTest";
        Board board = boardManager.getBoard();

        Position origin = new Position('A', 7);
        Position destination = new Position('A', 4);
        return pieceIlligalMovmentOnBoardTest(methodName, PieceColorEnum.BLACK, board, origin, destination);
    }

    private static Boolean pieceMovementOnBoardTest(String methodName, PieceColorEnum colorEnum, Board board, Position origin, Position destination) {
        Integer originTileInt = boardManager.convertPositionToInt(origin);
        Tile originTile = board.getTiles()[originTileInt];

        PawnState pawnState =testUtils.createPawnOnBoard(board, colorEnum, origin );

        Integer destinationTileInt = boardManager.convertPositionToInt(destination);
        Tile destinationTile = board.getTiles()[destinationTileInt];

        if (boardManager.tryMovePieceOnTheBoard(origin, destination) == false){
            logger.printTestFailed(methodName, "tryMovePieceOnTheBoard failed");
            return false;
        }

        if (!destinationTile.getPieceState().equals(pawnState) ){
            logger.printTestFailed(methodName, "destination is occupied but not by the right piece state");
            return false;
        }
        if (originTile.getPieceState() != null ){
            logger.printTestFailed(methodName, "origin still holds the original piece");
            return false;
        }

        logger.printTestPass(methodName);
        return true;
    }

    private static Boolean pieceIlligalMovmentOnBoardTest(String methodName, PieceColorEnum colorEnum, Board board, Position origin, Position destination) {
        Integer originTileInt = boardManager.convertPositionToInt(origin);
        Tile originTile = board.getTiles()[originTileInt];

        PawnState pawnState =testUtils.createPawnOnBoard(board, colorEnum, origin );

        Integer destinationTileInt = boardManager.convertPositionToInt(destination);
        Tile destinationTile = board.getTiles()[destinationTileInt];

        if (boardManager.tryMovePieceOnTheBoard(origin, destination) == true){
            logger.printTestFailed(methodName, "tryMovePieceOnTheBoard succeeded on illigal move");
            return false;
        }

        if (destinationTile.getPieceState() == null){
            logger.printTestFailed(methodName, "destination is illegally occupied ");
            return false;
        }
        if (destinationTile.getPieceState() != null ){
            logger.printTestFailed(methodName, "destination is illegally occupied by piece");
            return false;
        }
        if (originTile.getPieceState() != pawnState ){
            logger.printTestFailed(methodName, "origin is not holding the piece");
            return false;
        }
        if (originTile.getPieceState() == null ){
            logger.printTestFailed(methodName, "origin is not holding anything");
            return false;
        }
        logger.printTestPass(methodName);
        return true;
    }




    public static void main(String[] args){
//        if (!whitePawnGeneralMovementTest()){
//            logger.errorMsg("whitePawnGeneralMovementTest", "failed");
//            return;
//        }
        if (!pawnOutOfBoundTest(PieceColorEnum.BLACK)){
            logger.errorMsg("pawnOutOfBoundTest (black)", "failed");
            return;
        }
        if (!pawnOutOfBoundTest(PieceColorEnum.WHITE)){
            logger.errorMsg("pawnOutOfBoundTest (white)", "failed");
            return;
        }
        moveWhitePawnForward1MoveTest();
        moveBlackPawnForward2MoveTest();
        moveBlackPawnForward3MoveTest();

    }


}
