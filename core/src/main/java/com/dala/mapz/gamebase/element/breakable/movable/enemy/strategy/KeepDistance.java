package com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy;

import com.badlogic.gdx.math.Vector2;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.element.breakable.movable.Enemy;
import com.dala.mapz.gamebase.element.breakable.movable.Player;
import com.dala.mapz.utils.Orientation;

public class KeepDistance extends Strategy{

    public KeepDistance(Enemy enemy) {
        super(enemy);
    }

    @Override
    public void move() {
        Player player = Mapz.getInstance().getGameScreen().getPlayer();
        Vector2 positionPlayer = player.getBody().getPosition();
        Vector2 positionEnemy = enemy.getBody().getPosition();
        if(positionEnemy.x < positionPlayer.x){
            enemy.move(-enemy.getMoveSpeed(),0);
        }else{
            enemy.move(enemy.getMoveSpeed(),0);
        }
        if(positionEnemy.y < positionPlayer.y){
            enemy.move(0,-enemy.getMoveSpeed());
        }else{
            enemy.move(0,enemy.getMoveSpeed());
        }
        enemy.setOrientation(this.calculateOrientation(enemy,player));
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

    private Orientation calculateOrientation(Enemy enemy, Player player){
        Vector2 positionPlayer = player.getBody().getPosition();
        Vector2 positionEnemy = enemy.getBody().getPosition();
        float dtX = positionEnemy.x -positionPlayer.x;
        float dtY = positionEnemy.y -positionPlayer.y;

        if(Math.abs(dtX) > Math.abs(dtY)){
            if (dtX > 0) return Orientation.RIGHT;
            else return Orientation.LEFT;
        }else {
            if (dtY > 0) return Orientation.UP;
            else return Orientation.DOWN;
        }

    }
}
