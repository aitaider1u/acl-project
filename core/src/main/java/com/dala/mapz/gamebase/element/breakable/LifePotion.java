package com.dala.mapz.gamebase.element.breakable;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dala.mapz.gamebase.element.Breakable;
import com.dala.mapz.gamebase.element.Material;

public class LifePotion extends Breakable {
    //Nombre de pv rendus par la potion
    protected int potionValue = 15;
    private boolean defaultActive = false;

    /**
     * Créé un Element.
     *
     * @param x abscisse dans le monde.
     * @param y ordonnée dans le monde.
     */
    public LifePotion(int x, int y, boolean defaultActive){
        super(x,y);
        this.material = Material.POTION;
        this.health = 1;
        this.height = 2;
        this.width = 2;
        this.defaultActive = defaultActive;
    }

    public int getPotionValue() {
        return potionValue;
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
        body.setActive(defaultActive);
    }

    public void setActive(boolean active) {
        body.setActive(active);
    }
    @Override
    public void createTextures() {
        super.createTextures();
    }
}
