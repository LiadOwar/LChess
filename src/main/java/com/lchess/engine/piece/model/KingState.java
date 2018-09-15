package com.lchess.engine.piece.model;

import com.lchess.engine.piece.view.PieceColorEnum;
import com.lchess.engine.piece.view.PieceTypeEnum;

/**
 * Created by liad on 15/09/2018.
 */
public class KingState extends PieceState {

    private Boolean isUnderThreat = false;

    public KingState(PieceColorEnum color) {
        super(color);
        pieceType = PieceTypeEnum.KING;
    }

    public Boolean isUnderThreat(){
        return this.isUnderThreat;
    }

    public void setUnderThreat(Boolean underThreate) {
        isUnderThreat = underThreate;
    }
}
