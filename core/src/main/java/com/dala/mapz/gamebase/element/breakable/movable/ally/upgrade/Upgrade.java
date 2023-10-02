package com.dala.mapz.gamebase.element.breakable.movable.ally.upgrade;

public enum Upgrade {
    LIFE("Maximum de PV augmenté pour 25 PO",25,0,0),
    ATTACK("Attaque augmentée pour 25 PO",0,2,0),
    SPEED("Vitesse augmentée pour 25 PO",0,0,500);


    private String text;

    private int life;

    private int damage;

    private int speed;
    Upgrade(String text, int life, int damage, int speed){
        this.text = text;
        this.life = life;
        this.damage = damage;
        this.speed = speed;
    }

    public String getText() {
        return text;
    }

    public int getLife() {
        return life;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }
}
