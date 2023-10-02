package com.dala.mapz.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dala.mapz.CollisionListener;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.Dungeon;
import com.dala.mapz.gamebase.Room;
import com.dala.mapz.gamebase.element.Element;
import com.dala.mapz.gamebase.element.breakable.movable.Enemy;
import com.dala.mapz.gamebase.element.breakable.movable.MovableState;
import com.dala.mapz.gamebase.element.breakable.movable.Player;
import com.dala.mapz.gamebase.element.breakable.movable.ally.Pnj;
import com.dala.mapz.gamebase.element.doors.DoorToDungeon;
import com.dala.mapz.gamebase.factories.DungeonFactory;
import com.dala.mapz.utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private boolean debugMode;
    private Dungeon currentDungeon;
    private Player player = new Player(50, 10);
    private final CollisionListener collisionListener = new CollisionListener();
    private Box2DDebugRenderer box2DDebugRenderer;
    private final World world = new World(new Vector2(), false);
    private final List<Body> bodiesToRemove = new ArrayList<>();
    private Dungeon neutralDungeon;
    private Text text;
    private SpriteBatch hudBatch;
    private Texture floorTexture;
    private DoorToDungeon doorNextDungeon;
    private Texture healthBarPlayer;
    private Texture healthBarEnnemy;
    private Texture healthBarPlayerEmpty;
    private Texture healthBarEnnemyEmpty;
    private Pnj pnj;
    private float timeSinceLastDash;
    private boolean dash;

    public GameScreen() {
        this.debugMode = false;
        world.setContactListener(collisionListener);

        floorTexture = new Texture("floor.png");

        pnj = new Pnj(50, 90);
        Room room = new Room(100, 100);
        room.addElements(pnj);
        neutralDungeon = new Dungeon(room);

        hudBatch = new SpriteBatch();
        box2DDebugRenderer = new Box2DDebugRenderer();
        text = new Text();

        Pixmap pm = new Pixmap(100,100, Pixmap.Format.RGB888);
        pm.setColor(0,1,0,1);
        pm.fill();
        healthBarPlayer = new Texture(pm);
        pm.setColor(1,0,0,1);
        pm.fill();
        healthBarEnnemy = new Texture(pm);
        pm.setColor(10,18,20,1);
        pm.fill();
        healthBarPlayerEmpty = new Texture(pm);
        pm.setColor(0,0,0,1);
        pm.fill();
        healthBarEnnemyEmpty = new Texture(pm);
        timeSinceLastDash = 1;
        dash = false;
    }

    public void addBodyToRemove(Body ... bodies){
        this.bodiesToRemove.addAll(List.of(bodies));
    }

    /**
     * Déplace le personnage du joueur suivant les touches du clavier enfoncées.
     */
    private void updatePlayer() {
        int dx=0, dy=0;
        timeSinceLastDash += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            dx = -Constants.PLAYER_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            dx = Constants.PLAYER_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            dy = Constants.PLAYER_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            dy = -Constants.PLAYER_SPEED;
        }

        //Déplacement rapide du personnage (1 dash par seconde maximum)
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            if (timeSinceLastDash > 1.0f) {
                if(!dash) {
                    timeSinceLastDash = 1;
                }
                dash = true;
            }
        }
        if(dash) {
            dy = player.getOrientation().getY() * Constants.DASH_SPEED * Constants.PLAYER_SPEED;
            dx = player.getOrientation().getX() * Constants.DASH_SPEED * Constants.PLAYER_SPEED;
            if (timeSinceLastDash > 1.1f) {
                timeSinceLastDash = 0;
                dash = false;
            }
        }
        player.move(dx, dy);

        if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            SoundManager.ATTACK.play();
            player.attack();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        player.getBody().setLinearVelocity(0f, 0f);
    }

    /**
     * Place la caméra sur la salle courante du donjon courant.
     */
    private void updateCamera() {
        float x = this.player.getBody().getPosition().x;
        float y = this.player.getBody().getPosition().y;
        float width = Constants.CAMERA_WIDTH;
        float height = Constants.CAMERA_HEIGHT;
        //eviter les depacements a gauche et a doite de la salle
        if( x < width/2)
            x = width/2;
        else  if( x > (float)this.currentDungeon.getLoadedRoom().getWidth()-width/2)
            x = (float)this.currentDungeon.getLoadedRoom().getWidth()-width/2;
        //eviter les depacements en bas et a haut de la salle
        if( y < height/2)
            y = height/2;
        else  if( y > (float)this.currentDungeon.getLoadedRoom().getHeight()-height/2)
            y = (float)this.currentDungeon.getLoadedRoom().getHeight()-height/2;
        Mapz.getInstance().getCamera().position.set(x,y,0);
        Mapz.getInstance().getCamera().update();
    }

    private void updateWorld() {
        for(Body body: bodiesToRemove){
            world.destroyBody(body);
        }
        this.bodiesToRemove.clear();
    }

    public void reset() {
        if(currentDungeon != null)
            currentDungeon.getLoadedRoom().removeElements(player);
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for(Body b : bodies)
            addBodyToRemove(b);
        player.load();
        // We load the neutral room
        currentDungeon = neutralDungeon;
        currentDungeon.getLoadedRoom().removeElements(doorNextDungeon);
        doorNextDungeon = new DoorToDungeon(50, 50, neutralDungeon.getLoadedRoom(), DungeonFactory.getInstance().createDungeon(neutralDungeon), 50, 10, Orientation.UP);
        currentDungeon.getLoadedRoom().addElements(doorNextDungeon);
        currentDungeon.loadDungeon(player, world);
    }

    public void update(){
        updateWorld();
        updatePlayer();
        updateCamera();
        currentDungeon.update();
    }

    public Dungeon getCurrentDungeon() {
        return currentDungeon;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public void displayWithBatch() {
        SpriteBatch batch = Mapz.getInstance().getBatch();
        batch.begin();
        batch.setProjectionMatrix(Mapz.getInstance().getCamera().combined);
        batch.disableBlending();
        batch.draw(floorTexture, 0, 0);
        batch.enableBlending();
        currentDungeon.draw();
        drawHealthBarEnnemy(batch);
        batch.end();

        hudBatch.begin();
        if(player.getMovableState().equals(MovableState.DEAD)) {
            text.gameOverText(hudBatch,100, 400);
        }
        pnjTextDisplayingArea(hudBatch);
        hudPlayerDisplaying(hudBatch);
        hudBatch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //ScreenUtils.clear(1, 0, 0, 1);
        float deltaTime = Gdx.graphics.getDeltaTime();
        world.step(deltaTime, 6, 2);
        update();
        Gdx.gl.glClearColor(0f,0f,0f,0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyPressed(Input.Keys.F1)) debugMode = true;
        if(Gdx.input.isKeyPressed(Input.Keys.F2)) debugMode = false;
        if (debugMode) box2DDebugRenderer.render(world,Mapz.getInstance().getCamera().combined);
        else displayWithBatch();
    }

    @Override
    public void resize(int width, int height) {

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
    public void dispose() {
        world.dispose();
        hudBatch.dispose();
    }

    public void changeDungeon(Dungeon to) {
        if(to.equals(neutralDungeon)) {
            try{
                player.save();
            } catch(IOException e) {
                e.printStackTrace();
            }
            reset();
            return;
        }
        currentDungeon.getLoadedRoom().removeElements(player);
        World world = Mapz.getInstance().getGameScreen().getWorld();
        // Unlock the world to remove the bodies
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for(Body b : bodies)
            Mapz.getInstance().getGameScreen().addBodyToRemove(b);
        currentDungeon = to;
        currentDungeon.loadDungeon(player, world);
    }

    public void hudPlayerDisplaying(SpriteBatch batch){
        batch.draw(healthBarPlayer,30,445, Math.max(player.getHealth(), 0),15);
        batch.draw(healthBarPlayerEmpty,30+Math.max(player.getHealth(), 0),445,player.getMaxHealth()-Math.max(player.getHealth(), 0),15);
        batch.draw(new Texture("money.png"),580,430,40,40);
        text.moneyText(batch, "" + player.getMoney());
    }
    public void drawHealthBarEnnemy(SpriteBatch batch){
        for(Element e : currentDungeon.getLoadedRoom().getElements()){
            if(e.isEnemy()){
                Enemy enemy = (Enemy) e;
                if(enemy.getHealth()>0) {
                    batch.draw(healthBarEnnemy, enemy.getBody().getPosition().x - (enemy.getMaxHealth() >> 2), enemy.getBody().getPosition().y+2, enemy.getHealth() >> 1, 1);
                    batch.draw(healthBarEnnemyEmpty,enemy.getBody().getPosition().x - (enemy.getMaxHealth() >> 2) + (enemy.getHealth() >> 1), enemy.getBody().getPosition().y+2, (enemy.getMaxHealth() >> 1) - (enemy.getHealth() >> 1), 1);
                }
            }
        }
    }

    public void pnjTextDisplayingArea(SpriteBatch batch){
        float x = player.getBody().getPosition().x;
        float y = player.getBody().getPosition().y;
        float xM = pnj.getBody().getPosition().x;
        float yM = pnj.getBody().getPosition().y;
        if(((x-xM)*(x-xM)+(y-yM)*(y-yM))<1000) {
            if(currentDungeon == neutralDungeon && !player.getMovableState().equals(MovableState.DEAD)) {
                text.pnjText(batch, pnj.getUpgrade().getText(), pnj.getBody().getPosition().x * 4.3f, pnj.getBody().getPosition().y * 4.5f);
            }
        }
    }
}
