package com.dala.mapz;

import com.badlogic.gdx.physics.box2d.*;
import com.dala.mapz.gamebase.element.Breakable;
import com.dala.mapz.gamebase.element.breakable.LifePotion;
import com.dala.mapz.gamebase.element.breakable.movable.ally.Pnj;
import com.dala.mapz.gamebase.element.breakable.Lootable;
import com.dala.mapz.gamebase.element.doors.AbstractDoor;
import com.dala.mapz.gamebase.element.breakable.Coin;
import com.dala.mapz.gamebase.element.breakable.movable.Enemy;
import com.dala.mapz.gamebase.element.breakable.movable.Player;
import com.dala.mapz.utils.SoundManager;

/**
 * Gestionnaire de collisions.
 */
public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        Player player;
        Breakable userData;

        if (fa.getUserData() instanceof Player pl && fb.getUserData() instanceof Breakable br){
            player = pl;
            userData = br;
        } else if (fb.getUserData() instanceof Player pl && fa.getUserData() instanceof Breakable br){
            player = pl;
            userData = br;
        } else if(fa.getUserData() instanceof AbstractDoor && fb.getUserData() instanceof Player
        || fb.getUserData() instanceof AbstractDoor && fa.getUserData() instanceof Player) {
            AbstractDoor door;
            if(fa.getUserData() instanceof AbstractDoor) {
                door = (AbstractDoor) fa.getUserData();
            } else {
                door = (AbstractDoor) fb.getUserData();
            }
            door.goThrough();
            return;
        } else return;

        if (userData instanceof Coin coin){
            SoundManager.COIN.play();
            coin.takeDamage(player.getWeapon());
            player.incrementMoney();
        }

        if (userData instanceof LifePotion potion){
            SoundManager.LIFE_POTION.play();
            potion.takeDamage(player.getWeapon());
            player.lifeRegeneration(potion.getPotionValue());
        }

        if(userData instanceof Lootable lootable) {
            lootable.takeDamage(player.getWeapon());
            player.changeWeapon(lootable);
        }

        if(player.isFighting()) {
            userData.takeDamage(player.getWeapon());
        }

        if(userData instanceof Enemy enemy) {
            player.takeDamage(enemy.getWeapon());
        }

        if (userData instanceof Pnj pnj && pnj.isFighting()) {
            player.takeDamage(pnj.getWeapon());
        }

        if(userData instanceof  Pnj pnj){
            pnj.benediction(player);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
