package com.lchess.engine.board;

/**
 * Created by liad on 31/08/2018.
 */
public class BoardUtils {
    private static BoardManager boardManager = BoardManager.getInstanse();
    private static Board board;

    public BoardUtils() {
        this.board = boardManager.getBoard();
    }

    public static Boolean isOutOfBoard(Position pos){
        Boolean ret = false;
        char xPos = pos.getCoordinate().getxPos();
        int yPos = pos.getCoordinate().getyPos();
        if (xPos < 65 || xPos > 72){
            ret = true;
        }
        else if (yPos < 1 || yPos > 8){
            ret = true;
        }
        return ret;
    }
    public static Tile getTileFromPosition(Position position){
        int i = boardManager.convertPositionToInt(position);
        board = boardManager.getBoard();
        Tile tile = board.getTiles()[i];
        return tile;
    }
}
