package com.lchess.engine.piece.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lchess.engine.piece.view.PieceColorEnum;
import com.lchess.engine.piece.view.PieceTypeEnum;

/**
 * Created by liad on 01/09/2018.
 */
@JsonDeserialize(as = BishopState.class)
public class BishopState extends PieceState {
    public BishopState(PieceColorEnum color) {
        super(color);
        pieceType = PieceTypeEnum.BISHOP;
    }

    public BishopState() {
    }
}
