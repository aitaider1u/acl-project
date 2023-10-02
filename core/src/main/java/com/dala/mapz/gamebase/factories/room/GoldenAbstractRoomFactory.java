package com.dala.mapz.gamebase.factories.room;

import com.dala.mapz.gamebase.element.breakable.movable.Enemy;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.Boss;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.basic.Skeleton;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.boss.Golden;

public class GoldenAbstractRoomFactory extends AbstractRoomFactory {
    @Override
    protected Boss createBoss(int x, int y) {
        return new Golden(x, y);
    }

    @Override
    protected Enemy createEnemy(int x, int y) {
        return new Skeleton(x, y);
    }
}
