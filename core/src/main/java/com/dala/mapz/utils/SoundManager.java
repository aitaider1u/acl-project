package com.dala.mapz.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public enum SoundManager {
    COIN(Gdx.audio.newSound(Gdx.files.internal("sound/coinSound.wav")), 0.3f),
    LIFE_POTION(Gdx.audio.newSound(Gdx.files.internal("sound/potionLifeSound.wav")), 0.2f),
    DOOR(Gdx.audio.newSound(Gdx.files.internal("sound/doorSound.wav")), 0.5f),
    ATTACK(Gdx.audio.newSound(Gdx.files.internal("sound/attackPlayerSound.wav")), 0.2f),
    ENEMY_DEATH(Gdx.audio.newSound(Gdx.files.internal("sound/enemyDeathSound.wav")), 0.2f),
    GAME_OVER(Gdx.audio.newSound(Gdx.files.internal("sound/gameOverSound.wav")), 0.2f),
    BUTTON(Gdx.audio.newSound(Gdx.files.internal("sound/buttonSound.wav")), 0.3f),
    ENEMY_ATTACK(Gdx.audio.newSound(Gdx.files.internal("sound/attackEnemySound.wav")), 0.2f);

    private Sound sound;
    private float volume;

    SoundManager(Sound sound, float volume) {
        this.sound = sound;
        this.volume = volume;
    }

    public void play() {
        sound.play(volume);
    }

    public static void dispose(){
        for(SoundManager s : SoundManager.values()) {
            s.sound.dispose();
        }
    }

}
