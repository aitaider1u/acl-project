package com.dala.mapz.gamebase;

import com.badlogic.gdx.physics.box2d.World;
import com.dala.mapz.gamebase.element.Element;
import com.dala.mapz.gamebase.element.Wall;
import com.dala.mapz.gamebase.element.breakable.movable.Player;
import com.dala.mapz.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe représentant une salle contenant des éléments.
 */
public class  Room implements Displayed {
    private int width;
    private int height;
    private int nbEnemy;
    /**
     * Liste des éléments de la salle.
     */
    private List<Element> elements = new ArrayList<>();

    /**
     * Créé une salle avec une taille.
     * @param width largeur de la salle.
     * @param height hauteur de la salle.
     */
    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.nbEnemy = 0; // updated in loadRoom function
        elements.add(new Wall(0, 0, width * Constants.GAME_SCALE, 1)); // Mur du bas
        elements.add(new Wall(0, 0, 1, height * Constants.GAME_SCALE)); // Mur de gauche
        elements.add(new Wall(width, 0, 1, height * Constants.GAME_SCALE)); // Mur de droite
        elements.add(new Wall(0, height, width * Constants.GAME_SCALE, 1)); // Mur du haut
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean hasEnemy(){
        this.nbEnemy = 0;
        for(Element e : elements) {
            if (e.isEnemy() && e.getBody().isActive())
                this.nbEnemy++;
        }
        return this.nbEnemy >0;
    }
    public List<Element> getElements() {
        return elements;
    }

    public void  addElements(Element ... elements){
        this.elements.addAll(Arrays.asList(elements));
    }

    public void removeElements(Element ... elements){
        this.elements.removeAll(Arrays.asList(elements));
    }

    public int getNbElements(){ return this.elements.size(); }

    /**
     * Charge les éléments de la salle.
     * @param world monde utilisé pour créer les Body.
     */
    public void loadRoom(Player player, World world) {
        elements.add(player);
        for(Element e : elements) {
            if (e.isEnemy())
                this.nbEnemy++;
            e.createTextures();
            e.createBody(world);
        }
    }



    public String toString() {
        StringBuilder sb = new StringBuilder("Room : {");
        for(Element e : elements) {
            sb.append("\t").append(e);
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public void update() {
        for(Element e : elements) {
            e.update();
        }
    }

    @Override
    public void draw() {
        for (Element e: this.elements) {
            e.draw();
        }
    }

    @Override
    public void createTextures() {
        for(Element e : this.elements) {
            e.createTextures();
        }
    }
}
