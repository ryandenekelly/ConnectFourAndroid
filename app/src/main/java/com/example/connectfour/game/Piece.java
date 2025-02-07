package com.example.connectfour.game;

public class Piece {

    private Type type;

    public enum Type{
        Red,
        Blue,
        Empty
    }
    public Piece(Type piece) {
        this.type = type;
    }

    public Type getType(){
        return this.type;
    }
}
