package com.lchess.engine.piece.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lchess.engine.piece.view.PieceColorEnum;
import com.lchess.engine.piece.view.PieceTypeEnum;

/**
 * Created by liad on 31/08/2018.
 */
@JsonDeserialize(as = PawnState.class)
public class PawnState extends PieceState{

    private Boolean isFirstMove;

    private Boolean canEnPassant;

    public PawnState(PieceColorEnum color) {
        super(color);
        isFirstMove = true;
        canEnPassant = false;
        pieceType = PieceTypeEnum.PAWN;
    }

    public PawnState() {
    }

    public Boolean getFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(Boolean firstMove) {
        isFirstMove = firstMove;
    }

    public Boolean getCanEnPassant() {
        return canEnPassant;
    }

    public void setCanEnPassant(Boolean canEnPassant) {
        this.canEnPassant = canEnPassant;
    }
}
