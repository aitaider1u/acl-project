package com.dala.mapz.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.dala.mapz.MapzTest;
import com.dala.mapz.gamebase.element.Breakable;
import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.dala.mapz.gamebase.element.breakable.movable.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BreakableTest {

    @BeforeEach
    public void setUp() {
        new HeadlessApplication(new MapzTest(), new HeadlessApplicationConfiguration());
        Gdx.gl = Mockito.mock(GL20.class);
    }
    @Test
    void takeDamage() {
        Breakable breakable = new Player(0,0);
        breakable.takeDamage(new Weapon(1,1,1,10));
        assertEquals(90,breakable.getHealth());
    }

    @Test
    void setMaxHealth() {
        Breakable breakable = new Player(0,0);
        breakable.setMaxHealth(150);
        assertEquals(breakable.getMaxHealth(),150);
    }

    @Test
    void lifeRegeneration() {
        Breakable breakable = new Player(0,0);
        breakable.takeDamage(new Weapon(1,1,1,10));
        breakable.lifeRegeneration(10);
        assertEquals(breakable.getHealth(),100);
    }
}