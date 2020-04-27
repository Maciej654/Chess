package com.mygdx.game.states.playstate.chess_piece;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.states.playstate.Tile;

public abstract class Piece implements Cloneable{
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;

    public void dispose() {
        texture.dispose();
    }

    public enum Type{KING,QUEEN,ROOK,KNIGHT,BISHOP,PAWN}
    public enum Color{WHITE,BLACK}
    protected Type type;
    protected Texture texture;
    protected Color color;

    public Piece(Color color){
        this.color = color;
    }
    public  boolean is_move_allowed(int x1, int y1, int x2, int y2, Tile[][] tiles){
        if( tiles[y2][x2].getPiece() != null && tiles[y1][x1].getPiece().getColor() == tiles[y2][x2].getPiece().getColor() )
            return false;
        return true;
    }
    public Type getType() {
        return type;
    }

    public Texture getTexture() {
        return texture;
    }

    public Color getColor() {
        return color;
    }
    public void move(int x,int y,int cur_x,int cur_y,Tile[][] tiles){
        tiles[y][x].setPiece(tiles[cur_y][cur_x].getPiece());
        tiles[cur_y][cur_x].setPiece(null);

    }

    @Override
    public String toString(){
        return type.toString() + " " +  color.toString();
    }

    @Override
    public abstract Piece clone() throws CloneNotSupportedException;

}
