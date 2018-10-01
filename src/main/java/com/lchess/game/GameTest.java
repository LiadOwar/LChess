package com.lchess.game;

import com.lchess.UI.ConsoleUI;
import com.lchess.common.Logger;
import com.lchess.engine.board.Board;
import com.lchess.engine.board.BoardManager;
import com.lchess.engine.board.Position;
import com.lchess.engine.board.Tile;
import com.lchess.engine.piece.model.KingState;
import com.lchess.engine.piece.model.PieceState;
import com.lchess.engine.piece.view.Piece;
import com.lchess.engine.piece.view.PieceColorEnum;
import com.lchess.engine.test.utils.TestUtils;

import java.util.HashMap;

/**
 * Created by liad on 01/09/2018.
 */

public class GameTest {
    private static BoardManager boardManager = BoardManager.getInstanse();

    private static Logger logger = new Logger();

  private static Game game = new GameImpl();

    private static Boolean compareTileWithExpectation(Tile tile, HashMap<Position, PieceState> expectedInitBoardPositionMap){
        String methodName = "compareTileWithExpectation";
        PieceState tilePieceState = tile.getPieceState();
        Position tilePosition = tile.getPosition();

        PieceState expectedPieceState = expectedInitBoardPositionMap.get(tilePosition);
        if (tilePieceState == null){
            if (expectedPieceState == null){
                return true;
            }
            else{
                logger.errorMsg(methodName, "tile is not occupied but expected tile map holds piece state");
                return false;
            }
        }
        if (expectedPieceState == null){
            if (tilePieceState != null){
                logger.errorMsg(methodName, "tile is occupied but expected tile map does not holds piece state");
                return false;
            }
            else{
                return true;
            }
        }
        if (tilePieceState.equals(expectedPieceState)){
            return true;
        }
        else {
            logger.errorMsg(methodName, "tile and expected map holds different piece stats on the same position");
            return false;
        }
    }


    public static void getInitialTurnTest(){
        String methodName = "getInitialTurnTest";
        Game game = new GameImpl();
        game.startGame();
        PieceColorEnum currentTurn = game.getCurrentTurn();
        if (!currentTurn.equals(PieceColorEnum.WHITE)){
            logger.printTestFailed(methodName,"wrong turn");
        }
        else {
            logger.printTestPass(methodName);
        }
    }

    public static void getTurnOnLegalMoveTest(){
        String methodName = "getTurnOnLegalMoveTest";
        Game game = new GameImpl();
        game.startGame();
        Position origin = new Position('A', 2);
        Position destination = new Position('A', 3);
        game.movePiece(origin, destination);
        PieceColorEnum currentTurn = game.getCurrentTurn();
        if (!currentTurn.equals(PieceColorEnum.BLACK)){
            logger.printTestFailed(methodName,"wrong turn on legal move");
        }
        else {
            logger.printTestPass(methodName);
        }
    }

    public static void getTurnOnIllegalMoveTest(){
        String methodName = "getTurnOnIllegalMoveTest";
        Game game = new GameImpl();
        game.startGame();
        Position origin = new Position('A', 2);
        Position destination = new Position('A', 5);
        game.movePiece(origin, destination);
        PieceColorEnum currentTurn = game.getCurrentTurn();
        if (!currentTurn.equals(PieceColorEnum.WHITE)){
            logger.printTestFailed(methodName,"wrong turn on legal move");
        }
        else {
            logger.printTestPass(methodName);
        }
    }

    public static void moveBlackPawnOnWhitesTurnTest(){
        String methodName = "moveBlackPawnOnWhitesTurnTest";
        Game game = new GameImpl();
        game.startGame();
        Position origin = new Position('A', 7);
        Position destination = new Position('A', 6);
        game.movePiece(origin, destination);
        PieceColorEnum currentTurn = game.getCurrentTurn();
        if (!currentTurn.equals(PieceColorEnum.WHITE)){
            logger.printTestFailed(methodName,"Black moved in white's turn");
        }
        else {
            logger.printTestPass(methodName);
        }
    }

    public static void positionOfIllegalTurnMovePieceTest(){
        String methodName = "positionOfIllegalTurnMovePieceTest";

        Game game = new GameImpl();
        game.startGame();
        Position origin = new Position('A', 7);
        Position destination = new Position('A', 6);
        Position expectedDestination = new Position('A', 7);
        game.movePiece(origin, destination);
        HashMap<Position, PieceState> expectedBoardPositionMap = TestUtils.getExpectedInitBoardPositionsMap();
        TestUtils.setExpectedPosition(expectedBoardPositionMap, origin, expectedDestination);

        if (isBoardMetExpectaions(methodName, expectedBoardPositionMap)){
            logger.printTestPass(methodName);
        }
        else {
            logger.printTestFailed(methodName, "tile on board not as expected");
        }

    }

    public static void positionOfIllegalMovePieceTest(){
        String methodName = "positionOfIllegalMovePieceTest";

        Game game = new GameImpl();
        game.startGame();
        Position origin = new Position('A', 2);
        Position destination = new Position('B', 3);
        Position expectedDestination = new Position('A', 2);
        game.movePiece(origin, destination);
        HashMap<Position, PieceState> expectedBoardPositionMap = TestUtils.getExpectedInitBoardPositionsMap();
        TestUtils.setExpectedPosition(expectedBoardPositionMap, origin, expectedDestination);

        if (isBoardMetExpectaions(methodName, expectedBoardPositionMap)){
            logger.printTestPass(methodName);
        }
        else {
            logger.printTestFailed(methodName, "tile on board not as expected");
        }

    }

    private static Boolean isBoardMetExpectaions(String methodName, HashMap<Position, PieceState> expectedBoardPositionMap) {
        Board board = boardManager.getBoard();
        Tile[] tiles = board.getTiles();
        for (Tile tile : tiles) {
            if (compareTileWithExpectation(tile, expectedBoardPositionMap) == false) {
                return false;
            }
        }
        return true;
    }

    public static void startGameTest(){
        String methodName = "startGameTest";

        HashMap<Position,PieceState> initExpectedInitBoardPositionMap = TestUtils.getExpectedInitBoardPositionsMap();

        Game game = new GameImpl();
        game.startGame();

        if (isBoardMetExpectaions(methodName, initExpectedInitBoardPositionMap)){
            logger.printTestPass(methodName);
        }
        else {
            logger.printTestFailed(methodName, "tile on board not as expected");
        }

    }

    private static void tempMoveWhiteBishopTest() {
        String methodName = "tempMoveWhiteBishopTest";

        Game game = new GameImpl();
        game.startGame();
        Position origin = new Position('C', 1);
        Position destination = new Position('A', 3);
        Position expectedDestination = new Position('A', 3);
        game.movePiece(origin, destination);
        HashMap<Position, PieceState> expectedBoardPositionMap = TestUtils.getExpectedInitBoardPositionsMap();
        TestUtils.setExpectedPosition(expectedBoardPositionMap, origin, expectedDestination);

        if (isBoardMetExpectaions(methodName, expectedBoardPositionMap)){
            logger.printTestPass(methodName);
        }
        else {
            logger.printTestFailed(methodName, "tile on board not as expected");
        }
        printBoardDebug();
    }

    private static  void tempMoveWhiteBishopDownLeftTest() {
        String methodName = "tempMoveWhiteBishopDownLeftTest";
        Game game = new GameImpl();
        game.startGame();
        Position origin = new Position('F', 1);
        Position destination = new Position('C', 4);
        Position origin2 = new Position('C', 4);
        Position destination2 = new Position('B', 3);
        Position whitePawnOrigin = new Position('E', 2);
        Position whitePawnDest = new Position('E', 3);
        Position blackPawnOrigin = new Position('B', 7);
        Position blackPawnDest = new Position('B', 5);
        Position blackPawnOrigin2 = new Position('B', 5);
        Position blackPawnDest2 = new Position('B', 4);
        game.movePiece(whitePawnOrigin, whitePawnDest);
        game.movePiece(blackPawnOrigin, blackPawnDest);
        game.movePiece(origin, destination);
        game.movePiece(blackPawnOrigin2, blackPawnDest2);
        game.movePiece(origin2, destination2);
        HashMap<Position, PieceState> expectedBoardPositionMap = TestUtils.getExpectedInitBoardPositionsMap();
        TestUtils.setExpectedPosition(expectedBoardPositionMap, whitePawnOrigin,whitePawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackPawnOrigin,blackPawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, origin, destination);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackPawnOrigin2, blackPawnDest2);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, origin2,destination2);

        if (isBoardMetExpectaions(methodName, expectedBoardPositionMap)){
            logger.printTestPass(methodName);
        }
        else {
            logger.printTestFailed(methodName, String.format("tile on board not as expected: expect [%s] actual [%s]", destination2, boardManager.getTileFromPosition(destination2).getPosition())  );
        }
        printBoardDebug();
    }

    private static  void tempMoveWhiteBishopDownRightTest() {
        String methodName = "tempMoveWhiteBishopDownRightTest";
        Game game = new GameImpl();
        game.startGame();
        Position origin = new Position('F', 1);
        Position destination = new Position('C', 4);
        Position origin2 = new Position('C', 4);
        Position destination2 = new Position('D', 3);
        Position whitePawnOrigin = new Position('E', 2);
        Position whitePawnDest = new Position('E', 3);
        Position blackPawnOrigin = new Position('B', 7);
        Position blackPawnDest = new Position('B', 5);
        Position blackPawnOrigin2 = new Position('B', 5);
        Position blackPawnDest2 = new Position('B', 4);
        game.movePiece(whitePawnOrigin, whitePawnDest);
        game.movePiece(blackPawnOrigin, blackPawnDest);
        game.movePiece(origin, destination);
        game.movePiece(blackPawnOrigin2, blackPawnDest2);
        game.movePiece(origin2, destination2);
        HashMap<Position, PieceState> expectedBoardPositionMap = TestUtils.getExpectedInitBoardPositionsMap();
        TestUtils.setExpectedPosition(expectedBoardPositionMap, whitePawnOrigin,whitePawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackPawnOrigin,blackPawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, origin, destination);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackPawnOrigin2, blackPawnDest2);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, origin2,destination2);

        if (isBoardMetExpectaions(methodName, expectedBoardPositionMap)){
            logger.printTestPass(methodName);
        }
        else {
            logger.printTestFailed(methodName, String.format("tile on board not as expected: expect [%s] actual [%s]", destination2, boardManager.getTileFromPosition(destination2).getPosition())  );
        }
        printBoardDebug();
    }

    private static  void tempMoveWhiteBishopUpRightTest() {
        String methodName = "tempMoveWhiteBishopDownRightTest";
        Game game = new GameImpl();
        game.startGame();
        Position origin = new Position('F', 1);
        Position destination = new Position('C', 4);
        Position origin2 = new Position('C', 4);
        Position destination2 = new Position('E', 6);
        Position whitePawnOrigin = new Position('E', 2);
        Position whitePawnDest = new Position('E', 3);
        Position blackPawnOrigin = new Position('B', 7);
        Position blackPawnDest = new Position('B', 5);
        Position blackPawnOrigin2 = new Position('B', 5);
        Position blackPawnDest2 = new Position('B', 4);
        game.movePiece(whitePawnOrigin, whitePawnDest);
        game.movePiece(blackPawnOrigin, blackPawnDest);
        game.movePiece(origin, destination);
        game.movePiece(blackPawnOrigin2, blackPawnDest2);
        game.movePiece(origin2, destination2);
        HashMap<Position, PieceState> expectedBoardPositionMap = TestUtils.getExpectedInitBoardPositionsMap();
        TestUtils.setExpectedPosition(expectedBoardPositionMap, whitePawnOrigin,whitePawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackPawnOrigin,blackPawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, origin, destination);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackPawnOrigin2, blackPawnDest2);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, origin2,destination2);

        if (isBoardMetExpectaions(methodName, expectedBoardPositionMap)){
            logger.printTestPass(methodName);
        }
        else {
            logger.printTestFailed(methodName, String.format("tile on board not as expected: expect [%s] actual [%s]", destination2, boardManager.getTileFromPosition(destination2).getPosition())  );
        }
        printBoardDebug();
    }



    private static void tempMoveWhiteBishop1UpLeftTest() {
        String methodName = "tempMoveWhiteBishop1UpLeftTest";

        Game game = new GameImpl();
        game.startGame();
        Position origin = new Position('C', 1);
        Position destination = new Position('B', 2);
        Position expectedDestination = new Position('B', 2);
        Position whitePawnOrigin = new Position('B', 2);
        Position whitePawnDest = new Position('B', 3);
        Position blackPawnOrigin = new Position('B', 7);
        Position blackPawnDest = new Position('B', 5);
        game.movePiece(whitePawnOrigin, whitePawnDest);
        game.movePiece(blackPawnOrigin, blackPawnDest);
        game.movePiece(origin, destination);
        HashMap<Position, PieceState> expectedBoardPositionMap = TestUtils.getExpectedInitBoardPositionsMap();
        TestUtils.setExpectedPosition(expectedBoardPositionMap, whitePawnOrigin,whitePawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackPawnOrigin,blackPawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, origin, expectedDestination);

        if (isBoardMetExpectaions(methodName, expectedBoardPositionMap)){
            logger.printTestPass(methodName);
        }
        else {
            logger.printTestFailed(methodName, "tile on board not as expected");
        }
        printBoardDebug();
    }

    private static void isWhiteKingThreatenedTest(){
        String methodName = "isWhiteKingThreatenedTest";
        Game game = new GameImpl();
        game.startGame();
        Position whitePawnOrigin = new Position('B', 2);
        Position whitePawnDest = new Position('B', 3);

        Position blackPawnOrigin = new Position('E', 7);
        Position blackPawnDest = new Position('E', 6);

        Position whitePawnOrigin2 = new Position('B', 3);
        Position whitePawnDest2 = new Position('B', 4);

        Position blackBishopOrigin = new Position('F', 8);
        Position blackBishopDest = new Position('B', 4);


        game.movePiece(whitePawnOrigin, whitePawnDest);
        game.movePiece(blackPawnOrigin, blackPawnDest);
        game.movePiece(whitePawnOrigin2, whitePawnDest2);
        game.movePiece(blackBishopOrigin, blackBishopDest);

        HashMap<Position, PieceState> expectedBoardPositionMap = TestUtils.getExpectedInitBoardPositionsMap();
        TestUtils.setExpectedPosition(expectedBoardPositionMap, whitePawnOrigin,whitePawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackPawnOrigin,blackPawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, whitePawnOrigin2, whitePawnDest2);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackBishopOrigin, blackPawnDest);

        KingState whiteKingState = (KingState)(boardManager.getTileFromPosition(blackPawnDest).getPieceState());
        if (!whiteKingState.isUnderThreat()){
            logger.printTestFailed(methodName, "White king is not under threat as i should be!");
        }

        if (isBoardMetExpectaions(methodName, expectedBoardPositionMap)){
            logger.printTestPass(methodName);
        }
        else {
            logger.printTestFailed(methodName, "tile on board not as expected");
        }
        printBoardDebug();
    }



    private static void printBoardDebug() {
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.printBoard(boardManager.getBoard());
    }

    public static void main(String[] args){
        startGameTest();
        getInitialTurnTest();
        getTurnOnLegalMoveTest();
        getTurnOnIllegalMoveTest();
        moveBlackPawnOnWhitesTurnTest();
        positionOfIllegalTurnMovePieceTest();
        positionOfIllegalMovePieceTest();
        tempMoveWhiteBishopTest();
        tempMoveWhiteBishop1UpLeftTest();

        tempMoveWhiteBishopDownLeftTest();
        tempMoveWhiteBishopDownRightTest();
        tempMoveWhiteBishopUpRightTest();

    }
}
