package com.purestudios.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.purestudios.game.FlappyDemo;
import com.purestudios.game.sprites.Bird;
import com.purestudios.game.sprites.Tube;

public class PlayState extends State {
  
  private static final int TUBE_SPACING = 125;
  private static final int TUBE_COUNT = 4;
  
  private Bird bird;
  private Texture background;
  
  private Array<Tube> tubes;
  public PlayState(GameStateManager gsm) {
    super(gsm);
    bird = new Bird(50, 300);
    cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
    background = new Texture("bg.png");
    tubes = new Array<Tube>();
    
    for (int i = 1; i <= TUBE_COUNT; i++) {
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
    
    bird.update(dt);
    cam.position.x = bird.getPosition().x + 80;
    
    for (Tube tube : tubes) {
      if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
        tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
      }
    }
    
    cam.update();
  }
  
  @Override
  public void render(SpriteBatch sb) {
    sb.setProjectionMatrix(cam.combined);
    
    sb.begin();
    sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
    sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
    
    for (Tube tube : tubes) {
      sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
      sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
    }
    sb.end();
  }
  
  @Override
  public void dispose() {
    
  }
  
}
