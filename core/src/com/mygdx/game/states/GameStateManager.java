package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private Stack<State> states;

    public GameStateManager(){
        states = new Stack<>();
    }

    public void push(State s1){
        states.push(s1);

    }

    public void pop(){
        states.pop().dispose();
    }

    public void set(State s1){
        states.pop().dispose();
        states.push(s1);
    }

    public void update(float dt){
        states.peek().update(dt);
    }
    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }

}
