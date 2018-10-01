package com.lchess.game;

import com.lchess.UI.ConsoleUI;
import com.lchess.UI.PieceMovementClient;
import com.lchess.engine.board.BoardManager;
import com.lchess.engine.board.Position;
import com.lchess.engine.board.PositionConvertor;
import com.lchess.engine.board.Tile;
import com.lchess.engine.piece.view.PieceColorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liad on 05/09/2018.
 */
@RestController
public class GameController {

    @Autowired
    private ConsoleUI consoleUI;

    @Autowired
    private Game game;

    BoardManager boardManager = BoardManager.getInstanse();


    @CrossOrigin
    @RequestMapping("/game/start")
    public void startGame(){
//        game = new GameImpl();
        game.startGame();
        System.out.println("Starting game");
    }

    @CrossOrigin
    @RequestMapping("/game")
    public List getBoardTiles(){
        List<Tile> ret = consoleUI.getBoardTiles(game.getBoardManager().getBoard());
        consoleUI.printBoard(boardManager.getBoard());
        return ret;
//        return consoleUI.representBoard(boardManager.getBoard());

    }

    @CrossOrigin
    @RequestMapping("/game/turn")
    public PieceColorEnum getTurn(){
        PieceColorEnum ret = consoleUI.getTurn(game);
        System.out.println("current turn: " + ret);
        return ret;
    }

    @CrossOrigin
    @RequestMapping(method= RequestMethod.POST, value = "/game/move")
    public void tryMovePiece(@RequestBody PieceMovementClient pieceMovementClient){
       game.movePiece(PositionConvertor.convertClientPosition(pieceMovementClient.getStartPosition()),PositionConvertor.convertClientPosition(pieceMovementClient.getDestPosition()));
    }

    @CrossOrigin
    @RequestMapping("/game/saveState")
    public void saveState(){
        game.saveState();
        System.out.println("Saving state");
    }

    @CrossOrigin
    @RequestMapping("/game/loadState")
    public void loadState(){
        game.loadState();
        System.out.println("Load state");
    }
}
