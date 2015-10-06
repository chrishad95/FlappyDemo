package com.taylorbest.flappydemo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.taylorbest.flappydemo.Game;
import com.taylorbest.flappydemo.sprites.Animation;
import com.taylorbest.flappydemo.sprites.Bird;
import com.taylorbest.flappydemo.sprites.Tube;

/**
 * Created by chadley on 10/5/2015.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING=125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -30;

    private Bird bird;
    private Texture background;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;





    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,200);


        background = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - (cam.viewportWidth /2), GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth /2) + ground.getWidth() , GROUND_Y_OFFSET);

        cam.setToOrtho(false, Game.WIDTH/2, Game.HEIGHT/2);

        tubes = new Array<Tube>();
        for (int i =1; i<= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

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

        updateGround();
        bird.update(dt);

        cam.position.x = bird.getPosition().x + 80;
        for (int i=0; i< tubes.size; i++) {
            if (cam.position.x - (cam.viewportWidth/2) > tubes.get(i).getPosTopTube().x + tubes.get(i).getTopTube().getWidth()) {
                tubes.get(i).reposition(tubes.get(i).getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if(tubes.get(i).collides(bird.getBounds())) {
                gsm.set(new PlayState(gsm));
            }
        }
        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
            gsm.set(new PlayState(gsm));

        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube: tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube: tubes) {
            tube.dispose();
        }
    }

    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth/2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth/2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
