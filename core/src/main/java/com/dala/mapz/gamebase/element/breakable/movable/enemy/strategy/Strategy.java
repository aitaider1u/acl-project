package com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy;

import com.dala.mapz.gamebase.element.breakable.movable.Enemy;

public abstract class Strategy {
    protected Enemy enemy;
    public Strategy(Enemy enemy) {
        this.enemy = enemy;
    }
    void move(){ };
    void attack(){ };
    public abstract void update();
}
