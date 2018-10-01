package com.lchess.game;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lchess.engine.board.Board;
import com.lchess.engine.board.BoardManager;
import com.lchess.engine.board.Position;
import com.lchess.engine.board.Tile;
import com.lchess.engine.piece.model.*;
import com.lchess.engine.piece.view.PieceColorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


/**
 * Created by liad on 01/09/2018.
 */
@Component
public class GameImpl implements Game {
    @Autowired
    private BoardManager boardManager;
    private static Board board;

    private PieceColorEnum turn;
    @Override
    public void startGame() {
        boardManager = BoardManager.getInstanse();
        boardManager.resetBoard();
        board = boardManager.getBoard();

        turn = PieceColorEnum.WHITE;
    }

    public GameImpl() {
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    @Override
    public void saveState() {
        Board board = boardManager.getBoard();
        convertBoardToJSON(board);

    }

    @Override
    public void loadState() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(PawnState.class, KingState.class, BishopState.class, TurretState.class);

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {
            Board board = mapper.readValue(new File("lastSavedState.json"), Board.class);
            boardManager.setBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void convertBoardToJSON(Board board) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(PawnState.class, KingState.class, BishopState.class, TurretState.class);
        try {
            mapper.writeValue(new File("lastSavedState.json"), board);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void movePiece(Position origin, Position destination) {

        if (checkTurn(origin) == false){
            System.out.println(String.format("cannot move from [%s] to [%s] - wrong turn", origin, destination));
            return;
        }
        Boolean isCompleted = boardManager.tryMovePieceOnTheBoard(origin, destination);
        if (isCompleted){
            nextTurn();
        }
        else {
            System.out.println(String.format("cannot move from [%s] to [%s] - path not allow", origin, destination));
        }
    }

    private boolean checkTurn(Position origin) {
        Tile tileFromPosition = boardManager.getTileFromPosition(board, origin);
        PieceState pieceState = tileFromPosition.getPieceState();
        if (pieceState == null){
            return false;
        }
        PieceColorEnum color = pieceState.getColor();
        if (!color.equals(turn)){
            return false;
        }
        return true;
    }

    @Override
    public PieceColorEnum getCurrentTurn() {
        return turn;
    }

    private void nextTurn(){
        if (turn.equals(PieceColorEnum.WHITE)){
            turn = PieceColorEnum.BLACK;
        }
        else if (turn.equals(PieceColorEnum.BLACK)){
            turn = PieceColorEnum.WHITE;
        }
    }
}
