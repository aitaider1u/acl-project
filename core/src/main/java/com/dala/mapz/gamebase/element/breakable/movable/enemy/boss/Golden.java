package com.dala.mapz.gamebase.element.breakable.movable.enemy.boss;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.Room;
import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.dala.mapz.gamebase.element.breakable.movable.MovableState;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.Boss;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.basic.Skeleton;
import com.dala.mapz.screens.GameScreen;

import java.util.ArrayList;

public class Golden extends Boss {
    private final ArrayList<Skeleton> children;
    private boolean isLoaded = false;

    public Golden(int x, int y) {
        super(x, y, 5, 3);
        width = 9;
        height = 9;
        material = Material.GOLDEN;
        weapon = new Weapon(0.5f, 10f, 0.5f, 2);
        health = 1;
        maxHealth = health;
        children = new ArrayList<>();
    }

    @Override
    public void update() {
        if(!isLoaded) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (!movableState.equals(MovableState.DEAD) && Mapz.getInstance().getGameScreen().getPlayer().getHealth() >= 0) {
                        GameScreen gameScreen = Mapz.getInstance().getGameScreen();
                        Room room = gameScreen.getCurrentDungeon().getLoadedRoom();
                        Vector2 pos = body.getPosition();
                        for (int i = 0; i < 4; i++) {
                            Skeleton child = new Skeleton((int) pos.x, (int) pos.y);
                            child.createBody(gameScreen.getWorld());
                            child.createTextures();
                            children.add(child);
                            room.addElements(child);
                        }
                    } else this.cancel();
                }
            }, 0.2f, 15);
        }
    }


    @Override
    public void createBody(World world) {
        super.createBody(world);
        for (Skeleton child : children) {
            child.createBody(world);
            child.setActive(false);
        }
    }

    @Override
    public void createTextures() {
        super.createTextures();
        for (Skeleton child : children) {
            child.createTextures();
        }
    }
}
