package com.dala.mapz.element.movable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.dala.mapz.MapzTest;
import com.dala.mapz.gamebase.element.breakable.movable.Player;
import com.dala.mapz.utils.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @BeforeEach
    void setUp() {
        new HeadlessApplication(new MapzTest(), new HeadlessApplicationConfiguration());
        Gdx.gl = Mockito.mock(GL20.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addMoney() {
        Player player = new Player(0,0);
        player.incrementMoney();
        assertEquals(player.getMoney(),1);
    }

    @Test
    void setSpeed() {
        Player player = new Player(0,0);
        player.setSpeed(400);
        assertEquals(1900,Constants.PLAYER_SPEED);
    }

    @Test
    void increaseDamage() {
        Player player = new Player(0,0);
        player.increaseDamage(4);
        assertEquals(9,player.getWeapon().damage());
    }
}