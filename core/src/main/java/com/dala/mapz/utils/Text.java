package com.dala.mapz.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Text {
    private final BitmapFont police1;
    private final BitmapFont police2;

    /**
     * Génère des polices.
     */
    public Text(){
        FreeTypeFontGenerator fGen = new FreeTypeFontGenerator(Gdx.files.internal("Comic_Sans_MS_Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fParams.size = 20;
        fParams.color = new Color(0f, 0f, 1f, 0.75f);
        fParams.borderColor = Color.BLACK;
        fParams.borderWidth = 3;

        police1 = fGen.generateFont(fParams);
        police1.setUseIntegerPositions(false);

        fParams.size=130;
        fParams.color=new Color(1f, 0f, 0f, 0.75f);
        fParams.borderColor = Color.BLACK;
        fParams.borderWidth = 3;

        police2 = fGen.generateFont(fParams);
        police2.setUseIntegerPositions(false);

        fGen.dispose();
    }

    public void gameOverText(SpriteBatch spriteBatch, float x, float y){
        police2.draw(spriteBatch,"GAME OVER",x,y,400,0,true);
    }
    public void moneyText(SpriteBatch spriteBatch, String money){
        police1.draw(spriteBatch,money,510,460,60,0,true);
    }

    public void pnjText(SpriteBatch spriteBatch, String text, float x, float y){
        police1.draw(spriteBatch,text,x,y,200,1,true);
    }
}
