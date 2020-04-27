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
import com.mygdx.game.states.playstate.chess_piece.Bishop;
import com.mygdx.game.states.playstate.chess_piece.Knight;
import com.mygdx.game.states.playstate.chess_piece.Pawn;
import com.mygdx.game.states.playstate.chess_piece.Piece;
import com.mygdx.game.states.playstate.chess_piece.Queen;
import com.mygdx.game.states.playstate.chess_piece.Rook;

public class ChooseState extends State {
    private String message;
    private Skin skin;
    private TextButton button1;
    private TextButton button2;
    private TextButton button3;
    private TextButton button4;
    private Stage stage;
    private BitmapFont font;
    private PlayState playState;
    public ChooseState(final GameStateManager gsm, final Tile t, PlayState ps) {
        super(gsm);
        message = "Wybierz figure";
        font = new BitmapFont();
        font.setColor(Color.RED);
        font.getData().setScale(3);
        final Piece.Color c1 = t.getPiece().getColor();
        final Piece.Color c2 = (c1 == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;

        playState = ps;
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        button1 = new TextButton("Hetman",skin);
        button1.setTransform(true);
        button1.setScale(3);
        button1.setPosition((Chess.WIDTH/2) - (button1.getWidth()),50);
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                t.setPiece(new Queen(c1));
                if(playState.is_king_matted(c2))
                    gsm.set(new EndState(playState,gsm,c2));

                else
                    gsm.pop();
            }
        });

        button2 = new TextButton("Wieza",skin);
        button2.setTransform(true);
        button2.setScale(3);
        button2.setPosition(button1.getX() - 25 ,button1.getY()+100);

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                t.setPiece(new Rook(c1));
                if(playState.is_king_matted(c2))
                    gsm.set(new EndState(playState,gsm,c2));

                else
                    gsm.pop();
            }


        });
        button3 = new TextButton("Goniec",skin);
        button3.setTransform(true);
        button3.setScale(3);
        button3.setPosition(button1.getX() - 25 ,button1.getY()+200);

        button3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                t.setPiece(new Bishop(c1));
                if(playState.is_king_matted(c2))
                    gsm.set(new EndState(playState,gsm,c2));

                else
                    gsm.pop();
            }


        });
        button4 = new TextButton("Skoczek",skin);
        button4.setTransform(true);
        button4.setScale(3);
        button4.setPosition(button1.getX() - 25 ,button1.getY()+300);

        button4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                t.setPiece(new Knight(c1));
                if(playState.is_king_matted(c2))
                    gsm.set(new EndState(playState,gsm,c2));

                else
                    gsm.pop();
            }


        });


        stage.addActor(button1);
        stage.addActor(button2);
        stage.addActor(button3);
        stage.addActor(button4);



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
    }
}
