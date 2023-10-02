package com.dala.mapz.gamebase.element.breakable.movable.enemy.basic;

import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.dala.mapz.gamebase.element.breakable.movable.Enemy;

public class WolfChild extends Enemy {
    public WolfChild(int x, int y) {
        super(x, y, 600, 3);
        this.material = Material.WOLF_CHILD;
        health = 10;
        maxHealth = health;
        weapon = new Weapon(0.5f, 0.5f, 0.5f, 2);
    }
}
