package com.mygdx.game.states.playstate.chess_piece;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.states.playstate.Tile;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(color);
        type = Type.BISHOP;
        if(this.color == Color.BLACK)
            texture = new Texture("blackBishop.png");
        else
            texture = new Texture("whiteBishop.png");
    }

    @Override
    public boolean is_move_allowed(int x1, int y1, int x2, int y2, Tile[][] tiles) {

        if(!super.is_move_allowed( x1,  y1, x2, y2, tiles))
            return false;

        int direction = (this.color == Color.BLACK) ? -1 : 1;
        int vec_x = x2-x1;
        int vec_y = y2 - y1;
        if(vec_x == 0 && vec_y == 0)
            return false;
        if(Math.abs(vec_x) != Math.abs(vec_y))
            return false;

        for (int i = 1; i < Math.abs(vec_x); i++) {
            if(tiles[y1+ i*Integer.signum(vec_y)][x1+ i*Integer.signum(vec_x)].getPiece() != null)
                return false;
        }
        return true;

    }

    @Override
    public Bishop clone() throws CloneNotSupportedException {
        return new Bishop(color);
    }
}
