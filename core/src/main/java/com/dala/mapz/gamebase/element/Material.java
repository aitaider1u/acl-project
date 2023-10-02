package com.dala.mapz.gamebase.element;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.Random;

public enum Material
{
    TREE(new float[]{0f, 1.5f, 1.5f, -0.5f, -1.5f, -0.5f, -0.3f, -1.5f, 0.3f, -1.5f},false,new Texture("tree1.png"),new Texture("tree2.png"),new Texture("tree3.png")),
    DOOR(new float[]{-0.8f, -1f, -0.8f, 0.8f, 0.8f, -1f, 0.8f, 0.8f}, true, new Texture("door1.png")),
    WALL(new float[]{-1f, 1f, 1f, -1f, 1f, 1f, -1f, -1f}, false, new Texture("wall.png")),
    PLAYER(new float[]{-0.5f, 0.7f, 0.5f, 0.7f, -0.5f, -1.3f, 0.5f, -1.3f}, true,
            new Texture("player/dague.png"), new Texture("player/pickaxe.png"),
            new Texture("player/hache.png"), new Texture("player/hammer.png")),
    GREAT_MAGICIAN(new float[]{-0.5f, 0.7f, 0.5f, 0.7f, -0.5f, -1.3f, 0.5f, -1.3f}, true, new Texture("ally/magician.png")),
    ORC_CHILD(new float[]{-0.4f, 0.3f, 0.4f, 0.3f, -0.4f, -1.5f, 0.4f, -1.5f}, true, new Texture("enemy/orc_child.png")),
    WOLF_CHILD(new float[]{-0.4f, 0.3f, 0.4f, 0.3f, -0.4f, -1.5f, 0.4f, -1.5f}, true, new Texture("enemy/wolf_child.png")),
    BOAR_CHILD(new float[]{-0.4f, 0.3f, 0.4f, 0.3f, -0.4f, -1.5f, 0.4f, -1.5f}, true, new Texture("enemy/boar_child.png")),
    SKELETON(new float[]{-0.4f, 0.3f, 0.4f, 0.3f, -0.4f, -1.5f, 0.4f, -1.5f}, true, new Texture("enemy/skeleton.png")),
    OGRESS(new float[]{-0.4f, 0.3f, 0.4f, 0.3f, -0.4f, -1.5f, 0.4f, -1.5f}, true, new Texture("enemy/ogress.png")),
    SANTA(new float[]{-0.4f, 0.3f, 0.4f, 0.3f, -0.4f, -1.5f, 0.4f, -1.5f}, true, new Texture("enemy/santa.png")),
    KNIGHT(new float[]{-0.4f, 0.3f, 0.4f, 0.3f, -0.4f, -1.5f, 0.4f, -1.5f}, true, new Texture("enemy/knight.png")),
    GOLDEN(new float[]{-0.4f, 0.3f, 0.4f, 0.3f, -0.4f, -1.5f, 0.4f, -1.5f}, true, new Texture("enemy/golden.png")),
    COIN(true,new Texture("money1.png")),
    ROCK(new float[]{-1.3f, -1f, 1.3f, -1f, 1.3f, 0.8f, 0.8f, 1.3f, -1.3f, 0.4f, -0.4f, 1.3f}, false, new Texture("rock.png")),
    POTION(new float[]{-1f, 1f, 1f, -1f, 1f, 1f, -1f, -1f},false,new Texture("potion.png")),
    AXE(new float[]{-0.2f, 0.2f, 0.2f, -0.2f, 0.2f, 0.2f, -0.2f, -0.2f}, false, new Texture("axe.png")),
    DAGUE(new float[]{-0.2f, 0.2f, 0.2f, -0.2f, 0.2f, 0.2f, -0.2f, -0.2f}, false, new Texture("dague.png")),
    PICKAXE(new float[]{-0.2f, 0.2f, 0.2f, -0.2f, 0.2f, 0.2f, -0.2f, -0.2f}, false, new Texture("pickaxe.png")),
    HAMMER(new float[]{-0.2f, 0.2f, 0.2f, -0.2f, 0.2f, 0.2f, -0.2f, -0.2f}, false, new Texture("hammer.png")),
    DEFAULT(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f});
    private final float[] shape;
    private Texture[] textures;
    private final boolean hasAnimation;

    Material(float[] shape) {
        this.shape = shape;
        hasAnimation = false;
    }

    Material(float[] shape, boolean hasAnimation, Texture... textures) {
        this.shape = shape;
        this.textures =textures;
        this.hasAnimation = hasAnimation;
    }

    Material(boolean hasAnimation,Texture... textures) {
        this.shape =  null;
        this.textures =textures;
        this.hasAnimation = hasAnimation;
    }

    /**
     * Créer la fixture avec la forme.
     * @param width largeur pour la forme.
     * @param height hauteur pour la forme.
     * @return la fixture.
     */

    public FixtureDef getFixtureDef(int width, int height) {
        float[] scaledShape = new float[shape.length];
        for(int i = 0; i < shape.length; i+=2) {
            scaledShape[i] = width * shape[i];
            scaledShape[i+1] = height * shape[i+1];
        }
        PolygonShape polShape = new PolygonShape();
        polShape.set(scaledShape);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polShape;
        return fixtureDef;
    }
    public FixtureDef getFixtureDef(float radius) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        return fixtureDef;
    }

    public boolean hasAnimation() {
        return hasAnimation;
    }

    public Texture getTexture() {
        Random random = new Random();
        return textures[random.nextInt(textures.length)];
    }

    public int getNbTextures() {
        return textures.length;
    }

    public Texture getTexture(int index) {
        return textures[index];
    }
    public boolean hasTexture() {
        return this.textures != null && this.textures.length != 0;
    }

    public static void dispose()
    {
        for(Material m : Material.values())
        {
            if(m.textures != null) {
                for (Texture t : m.textures) {
                    t.dispose();
                }
            }
        }
    }
}
