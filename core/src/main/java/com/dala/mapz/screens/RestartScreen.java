package com.dala.mapz.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dala.mapz.Mapz;
import com.dala.mapz.utils.SoundManager;

public class RestartScreen implements Screen,ClickableScreen {
    private final Skin skin;
    private final Stage stage;
    private Texture background = new Texture("floor.png");

    public RestartScreen() {
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        stage = new Stage(new ScreenViewport());

        Button restartButton = new TextButton("Restart", skin,"small");
        restartButton.setSize(Gdx.graphics.getWidth()/4f,Gdx.graphics.getHeight()/8f);
        restartButton.setPosition(Gdx.graphics.getWidth()/2f-restartButton.getWidth()/2f,Gdx.graphics.getHeight()/2f);

        restartButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                SoundManager.BUTTON.play();
                Mapz.getInstance().setGameScreen();
            }
        });

        stage.addActor(restartButton);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());   // back
        stage.getBatch().end();
        stage.draw();
    }


    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
