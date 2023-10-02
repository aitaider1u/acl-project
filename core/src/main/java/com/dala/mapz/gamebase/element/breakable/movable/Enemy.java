package com.dala.mapz.gamebase.element.breakable.movable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.element.breakable.Coin;
import com.dala.mapz.gamebase.element.breakable.LifePotion;
import com.dala.mapz.gamebase.element.breakable.Movable;
import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy.CompositeStrategy;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy.RandomWalk;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy.Strategy;
import com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy.TowardsPlayer;
import com.dala.mapz.utils.SoundManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe représentant un ennemi.
 */
public abstract class Enemy extends Movable {
    private int moveSpeed;
    protected Strategy strategy;
    protected List<Coin> coins = new ArrayList<>();
    protected LifePotion potion = null;
    protected double potionDrop = 0.5;
    protected Enemy(int x, int y, int moveSpeed, int valueDead) {
        super(x, y);
        this.moveSpeed = moveSpeed;
        Random random = new Random();
        if(random.nextInt(10)<=10*potionDrop){
            potion = new LifePotion(x,y, false);
        }

        for(int i = 0; i < valueDead; i++) {
            coins.add(new Coin(x, y));
        }

       this.strategy = new CompositeStrategy(this, () -> {
           Vector2 positionPlayer = Mapz.getInstance().getGameScreen().getPlayer().getBody().getPosition();
           Vector2 positionEnemy = body.getPosition();
           double distance =  Math.sqrt(Math.pow( positionEnemy.x - positionPlayer.x,2)+Math.pow(positionEnemy.y-positionPlayer.y,2));
           return distance > 20f || Mapz.getInstance().getGameScreen().getPlayer().getHealth()<=0;
       }, new RandomWalk(this), new TowardsPlayer(this));
    }

    @Override
    public void createBody(World world) {
        super.createBody(world);
        for(Coin coin : coins) {
            coin.createBody(world);
        }
        if(potion!=null){
            potion.createBody(world);
        }
    }

    @Override
    public void draw() {
        super.draw();
        for(Coin coin : coins) {
            coin.draw();
        }
        if(potion!=null){
            potion.draw();
        }
    }

    @Override
    public void createTextures() {
        super.createTextures();
        for(Coin coin : coins) {
            coin.createTextures();
        }
        if(potion!=null){
            potion.createTextures();
        }
    }


    @Override
    public void update() {
        super.update();
        strategy.update();
        for(Coin coin : coins) {
            coin.update();
        }
        if(potion!=null){
            potion.update();
        }
    }

    @Override
    public void takeDamage(Weapon weapon) {
        super.takeDamage(weapon);
        if (this.health <=0 && !(movableState.equals(MovableState.DEAD))){
            SoundManager.ENEMY_DEATH.play();
            //save the position where the enemy has been killed
            this.x = (int) this.getBody().getPosition().x;
            this.y = (int) this.getBody().getPosition().y;
            Mapz.getInstance().getGameScreen().getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);
            Random random = new Random();
            for(Coin coin : coins) {
                coin.setActive(true);
                coin.setX(this.x - width/2 + random.nextInt((width)*4));
                coin.setY(this.y - height/2 + random.nextInt((height)*4));
            }
            if(potion!=null){
                potion.setActive(true);
                potion.setX(this.x - width/2 + random.nextInt((width)*4));
                potion.setY(this.y - height/2 + random.nextInt((height)*4));
            }
        }
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public boolean canAttackPlayer() {
        if(Mapz.getInstance().getGameScreen().getPlayer().getHealth()<=0)
            return false;
        Vector2 positionPlayer = Mapz.getInstance().getGameScreen().getPlayer().getBody().getPosition();
        Vector2 positionEnemy = body.getPosition();
        if( Math.abs(positionEnemy.x-positionPlayer.x) < Mapz.getInstance().getGameScreen().getPlayer().getWidth() &&
            Math.abs(positionEnemy.y-positionPlayer.y) < Mapz.getInstance().getGameScreen().getPlayer().getHeight()*2.2f){ //2.2f pour la feature de libgdx
            return true;
        }
        return false;
    }

    @Override
    public boolean isEnemy() {
        return true;
    }

    @Override
    public String toString() {
        return "Enemy : "+ super.toString();
    }
}
