package com.lchess.engine.piece.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lchess.engine.piece.view.PieceColorEnum;
import com.lchess.engine.piece.view.PieceTypeEnum;

/**
 * Created by liad on 20/09/2018.
 */
@JsonDeserialize(as = TurretState.class)
public class TurretState extends PieceState {

    public TurretState(PieceColorEnum color) {
        super(color);
        pieceType = PieceTypeEnum.TURRET;
    }

    public TurretState() {
    }
}
