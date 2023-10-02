package com.dala.mapz.gamebase.element.breakable.movable.enemy.boss;

import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.Boss;

public class Knight extends Boss {
    public Knight(int x, int y) {
        super(x, y, 5, 3);
        width = 9;
        height = 9;
        material = Material.KNIGHT;
        weapon = new Weapon(0.5f, 10f, 0.5f, 0);
        health = 1;
        maxHealth = health;
    }
}
