package com.mygdx.game.states.playstate.chess_piece;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.states.playstate.Tile;

public class Queen extends Piece {

    public Queen(Color color) {
        super(color);
        type = Type.QUEEN;
        if(this.color == Color.BLACK)
            texture = new Texture("blackQueen.png");
        else
            texture = new Texture("whiteQueen.png");
    }

    @Override
    public boolean is_move_allowed(int x1, int y1, int x2, int y2, Tile[][] tiles) {
        Rook r1 = new Rook(color);
        Bishop b1 = new Bishop(color);
        return r1.is_move_allowed(x1,  y1, x2, y2, tiles) || b1.is_move_allowed(x1,  y1, x2, y2, tiles);

    }
    @Override
    public Queen clone() throws CloneNotSupportedException {
        return new Queen(color);
    }
}
