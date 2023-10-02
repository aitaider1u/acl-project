package com.dala.mapz.gamebase.element.breakable.movable.enemy.boss;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.Room;
import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.dala.mapz.gamebase.element.breakable.movable.MovableState;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.basic.BoarChild;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.Boss;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy.CompositeStrategy;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy.KeepDistance;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy.TowardsPlayer;
import com.dala.mapz.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class Santa extends Boss {
    private List<BoarChild> children;
    private boolean isLoaded = false;

    public Santa(int x, int y) {
        super(x, y, 5, 3);
        width = 9;
        height = 9;
        material = Material.SANTA;
        weapon = new Weapon(0.5f, 8f, 0.5f, 10);
        health = 150;
        maxHealth = health;
        children = new ArrayList<>();

        strategy = new CompositeStrategy(this, () -> {
            Vector2 positionPlayer = Mapz.getInstance().getGameScreen().getPlayer().getBody().getPosition();
            Vector2 positionEnemy = body.getPosition();
            double distance =  Math.sqrt(Math.pow( positionEnemy.x - positionPlayer.x,2)+Math.pow(positionEnemy.y-positionPlayer.y,2));
            return distance > 15f || Mapz.getInstance().getGameScreen().getPlayer().getHealth()<=0;
        }, new KeepDistance(this), new TowardsPlayer(this));
    }

    @Override
    public void update() {
        super.update();
        if(!isLoaded) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (!movableState.equals(MovableState.DEAD) && Mapz.getInstance().getGameScreen().getPlayer().getHealth() >= 0) {
                        Vector2 pos = body.getPosition();
                        BoarChild child = new BoarChild((int) pos.x, (int) pos.y);
                        GameScreen gameScreen = Mapz.getInstance().getGameScreen();
                        child.createBody(gameScreen.getWorld());
                        child.createTextures();
                        children.add(child);
                        Room room = gameScreen.getCurrentDungeon().getLoadedRoom();
                        room.addElements(child);
                    } else this.cancel();
                }
            }, 1, 8);
            isLoaded = true;
        }
    }

    @Override
    public void createBody(World world) {
        super.createBody(world);
        for (BoarChild child : children) {
            child.createBody(world);
            child.setActive(false);
        }
    }

    @Override
    public void createTextures() {
        super.createTextures();
        for (BoarChild child : children) {
            child.createTextures();
        }
    }
}
