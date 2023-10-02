package com.dala.mapz.gamebase.element.breakable.movable.enemy.basic;

import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.dala.mapz.gamebase.element.breakable.movable.Enemy;

public class BoarChild extends Enemy {

    public BoarChild(int x, int y) {
        super(x, y, 20, 3);
        this.material = Material.BOAR_CHILD;
        health = 50;
        maxHealth = health;
        weapon = new Weapon(0.5f, 0.5f, 0.5f, 1);
    }
}
