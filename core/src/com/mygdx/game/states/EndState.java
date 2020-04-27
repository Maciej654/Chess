package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Chess;
import com.mygdx.game.states.playstate.PlayState;
import com.mygdx.game.states.playstate.Tile;
import com.mygdx.game.states.playstate.chess_piece.Piece;

public class EndState extends State {
    private String message;
    private Skin skin;
    private TextButton button1;
    private TextButton button2;
    private Stage stage;
    private BitmapFont font;
    private PlayState playState;
    public EndState(PlayState ps,final GameStateManager gsm, Piece.Color color) {
        super(gsm);
        message = (color == Piece.Color.WHITE) ? "Czarni wygrali": "Biali wygrali";
        font = new BitmapFont();
        font.setColor(Color.RED);
        font.getData().setScale(3);

        playState = ps;
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
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

        button2 = new TextButton("Wyjdz",skin);
        button2.setTransform(true);
        button2.setScale(3);
        button2.setPosition(button1.getX() - 25 ,button1.getY()+100);

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               Gdx.app.exit();
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
    }

    @Override
    public void render(SpriteBatch sb) {

        playState.render(sb);
        sb.begin();
        font.draw(sb,message,200.f,520.f);
        sb.end();

        stage.draw();
        stage.act();



    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
    }
}
