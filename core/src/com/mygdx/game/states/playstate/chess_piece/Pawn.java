package com.mygdx.game.states.playstate.chess_piece;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.states.playstate.Tile;

public class Pawn extends Piece{
    private boolean moved = false;
    private boolean en_passant = false;
    public Pawn(Color color) {
        super(color);

        type = Type.PAWN;
        if(this.color == Color.BLACK)
            texture = new Texture("blackPawn.png");
        else
            texture = new Texture("whitePawn.png");
    }

    @Override
    public boolean is_move_allowed(int x1, int y1, int x2, int y2, Tile[][] tiles) {
        if(!super.is_move_allowed( x1,  y1, x2, y2, tiles))
            return false;

        int direction = (this.color == Color.BLACK) ? -1 : 1;
        int vec_x = x2-x1;
        int vec_y = y2 - y1;

        if( !moved  && vec_x == 0 && vec_y*direction == 2 && tiles[(y2+y1)/2][x2].getPiece() == null && tiles[y2][x2].getPiece() == null){ //2 tiles forward
            return true;
        }
        else if(vec_x == 0 && vec_y*direction == 1 && tiles[y2][x2].getPiece() == null){
            return true;

        }
        else if(Math.abs(vec_x) == 1 && direction*vec_y == 1 && tiles[y2][x2].getPiece() != null ){
            return true;
        }
        else if(Math.abs(vec_x) == 1 && direction*vec_y == 1 && tiles[y2][x2].getPiece() == null  && tiles[y1][x1+vec_x].getPiece() != null
        && tiles[y1][x1+vec_x].getPiece().getType() == Type.PAWN &&tiles[y1][x1+vec_x].getPiece().getColor() != color ){
            Pawn p1 = (Pawn)tiles[y1][x1+vec_x].getPiece();
            return p1.en_passant;
        }
        else
            return false;
    }
    @Override
    public String toString(){
        return super.toString() + " " + moved + " " + en_passant;
    }

    @Override
    public void move(int x, int y, int cur_x, int cur_y, Tile[][] tiles) {
        System.out.println(moved);
        if(Math.abs(x-cur_x) == 1 && Math.abs(y-cur_y) == 1 && tiles[y][x].getPiece() == null)
            tiles[cur_y][x].setPiece(null);

        super.move(x, y, cur_x, cur_y, tiles);

        moved = true;
        if(Math.abs(cur_y -y) == 2)
            en_passant = true;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isEn_passant() {
        return en_passant;
    }

    public void setEn_passant(boolean en_passant) {
        this.en_passant = en_passant;
    }

    @Override
    public Pawn clone() throws CloneNotSupportedException {
        Pawn p = new Pawn(color);
        p.setMoved(moved);
        p.en_passant = en_passant;
        return p;
    }
}

