package com.dala.mapz.gamebase.element.breakable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Timer;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.element.Breakable;
import com.dala.mapz.gamebase.element.breakable.movable.MovableState;
import com.dala.mapz.screens.GameScreen;
import com.dala.mapz.utils.Constants;
import com.dala.mapz.utils.Orientation;
import com.dala.mapz.utils.SoundManager;

import java.util.Arrays;
import java.util.List;

/**
 * Classe représentant des éléments qui peuvent bouger (avec un body dynamique).
 */
public abstract class Movable extends Breakable {
    protected transient Weapon weapon;
    protected transient Body weaponBody;
    protected transient Fixture weaponFixture;
    protected transient Orientation orientation;
    protected transient boolean isFighting;
    protected transient List<Animation<TextureRegion>> walkAnimations;
    protected transient List<Animation<TextureRegion>> fightAnimations;
    protected transient Animation<TextureRegion> deathAnimation;
    private transient float stateTime = 0f;
    protected transient MovableState movableState = MovableState.NOT_MOVING;
    private int indexTexture = 0;

    public Movable(int x, int y) {
        super(x, y);
        this.dynamic = true;
        this.isFighting = false;
        orientation = Orientation.DOWN;
    }

    /**
     * Bouge l'élément. Replace l'arme et l'orientation en fonction du déplacement.
     * @param dx abscisse du vecteur de mouvement.
     * @param dy ordonnée du vecteur de mouvement.
     */
    public void move(float dx,float dy) {
        if(health <= 0) return;
        if (dx>0) {
            orientation = Orientation.RIGHT;
        } else if(dx<0) {
            orientation = Orientation.LEFT;
        }
        if (dy>0) {
            orientation = Orientation.UP;
        } else if(dy<0) {
            orientation = Orientation.DOWN;
        }

        if (!isFighting) {
            if (dx == 0 && dy == 0) {
                movableState = MovableState.NOT_MOVING;
            } else {
                movableState = MovableState.WALKING;
            }
            weaponBody.setTransform(body.getPosition(), orientation.getAngle());
        }

        this.body.applyForceToCenter(dx, dy,false);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Créer le body de l'élément avec son arme.
     * @param world monde utilisé.
     */
    @Override
    public void createBody(World world) {
        super.createBody(world);

        BodyDef bodyDefWeapon = new BodyDef();
        bodyDefWeapon.type = BodyDef.BodyType.DynamicBody;
        bodyDefWeapon.position.set(this.x, this.y);

        weaponBody = world.createBody(bodyDefWeapon);
        weaponFixture = weaponBody.createFixture(weapon.createShape(), 0f);
        weaponFixture.setSensor(true);
        weaponFixture.setUserData(this);

        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.bodyA = body;
        jointDef.bodyB = weaponBody;
        jointDef.collideConnected = false;
        world.createJoint(jointDef);
    }

    public void changeWeapon(Lootable lootable) {
        this.weapon = lootable.getLootableWeapon().getWeapon();
        GameScreen game = Mapz.getInstance().getGameScreen();
        this.x = (int) body.getPosition().x;
        this.y = (int) body.getPosition().y;
        game.addBodyToRemove(weaponBody, body);
        game.getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);
        indexTexture = lootable.getLootableWeapon().getIndex();
        createBody(Mapz.getInstance().getGameScreen().getWorld());
        createTextures();
    }

    public boolean isFighting() {
        return isFighting;
    }

    public void setFighting(boolean fighting) {
        isFighting = fighting;
    }

    public MovableState getMovableState() {
        return movableState;
    }

    /**
     * Actualise l'animation de l'élément et la dessine.
     */
    @Override
    public void draw() {
        stateTime += Gdx.graphics.getDeltaTime();
        if(material.hasAnimation()){
            TextureRegion currentFrame;
            stateTime += Gdx.graphics.getDeltaTime();
            Animation<TextureRegion> animation;
            switch (movableState) {
                case FIGHTING -> animation = fightAnimations.get(orientation.getIndex());
                case DEAD -> animation = deathAnimation;
                case NOT_MOVING -> {
                    stateTime = 0f;
                    animation = walkAnimations.get(orientation.getIndex());
                }
                default -> animation = walkAnimations.get(orientation.getIndex());
            }
            currentFrame = animation.getKeyFrame(stateTime, !movableState.equals(MovableState.DEAD));
            sprite.setRegion(currentFrame);
            sprite.setCenter(body.getPosition().x, body.getPosition().y);
            sprite.draw(Mapz.getInstance().getBatch());
        }
    }

    /**
     * Met à jour l'état de l'élément.
     */
    @Override
    public void update() {
        super.update();
        if (health <= 0) {
            weaponBody.setActive(false);
            movableState = MovableState.DEAD;
        }
    }

    /**
     * Créer les textures de l'animation.
     */
    @Override
    public void createTextures() {
        super.createTextures();
        if (material.hasAnimation()) {
            TextureRegion[][] tmp = TextureRegion.split(material.getTexture(indexTexture),
                    material.getTexture().getWidth() / Constants.FRAME_WALK_COLS,
                    material.getTexture().getHeight() / Constants.FRAME_ROWS);

            walkAnimations = List.of(new Animation<>(Constants.DURATION_ANIMATION, tmp[0]),
                    new Animation<>(Constants.DURATION_ANIMATION, tmp[1]),
                    new Animation<>(Constants.DURATION_ANIMATION, tmp[2]),
                    new Animation<>(Constants.DURATION_ANIMATION, tmp[3]));

            float fightDuration = weapon.duration() / Constants.FRAME_FIGHT_COLS;
            fightAnimations = List.of(new Animation<>(fightDuration, Arrays.copyOf(tmp[4], Constants.FRAME_FIGHT_COLS)),
                    new Animation<>(fightDuration, Arrays.copyOf(tmp[5], Constants.FRAME_FIGHT_COLS)),
                    new Animation<>(fightDuration, Arrays.copyOf(tmp[6], Constants.FRAME_FIGHT_COLS)),
                    new Animation<>(fightDuration, Arrays.copyOf(tmp[7], Constants.FRAME_FIGHT_COLS)));

            deathAnimation = new Animation<>(Constants.DURATION_ANIMATION, Arrays.copyOf(tmp[12], Constants.FRAME_FIGHT_COLS + 1));
        }
    }

    /**
     * Si l'élément n'est pas déjà en attaque, change son animation et son arme pour le faire attaquer.
     * L'attaque est arrêtée au bout d'un certain temps.
     */
    public void attack() {
        if (!isFighting && !movableState.equals(MovableState.DEAD)) {
            this.movableState = MovableState.FIGHTING;
            isFighting = true;
            weaponFixture.setSensor(false);
            weaponBody.setTransform(body.getPosition().x + (width/2f + weapon.width())*orientation.getX(), body.getPosition().y + (height/2f + weapon.height())*orientation.getY(), weaponBody.getAngle());
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (isEnemy())
                        SoundManager.ENEMY_ATTACK.play();
                    isFighting = false;
                    movableState = MovableState.NOT_MOVING;
                    weaponBody.setAwake(false);
                    weaponFixture.setSensor(true);
                    weaponBody.setTransform(body.getPosition(), weaponBody.getAngle());
                }
            }, weapon.duration() /2f);
        }
    }

    public Orientation getOrientation(){
        return orientation;
    }

    @Override
    public String toString() {
        return  super.toString();
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setActive(boolean active) {
        body.setActive(active);
        weaponBody.setActive(active);
    }
}
