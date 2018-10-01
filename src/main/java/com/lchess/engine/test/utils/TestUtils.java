package com.lchess.engine.test.utils;

import com.lchess.common.Logger;
import com.lchess.engine.board.*;
import com.lchess.engine.piece.model.*;
import com.lchess.engine.piece.model.pojo.PieceMovementInfo;
import com.lchess.engine.piece.model.pojo.PieceMovementPath;
import com.lchess.engine.piece.view.PieceColorEnum;
import com.lchess.game.Game;
import com.lchess.game.GameImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by liad on 31/08/2018.
 */
public class TestUtils {
    private static  Logger logger;
    private static Game game = new GameImpl();

    private static BoardManager boardManager = game.getBoardManager();

    public static PieceMovementPath generatePath(Position[] positions){
        PieceMovementPath ret = new PieceMovementPath(positions[0]);
        for (int i = 1 ;i < positions.length ; i++){
            ret.addPositionToPath(positions[i]);
        }
        return ret;
    }

    public static boolean isNumOfPathsCorrect(int allowabelPathNum, PieceMovementInfo generalAllowableMoves) {
        return allowabelPathNum == generalAllowableMoves.getAllowedPaths().size();
    }

    public static Boolean isSamePath(PieceMovementPath path, PieceMovementPath otherPath){
        String methodName = "isSamePath";

        ArrayList<Position> pathArray = path.getPath();
        ArrayList<Position> otherPathArray = otherPath.getPath();
        if (pathArray.size() != otherPathArray.size()){
            return false;
        }
        for (int i = 0; i < pathArray.size(); i++) {
            if (BoardUtils.isOutOfBoard(pathArray.get(i))){
                logger.errorMsg(methodName, String.format("position [%s] is outOfBoard", pathArray.get(i)));
                return false;
            }
            if (!pathArray.get(i).getPosition().equals(otherPathArray.get(i).getPosition())){
                return false;
            }
        }
        return true;
    }

    public static void printPieceState(PieceState pieceState){
        System.out.println(String.format("[%s] [%s]", pieceState.getColor().toString(), pieceState.getClass().getSimpleName().replace("State","")));
    }

    public static void printAllowablePaths(PieceMovementInfo pieceMovementInfo){
        Set<PieceMovementPath> allowedPaths = pieceMovementInfo.getAllowedPaths();
        PieceMovementPath[] allowedPathsArray = allowedPaths.toArray((new PieceMovementPath[allowedPaths.size()]));
        System.out.println(String.format("********allowed [%s] paths *********", allowedPaths.size()));
        for (int i = 0 ; i < allowedPaths.size() ; i++){
            System.out.println(String.format("********path# [%s] *********", i));

            printPath(allowedPathsArray[i]);
        }
    }

    private static void printPath(PieceMovementPath pieceMovementPath){
        ArrayList<Position> path = pieceMovementPath.getPath();
        for (Position position : path){
            System.out.println(String.format("[%s]", getPositionString(position)));
        }
        System.out.println("");
    }

    private static String getPositionString(Position position){
        return(String.format("(%s,%s)", position.getPosition().getxPos(), position.getPosition().getyPos()));
    }

    public static PawnState createPawnOnBoard(Board board, PieceColorEnum colorEnum, Position position){
        PawnState pawnState = new PawnState(colorEnum);
        Tile[] tiles = board.getTiles();
        int positionToInt = boardManager.convertPositionToInt(position);

        Tile tile = tiles[positionToInt];
        tile.setPieceState(pawnState);
        return pawnState;
    }

    public static void printTurnNum(Integer turnNum) {
        System.out.println(String.format("********Turn [%s]********", turnNum));
    }

    public static Board getBoardWithWhitePawn(PieceColorEnum colorEnum, Position position) {
        Board board = boardManager.getBoard();
        TestUtils.createPawnOnBoard(board, colorEnum, position);
        return board;
    }

    public static  HashMap<Position, PieceState> getExpectedInitBoardPositionsMap(){
        HashMap<Position, PieceState> expectedInitBoardPositionMap = new HashMap<>();
        setTestExpectedWhitePawnsOnBoard(expectedInitBoardPositionMap);
        setTestExcpectedBlackPawnsOnBoard(expectedInitBoardPositionMap);
        setTestExcpectedWhiteBishopsOnBoard(expectedInitBoardPositionMap);
        setTestExcpectedBlackBishopsOnBoard(expectedInitBoardPositionMap);
        setTestExcpectedKingsOnBoard(expectedInitBoardPositionMap);
        setExpectedTurretsOnBoard(expectedInitBoardPositionMap);
        return expectedInitBoardPositionMap;

    }

    private static void setTestExcpectedKingsOnBoard(HashMap<Position, PieceState> expectedInitBoardPositionMap) {
        Position startPosition1 = new Position('E', 1);
        Position startPosition2 = new Position('E', 8);

        expectedInitBoardPositionMap.put(startPosition1, new KingState(PieceColorEnum.WHITE));
        expectedInitBoardPositionMap.put(startPosition2, new KingState(PieceColorEnum.BLACK));
    }

    private static void setTestExcpectedWhiteBishopsOnBoard(HashMap<Position, PieceState> expectedInitBoardPositionMap) {
        Position startPosition1 = new Position('C', 1);
        Position startPosition2 = new Position('F', 1);

        expectedInitBoardPositionMap.put(startPosition1, new BishopState(PieceColorEnum.WHITE));
        expectedInitBoardPositionMap.put(startPosition2, new BishopState(PieceColorEnum.WHITE));
    }

    private static void setTestExcpectedBlackBishopsOnBoard(HashMap<Position, PieceState> expectedInitBoardPositionMap) {
        Position startPosition1 = new Position('C', 8);
        Position startPosition2 = new Position('F', 8);

        expectedInitBoardPositionMap.put(startPosition1, new BishopState(PieceColorEnum.BLACK));
        expectedInitBoardPositionMap.put(startPosition2, new BishopState(PieceColorEnum.BLACK));
    }

    public static void setExpectedPosition(HashMap<Position, PieceState> expectedInitBoardPositionsMap, Position origin, Position destination) {
        PieceState pieceState = expectedInitBoardPositionsMap.get(origin);
        expectedInitBoardPositionsMap.remove(origin);
        expectedInitBoardPositionsMap.put(destination, pieceState);

    }
    private static void setTestExpectedWhitePawnsOnBoard(HashMap<Position, PieceState> expectedInitBoardPositionMap) {
        Position startPosition = new Position('A', 2);
        for(int i = 0 ; i < 8 ; i++){
            char xPos = (char)(startPosition.getPosition().getxPos() + i);
            int yPos = startPosition.getPosition().getyPos();
            Position position = new Position(xPos, yPos);
            expectedInitBoardPositionMap.put(position, new PawnState(PieceColorEnum.WHITE));
        }
    }

    private static void setTestExcpectedBlackPawnsOnBoard(HashMap<Position, PieceState> expectedInitBoardPositionMap) {
        Position startPosition = new Position('A', 7);
        for(int i = 0 ; i < 8 ; i++){
            char xPos = (char)(startPosition.getPosition().getxPos() + i);
            int yPos = startPosition.getPosition().getyPos();
            Position position = new Position(xPos, yPos);
            expectedInitBoardPositionMap.put(position, new PawnState(PieceColorEnum.BLACK));
        }
    }

    public static void movePieceDebug(Game game, Position origin, Position destination) {


        Tile originTile = game.getBoardManager().getTileFromPosition(origin);
        Tile destTile = game.getBoardManager().getTileFromPosition(destination);
        PieceState originPieceState = originTile.getPieceState();
        destTile.setPieceState(originPieceState);
        originTile.setPieceState(null);
    }


    public static void setExpectedTurretsOnBoard(HashMap<Position,PieceState> expectedInitBoardPositionMap) {
        Position whiteTurretPosition1 = new Position('A', 1);
        Position whiteTurretPosition2 = new Position('H', 1);

        Position blackTurretPosition1 = new Position('A', 8);
        Position blackTurretPosition2 = new Position('H', 8);

        expectedInitBoardPositionMap.put(whiteTurretPosition1, new TurretState(PieceColorEnum.WHITE));
        expectedInitBoardPositionMap.put(whiteTurretPosition2, new TurretState(PieceColorEnum.WHITE));
        expectedInitBoardPositionMap.put(blackTurretPosition1, new TurretState(PieceColorEnum.BLACK));
        expectedInitBoardPositionMap.put(blackTurretPosition2, new TurretState(PieceColorEnum.BLACK));
    }
}
