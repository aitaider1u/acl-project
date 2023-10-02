package com.dala.mapz.gamebase.element.breakable.movable.enemy.basic;

import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.dala.mapz.gamebase.element.breakable.movable.Enemy;

public class OrcChild extends Enemy {
    public OrcChild(int x, int y) {
        super(x, y, 250, 3);
        this.material = Material.ORC_CHILD;
        health = 20;
        maxHealth = health;
        weapon = new Weapon(0.5f, 0.5f, 0.5f, 1);
    }
}
