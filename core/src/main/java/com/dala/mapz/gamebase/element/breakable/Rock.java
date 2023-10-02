package com.dala.mapz.gamebase.element.breakable;

import com.dala.mapz.gamebase.element.Breakable;
import com.dala.mapz.gamebase.element.Material;

public class Rock extends Breakable {
    /**
     * Créé un Element.
     *
     * @param x abscisse dans le monde.
     * @param y ordonnée dans le monde.
     */
    public Rock(int x, int y) {
        super(x, y);
        material = Material.ROCK;
        health = 100;
    }
}
