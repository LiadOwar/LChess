package com.lchess;

import com.lchess.UI.ConsoleUI;
import com.lchess.common.Logger;
import com.lchess.engine.board.Board;
import com.lchess.engine.board.BoardManager;
import com.lchess.engine.board.Position;
import com.lchess.engine.board.Tile;
import com.lchess.engine.piece.model.KingState;
import com.lchess.engine.piece.model.PieceState;
import com.lchess.engine.test.utils.TestUtils;
import com.lchess.game.Game;
import com.lchess.game.GameImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * Created by liad on 23/09/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpring {

    @Autowired
    private Game game;

    @Autowired
    private ConsoleUI consoleUI;

    private static Logger logger = new Logger();

    @Test
    public void startGameTest(){
        String methodName = "startGameTest";

        HashMap<Position,PieceState> initExpectedInitBoardPositionMap = TestUtils.getExpectedInitBoardPositionsMap();
        game.startGame();

        if (isBoardMetExpectations(methodName, initExpectedInitBoardPositionMap)){
            logger.printTestPass(methodName);
        }
        else {
            logger.printTestFailed(methodName, "tile on board not as expected");
            assert false;
        }
        consoleUI.printBoard(game.getBoardManager().getBoard());
    }
    @Test
    public void isWhiteKingThreatenedTest(){
        String methodName = "isWhiteKingThreatenedTest";

        game.startGame();
        BoardManager boardManager = game.getBoardManager();
        Position whitePawnOrigin = new Position('B', 2);
        Position whitePawnDest = new Position('B', 3);

        Position blackPawnOrigin = new Position('E', 7);
        Position blackPawnDest = new Position('E', 6);

        Position whitePawnOrigin2 = new Position('B', 3);
        Position whitePawnDest2 = new Position('B', 4);

        Position blackBishopOrigin = new Position('F', 8);
        Position blackBishopDest = new Position('B', 4);

        Position whitePawn2Origin = new Position('A', 2);
        Position whitePawn2Dest = new Position('A', 3);

        Position blackBishopOrigin2 = blackBishopDest;
        Position blackBishopDest2 = new Position('D', 2);

        Position whiteKingPosition = new Position('E', 1);


        game.movePiece(whitePawnOrigin, whitePawnDest);
        consoleUI.printBoard(boardManager.getBoard());
        game.movePiece(blackPawnOrigin, blackPawnDest);
        consoleUI.printBoard(boardManager.getBoard());
        game.movePiece(whitePawnOrigin2, whitePawnDest2);
        consoleUI.printBoard(boardManager.getBoard());
        game.movePiece(blackBishopOrigin, blackBishopDest);
        consoleUI.printBoard(boardManager.getBoard());
        game.movePiece(whitePawn2Origin, whitePawn2Dest);
        consoleUI.printBoard(boardManager.getBoard());
        game.movePiece(blackBishopOrigin2, blackBishopDest2);


        HashMap<Position, PieceState> expectedBoardPositionMap = TestUtils.getExpectedInitBoardPositionsMap();
        TestUtils.setExpectedPosition(expectedBoardPositionMap, whitePawnOrigin,whitePawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackPawnOrigin,blackPawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, whitePawnOrigin2, whitePawnDest2);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackBishopOrigin, blackBishopDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, whitePawn2Origin, whitePawn2Dest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackBishopOrigin2, blackBishopDest2);

        KingState whiteKingState = (KingState)(boardManager.getTileFromPosition(whiteKingPosition).getPieceState());
        if (!whiteKingState.isUnderThreat()){
            logger.printTestFailed(methodName, "White king is not under threat as i should be!");
        }


        if (isBoardMetExpectations(methodName, expectedBoardPositionMap)){
            logger.printTestPass(methodName);
        }
        else {
            logger.printTestFailed(methodName, "tile on board not as expected");
        }
        consoleUI.printBoard(boardManager.getBoard());
    }

    @Test
    public void canMoveBlockinkKingThreatePieceTest(){
        String methodName = "isWhiteKingThreatenedTest";

        game.startGame();
        BoardManager boardManager = game.getBoardManager();
        Position whitePawnOrigin = new Position('B', 2);
        Position whitePawnDest = new Position('B', 3);

        Position blackPawnOrigin = new Position('E', 7);
        Position blackPawnDest = new Position('E', 6);

        Position whitePawnOrigin2 = new Position('B', 3);
        Position whitePawnDest2 = new Position('B', 4);

        Position blackBishopOrigin = new Position('F', 8);
        Position blackBishopDest = new Position('B', 4);

        Position whitePawn2Origin = new Position('A', 2);
        Position whitePawn2Dest = new Position('A', 3);

        Position blackBishopOrigin2 = blackBishopDest;
        Position blackBishopDest2 = new Position('A', 5);

        Position whitePawnOrigin3 =  new Position('D', 2);;
        Position whitePawnDest3 = new Position('D', 3);

        Position whiteKingPosition = new Position('E', 1);


        game.movePiece(whitePawnOrigin, whitePawnDest);
        consoleUI.printBoard(boardManager.getBoard());
        game.movePiece(blackPawnOrigin, blackPawnDest);
        consoleUI.printBoard(boardManager.getBoard());
        game.movePiece(whitePawnOrigin2, whitePawnDest2);
        consoleUI.printBoard(boardManager.getBoard());
        game.movePiece(blackBishopOrigin, blackBishopDest);
        consoleUI.printBoard(boardManager.getBoard());
        game.movePiece(whitePawn2Origin, whitePawn2Dest);
        consoleUI.printBoard(boardManager.getBoard());
        game.movePiece(blackBishopOrigin2, blackBishopDest2);
        consoleUI.printBoard(boardManager.getBoard());
        game.movePiece(whitePawnOrigin3, whitePawnDest3);

        HashMap<Position, PieceState> expectedBoardPositionMap = TestUtils.getExpectedInitBoardPositionsMap();
        TestUtils.setExpectedPosition(expectedBoardPositionMap, whitePawnOrigin,whitePawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackPawnOrigin,blackPawnDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, whitePawnOrigin2, whitePawnDest2);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackBishopOrigin, blackBishopDest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, whitePawn2Origin, whitePawn2Dest);
        TestUtils.setExpectedPosition(expectedBoardPositionMap, blackBishopOrigin2, blackBishopDest2);


        KingState whiteKingState = (KingState)(boardManager.getTileFromPosition(whiteKingPosition).getPieceState());
        if (whiteKingState.isUnderThreat()){
            logger.printTestFailed(methodName, "White king is under threat as i should not be!");
        }
        if (isBoardMetExpectations(methodName, expectedBoardPositionMap)){
            logger.printTestPass(methodName);
        }
        else {
            logger.printTestFailed(methodName, "tile on board not as expected");
        }
        consoleUI.printBoard(boardManager.getBoard());
    }



    private Boolean isBoardMetExpectations(String methodName, HashMap<Position, PieceState> expectedBoardPositionMap) {
        Board board = game.getBoardManager().getBoard();
        Tile[] tiles = board.getTiles();
        for (Tile tile : tiles) {
            if (compareTileWithExpectation(tile, expectedBoardPositionMap) == false) {
                return false;
            }
        }
        return true;
    }

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
            logger.errorMsg(methodName, String.format("actual [%s] expected [%s] at tile [%s]",tilePieceState ,expectedPieceState, tile.getPosition().toString()));
            return false;
        }
    }



}
