package com.dala.mapz.gamebase.element;

import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;

public abstract class Breakable extends Element{
    private static final Color COLOR_DAMAGED = new Color(0.75f, 0.2f, 0.2f, 1f);
    protected int health = 100;
    protected int maxHealth = health;

    /**
     * Créé un Element.
     * @param x abscisse dans le monde.
     * @param y ordonnée dans le monde.
     */
    public Breakable(int x, int y) { super(x, y); }

    /**
     * Désactive le body si l'élément n'a pas de vie.
     */
    @Override
    public void update() {
        if(health <= 0) {
            this.body.setActive(false);
        }
    }

    /**
     * Baisse la vie de l'élément du nombre de dégâts de l'arme donnée.
     * @param weapon arme utilisée pour infliger des dégâts.
     */
    public void takeDamage(Weapon weapon){
        sprite.setColor(COLOR_DAMAGED);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                sprite.setColor(Color.WHITE);
            }
        }, 0.2f);
        this.health = this.health - weapon.damage();
    }
    public int getHealth() {
        return health;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public void setMaxHealth(int health){
        maxHealth = health;
    }

    public void lifeRegeneration(int life){
        if(health+life <= maxHealth) {
            health += life;
        }else{
            health = maxHealth;
        }
    }
}

