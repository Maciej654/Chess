package com.mygdx.game.states.playstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Chess;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.State;
import com.mygdx.game.states.playstate.chess_piece.Piece;
import com.mygdx.game.states.playstate.chess_piece.Rook;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

public class BreakState extends State {
    private Texture background;
    private Skin skin;
    private TextButton button1;
    private TextButton button2;
    private TextButton button3;
    private TextButton button4;
    private Stage stage;

    public BreakState(final GameStateManager gsm, final Tile[][] tiles , final Piece.Color turn) {
        super(gsm);
        background = new Texture("menu_img.jpg");
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        button1 = new TextButton("Wznow",skin);
        button1.setTransform(true);
        button1.setScale(3);
        button1.setPosition((Chess.WIDTH/2) - (button1.getWidth()),(Chess.HEIGHT/2) - (button1.getHeight()) + 100);
        stage.addActor(button1);

        button2 = new TextButton("Wyjdz",skin);
        button2.setTransform(true);
        button2.setScale(3);
        button2.setPosition((Chess.WIDTH/2) - (button2.getWidth()),button1.getY() - 100 );
        stage.addActor(button2);

        button3 = new TextButton("Zapisz",skin);
        button3.setTransform(true);
        button3.setScale(3);
        button3.setPosition((Chess.WIDTH/2) - (button3.getWidth()),button2.getY() - 100 );
        stage.addActor(button3);

        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.pop(); /////// Z jakiegos powodu gsm musi byc final,do sprawdzenia
            }
        });

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        button3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor ) {
                StringBuilder sb = new StringBuilder();

                for(Tile[] ta : tiles){
                    for (Tile t : ta)
                                sb.append(t.toString());
                }
                sb.append(turn.toString());

                Writer writer = null;

                try {
                    writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream("saving.txt"), "utf-8"));
                    writer.write(sb.toString());
                } catch (IOException ex) {
                } finally {

                    try {writer.close();} catch (Exception ex) {/*ignore*/}
                }
            }
        });

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
}