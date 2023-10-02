package com.dala.mapz.gamebase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.element.breakable.movable.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe représentant un donjon, composé de salles et d'un monde.
 */
public class Dungeon implements Displayed {

    /**
     * Liste des salles du donjon.
     */
    private List<Room> rooms = new ArrayList<>();
    private Room loadedRoom;

    /**
     * Créé un nouveau donjon avec la salle contenant le joueur
     */
    public Dungeon(Room initialRoom) {
        loadedRoom = initialRoom;
        rooms.add(initialRoom);
    }

    public Room getLoadedRoom() {
        return loadedRoom;
    }

    /**
     * Retire des salles du donjon.
     * @param rooms les salles à retirer.
     */
    public void removeRooms(Room ... rooms){
        this.rooms.removeAll(Arrays.asList(rooms));
    }

    /**
     * Ajoute des salles au donjon.
     * @param rooms salles à ajouter.
     */
    public void addRooms(Room ... rooms) {
        this.rooms.addAll(Arrays.asList(rooms));
    }

    public int getNbRooms(){ return this.rooms.size(); }

    /**
     * Déplace le joueur dans une nouvelle salle.
     * @param room la nouvelle salle à charger.
     */
    public void changeLoadedRoom(Room room) {
        Player player = Mapz.getInstance().getGameScreen().getPlayer();
        loadedRoom.removeElements(player);
        loadedRoom = room;
        World world = Mapz.getInstance().getGameScreen().getWorld();
        // Unlock the world to remove the bodies
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for(Body b : bodies)
            Mapz.getInstance().getGameScreen().addBodyToRemove(b);
        loadedRoom.loadRoom(player, world);
    }

    public void loadDungeon(Player player, World world) {
        this.loadedRoom.loadRoom(player, world);
    }

    @Override
    public void update() {
        loadedRoom.update();
    }

    @Override
    public void draw() {
        this.loadedRoom.draw();
    }

    @Override
    public void createTextures() {
        this.loadedRoom.createTextures();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Dungeon : {");
        for(Room r : rooms) {
            sb.append("\t").append(r);
        }
        sb.append("}");
        return sb.toString();
    }
}
