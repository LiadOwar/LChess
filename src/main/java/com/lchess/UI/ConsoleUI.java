package com.lchess.UI;

import com.lchess.engine.board.Board;
import com.lchess.engine.board.Tile;
import com.lchess.engine.piece.model.PieceState;
import com.lchess.engine.piece.view.PieceColorEnum;
import com.lchess.engine.piece.view.PieceTypeEnum;
import com.lchess.game.Game;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liad on 31/08/2018.
 */
@Component
public class ConsoleUI extends UI {
    @Override
    public void printBoard(Board board) {
        Tile[] tiles = board.getTiles();
        System.out.println("********-Board-********");
        System.out.print("  ");
        for (int i = 0 ; i < 8; i++){
            System.out.print(String.format(" %c ",('A' + i)));
        }
        System.out.println("");
        for(Tile tile : tiles){
            if (tile.getPosition().getPosition().getxPos() == 'A'){
                System.out.print(String.format("%s ", tile.getPosition().getPosition().getyPos()));
            }
            printTile(tile);
            if (tile.getPosition().getPosition().getxPos() == 'H'){
                System.out.println(String.format(" %s", tile.getPosition().getPosition().getyPos()));
            }
        }
        System.out.print("  ");
        for (int i = 0 ; i < 8; i++){
            System.out.print(String.format(" %c ",('A' + i)));
        }
        System.out.println("");
    }

    @Override
    public PieceColorEnum getTurn(Game game) {
        return game.getCurrentTurn();
    }

    public String representBoard(Board board) {
        Tile[] tiles = board.getTiles();
        StringBuilder builder = new StringBuilder();
        System.out.println("********-Board-********");
        System.out.print("  ");
        for (int i = 0 ; i < 8; i++){
            System.out.print(String.format(" %c ",('A' + i)));
            builder.append(String.format(" %c ",('A' + i)));
        }
        System.out.println("");
        for(Tile tile : tiles){
            if (tile.getPosition().getPosition().getxPos() == 'A'){
                System.out.print(String.format("%s ", tile.getPosition().getPosition().getyPos()));
                builder.append(String.format("%s ", tile.getPosition().getPosition().getyPos()));
            }
            builder.append(printTile(tile));
            if (tile.getPosition().getPosition().getxPos() == 'H'){
                System.out.println(String.format(" %s", tile.getPosition().getPosition().getyPos()));
                builder.append(String.format(" %s", tile.getPosition().getPosition().getyPos()));
            }
        }
        System.out.print("  ");
        for (int i = 0 ; i < 8; i++){
            builder.append((String.format(" %c ",('A' + i))));

            System.out.print(String.format(" %c ",('A' + i)));
        }
        builder.append("");
        System.out.println("");
        return builder.toString();
    }

    public List getBoardTiles(Board board) {
        List<Tile> ret = new ArrayList<>();
        Tile[] tiles = board.getTiles();
        for (int i = 0 ; i <tiles.length; i++){
            ret.add(tiles[i]);
        }
        return ret;
    }

    private String printTile(Tile tile) {
        StringBuilder tileFormate = new StringBuilder();
        tileFormate.append("[");
        if (tile.getPieceState() == null){
            tileFormate.append(" ");
        }
        else {
            PieceState pieceState = tile.getPieceState();
            tileFormate.append(getPieceTypeStringRep(pieceState));
        }
        tileFormate.append("]");
        System.out.print(tileFormate);
        return tileFormate.toString();
    }

    private String getPieceTypeStringRep(PieceState pieceState){
        PieceTypeEnum typeName = pieceState.getPieceType();
        String ret = "";
        switch (typeName){
            case PAWN:
                if (pieceState.getColor().equals(PieceColorEnum.WHITE)){
                    ret = "P";
                }
                else {
                    ret = "p";
                }
                break;
            case BISHOP:
                if (pieceState.getColor().equals(PieceColorEnum.WHITE)){
                    ret = "B";
                } else {
                    ret = "b";
                }
                break;
            case KING:
                if (pieceState.getColor().equals(PieceColorEnum.WHITE)){
                    ret = "K";
                } else {
                    ret = "k";
                }
                break;
            case TURRET:
                if (pieceState.getColor().equals(PieceColorEnum.WHITE)){
                    ret = "T";
                } else {
                    ret = "t";
                }
                break;
        }
        return ret;
    }

    public ConsoleUI() {
    }
}
