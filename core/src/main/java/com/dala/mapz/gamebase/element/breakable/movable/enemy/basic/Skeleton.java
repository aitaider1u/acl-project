package com.dala.mapz.gamebase.element.breakable.movable.enemy.basic;

import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.dala.mapz.gamebase.element.breakable.movable.Enemy;

public class Skeleton extends Enemy {
    public Skeleton(int x, int y) {
        super(x, y, 10, 1);
        this.material = Material.SKELETON;
        health = 8;
        maxHealth = health;
        weapon = new Weapon(0.5f, 0.5f, 0.5f, 2);
    }
}
