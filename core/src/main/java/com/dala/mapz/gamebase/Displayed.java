package com.dala.mapz.gamebase;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Elements affichés avec une texture dans le jeu.
 */
public interface Displayed {
    /**
     * Met à jour l'état de l'élément.
     */
    void update();

    /**
     * Dessine la texture.
     */
    void draw();

    /**
     * Instancie la texture.
     */
    void createTextures();
}
