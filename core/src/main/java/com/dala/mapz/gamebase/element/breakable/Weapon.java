package com.dala.mapz.gamebase.element.breakable;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Armes utilisées dans le jeu par le joueur et les ennemis.
 */
public record Weapon(float width, float height, float duration, int damage) {

    /**
     * Créer la forme pour le body de cette arme.
     * @return shape
     */
    public Shape createShape() {
        PolygonShape polShape = new PolygonShape();
        polShape.setAsBox(width, height);
        return polShape;
    }
}
