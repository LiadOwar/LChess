package com.lchess.engine.board;

import com.lchess.engine.piece.model.PieceState;

/**
 * Created by liad on 31/08/2018.
 */
public class Tile {

    private Position position;


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

    public PieceState getPieceState() {

        return pieceState;
    }

    public void setPieceState(PieceState pieceState) {
        this.pieceState = pieceState;
    }

    public void removePiece(Tile tile){
        pieceState = null;
    }
}
