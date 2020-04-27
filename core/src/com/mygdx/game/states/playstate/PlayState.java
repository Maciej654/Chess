package com.mygdx.game.states.playstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Chess;
import com.mygdx.game.states.ChooseState;
import com.mygdx.game.states.EndState;
import com.mygdx.game.states.playstate.chess_piece.Bishop;
import com.mygdx.game.states.playstate.chess_piece.King;
import com.mygdx.game.states.playstate.chess_piece.Knight;
import com.mygdx.game.states.playstate.chess_piece.Pawn;
import com.mygdx.game.states.playstate.chess_piece.Piece;
import com.mygdx.game.states.playstate.chess_piece.Queen;
import com.mygdx.game.states.playstate.chess_piece.Rook;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.State;


public class PlayState extends State {
    private Texture background;
    private Tile[][] tiles;
    private boolean picked;
    private int coordinates_x,coordinates_y;
    private Piece.Color turn;

    public  PlayState(GameStateManager gsm) {
        super(gsm);
        turn = Piece.Color.WHITE;
        background = new Texture("chess_board.jpg");
        tiles = new Tile[8][8];
        picked = false;

        for(int i = 0; i < 8 ; i++)
            for(int j = 0; j < 8;j++){
                tiles[i][j] = new Tile(null);
                tiles[i][j].set_x_y(j,i); //IMPORTANT TO SET X AND Y AXIS
            }



        for(int i = 0;i < 8;i++)
            tiles[1][i] = new Tile(new Pawn(Piece.Color.WHITE));
        for(int i = 0;i < 8;i++)
            tiles[6][i] = new Tile(new Pawn(Piece.Color.BLACK));
        tiles[0][0] = new Tile(new Rook(Piece.Color.WHITE));
         tiles[0][7] = new Tile(new Rook(Piece.Color.WHITE));
        tiles[7][0]  = new Tile(new Rook(Piece.Color.BLACK));
         tiles[7][7] = new Tile(new Rook(Piece.Color.BLACK));
        tiles[0][1]  = new Tile(new Knight(Piece.Color.WHITE));
        tiles[0][6] = new Tile(new Knight(Piece.Color.WHITE));
        tiles[7][1]  = new Tile(new Knight(Piece.Color.BLACK));
         tiles[7][6] = new Tile(new Knight(Piece.Color.BLACK));

        tiles[0][2]  = new Tile(new Bishop(Piece.Color.WHITE));
        tiles[0][5] = new Tile(new Bishop(Piece.Color.WHITE));

        tiles[7][2]  = new Tile(new Bishop(Piece.Color.BLACK));
        tiles[7][5] = new Tile(new Bishop(Piece.Color.BLACK));

        tiles[0][3] = new Tile(new Queen(Piece.Color.WHITE));
        tiles[7][3] = new Tile(new Queen(Piece.Color.BLACK));

        tiles[0][4] = new Tile(new King(Piece.Color.WHITE));
        tiles[7][4] = new Tile(new King(Piece.Color.BLACK));



    }
    public PlayState(GameStateManager gsm, Tile[][] t1, Piece.Color turn){
        this(gsm);
        if(t1 != null){
            tiles = t1;
            this.turn = turn;
        }


    }

    private void pick(int x,int y){


        boolean king_safe = false;
        if (!tiles[y][x].isPicked() && tiles[y][x].getPiece() != null && turn == tiles[y][x].getPiece().getColor()) {
            picked = true;
            tiles[y][x].setPicked(true);
            coordinates_x = x;
            coordinates_y = y;

            //en_passe
            for (int i = 0; i < tiles.length; i++)
                for (int j = 0; j < tiles.length; j++)
                    if(tiles[i][j].getPiece() instanceof Pawn && tiles[i][j].getPiece().getColor() == tiles[y][x].getPiece().getColor())
                        ((Pawn)tiles[i][j].getPiece()).setEn_passant(false);



            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (tiles[coordinates_y][coordinates_x].getPiece().is_move_allowed(coordinates_x, coordinates_y, j, i, tiles)){
                        try{
                            Piece p1 =  (tiles[i][j].getPiece() == null) ? null: tiles[i][j].getPiece().clone();
                            tiles[i][j].setPiece(tiles[coordinates_y][coordinates_x].getPiece());
                            tiles[coordinates_y][coordinates_x].setPiece(null);

                            king_safe = is_king_safe(tiles[i][j].getPiece().getColor());

                            tiles[coordinates_y][coordinates_x].setPiece(tiles[i][j].getPiece());
                            tiles[i][j].setPiece(p1);
                        }
                        catch (Exception e){System.out.println("Blad klonowania" );}
                            if(king_safe)
                                tiles[i][j].setAviavle(true);
                            else
                                tiles[i][j].setAviavle(false);

                    }

                    else
                        tiles[i][j].setAviavle(false);

                }
            }
        }

    }
    private void unpick(int x,int y){
        picked = false;
        tiles[y][x].setPicked(false);
        for (Tile[] at:tiles)
            for (Tile t:at)
                t.setAviavle(false);
    }
    private void move(int x,int y){
        tiles[coordinates_y][coordinates_x].getPiece().move(x,y,coordinates_x,coordinates_y,tiles);
        unpick(coordinates_x,coordinates_y);
        turn = (turn == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
        if(is_king_matted(turn))
            gsm.push(new EndState(this,gsm,turn));

        if((y == 7 || y == 0) && tiles[y][x].getPiece() instanceof Pawn)
            gsm.push(new ChooseState(gsm,tiles[y][x],this));
        if(is_king_matted(turn)){
            System.out.println("Alkuu");
            gsm.push(new EndState(this,gsm,turn));

        }



    }

    @Override
    protected void handleinput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("NEW SCENE TO IMPLEMENT");
            gsm.push(new BreakState(gsm,tiles,turn));
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX() / Piece.WIDTH;
            int y = 7 - Gdx.input.getY() / Piece.HEIGHT;
            if (!picked)
                pick(x,y);
            else if(picked && coordinates_x == x && coordinates_y == y)
                unpick(x,y);
            else if(picked && (coordinates_y != y || coordinates_x != x) && tiles[y][x].isAviavle())
                move(x,y);


        }

    }
    @Override
    public void update(float dt) {
        handleinput();
    }

    private Piece new_piece(final Piece.Color color) {
        final boolean[] chosen = {false};
        final Piece[] p1 = new Piece[1];
        ImageButton [] buttons  = new ImageButton [4];
        Texture [] myTexture = new Texture[4];
        TextureRegionDrawable [] myTexRegionDrawable = new TextureRegionDrawable[4];
        TextureRegion[] myTexRegion = new TextureRegion[4];

        String prefix = (color == Piece.Color.WHITE) ? "white" : "black";
        myTexture[0] = new Texture(prefix + "Rook.png");
        myTexture[1] = new Texture(prefix + "Queen.png");
        myTexture[2] = new Texture(prefix + "Knight.png");
        myTexture[3] = new Texture(prefix + "Bishop.png");
        for (int i = 0; i < myTexRegionDrawable.length; i++) {
            myTexRegion[i] = new TextureRegion(myTexture[i]);
            myTexRegionDrawable[i] = new TextureRegionDrawable(myTexRegion[i]);
            buttons[i] = new ImageButton(myTexRegionDrawable[i]);
        }

        Stage s1 = new Stage();
        buttons[0].addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                chosen[0] = true;
                p1[0] = new Rook(color);
                return true;
            }
        });
        buttons[1].addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                chosen[0] = true;
                p1[0] = new Queen(color);
                return true;
            }
        });
        buttons[2].addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                chosen[0] = true;
                p1[0] = new Knight(color);
                return true;
            }
        });
        buttons[3].addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                chosen[0] = true;
                p1[0] = new Bishop(color);
                return true;
            }
        });

        s1.addActor(buttons[0]);
        s1.addActor(buttons[1]);
        s1.addActor(buttons[2]);
        s1.addActor(buttons[3]);
        Gdx.input.setInputProcessor(s1);

        while(!chosen[0]){
            s1.act();
            s1.draw();
        }
        s1.dispose();
        return p1[0];
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(background,0,0, Chess.WIDTH,Chess.HEIGHT);
        for(int i = 0 ; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                tiles[i][j].draw(sb,j,i);
            }


        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        for(Tile[] ta:tiles)
            for(Tile t:ta)
                t.dispose();
    }

    public boolean is_king_safe(Piece.Color color){
        int x_king = 0,y_king = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if(tiles[i][j].getPiece() instanceof King && tiles[i][j].getPiece().getColor() == color){
                    x_king = j;
                    y_king = i;
                }
            }
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if(tiles[i][j].getPiece() != null && tiles[i][j].getPiece().is_move_allowed(j,i,x_king,y_king,tiles)){
                    ((King)(tiles[y_king][x_king].getPiece())).setSafe(false);
                    return false;
                }

            }
        }
        ((King)(tiles[y_king][x_king].getPiece())).setSafe(true);
        return true;
    }

    public boolean is_king_matted(Piece.Color color){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if(tiles[i][j].getPiece() != null &&tiles[i][j].getPiece().getColor() == color ){

                    for (int k = 0; k < tiles.length; k++) {
                        for (int z = 0; z < tiles.length; z++) {

                            if (tiles[i][j].getPiece().is_move_allowed(j, i, z, k, tiles)){

                                try{
                                    Piece p1 =  (tiles[k][z].getPiece() == null) ? null: tiles[k][z].getPiece().clone();
                                    tiles[k][z].setPiece(tiles[i][j].getPiece());
                                    tiles[i][j].setPiece(null);
                                    if(is_king_safe(color)){
                                        tiles[i][j].setPiece(tiles[k][z].getPiece());
                                        tiles[k][z].setPiece(p1);
                                        return false;
                                    }
                                    tiles[i][j].setPiece(tiles[k][z].getPiece());
                                    tiles[k][z].setPiece(p1);

                                }
                                catch (Exception e){System.out.println("Blad klonowania" );}

                            }


                        }
                    }
                }
            }
        }
    return true;
    }
}
