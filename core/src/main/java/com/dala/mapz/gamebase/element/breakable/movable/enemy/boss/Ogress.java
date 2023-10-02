package com.dala.mapz.gamebase.element.breakable.movable.enemy.boss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.Room;
import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.dala.mapz.gamebase.element.breakable.movable.MovableState;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.Boss;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.basic.OrcChild;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy.CompositeStrategy;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy.KeepDistance;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy.TowardsPlayer;
import com.dala.mapz.screens.GameScreen;

public class Ogress extends Boss {
    private final OrcChild[] children;
    public Ogress(int x, int y) {
        super(x, y, 2, 3);
        width = 9;
        height = 9;
        material = Material.OGRESS;
        weapon = new Weapon(0.5f, 10f, 0.5f, 10);
        health = 50;
        maxHealth = health;
        children = new OrcChild[]{
                new OrcChild(x, y),
                new OrcChild(x, y),
                new OrcChild(x, y)
        };

        strategy = new TowardsPlayer(this);
    }

    @Override
    public void createBody(World world) {
        super.createBody(world);
        for (OrcChild child : children) {
            child.createBody(world);
            child.setActive(false);
        }
    }

    @Override
    public void createTextures() {
        super.createTextures();
        for (OrcChild child : children) {
            child.createTextures();
        }
    }

    @Override
    public void takeDamage(Weapon weapon) {
        super.takeDamage(weapon);
        if (this.health <=0 && !(movableState.equals(MovableState.DEAD))){
            this.x = (int) this.getBody().getPosition().x;
            this.y = (int) this.getBody().getPosition().y;
            GameScreen gameScreen = Mapz.getInstance().getGameScreen();
            gameScreen.getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);

            Room room = gameScreen.getCurrentDungeon().getLoadedRoom();
            room.addElements(children);
            for (OrcChild child: children) {
                child.setActive(true);
                child.setX(this.x - width/2);
                child.setY(this.y - height/2);
            }
        }
    }
}
