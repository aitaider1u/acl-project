package com.dala.mapz.gamebase.element;

/**
 * Classe représentant un mur dans le monde.
 */
public class Wall extends Element{
    public Wall(int x, int y) {
        super(x, y);
        width = 2;
        height = 2;
        this.material = Material.WALL;
    }
    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.material = Material.WALL;
    }

    @Override
    public void update() {

    }

    @Override
    public String toString() {
        return "Wall : "+ super.toString();
    }
}
