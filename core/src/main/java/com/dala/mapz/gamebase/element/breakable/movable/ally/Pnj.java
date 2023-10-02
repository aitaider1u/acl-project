package com.dala.mapz.gamebase.element.breakable.movable.ally;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.gamebase.element.breakable.Movable;
import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.dala.mapz.gamebase.element.breakable.movable.Player;
import com.dala.mapz.gamebase.element.breakable.movable.ally.upgrade.Upgrade;
import com.dala.mapz.utils.Constants;
import com.dala.mapz.utils.Orientation;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Pnj extends Movable {

    private Upgrade upgrade;

    public Pnj(int x, int y){
        super(x,y);
        height = 5;
        width = 4;
        health = 9999;
        material = Material.GREAT_MAGICIAN;
        weapon = new Weapon(2f, 4f, 2f, 50);
        dynamic = false;
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0 -> upgrade = Upgrade.LIFE;
            case 1 -> upgrade = Upgrade.ATTACK;
            case 2 -> upgrade = Upgrade.SPEED;
        }

    }

    public void benediction(Player player){
        if(player.getMoney()>=25){
            player.setMaxHealth(player.getMaxHealth() + upgrade.getLife());
            player.lifeRegeneration(upgrade.getLife());
            player.setSpeed(upgrade.getSpeed());
            player.increaseDamage(upgrade.getDamage());
            player.setMoney(player.getMoney()-25);
        }
    }

    public Upgrade getUpgrade() {
        return upgrade;
    }

    @Override
    public void update() {
        if (this.health < 9999) {
            attack();
            health += 10;
        }

        Vector2 positionPlayer = Mapz.getInstance().getGameScreen().getPlayer().getBody().getPosition();
        Vector2 position = body.getPosition();
        float dtX = position.x - positionPlayer.x;
        float dtY = position.y - positionPlayer.y - (height/2f);

        if (dtY > 0) {
            this.orientation = Orientation.DOWN;
        } else {
            this.orientation = (dtX > 0)? Orientation.LEFT : Orientation.RIGHT;
        }
    }

    @Override
    public void createTextures() {
        sprite = new Sprite(material.getTexture());
        sprite.setBounds(x-((width* Constants.GAME_SCALE)/2f), y-((height*Constants.GAME_SCALE)/2f), width*Constants.GAME_SCALE, height*Constants.GAME_SCALE);

        TextureRegion[][] tmp = TextureRegion.split(material.getTexture(),
                material.getTexture().getWidth() / Constants.FRAME_WALK_COLS,
                material.getTexture().getHeight() / 21);

        walkAnimations = List.of(new Animation<>(Constants.DURATION_ANIMATION, tmp[8]),
                new Animation<>(Constants.DURATION_ANIMATION, tmp[9]),
                new Animation<>(Constants.DURATION_ANIMATION, tmp[10]),
                new Animation<>(Constants.DURATION_ANIMATION, tmp[11]));

        float fightDuration = weapon.duration() / 8;
        fightAnimations = List.of(new Animation<>(fightDuration, Arrays.copyOf(tmp[4], 8)),
                new Animation<>(fightDuration, Arrays.copyOf(tmp[5], 8)),
                new Animation<>(fightDuration, Arrays.copyOf(tmp[6], 8)),
                new Animation<>(fightDuration, Arrays.copyOf(tmp[7], 8)));

        deathAnimation = new Animation<>(Constants.DURATION_ANIMATION, Arrays.copyOf(tmp[20], Constants.FRAME_FIGHT_COLS + 1));
    }
}
