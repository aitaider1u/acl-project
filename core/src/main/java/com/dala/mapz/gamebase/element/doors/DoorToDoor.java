package com.dala.mapz.gamebase.element.doors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.Room;
import com.dala.mapz.gamebase.element.Element;
import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.gamebase.element.breakable.movable.Player;
import com.dala.mapz.utils.Constants;
import com.dala.mapz.utils.Orientation;
import com.dala.mapz.utils.SoundManager;

/**
 * Classe représentant une porte (passerelle entre 2 salles).
 */
public class DoorToDoor extends AbstractDoor {

    /**
     * Porte de destination
     */
    private DoorToDoor to;

    /**
     * Créé une porte. Appelle le constructeur Element(world, x, y, dynamic) et ajoute les 2 salles.
     *
     * @param x
     * @param y
     * @param from        salle de départ.
     * @param orientation
     */
    public DoorToDoor(int x, int y, Room from, Orientation orientation) {
        super(x, y, from, orientation);
    }

    @Override
    public void goThrough() {
        if(isOpen) {
            SoundManager.DOOR.play();
            Mapz.getInstance().getGameScreen().getCurrentDungeon().changeLoadedRoom(to.from);
            Player player = Mapz.getInstance().getGameScreen().getPlayer();
            player.setX(to.outX());
            player.setY(to.outY());
        }
    }

    public void setTo(DoorToDoor to) {
        this.to = to;
    }
}
