package com.lchess.engine.piece.model;

import com.lchess.engine.piece.view.Piece;
import com.lchess.engine.piece.view.PieceColorEnum;
import com.lchess.engine.piece.view.PieceTypeEnum;

/**
 * Created by liad on 31/08/2018.
 */
public abstract class PieceState {

    private Boolean isAlive;

    protected PieceTypeEnum pieceType;

    private PieceColorEnum color;

    public PieceState(PieceColorEnum color) {
        isAlive = true;
        this.color = color;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public PieceColorEnum getColor() {
        return color;
    }

    public PieceTypeEnum getPieceType(){
        return pieceType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PieceState that = (PieceState) o;

        if (isAlive != null ? !isAlive.equals(that.isAlive) : that.isAlive != null) return false;
        if (pieceType != that.pieceType) return false;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        int result = isAlive != null ? isAlive.hashCode() : 0;
        result = 31 * result + (pieceType != null ? pieceType.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
