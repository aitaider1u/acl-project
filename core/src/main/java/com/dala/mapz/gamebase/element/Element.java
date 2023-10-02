package com.dala.mapz.gamebase.element;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.Displayed;
import com.dala.mapz.utils.Constants;

import java.util.Random;

/**
 * Classe abstraite des éléments qui peuvent etre dans le monde.
 */
public abstract class Element implements Displayed {

    protected transient Body body;
    protected transient Sprite sprite = new Sprite();
    protected boolean dynamic = false;
    protected transient int x;
    protected transient int y;
    protected int width = Constants.GAME_SCALE;
    protected int height = Constants.GAME_SCALE;
    protected transient Material material = Material.DEFAULT;

    /**
     * Créé un Element.
     * @param x abscisse dans le monde.
     * @param y ordonnée dans le monde.
     */
    public Element(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Element(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Body getBody() {
        return body;
    }

    /**
     * Créer le Body de cet élément (de forme carrée et de taille 1 sur 1).
     * @param world monde utilisé.
     * @return le body créé.
     */
    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        if(!this.dynamic)
            bodyDef.type = BodyDef.BodyType.StaticBody;
        else
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.body = world.createBody(bodyDef);
        body.createFixture(material.getFixtureDef(width, height)).setUserData(this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
        body.setTransform(x-(width/2f), y-(height/2f), body.getAngle());
    }

    public void setY(int y) {
        this.y = y;
        body.setTransform(x-(width/2f), y-(height/2f), body.getAngle());
    }

    public abstract void update();

    @Override
    public void createTextures() {
        if (this.material.hasTexture()){
            sprite = new Sprite(material.getTexture());
            sprite.setBounds(x-((width*Constants.GAME_SCALE)/2f), y-((height*Constants.GAME_SCALE)/2f), width*Constants.GAME_SCALE, height*Constants.GAME_SCALE);
        }
    }

    @Override
    public void draw() {
        if (this.material.hasTexture() && this.body.isActive() ){
            sprite.setCenter(body.getPosition().x, body.getPosition().y);
            this.sprite.draw(Mapz.getInstance().getBatch());
        }
    }

    public boolean isEnemy(){
        return false;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Material getMaterial() {
        return material;
    }


    /**
     * Donne une chaine de caractères contenant les coordonnées de l'élément dans son monde.
     */
    @Override
    public String toString() {
        return  " ("+this.body.getPosition().x+","+this.body.getPosition().y+")";
    }
}
