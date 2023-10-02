package com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy;

import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.element.breakable.movable.Enemy;
import com.dala.mapz.gamebase.element.breakable.movable.Player;
import com.dala.mapz.utils.SoundManager;

import java.util.Random;

public class RandomWalk extends Strategy{
    private Player player = Mapz.getInstance().getGameScreen().getPlayer();
    private int x = 0, y = 0, walkStep =0;

    public RandomWalk(Enemy enemy) {
        super(enemy);
    }

    @Override
    public void move() {
        if (walkStep <=0){
            Random random = new Random();
            walkStep = 1+  random.nextInt(150);
            x = random.nextInt(3) -1;
            y = random.nextInt(3) -1;
        }
        enemy.move(enemy.getMoveSpeed() * x,enemy.getMoveSpeed()* y);
        walkStep--;
        enemy.getBody().setLinearVelocity(0f, 0f);
    }

    @Override
    public void attack() {
        if(enemy.canAttackPlayer()){
            this.enemy.attack();
        }
    }

    @Override
    public void update() {
        this.move();
        this.attack();
    }
}
