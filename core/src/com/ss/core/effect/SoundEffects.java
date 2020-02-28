package com.ss.core.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;

public class SoundEffects {

    public static HashMap<String, Sound> s;

    public static FileHandle burn = Gdx.files.internal("sound/burn.mp3");
    public static FileHandle buy_cannon = Gdx.files.internal("sound/buy_cannon.mp3");
    public static FileHandle cannon_shot = Gdx.files.internal("sound/cannon_shot.mp3");
    public static FileHandle merge_cannon = Gdx.files.internal("sound/merge_cannon.mp3");
    public static FileHandle smoke = Gdx.files.internal("sound/smoke.mp3");
    public static FileHandle unlock_cannon = Gdx.files.internal("sound/unlock_cannon.mp3");
    public static FileHandle unlock_cannon_intro = Gdx.files.internal("sound/unlock_cannon_intro.mp3");
    public static FileHandle unlock_enemy = Gdx.files.internal("sound/unlock_enemy.mp3");
    public static FileHandle wave_failed = Gdx.files.internal("sound/wave_failed.mp3");
    public static FileHandle wave_finished = Gdx.files.internal("sound/wave_finished.mp3");
    public static FileHandle wave_start = Gdx.files.internal("sound/wave_start.mp3");
    public static FileHandle whoosh = Gdx.files.internal("sound/whoosh.mp3");
    public static FileHandle click_button = Gdx.files.internal("sound/click_button.mp3");

    public static boolean isMute = false;

    public static void initSound() {

        s = new HashMap<>();

        s.put("unlock_cannon", Gdx.audio.newSound(unlock_cannon));
        s.put("unlock_cannon_intro", Gdx.audio.newSound(unlock_cannon_intro));
        s.put("unlock_enemy", Gdx.audio.newSound(unlock_enemy));
        s.put("wave_failed", Gdx.audio.newSound(wave_failed));
        s.put("wave_finished", Gdx.audio.newSound(wave_finished));
        s.put("wave_start", Gdx.audio.newSound(wave_start));
        s.put("whoosh", Gdx.audio.newSound(whoosh));
        s.put("click_button", Gdx.audio.newSound(click_button));

    }

    public static void start(Sound music) {

      if (!isMute)
        music.play();

    }

    public static void stop(Sound music) {

      if (!isMute)
        music.stop();
    }

    public static void start(String music) {

      if (!isMute)
        s.get(music).play();

    }

    public static void stop(String music) {

      if (!isMute)
        s.get(music).stop();

    }
}
