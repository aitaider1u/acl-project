package com.dala.mapz.gamebase.factories.room;

import com.dala.mapz.gamebase.element.breakable.movable.Enemy;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.basic.BoarChild;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.Boss;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.boss.Santa;

public class SantaAbstractRoomFactory extends AbstractRoomFactory {
    @Override
    protected Boss createBoss(int x, int y) {
        return new Santa(x, y);
    }

    @Override
    protected Enemy createEnemy(int x, int y) {
        return new BoarChild(x, y);
    }
}
