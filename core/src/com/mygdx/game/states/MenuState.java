package com.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Chess;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.states.playstate.PlayState;
import com.mygdx.game.states.playstate.Tile;
import com.mygdx.game.states.playstate.chess_piece.Bishop;
import com.mygdx.game.states.playstate.chess_piece.King;
import com.mygdx.game.states.playstate.chess_piece.Knight;
import com.mygdx.game.states.playstate.chess_piece.Pawn;
import com.mygdx.game.states.playstate.chess_piece.Piece;
import com.mygdx.game.states.playstate.chess_piece.Queen;
import com.mygdx.game.states.playstate.chess_piece.Rook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import sun.misc.IOUtils;

public class MenuState extends State {
    private Texture background;
    private Skin skin;
    private TextButton button1;
    private TextButton button2;
    private Stage stage;

    public MenuState(final GameStateManager gsm){
        super(gsm);
        background = new Texture("menu_img.jpg");
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        button1 = new TextButton("Nowa gra",skin);
        button1.setTransform(true);
        button1.setScale(3);
        button1.setPosition((Chess.WIDTH/2) - (button1.getWidth()),(Chess.HEIGHT/2) - (button1.getHeight()));
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.set(new PlayState(gsm)); /////// Z jakiegos powodu gsm musi byc final,do sprawdzenia
            }
        });

        button2 = new TextButton("Wczytaj gre",skin);
        button2.setTransform(true);
        button2.setScale(3);
        button2.setPosition(button1.getX() - 25 ,button1.getY()+100);

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Tile[][] tiles = new Tile[8][8];
                Piece.Color c = Piece.Color.BLACK;
                try{c = read_from_file(tiles);}
                catch (Exception e){System.out.println("Blad oczytu");tiles = null;}

                gsm.set(new PlayState(gsm,tiles,c));
            }


        });




        stage.addActor(button1);
        stage.addActor(button2);



        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleinput() {

    }

    @Override
    public void update(float dt) {
        handleinput();
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
        sb.draw(background,0,0, Chess.WIDTH,Chess.HEIGHT);
        sb.end();

        stage.draw();
        stage.act();


    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        skin.dispose();

    }
    private Piece.Color read_from_file(Tile[][] tiles) throws Exception{
        File file = new File("saving.txt");
        int i = 0, j = 0;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        Piece piece = new Pawn(Piece.Color.WHITE);
        Piece.Color color = Piece.Color.WHITE;
        boolean moved;
        boolean en_passant;
        boolean safe;
        while ((st = br.readLine()) != null){
                    System.out.println(st);
                    if(i == 8){
                        color = (st.equals("WHITE")) ? Piece.Color.WHITE : Piece.Color.BLACK;
                        break;
                    }

                    String [] patrts = st.split(" ");
                    if(patrts.length > 1)
                        color = (patrts[1].equals("WHITE")) ? Piece.Color.WHITE : Piece.Color.BLACK;

                    switch (patrts[0]){
                        case "ROOK":
                            moved = (patrts[2].equals("true"));
                            piece = new Rook(color);
                            ((Rook)piece).setMoved(moved);
                            break;
                        case "KNIGHT":
                            piece = new Knight(color);
                            break;
                        case "BISHOP":
                            piece = new Bishop(color);
                            break;
                        case "QUEEN":
                            piece = new Queen(color);
                            break;
                        case "KING":
                            piece = new King(color);
                            moved = (patrts[2].equals("true"));
                            ((King)piece).setMoved(moved);
                            safe =  (patrts[2].equals("true"));
                            ((King)piece).setMoved(safe);
                            break;
                        case "PAWN":
                            piece = new Pawn(color);
                            moved = (patrts[2].equals("true"));
                            ((Pawn)piece).setMoved(moved);
                            en_passant =  (patrts[2].equals("true"));
                            ((Pawn)piece).setEn_passant(en_passant);
                            break;
                        case "BLANK":
                            piece = null;
                            break;

                    }
                    tiles[i][j] = new Tile(piece);
                    j++;
                    if(j == 8){ i ++;j=0;}

        }

        return color;
    }

}
