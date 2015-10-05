package com.taylorbest.flappydemo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taylorbest.flappydemo.Game;
import com.taylorbest.flappydemo.sprites.Bird;

/**
 * Created by chadley on 10/5/2015.
 */
public class PlayState extends State {

    private Bird bird;
    private Texture background;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,100);
        background = new Texture("bg.png");
        cam.setToOrtho(false, Game.WIDTH/2, Game.HEIGHT/2);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();

        }
    }

    @Override
    public void update(float dt) {

        handleInput();
        bird.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth /2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
