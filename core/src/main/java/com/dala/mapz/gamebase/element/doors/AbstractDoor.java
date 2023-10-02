package com.dala.mapz.gamebase.element.doors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.Room;
import com.dala.mapz.gamebase.element.Element;
import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.utils.Constants;
import com.dala.mapz.utils.Orientation;

public abstract class AbstractDoor extends Element
{
    /**
     * Salle où se trouve la porte.
     */
    protected Room from;

    /**
     * Le joueur peut passer par la porte si elle est ouverte.
     */
    protected boolean isOpen;

    protected Orientation orientation;
    protected float stateTime = 0f;

    protected Animation<TextureRegion> openingDoorAnimation;

    /**
     * Créé une porte. Appelle le constructeur Element(world, x, y, dynamic) et ajoute les 2 salles.
     * @param from salle de départ.
     */
    public AbstractDoor(int x, int y, Room from, Orientation orientation) {
        super(x, y);
        this.isOpen = false;
        this.from = from;
        this.orientation = orientation;
        this.material = Material.DOOR;
    }

    /**
     * Ouvre la porte si la salle from n'a pas d'ennemis vivants
     */
    @Override
    public void update() {
        if (!this.from.hasEnemy() && !this.isOpen){
            this.isOpen = true;
        }
    }

    @Override
    public void createTextures() {
        super.createTextures();
        if (material.hasAnimation()) {
            TextureRegion[][] tmp = TextureRegion.split(material.getTexture(),
                    material.getTexture().getWidth() / Constants.FRAME_DOOR_COL,
                    material.getTexture().getHeight() / Constants.FRAME_DOOR_ROW);
            Array<TextureRegion> textureRegions = new Array<>();
            for (int i = 0; i < Constants.FRAME_DOOR_COL; i++) {
                textureRegions.add(tmp[0][i]);
            }
            this.openingDoorAnimation = new Animation<>( (!this.from.hasEnemy()) ? 0.001f : Constants.DURATION_ANIMATION*2,textureRegions, Animation.PlayMode.NORMAL);
        }
    }

    /**
     * Abscisse utilisée pour replacer le joueur lorsqu'il passe par la porte.
     */
    public int outX() {
        int width = Mapz.getInstance().getGameScreen().getPlayer().getWidth();
        switch (orientation) {
            case LEFT -> { return x-Constants.GAME_SCALE*width; }
            case RIGHT -> { return x+Constants.GAME_SCALE*width; }
        }
        return x;
    }

    /**
     * Ordonnée utilisée pour replacer le joueur lorsqu'il passe par la porte.
     */
    public int outY() {
        int height = Mapz.getInstance().getGameScreen().getPlayer().getHeight();
        switch (orientation) {
            case DOWN -> { return y-Constants.GAME_SCALE*height; }
            case UP -> { return y+Constants.GAME_SCALE*height; }
        }
        return y;
    }

    public abstract void goThrough();

    @Override
    public void draw() {
        if (isOpen){
            stateTime += Gdx.graphics.getDeltaTime();
            this.sprite.setRegion(openingDoorAnimation.getKeyFrame(stateTime));
            super.draw();
            return;
        }
        this.sprite.setRegion(openingDoorAnimation.getKeyFrame(stateTime));
        super.draw();
    }


    @Override
    public String toString() {
        return "Door : "+ super.toString();
    }
}
