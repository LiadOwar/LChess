package com.lchess.engine.board;

import com.lchess.engine.piece.model.*;
import com.lchess.engine.piece.model.pojo.PieceMovementInfo;
import com.lchess.engine.piece.model.pojo.PieceMovementPath;
import com.lchess.engine.piece.services.PieceMovementManager;
import com.lchess.engine.piece.services.PieceMovementManagerImpl;
import com.lchess.engine.piece.view.PieceColorEnum;
import com.lchess.engine.piece.view.PieceTypeEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public void setBoard(Board board) {
        this.board =board;
    }

    private void initBoard(){
        Tile[] tiles = board.getTiles();
        Position position = new Position(new ChessCoordinate('A', 8));
        Tile tile;
        char xPos = position.getCoordinate().getxPos();
        int yPos = position.getCoordinate().getyPos();
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
            case KING:
                return new KingModel();
            case TURRET:
                return new TurretModel();

                default: return null;
        }

    }

    public Boolean tryMovePieceOnTheBoard(Position origin, Position destenation){
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


        if (isOwnKingUnderThreat(pieceState.getColor())) {

        }

        if (tryMovePieceRevertIfCannotPerform(originTile, destinationTile, pieceState) == false){
            return  false;
        }
        //Check if move will result threat enemy king
        getPieceWhoThreatsKing();
        return true;
    }

    private Boolean tryMovePieceRevertIfCannotPerform(Tile originTile, Tile destinationTile, PieceState pieceState) {
        Boolean ret= true;
        Tile originalDestinationTile = destinationTile;
        Tile originalOrginTile = originTile;
        getPieceWhoThreatsKing();
        movePieceToDestination(originTile, pieceState, destinationTile);
        if (pieceState.getPieceType() == PieceTypeEnum.PAWN){
            PawnState pawnState = (PawnState)pieceState;
            pawnState.setFirstMove(false);
        }
        if (isOwnKingUnderThreat(pieceState.getColor())){
            System.out.println("detect king under threat");
            movePieceToDestination(originalDestinationTile, originalDestinationTile.getPieceState(), originalOrginTile);
            ret =  false;

        }
        getPieceWhoThreatsKing();
        return ret;
    }

    public Tile getPieceWhoThreatsKing() {
        ArrayList<Tile> allLiveWhitePiecesByColor = getAllLivePiecesByColor(PieceColorEnum.WHITE);
        ArrayList<Tile> allLiveBlackPiecesByColor = getAllLivePiecesByColor(PieceColorEnum.BLACK);
        Tile whiteKingTile = getKingTile(PieceColorEnum.WHITE);
        Tile blackKingTile = getKingTile(PieceColorEnum.BLACK);
        Tile tile = getWhitePieceTileWithPathToBlackKing(allLiveWhitePiecesByColor, blackKingTile);
        if (tile != null) {
            return tile;
        }
        else tile = getBlackPieceTileWithPathToWhiteKing(allLiveBlackPiecesByColor, whiteKingTile);
        if (tile != null) {
            return tile;
        }
        return null;
    }

    private Tile getWhitePieceTileWithPathToBlackKing(ArrayList<Tile> allLiveWhitePiecesByColor, Tile blackKingTile) {
        System.out.println(String.format("black king tile [%s]", blackKingTile.getPosition()));
        KingState enemyKingState = (KingState) blackKingTile.getPieceState();
        for (Tile tile : allLiveWhitePiecesByColor){
            PieceMovementPath pathToEnemyKing = getPathToDestination(tile.getPosition(), blackKingTile.getPosition());
            if (pathToEnemyKing != null){
                System.out.println(String.format("[%s] king is threatened by [%s] [%s] at [%s]", enemyKingState.getColor(), tile.getPieceState().getColor(), tile.getPieceState().getPieceType(), tile.getPosition()));
                enemyKingState.setUnderThreat(true);
                tile.getPieceState().setThreatEnemyKing(true);
                return tile;
            }
            else {
                tile.getPieceState().setThreatEnemyKing(false);
            }
        }
        enemyKingState.setUnderThreat(false);
        return null;
    }

    private Tile getBlackPieceTileWithPathToWhiteKing(ArrayList<Tile> allLiveBlackPiecesByColor, Tile blackKingTile) {
        KingState enemyKingState = (KingState) blackKingTile.getPieceState();
        for (Tile tile : allLiveBlackPiecesByColor){
            PieceMovementPath pathToEnemyKing = getPathToDestination(tile.getPosition(), blackKingTile.getPosition());
            if (pathToEnemyKing != null){
                System.out.println(String.format("[%s] king is threatened by [%s] [%s] at [%s]", enemyKingState.getColor(), tile.getPieceState().getColor(), tile.getPieceState().getPieceType(), tile.getPosition()));
                enemyKingState.setUnderThreat(true);
                tile.getPieceState().setThreatEnemyKing(true);
                return tile;
            }
            else {
                tile.getPieceState().setThreatEnemyKing(false);
            }
        }
        enemyKingState.setUnderThreat(false);
        return null;
    }

    private static Tile getKingTile(PieceColorEnum color) {
        ArrayList<Tile> allLivePiecesByColor = getAllLivePiecesByColor(color);
        Tile kingTile = findKing(allLivePiecesByColor);
        return kingTile;

    }

    private static Tile findKing(ArrayList<Tile> allLivePiecesByColor) {
        for (Tile tile : allLivePiecesByColor){
            if (tile.getPieceState().getPieceType().equals(PieceTypeEnum.KING)){
                return tile;
            }
        }
        System.out.println("ERROR. no king was found");
        return null;
    }

    private static PieceColorEnum getOpositeColor(PieceColorEnum friendlyColor) {
        PieceColorEnum ret = PieceColorEnum.WHITE;
        if (friendlyColor.equals(PieceColorEnum.WHITE)){
            ret = PieceColorEnum.BLACK;
        }
        else {
            ret = PieceColorEnum.WHITE;
        }
        return ret;
    }

    private PieceMovementPath getPathToDestination(Position origin, Position destination){
        Tile originTile = BoardUtils.getTileFromPosition(origin);

        PieceState pieceState = originTile.getPieceState();
        PieceModel pieceModel = getPieceModel(pieceState);
        PieceMovementInfo pieceMovementInfo = pieceModel.getPieceMovementInfo(origin, pieceState);
        HashSet<PieceMovementPath> allowedPaths = pieceMovementInfo.getAllowedPaths();

        PieceMovementPath correctPath = pieceMovementManager.findPathToDestination(allowedPaths, origin, destination);
       return correctPath;
    }
    private static ArrayList<Tile> getAllLivePiecesByColor(PieceColorEnum color) {
        Tile[] tiles = getBoard().getTiles();
        ArrayList<Tile> ret = new ArrayList<>();
        for (int i = 0 ; i < tiles.length ; i++){
            if (tiles[i].getPieceState() != null && tiles[i].getPieceState().getColor().equals(color)){
                ret.add(tiles[i]);
            }
        }
        return ret;
    }

    public AddPositionToPathResult checkIfCanMoveToPosition(PieceMovementPath path, Position position) {
        AddPositionToPathResult result = new AddPositionToPathResult();
        Position startPosition = path.getPathStartingPosition(path);

        Tile startMovementTile = getTileFromPosition(board, startPosition);
        PieceState startPieceState = startMovementTile.getPieceState();
        Tile destinationTile = getTileFromPosition(board, position);
        PieceState destinationPieceState = destinationTile.getPieceState();

        if (!validatePawnMove(path, startPieceState, destinationTile, destinationPieceState))
        {
            result.setSuccess(false);

        }
        else if (destinationPieceState != null){
            if (canAttack(startPieceState, destinationPieceState)){
                if (result.getLastPositionOccupiedByEnemyPiece()){
                    result.setSuccess(false);
                    result.setLastPositionOccupiedByEnemyPiece(false);
                }
                else {
                    result.setSuccess(true);
                    result.setLastPositionOccupiedByEnemyPiece(true);
                }
            }
            else {
                result.setSuccess(false);
            }
        }
        else {
            result.setSuccess(true);
        }
        return result;
    }

    public boolean isOwnKingUnderThreat(PieceColorEnum color) {
        getPieceWhoThreatsKing();
        Tile kingTile = getKingTile(color);
        KingState kingState = (KingState) (kingTile.getPieceState());
        if (kingState.isUnderThreat()){
            return true;
        }
        return false;
    }

    private boolean validatePawnMove(PieceMovementPath path, PieceState startPieceState, Tile destinationTile, PieceState destinationPieceState) {
        if (startPieceState.getPieceType() == PieceTypeEnum.PAWN){
            PieceColorEnum color = startPieceState.getColor();
            Position position = destinationTile.getPosition();
            char xPos = position.getCoordinate().getxPos();
            char borderedXpos = path.getPath().get(path.getPath().size() - 1).getCoordinate().getxPos();
            Integer yPos = position.getCoordinate().getyPos();
            Integer borderedYpos = path.getPath().get(path.getPath().size() - 1).getCoordinate().getyPos();
            if (destinationPieceState != null){
                if (xPos == borderedXpos){
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
                return false;
            }

        }
        return true;
    }

    private void attack(Tile startMovementTile, PieceState startPieceState, Tile destinationTile) {
        destinationTile.setPieceState(startPieceState);
        System.out.println(String.format("REMOVE [%s, %s]", startPieceState.getPieceType(), startPieceState.getColor()));
        startMovementTile.setPieceState(null);
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
        arrangeKings(PieceColorEnum.WHITE);
        arrangeTurrets(PieceColorEnum.WHITE);
    }

    private  void arrangeBlackPieces() {
        arrangePawns(PieceColorEnum.BLACK);
        arrangeBishops(PieceColorEnum.BLACK);
        arrangeKings(PieceColorEnum.BLACK);
        arrangeTurrets(PieceColorEnum.BLACK);
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
        char xPos = position.getCoordinate().getxPos();
        int yPos = position.getCoordinate().getyPos();
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

    private void arrangeKings(PieceColorEnum colorEnum){
        Position position1 = null;
        Position position2 = null;
        switch (colorEnum){
            case WHITE: {
                position1 = new Position('E', 1);
                break;
            }
            case BLACK:
                position1 = new Position('E', 8);
                break;
        }

        KingState kingState = new KingState(colorEnum);
        setPieceOnBoard(kingState, position1, board);
    }

    private void arrangeTurrets(PieceColorEnum colorEnum) {
        Position position1 = null;
        Position position2 = null;
        switch (colorEnum){
            case WHITE: {
                position1 = new Position('A', 1);
                position2 = new Position('H', 1);
                break;
            }
            case BLACK:
                position1 = new Position('A', 8);
                position2 = new Position('H', 8);
                break;
        }

        TurretState turret1State = new TurretState(colorEnum);
        TurretState turret2State = new TurretState(colorEnum);

        setPieceOnBoard(turret1State, position1, board);
        setPieceOnBoard(turret2State, position2, board);

    }


    private static void movePieceToDestination(Tile originTile, PieceState pieceState, Tile destinationTile) {

        originTile.setPieceState(null);
        destinationTile.setPieceState(pieceState);
    }

    private static MovementTypeEnum getMovmentTypeFromDestination(Position origin, Position destination){
        //todo implement
        return MovementTypeEnum.FORWARD;
    }

    private static void initPositionConvertor() {
        Position position = new Position(new ChessCoordinate('A', 8));
        char xPos = position.getCoordinate().getxPos();
        int yPos = position.getCoordinate().getyPos();
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

    private Boolean isPathAttackKing(PieceMovementPath pieceMovementPath, PieceColorEnum pieceColorEnum) {
        Tile kingTile = getKingTile(board, pieceColorEnum);

        if (isTileOnPath(kingTile, pieceMovementPath)){
            System.out.println(String.format("[%] king is treatened by", pieceColorEnum.toString(), pieceMovementPath.getPathStartingPosition(pieceMovementPath)));
            return true;
        }
        else {
            return false;
        }
    }

    private Tile getKingTile(Board board, PieceColorEnum pieceColorEnum) {
        Tile kingTile = null;
        Tile[] tiles = board.getTiles();
        for (int i = 0 ; i < tiles.length ; i++) {
            Tile currentTIle = tiles[i];
            PieceState currentPieceState = currentTIle.getPieceState();

            if (currentPieceState == null){
                continue;
            }
            PieceTypeEnum currentPieceType = currentTIle.getPieceState().getPieceType();
            PieceColorEnum currentPieceColor = currentTIle.getPieceState().getColor();
            if (currentPieceType.equals(PieceTypeEnum.KING) && currentPieceColor.equals(pieceColorEnum)){
                kingTile = currentTIle;
                break;
            }
        }
        return kingTile;
    }

    public Boolean isTileOnPath(Tile tile, PieceMovementPath pieceMovementPath){
        String methodName = "isTileOnPath";
        ArrayList<Position> path = pieceMovementPath.getPath();

        for (Position position : path) {
            Tile tileFromPosition = getTileFromPosition(position);
            if (tile.getPosition() == tileFromPosition.getPosition()) {
                return true;
            }
        }
        return false;
    }


}
