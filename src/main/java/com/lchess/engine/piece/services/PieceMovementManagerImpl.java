package com.lchess.engine.piece.services;

import com.lchess.engine.board.Position;
import com.lchess.engine.piece.model.pojo.PieceMovementPath;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by liad on 01/09/2018.
 */
public class PieceMovementManagerImpl implements PieceMovementManager {

    public PieceMovementPath findPathToDestination(HashSet<PieceMovementPath> allowedPaths, Position origin, Position destenation) {
        PieceMovementPath ret = null;
        for(PieceMovementPath tempPath : allowedPaths){
            Position pathDestination = getDestinationFromPath(tempPath);
            if (pathDestination.equals(destenation)){
                ret = tempPath;
                break;
            }
        }
        return ret;
    }

    private static Position getDestinationFromPath(PieceMovementPath pieceMovementPath){
        ArrayList<Position> path = pieceMovementPath.getPath();
        Position destination = path.get(path.size() - 1);
        return destination;
    }


}
