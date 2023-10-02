package com.dala.mapz.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dala.mapz.Mapz;
import com.dala.mapz.utils.SoundManager;

public class MenuScreen implements Screen,ClickableScreen {
    private final Skin skin;
    private final Stage stage;
    private Texture background = new Texture("background.png");

    public MenuScreen() {
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        stage = new Stage(new ScreenViewport());

        Label gameTitle = new Label("MapZ",skin,"big");
        gameTitle.setSize(Gdx.graphics.getWidth()/8f,Gdx.graphics.getHeight()/8f);
        gameTitle.setPosition(Gdx.graphics.getWidth()/2f-gameTitle.getWidth()/2f,Gdx.graphics.getWidth()/2f+Gdx.graphics.getHeight()/8f);
        gameTitle.setAlignment(Align.center);

        Button startButton = new TextButton("Start",skin,"small");
        startButton.setSize(Gdx.graphics.getWidth()/4f,Gdx.graphics.getHeight()/8f);
        startButton.setPosition(Gdx.graphics.getWidth()/2f-startButton.getWidth()/2f,Gdx.graphics.getHeight()/3.5f);


        TextField txtUsername = new TextField("", skin);
        txtUsername.setMessageText("Player name");
        txtUsername.setSize(Gdx.graphics.getWidth()/4f,Gdx.graphics.getHeight()/10f);
        txtUsername.setPosition(Gdx.graphics.getWidth()/2f-startButton.getWidth()/2f,Gdx.graphics.getHeight()/2f);
        txtUsername.setAlignment(Align.center);
        String test = txtUsername.getText();


        startButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!txtUsername.getText().isBlank()) {
                    SoundManager.BUTTON.play();
                    Mapz.getInstance().getGameScreen().getPlayer().setName(txtUsername.getText());
                    Mapz.getInstance().setGameScreen();
                }
            }
        });
        stage.addActor(gameTitle);
        stage.addActor(startButton);
        stage.addActor(txtUsername);

    }

    @Override
    public void show(){

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
