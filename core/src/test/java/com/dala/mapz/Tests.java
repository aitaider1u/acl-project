package com.dala.mapz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.dala.mapz.gamebase.Room;
import com.dala.mapz.gamebase.element.Wall;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class Tests {
    @BeforeEach
    public void setUp() {
        new HeadlessApplication(new MapzTest(), new HeadlessApplicationConfiguration());
        Gdx.gl = Mockito.mock(GL20.class);
    }

    @Test
    public void tests() {
        Room room = new Room(50, 50);
        room.addElements(new Wall(10, 10));
    }

    @AfterEach
    public void tearDown() {

    }
}