package com.dala.mapz.gamebase.element.breakable;

import com.dala.mapz.gamebase.element.Breakable;
import com.dala.mapz.gamebase.element.Material;

public class Tree extends Breakable {
    /**
     * Créé un Element.
     *
     * @param x abscisse dans le monde.
     * @param y ordonnée dans le monde.
     */
    public Tree(int x, int y) {
        super(x, y);
        this.material = Material.TREE;
        health = 30;
    }

    @Override
    public void createTextures() {
        super.createTextures();
    }
}
