package com.dala.mapz.gamebase.factories;

import com.dala.mapz.gamebase.Dungeon;
import com.dala.mapz.gamebase.Room;
import com.dala.mapz.gamebase.element.doors.DoorToDoor;
import com.dala.mapz.gamebase.element.doors.DoorToDungeon;
import com.dala.mapz.gamebase.factories.room.AbstractRoomFactory;
import com.dala.mapz.gamebase.factories.room.RoomFactoryEnum;
import com.dala.mapz.utils.Constants;
import com.dala.mapz.utils.Orientation;

public class DungeonFactory extends AbstractFactory
{
    private static final DungeonFactory instance = new DungeonFactory();
    public static DungeonFactory getInstance() {
        return instance;
    }
    public Dungeon createDungeon(Dungeon neutralDungeon) {
        int nbRooms = random.nextInt(Constants.MIN_ROOM_DUNGEON, Constants.MAX_ROOM_DUNGEON+1);

        int indexRoomFactory = random.nextInt(0, RoomFactoryEnum.values().length);
        AbstractRoomFactory rf = RoomFactoryEnum.values()[indexRoomFactory].roomFactory;

        // Seed de génération pour les salles (ainsi si on veut refaire les mêmes donjons on peut)
        rf.setSeed(random.nextLong());

        Room beginRoom = rf.createBeginRoom();

        RoomTree tree = new RoomTree(beginRoom, Orientation.UP);
        RoomTree next = new RoomTree(rf.createEnemyRoom(), Orientation.DOWN);

        createDoors(tree.getRoom(), Orientation.UP, next.getRoom());
        tree.addNexts(next);

        // Génération aléatoire des salles avec un chemin gagnant
        while(tree.size() < nbRooms) {
            int nbNext = random.nextInt(1, 4);
            RoomTree[] nexts = new RoomTree[nbNext];
            for(int i = 0; i < nbNext; i++) {
                Room nextRoom;
                if(random.nextInt(0, 101) <= 5)
                    nextRoom = rf.createLootableRoom();
                else if(random.nextInt(0, 101) <= 2)
                    nextRoom = rf.createPotionRoom();
                else
                    nextRoom = rf.createEnemyRoom();
                nexts[i] = new RoomTree(nextRoom, doorOrientationsForDoor(next.getOrientation(), i));
            }
            next.addNexts(nexts);
            next = nexts[random.nextInt(0, nbNext)];
        }
        RoomTree bossRoom = new RoomTree(rf.createBossRoom(), doorOrientationsForDoor(next.getOrientation(), 0));
        next.addNexts(bossRoom);
        Dungeon dungeon = new Dungeon(beginRoom);
        bossRoom.getRoom().addElements(getDoorToNeutralRoom(bossRoom.getRoom(), neutralDungeon, next.getOrientation().inv()));
        createDungeon(tree.iterator().next(), dungeon);

        return dungeon;
    }

    private void createDungeon(RoomTree tree, Dungeon dungeon) {
        for(RoomTree next : tree) {
            createDoors(tree.getRoom(), next.getOrientation().inv(), next.getRoom());
            createDungeon(next, dungeon);
        }
        dungeon.addRooms(tree.getRoom());
    }

    private Orientation doorOrientationsForDoor(Orientation or, int number) {
        return switch (number) {
            case 0 -> or;
            case 1 -> or.getSide();
            case 2 -> or.getSide().inv();
            default -> or.inv();
        };
    }

    private void createDoors(Room from, Orientation doorOrientation, Room to) {
        DoorToDoor fromDoorToDoor = switch (doorOrientation) {
            case LEFT -> new DoorToDoor(10, (int) (from.getHeight() / 2f), from, Orientation.RIGHT);
            case RIGHT -> new DoorToDoor(from.getWidth() - 10, (int) (from.getHeight() / 2f), from, Orientation.LEFT);
            case DOWN -> new DoorToDoor((int) (from.getWidth() / 2f), 10, from, Orientation.UP);
            case UP -> new DoorToDoor((int) (from.getWidth() / 2f), from.getHeight() - 10, from, Orientation.DOWN);
        };
        DoorToDoor toDoorToDoor = switch (doorOrientation) {
            case LEFT -> new DoorToDoor(to.getWidth() - 10, (int) (to.getHeight() / 2f), to, Orientation.LEFT);
            case RIGHT -> new DoorToDoor(10, (int) (to.getHeight() / 2f), to, Orientation.RIGHT);
            case DOWN -> new DoorToDoor((int) (to.getWidth() / 2f), to.getHeight() - 10, to, Orientation.DOWN);
            case UP -> new DoorToDoor((int) (to.getWidth() / 2f), 10, to, Orientation.UP);
        };
        from.addElements(fromDoorToDoor);
        to.addElements(toDoorToDoor);
        fromDoorToDoor.setTo(toDoorToDoor);
        toDoorToDoor.setTo(fromDoorToDoor);
    }

    private DoorToDungeon getDoorToNeutralRoom(Room from, Dungeon to, Orientation doorOrientation) {
        return switch (doorOrientation) {
            case LEFT -> new DoorToDungeon(10, 50, from, to, 50, 10, Orientation.RIGHT);
            case RIGHT -> new DoorToDungeon(90, 50, from, to, 50, 10, Orientation.LEFT);
            case DOWN -> new DoorToDungeon(50, 10, from, to, 50, 10, Orientation.UP);
            case UP -> new DoorToDungeon(50, 90, from, to, 50, 10, Orientation.DOWN);
        };
    }
}
