package com.dala.mapz.gamebase.element.breakable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.dala.mapz.gamebase.element.Breakable;
import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.utils.Constants;

public class Coin extends Breakable {

    private Animation<TextureRegion> moneyAnimation;
    private float stateTime = 0f;

    /**
     * Créé un Element.
     *
     * @param x abscisse dans le monde.
     * @param y ordonnée dans le monde.
     */
    public Coin(int x, int y) {
        super(x, y);
        this.material = Material.COIN;
        this.width = 1;
        this.height = 1;
        this.health = 1;
    }

    @Override
    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        this.body = world.createBody(bodyDef);
        FixtureDef fixtureDef =material.getFixtureDef(width);
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        body.setActive(false);
    }

    public void setActive(boolean active) {
        body.setActive(active);
    }

    @Override
    public void createTextures() {
        super.createTextures();
        if (material.hasAnimation()) {
            TextureRegion[][] tmp = TextureRegion.split(material.getTexture(),
                    material.getTexture().getWidth() / Constants.FRAME_MONEY_COL,
                    material.getTexture().getHeight() / Constants.FRAME_MONEY_ROW);
            Array<TextureRegion> textureRegions = new Array<>();
            for (int i = 0; i < Constants.FRAME_MONEY_COL; i++) {
                textureRegions.add(tmp[0][i]);
            }
            this.moneyAnimation = new Animation<>(Constants.DURATION_ANIMATION,textureRegions, Animation.PlayMode.LOOP);
        }
    }


    @Override
    public void draw() {
        stateTime += Gdx.graphics.getDeltaTime();
        this.sprite.setRegion(moneyAnimation.getKeyFrame(stateTime));
        super.draw();
    }
}
