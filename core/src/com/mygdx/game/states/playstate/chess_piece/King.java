package com.mygdx.game.states.playstate.chess_piece;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.states.playstate.Tile;

public class King extends Piece {
    private boolean safe = true;
    private boolean moved = false;
    public King(Color color) {
        super(color);
        type = Type.KING;
        if(this.color == Color.BLACK)
            texture = new Texture("blackKing.png");
        else
            texture = new Texture("whiteKing.png");
    }

    @Override
    public boolean is_move_allowed(int x1, int y1, int x2, int y2, Tile[][] tiles)
    {
        int vec_x = x2-x1;
        int vec_y = y2 - y1;
        if(Math.abs(vec_x) == 2 && Math.abs(vec_y) == 0  && !moved && safe) {
            if(x2 == 2 && tiles[y1][0].getPiece() instanceof Rook && !((Rook) tiles[y1][0].getPiece()).isMoved() &&  tiles[y1][1].getPiece() == null && tiles[y1][2].getPiece() == null && tiles[y1][3].getPiece() == null)
                return true;
            else if(x2 == 6 && tiles[y1][7].getPiece() instanceof Rook && !((Rook) tiles[y1][7].getPiece()).isMoved() &&  tiles[y1][5].getPiece() == null && tiles[y1][6].getPiece() == null )
                return true;
            else return false;
        }
        if(!super.is_move_allowed( x1,  y1, x2, y2, tiles))
            return false;
        if((Math.abs(x1-x2) > 1) || (Math.abs(y2-y1) > 1) )
            return false;
        return true;

    }
    @Override
    public King clone() throws CloneNotSupportedException {
        King k1 = new King(color);
        k1.setMoved(moved);
        k1.setSafe(safe);
        return k1;

    }

    @Override
    public void move(int x, int y, int cur_x, int cur_y, Tile[][] tiles) {
        moved = true;
        super.move(x, y, cur_x, cur_y, tiles);
        if(x-cur_x == -2)
          //  super.move(0,cur_y,,tiles);
            super.move(3,cur_y,0,cur_y,tiles);
        if(x-cur_x == 2 )
            //super.move(7,cur_y,5,cur_y,tiles);
            super.move(5,cur_y,7,cur_y,tiles);


    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    @Override
    public String toString() {
        return super.toString() + " " + moved + " " + safe;
    }
}
