package com.dala.mapz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.dala.mapz.gamebase.Dungeon;
import com.dala.mapz.gamebase.Room;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DungeonTest {

    @BeforeEach
    public void setUp() {
        new HeadlessApplication(new MapzTest(), new HeadlessApplicationConfiguration());
        Gdx.gl = Mockito.mock(GL20.class);
    }

    @org.junit.jupiter.api.Test
    void addOneRoom() {
        Dungeon dungeon = new Dungeon(new Room(100, 250));
        assertEquals(dungeon.getNbRooms(),1);
    }


    @org.junit.jupiter.api.Test
    void addTwoRoom() {
        Dungeon dungeon = new Dungeon(new Room(100, 250));
        Room room2 = new Room(23, 332);
        dungeon.addRooms(room2);
        assertEquals(dungeon.getNbRooms(),2);
    }

    @org.junit.jupiter.api.Test
    void removeOneRoom() {
        Room room = new Room(100, 250);
        Dungeon dungeon = new Dungeon(room);
        dungeon.removeRooms(room);
        assertEquals(dungeon.getNbRooms(),0);
    }

    @org.junit.jupiter.api.Test
    void removeTwoRoom() {
        Room room = new Room(100, 250);
        Dungeon dungeon = new Dungeon(room);
        Room room2 = new Room(100, 250);
        dungeon.removeRooms(room);
        assertEquals(dungeon.getNbRooms(),0);
    }

}