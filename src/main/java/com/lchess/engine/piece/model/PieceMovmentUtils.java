package com.lchess.engine.piece.model;

import com.lchess.engine.board.ChessCoordinate;
import com.lchess.engine.board.Position;
import com.lchess.engine.piece.view.PieceColorEnum;

/**
 * Created by liad on 31/08/2018.
 */
public class PieceMovmentUtils {
    public static Position moveForward(Position origin, PieceColorEnum colorEnum, int numMoves){
        Position ret;
        int yPos = origin.getPosition().getyPos();
        switch (colorEnum){
            case WHITE:
                yPos += numMoves;
                break;

            case BLACK:
                yPos -= numMoves;
                break;
                default:
                    break;
        }
        ChessCoordinate updatedCoordinate = new ChessCoordinate(origin.getPosition().getxPos(), yPos);
        ret = new Position(updatedCoordinate);
        return ret;
    }

    public static Position moveDiagonalUpLeft(Position origin, PieceColorEnum colorEnum, int numMoves){
        Position ret;
        char xPos = origin.getPosition().getxPos();
        int yPos = origin.getPosition().getyPos();
        switch (colorEnum){
            case WHITE:
                yPos += numMoves;
                xPos -= numMoves;
                break;

            case BLACK:
                yPos -= numMoves;
                xPos -= numMoves;
                break;
            default:
                break;
        }
        ChessCoordinate updatedCoordinate = new ChessCoordinate(xPos, yPos);
        ret = new Position(updatedCoordinate);
        return ret;
    }
}