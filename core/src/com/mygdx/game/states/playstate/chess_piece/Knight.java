package com.mygdx.game.states.playstate.chess_piece;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.states.playstate.Tile;

public class Knight extends Piece {
    public Knight(Color color) {
        super(color);
        type = Type.KNIGHT;
        if(this.color == Color.BLACK)
            texture = new Texture("blackKnight.png");
        else
            texture = new Texture("whiteKnight.png");
    }

    @Override
    public boolean is_move_allowed(int x1, int y1, int x2, int y2, Tile[][] tiles) {
        if(!super.is_move_allowed( x1,  y1, x2, y2, tiles))
            return false;

        int direction = (this.color == Color.BLACK) ? -1 : 1;
        int vec_x = x2-x1;
        int vec_y = y2 - y1;

        if((Math.abs(vec_x) == 1 && Math.abs((vec_y) ) == 2 ) || (Math.abs(vec_x) == 2 && Math.abs((vec_y) ) == 1 )) {
            if(tiles[y2][x2].getPiece() == null || tiles[y2][x2].getPiece().getColor() != this.color)
                return true;
            else
                return false;
        }
        else
            return false;
    }
    @Override
    public Knight clone() throws CloneNotSupportedException {
        return new Knight(color);
    }
}
