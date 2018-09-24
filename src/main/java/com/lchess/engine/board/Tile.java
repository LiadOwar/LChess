package com.lchess.engine.board;

import com.lchess.engine.piece.model.PieceState;

/**
 * Created by liad on 31/08/2018.
 */
public class Tile {

    private Position position;

    private Boolean isOccupide = false;

    private PieceState pieceState;

    public Tile(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Boolean getOccupide() {
        return isOccupide;
    }

    public void setOccupide(Boolean occupide) {
        isOccupide = occupide;
    }

    public PieceState getPieceState() {

        return pieceState;
    }

    public void setPieceState(PieceState pieceState) {
        isOccupide = true;
        this.pieceState = pieceState;
    }

    public void removePiece(Tile tile){
        isOccupide = false;
        pieceState = null;
    }
}
