package com.mygdx.game.states.playstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Chess;
import com.mygdx.game.states.playstate.chess_piece.Piece;

public class Tile implements Cloneable{
    private Piece piece;
    private boolean picked; // used in poiting the tile
    private boolean aviavle; // for some piece to get here
    private Texture blueRect;
    private Texture redRect;
    public Tile(Piece piece){
        this.piece = piece;
        blueRect = new Texture("blueRect.jpg");
        redRect = new Texture("redRect.png");

    }
    public void draw(SpriteBatch sb,int x,int y){
        if(picked){
            sb.draw(blueRect,x*Piece.WIDTH   ,y*Piece.HEIGHT  ,Piece.WIDTH,Piece.HEIGHT);
        }

        if(aviavle)
            sb.draw(redRect,x*Piece.WIDTH ,y*Piece.HEIGHT ,Piece.WIDTH ,Piece.HEIGHT );

        if(piece != null)
            sb.draw(piece.getTexture(),x*Piece.WIDTH,y*Piece.HEIGHT,Piece.WIDTH,Piece.HEIGHT);
    }


    public void set_x_y(int x,int y){

    }

    public boolean isPicked() {
        return picked;
    }

    public boolean isAviavle() {
        return aviavle;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    public void setAviavle(boolean aviavle) {

        this.aviavle = aviavle;
    }


    public void dispose() {
        piece.dispose();
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public String toString(){
        if(piece == null)
            return "BLANK\n";
       else
           return piece.toString() + "\n";
    }

    @Override
    protected Tile clone() throws CloneNotSupportedException {
        return new Tile(piece);
    }
}
