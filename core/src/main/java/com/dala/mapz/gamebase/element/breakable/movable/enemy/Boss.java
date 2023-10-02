package com.dala.mapz.gamebase.element.breakable.movable.enemy;

import com.dala.mapz.gamebase.element.breakable.movable.Enemy;
import com.dala.mapz.utils.Constants;

public abstract class Boss extends Enemy {

    protected Boss(int x, int y, int moveSpeed, int valueDead) {
        super(x, y, moveSpeed, valueDead);
    }

    @Override
    public void createTextures() {
        super.createTextures();
        sprite.setBounds(x-((width* Constants.GAME_SCALE)/2f), y-((height*Constants.GAME_SCALE)/2f), width*Constants.GAME_SCALE*3, height*Constants.GAME_SCALE*3);
    }
}
