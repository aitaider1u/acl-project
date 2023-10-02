package com.dala.mapz.gamebase.factories.room;

import com.dala.mapz.gamebase.element.breakable.movable.Enemy;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.Boss;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.boss.Ogress;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.basic.OrcChild;

public class OgressAbstractRoomFactory extends AbstractRoomFactory{
    @Override
    protected Boss createBoss(int x, int y) {
        return new Ogress(x, y);
    }

    @Override
    protected Enemy createEnemy(int x, int y) {
        return new OrcChild(x, y);
    }
}
