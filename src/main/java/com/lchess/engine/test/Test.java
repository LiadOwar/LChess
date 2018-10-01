package com.lchess.engine.test;

import com.lchess.UI.ConsoleUI;
import com.lchess.UI.UI;
import com.lchess.common.Logger;
import com.lchess.engine.board.*;
import com.lchess.engine.piece.model.*;
import com.lchess.engine.piece.view.PieceColorEnum;
import com.lchess.engine.piece.view.PieceTypeEnum;
import com.lchess.engine.test.utils.TestUtils;
import com.lchess.game.Game;
import com.lchess.game.GameImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liad on 31/08/2018.
 */
public class Test {
    private static BoardManager boardManager = BoardManager.getInstanse();
    private static Logger logger;
    private Game game = new GameImpl();

    private static UI ui = new ConsoleUI();

    private static void positionToIntTest(){
        Board board = boardManager.getBoard();
        HashMap<Position, Integer> positionIntegerHashMap = boardManager.getPositionIntegerHashMap();
        Tile[] tiles = board.getTiles();
        for (Tile tile : tiles){
            Position position = tile.getPosition();
            Integer converted = boardManager.convertPositionToInt(position);
            System.out.print(String.format("[%s,%s -> %s]",
                    position.getPosition().getxPos(), position.getPosition().getyPos(), converted));
            if (position.getPosition().getxPos() == 'H'){
                System.out.println("");
            }
        }

    }

    private static void movePawnTest(PieceColorEnum colorEnum, Position position){
        Board board = TestUtils.getBoardWithWhitePawn(colorEnum, position);
        TestUtils.printTurnNum(0);

        ui.printBoard(board);
        Position position1 = PieceMovmentUtils.moveForward(position, colorEnum, 1);
        Tile prevTile = boardManager.getTileFromPosition(board, position);
        Tile currentTile = boardManager.getTileFromPosition(board, position1);

        currentTile.setPieceState(prevTile.getPieceState());
        prevTile.setPieceState(null);

        TestUtils.printTurnNum(1);
        ui.printBoard(board);
    }
    private static Boolean moveWhitePownForwardTest(){
        Boolean ret = false;
        String methodName = "moveWhitePownForwardTest";
        Position origin = new Position('A', 2);
        Board board = TestUtils.getBoardWithWhitePawn(PieceColorEnum.WHITE, origin);
        Position position1 = PieceMovmentUtils.moveForward(origin, PieceColorEnum.WHITE, 1);

        Tile prevTile = boardManager.getTileFromPosition(board, origin);
        Tile currentTile = boardManager.getTileFromPosition(board, position1);

        currentTile.setPieceState(prevTile.getPieceState());
        prevTile.setPieceState(null);

        if (currentTile.getPieceState() == null){
            return false;
        }
        if (prevTile.getPieceState() != null){
            return false;
        }
        Position expectedCurrentPosition = new Position('A', 3);
        Tile testedCurrentTile = boardManager.getTileFromPosition(board, position1);
        if (!testedCurrentTile.getPosition().equals(expectedCurrentPosition)){
            logger.printTestFailed(methodName, "position is not as expected");
            ret = false;
        }
        else {
            logger.printTestPass(methodName);
            ret = true;
        }
        return ret;

    }



    public static void main(String[] args){
     //   positionToIntTest();
//        movePawnTest(PieceColorEnum.BLACK, new Position(new ChessCoordinate('B', 2)));
        moveWhitePownForwardTest();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
