package com.dala.mapz.gamebase.factories.room;

import com.dala.mapz.gamebase.Room;
import com.dala.mapz.gamebase.element.Wall;
import com.dala.mapz.gamebase.element.breakable.LifePotion;
import com.dala.mapz.gamebase.element.breakable.Rock;
import com.dala.mapz.gamebase.element.breakable.Tree;
import com.dala.mapz.gamebase.element.breakable.movable.Enemy;
import com.dala.mapz.gamebase.element.breakable.Lootable;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.Boss;
import com.dala.mapz.gamebase.factories.AbstractFactory;
import com.dala.mapz.utils.Constants;

public abstract class AbstractRoomFactory extends AbstractFactory
{
    public Room createBeginRoom() {
        Room room = new Room(100, 100);
        return room;
    }

    public Room createBossRoom() {
        Room room = new Room(100, 100);
        room.addElements(createBoss(50, 50));
        return room;
    }

    protected abstract Boss createBoss(int x, int y);

    public Room createEnemyRoom() {
        Room room = new Room(random.nextInt(100, 200), random.nextInt(100, 200));

        // hasForest
        if(random.nextInt(0, 101) <= 10) {
            createForest(room);
        }

        // hasWalls
        if(random.nextInt(0, 101) <= 40) {
            createWalls(room);
        }

        // addEnnemies
        if(random.nextInt(0, 101) <= 90) {
            createEnemies(room);
        }

        // rocks
        if (random.nextInt(0, 101) <= 15) {
            createRocks(room);
        }

        return room;
    }

    public Room createLootableRoom() {
        Room room = new Room(100, 100);
        room.addElements(new Lootable(50, 50, Lootable.LootableWeapon.values()[random.nextInt(0, Lootable.LootableWeapon.values().length)]));
        return room;
    }

    public Room createPotionRoom() {
        Room room = new Room(100, 100);
        for(int i = 0; i < 10; i+=2) {
            for(int j = 0; j < 10; j+=2) {
                room.addElements(new LifePotion(40+2*i, 40+2*j, true));
            }
        }
        return room;
    }

    private void createEnemies(Room room) {
        int nbEnemies = random.nextInt(3, 11);
        for(int i = 0; i < nbEnemies; i++) {
            room.addElements(createEnemy(random.nextInt(10, room.getWidth()-10), random.nextInt(10, room.getHeight()-10)));
        }
    }

    protected abstract Enemy createEnemy(int x, int y);

    private void createWalls(Room room) {
        int nbWalls = random.nextInt(0, 11);
        for(int i = 0; i < nbWalls; i++) {
            room.addElements(new Wall(random.nextInt(0, room.getWidth()), random.nextInt(0, room.getHeight())));
        }
    }

    private void createRocks(Room room) {
        int nbWalls = random.nextInt(0, 3);
        for(int i = 0; i < nbWalls; i++) {
            room.addElements(new Rock(random.nextInt(0, room.getWidth()), random.nextInt(0, room.getHeight())));
        }
    }

    private void createForest(Room room) {
        Tree t = new Tree(0, 0);
        int treeWidth = t.getWidth()*Constants.GAME_SCALE;
        int treeHeight = t.getHeight()*Constants.GAME_SCALE;
        int forestWidth = random.nextInt(4, 8);
        int forestHeight = random.nextInt(4, 8);
        for(int i = 0; i < forestWidth; i++) {
            for(int j = 0; j < forestHeight; j++) {
                room.addElements(new Tree((int) ((room.getWidth()/2f) - (forestWidth*treeWidth/2f) + (i*treeWidth)),
                        (int) ((room.getHeight()/2f) - (forestHeight*treeHeight/2f) + (j*treeHeight))));
            }
        }
    }
}
