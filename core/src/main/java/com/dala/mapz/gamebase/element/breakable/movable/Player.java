package com.dala.mapz.gamebase.element.breakable.movable;

import com.badlogic.gdx.utils.Timer;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.dala.mapz.Mapz;
import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.gamebase.element.breakable.Movable;
import com.dala.mapz.gamebase.element.breakable.Weapon;
import com.dala.mapz.utils.Constants;
import com.dala.mapz.utils.SoundManager;

import java.io.*;


/**
 * Classe représentant un joueur.
 */
public class Player extends Movable {

    private int money = 0;
    private String name;

    public Player(int x, int y) {
        super(x, y);
        this.material = Material.PLAYER;
        weapon = new Weapon(0.5f, 0.5f, 0.5f, 5);
    }

    @Override
    public void update() {
        if(health <= 0 && this.body.isActive()) {
            //transition vers le screen de restart
            SoundManager.GAME_OVER.play();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Mapz.getInstance().setRestartScreen();
                }
            },3f); // lancer le timer
        }
        super.update();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void save() throws IOException {
        if(name == null || name.equals(""))
            return;
        String json = JsonWriter.objectToJson(this);
        File folder = new File("../playerdata/");
        if(!folder.exists())
            folder.mkdirs();
        File file = new File(folder + "/" + name + ".json");
        if(!file.exists())
            file.createNewFile();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(json);
        }
    }

    public void load() {
        if(name == null || name.equals(""))
            return;
        File folder = new File("../playerdata/");
        if(!folder.exists())
            folder.mkdirs();
        StringBuilder json = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(folder + "/" + name + ".json"))) {
            String line = "";
            while ((line = reader.readLine()) != null)
                json.append(line);
        } catch(IOException e) {
            try {
                save();
            } catch(IOException e1) {
                e1.printStackTrace();
            }
            return;
        }
        Player p = (Player) JsonReader.jsonToJava(json.toString());
        this.health = p.health;
        this.maxHealth = p.maxHealth;
        this.weapon = p.weapon;
        this.money = p.money;
    }

    @Override
    public String toString() {
        return "Player : "+ super.toString();
    }



    public void incrementMoney(){
        this.money++;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money){
        this.money = money;
    }

    public void setSpeed(int speedBonus){
        Constants.PLAYER_SPEED += speedBonus;
    }

    public void increaseDamage(int damage){
        weapon = new Weapon(weapon.width(), weapon.height(), weapon.duration(), weapon.damage() + damage);
    }
}
