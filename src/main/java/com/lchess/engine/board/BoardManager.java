package com.lchess.engine.board;

import com.lchess.engine.piece.model.*;
import com.lchess.engine.piece.model.pojo.PieceMovementInfo;
import com.lchess.engine.piece.model.pojo.PieceMovementPath;
import com.lchess.engine.piece.services.PieceMovementManager;
import com.lchess.engine.piece.services.PieceMovementManagerImpl;
import com.lchess.engine.piece.view.PieceColorEnum;
import com.lchess.engine.piece.view.PieceTypeEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by liad on 31/08/2018.
 */
@Component
public class BoardManager {

    private static HashMap<Position, Integer> positionIntegerHashMap = new HashMap<>();

    private static BoardManager instanse;

    private static Board board;

    private static PieceMovementManager pieceMovementManager;

    private BoardManager(){
        initPositionConvertor();
        resetBoard();
    }

    static {
        try{
            instanse = new BoardManager();
            pieceMovementManager = new PieceMovementManagerImpl();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void resetBoard(){
        board = new Board();
        initBoard();
        arrangeBoard();
    }
    public static BoardManager getInstanse() {
        return instanse;
    }

    public static Board getBoard() {
        return board;
    }

    private void initBoard(){
        Tile[] tiles = board.getTiles();
        Position position = new Position(new ChessCoordinate('A', 8));
        Tile tile;
        char xPos = position.getPosition().getxPos();
        int yPos = position.getPosition().getyPos();
        for (int i = 0 ; i < tiles.length; i++){

            position = new Position(new ChessCoordinate(xPos, yPos));
            tile = new Tile(position);
            tiles[i] = tile;
            xPos++;
            if (xPos > 'H') {
                xPos = 'A';
                yPos--;
                if (yPos < 1){
                    break;
                }
            }
        }
    }

    public HashMap<Position, Integer> getPositionIntegerHashMap() {
        return positionIntegerHashMap;
    }

    public void setPieceOnBoard(PieceState pieceOnBoard, Position position, Board board){
        Tile[] tiles = board.getTiles();
        Integer intPosition = convertPositionToInt(position);
        tiles[intPosition].setPieceState(pieceOnBoard);

    }

    //todo move this into board utils
    public int convertPositionToInt(Position position){
        return positionIntegerHashMap.get(position);
    }

    //TODO remove this
    public Tile getTileFromPosition(Board board, Position position){
        Integer idx = positionIntegerHashMap.get(position);
        Tile tile = board.getTiles()[idx];
        return tile;
    }

    public Tile getTileFromPosition(Position position){
        Integer idx = positionIntegerHashMap.get(position);
        Tile tile = board.getTiles()[idx];
        return tile;
    }

    //todo maby this should be private
    public static PieceModel getPieceModel(PieceState pieceState){
        switch (pieceState.getPieceType()){
            case PAWN:
                return new PawnModel();
            case BISHOP:
                return new BishopModel();

                default: return null;
        }

    }

    public static Boolean tryMovePieceOnTheBoard(Position origin, Position destenation){
        Tile originTile = BoardUtils.getTileFromPosition(origin);
        Tile destinationTile = BoardUtils.getTileFromPosition(destenation);

        PieceState pieceState = originTile.getPieceState();
        PieceModel pieceModel = getPieceModel(pieceState);
        PieceMovementInfo pieceMovementInfo = pieceModel.getPieceMovementInfo(origin, pieceState);
        HashSet<PieceMovementPath> allowedPaths = pieceMovementInfo.getAllowedPaths();

        PieceMovementPath correctPath = pieceMovementManager.findPathToDestination(allowedPaths, origin, destenation);
        if (correctPath == null){
            return false;
        }
        movePieceToDestination(originTile, pieceState, destinationTile);
        if (pieceState.getPieceType() == PieceTypeEnum.PAWN){
            PawnState pawnState = (PawnState)pieceState;
            pawnState.setFirstMove(false);
        }
        return true;
        }

    public boolean checkIfCanMoveToPosition(PieceMovementPath path, Position position) {
        Position startPosition = path.getPathStartingPosition(path);

        Tile startMovementTile = getTileFromPosition(board, startPosition);
        PieceState startPieceState = startMovementTile.getPieceState();
        Tile destinationTile = getTileFromPosition(board, position);
        PieceState destinationPieceState = destinationTile.getPieceState();

        if (!validatePawnMove(path, startPieceState, destinationTile, destinationPieceState))
        {
            return false;
        }

        if (destinationPieceState != null){
            if (canAttack(startPieceState, destinationPieceState)){

                return true;
            }
            else {
                return false;
            }
        }
        return true;

    }

    private boolean validatePawnMove(PieceMovementPath path, PieceState startPieceState, Tile destinationTile, PieceState destinationPieceState) {
        if (startPieceState.getPieceType() == PieceTypeEnum.PAWN){
            PieceColorEnum color = startPieceState.getColor();
            Position position = destinationTile.getPosition();
            char xPos = position.getPosition().getxPos();
            char borderedXpos = path.getPath().get(path.getPath().size() - 1).getPosition().getxPos();
            Integer yPos = position.getPosition().getyPos();
            Integer borderedYpos = path.getPath().get(path.getPath().size() - 1).getPosition().getyPos();
            if (destinationPieceState != null){


                if (xPos == borderedXpos){
                    System.out.println("PAWN cannot attack forward");
                    return false;
                }
                else if (canAttack(startPieceState, destinationPieceState)){
                    switch (color) {
                        case WHITE:
                            if ((xPos == borderedXpos + 1 && yPos == borderedYpos + 1) || (xPos == borderedXpos - 1 && yPos == borderedYpos + 1)){
                                return true;
                            }
                            break;
                        case BLACK:
                            if ((xPos == borderedXpos + 1 && yPos == borderedYpos - 1) || (xPos == borderedXpos - 1 && yPos == borderedYpos - 1)){
                                return true;
                            }
                            break;
                    }
                    return false;
                }
            }
            if (xPos != borderedXpos){
                System.out.println("PAWN cannot move diagonally without attack");
                return false;
            }

        }
        return true;
    }

    private void attack(Tile startMovementTile, PieceState startPieceState, Tile destinationTile) {
        destinationTile.setPieceState(startPieceState);
        System.out.println(String.format("REMOVE [%s, %s]", startPieceState.getPieceType(), startPieceState.getColor()));
        startMovementTile.setPieceState(null);
        startMovementTile.setOccupide(false);
    }

    private Boolean canAttack(PieceState currentTurnPieceState, PieceState destinationPieceState) {
//        if (checkIfMoveWontThreatFriendlyKing()){
//            return false;
//        }
        if (are2PieacesSameTeam(currentTurnPieceState, destinationPieceState)){
            return false;
        }
        else {
            return true;
        }
    }

    private Boolean are2PieacesSameTeam(PieceState currentTurnPieceState, PieceState destinationPieceState){
        if (!currentTurnPieceState.getColor().equals(destinationPieceState.getColor())){
            return false;
        }
        else {
            return true;
        }
    }

    private void arrangeBoard(){
        arrangeWhitePieces();
        arrangeBlackPieces();

    }

    private  void arrangeWhitePieces() {
        arrangePawns(PieceColorEnum.WHITE);
        arrangeBishops(PieceColorEnum.WHITE);
    }

    private  void arrangeBlackPieces() {
        arrangePawns(PieceColorEnum.BLACK);
        arrangeBishops(PieceColorEnum.BLACK);
    }

    private void arrangePawns(PieceColorEnum colorEnum){
        Position position = null;
        switch (colorEnum){
            case WHITE: {
                position = new Position('A', 2);
                break;
            }
            case BLACK:
                position = new Position('A', 7);
                break;
        }
        char xPos = position.getPosition().getxPos();
        int yPos = position.getPosition().getyPos();
        for (int i = 0 ; i < 8 ; i++){
            PawnState pawnState = new PawnState(colorEnum);
            position.setCoordinate(xPos, yPos);
            setPieceOnBoard(pawnState, position, board);
            xPos++;
        }

    }

    private void arrangeBishops(PieceColorEnum colorEnum) {
        Position position1 = null;
        Position position2 = null;
        switch (colorEnum){
            case WHITE: {
                position1 = new Position('C', 1);
                position2 = new Position('F', 1);
                break;
            }
            case BLACK:
                position1 = new Position('C', 8);
                position2 = new Position('F', 8);
                break;
        }

        BishopState bishop1State = new BishopState(colorEnum);
        BishopState bishop2State = new BishopState(colorEnum);

        setPieceOnBoard(bishop1State, position1, board);
        setPieceOnBoard(bishop2State, position2, board);

    }


    private static void movePieceToDestination(Tile originTile, PieceState pieceState, Tile destinationTile) {

        originTile.setPieceState(null);
        originTile.setOccupide(false);
        destinationTile.setOccupide(true);
        destinationTile.setPieceState(pieceState);
    }

    private static MovementTypeEnum getMovmentTypeFromDestination(Position origin, Position destination){
        //todo implement
        return MovementTypeEnum.FORWARD;
    }

    private static void initPositionConvertor() {
        Position position = new Position(new ChessCoordinate('A', 8));
        char xPos = position.getPosition().getxPos();
        int yPos = position.getPosition().getyPos();
        for (int i = 0 ; i < 64 ; i++){
            position = new Position(new ChessCoordinate(xPos, yPos));
            positionIntegerHashMap.put(position, i);
            xPos++;
            if (xPos > 'H') {
                xPos = 'A';
                yPos--;
                if (yPos < 1){
                    break;
                }
            }
        }
    }



}
