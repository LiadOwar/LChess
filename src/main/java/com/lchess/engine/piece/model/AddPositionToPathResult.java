package com.lchess.engine.piece.model;

/**
 * Created by liad on 26/09/2018.
 */
public class AddPositionToPathResult {
    private Boolean success = false;
    private Boolean lastPositionOccupiedByEnemyPiece = false;

    public AddPositionToPathResult(Boolean success, Boolean lastPositionOccupiedByEnemyPiece) {
        this.success = success;
        this.lastPositionOccupiedByEnemyPiece = lastPositionOccupiedByEnemyPiece;
    }

    public AddPositionToPathResult() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getLastPositionOccupiedByEnemyPiece() {
        return lastPositionOccupiedByEnemyPiece;
    }

    public void setLastPositionOccupiedByEnemyPiece(Boolean lastPositionOccupiedByEnemyPiece) {
        this.lastPositionOccupiedByEnemyPiece = lastPositionOccupiedByEnemyPiece;
    }
}
