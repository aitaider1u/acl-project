package com.dala.mapz.gamebase.element.doors;

import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.Dungeon;
import com.dala.mapz.gamebase.Room;
import com.dala.mapz.gamebase.element.breakable.movable.Player;
import com.dala.mapz.utils.Orientation;
import com.dala.mapz.utils.SoundManager;

public class DoorToDungeon extends AbstractDoor
{
    private Dungeon to;
    private Dungeon fromDungeon;
    private int toX;
    private int toY;
    /**
     * Créé une porte. Appelle le constructeur Element(world, x, y, dynamic) et ajoute les 2 salles.
     *
     * @param x
     * @param y
     * @param from        salle de départ.
     * @param orientation
     */
    public DoorToDungeon(int x, int y, Room from, Dungeon to, int toX, int toY, Orientation orientation) {
        super(x, y, from, orientation);
        this.to = to;
        this.toX = toX;
        this.toY = toY;
    }

    @Override
    public void goThrough() {
        if(isOpen) {
            SoundManager.DOOR.play();
            Mapz.getInstance().getGameScreen().changeDungeon(to);
            Player player = Mapz.getInstance().getGameScreen().getPlayer();
            player.setX(toX);
            player.setY(toY);
        }
    }
}
