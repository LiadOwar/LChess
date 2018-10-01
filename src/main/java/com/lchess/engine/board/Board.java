package com.lchess.engine.board;

/**
 * Created by liad on 31/08/2018.
 */
public class Board {
    private Tile[] tiles;

    public Board() {
        tiles = new Tile[64];
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }
}
