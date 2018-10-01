package com.lchess.engine.board;

import java.awt.*;

/**
 * Created by liad on 31/08/2018.
 */
public class Position {
    private ChessCoordinate coordinate;

    public Position(ChessCoordinate position) {
        this.coordinate = position;
    }

    public Position(char a, Integer i){
        this.coordinate = new ChessCoordinate(a, i );
    }
    public Position(String a, String i){
        this.coordinate = new ChessCoordinate(a.charAt(0), Integer.parseInt(i));
    }

    public ChessCoordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(char a, Integer i){
        this.coordinate.setXPos(a);
        this.coordinate.setYPos(i);
    }

    public Position() {
    }

    @Override
    public String toString() {
        return "Position{" +
                "xPos=" + coordinate.getxPos() +
                " yPos=" + coordinate.getyPos() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;
        if (position.coordinate.equals(this.coordinate)){
            return true;
        }


        return coordinate != null ? coordinate.equals(position.coordinate) : position.coordinate == null;
    }

    @Override
    public int hashCode() {
        return coordinate != null ? coordinate.hashCode() : 0;
    }
    //    public ChessCoordinate getCoordinate() throws Exception {
//        char x = coordinate.getxPos();
//        int y = coordinate.getyPos();
//        if (x < 65 || x > 72){
//            System.out.println(String.format("coordinate x is [%s]: out of board range", x));
//            throw new Exception ("coordinate exception");
//        }
//        if (y < 1 || y > 8){
//            System.out.println(String.format("coordinate y is [%s]: out of board range", y));
//            throw new Exception ("coordinate exception");
//        }
//        return coordinate;
//    }
}
