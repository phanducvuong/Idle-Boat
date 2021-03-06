package com.ss.gameLogic.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GStage;
import com.ss.gameLogic.Game;

public class Config {

    public static float ratio = Gdx.graphics.getWidth() / 720;

//    public static Vector2 POS_1 = new Vector2(GStage.getWorldWidth()/2 - 44, Game.yBulwark + 100);
//    public static Vector2 POS_0 = new Vector2(POS_1.x - 115, POS_1.y);
//    public static Vector2 POS_2 = new Vector2(POS_1.x + 115, POS_1.y);
//    public static Vector2 POS_4 = new Vector2(POS_1.x, POS_1.y + 150);
//    public static Vector2 POS_3 = new Vector2(POS_1.x + 115, POS_1.y + 150);
//    public static Vector2 POS_5 = new Vector2(POS_1.x - 115, POS_1.y + 150);
//    public static Vector2 POS_6 = new Vector2(POS_0.x - 115, POS_1.y + 50);
//    public static Vector2 POS_7 = new Vector2(POS_6.x, POS_6.y + 130);
//    public static Vector2 POS_8 = new Vector2(POS_2.x + 115, POS_6.y);
//    public static Vector2 POS_9 = new Vector2(POS_8.x, POS_7.y);

  public static Vector2 POS_1 = new Vector2();
  public static Vector2 POS_0 = new Vector2();
  public static Vector2 POS_2 = new Vector2();
  public static Vector2 POS_4 = new Vector2();
  public static Vector2 POS_3 = new Vector2();
  public static Vector2 POS_5 = new Vector2();
  public static Vector2 POS_6 = new Vector2();
  public static Vector2 POS_7 = new Vector2();
  public static Vector2 POS_8 = new Vector2();
  public static Vector2 POS_9 = new Vector2();

  public static Vector2 POS_BOAT_0 = new Vector2();
  public static Vector2 POS_BOAT_1 = new Vector2(); // x: min, y: max
  public static Vector2 POS_BOAT_2 = new Vector2();
  public static Vector2 POS_BOAT_3 = new Vector2();
  public static Vector2 POS_BOAT_4 = new Vector2();

  public static void initPos() {

    //pos of weapon
    POS_1.x = GStage.getWorldWidth()/2 - 44;
    POS_1.y = Game.yBulwark + 70;

    POS_0.x = POS_1.x - 115;
    POS_0.y = POS_1.y;

    POS_2.x = POS_1.x + 115;
    POS_2.y = POS_1.y;

    POS_4.x = POS_1.x;
    POS_4.y = POS_1.y + 150;

    POS_3.x = POS_1.x + 115;
    POS_3.y = POS_1.y + 150;

    POS_5.x = POS_1.x - 115;
    POS_5.y = POS_1.y + 150;

    POS_6.x = POS_0.x - 115;
    POS_6.y = POS_1.y + 50;

    POS_7.x = POS_6.x;
    POS_7.y = POS_6.y + 130;

    POS_8.x = POS_2.x + 115;
    POS_8.y = POS_6.y;

    POS_9.x = POS_8.x;
    POS_9.y = POS_7.y;

    //pos of boat
    POS_BOAT_0.x = POS_6.x - 10;
    POS_BOAT_0.y = POS_6.y + 20;

    POS_BOAT_1.x = POS_0.x - 10;
    POS_BOAT_1.y = POS_0.y + 20;

    POS_BOAT_2.x = POS_1.x - 10;
    POS_BOAT_2.y = POS_1.y + 20;

    POS_BOAT_3.x = POS_2.x - 10;
    POS_BOAT_3.y = POS_2.y + 20;

    POS_BOAT_4.x = POS_8.x - 10;
    POS_BOAT_4.y  =POS_8.y + 20;

  }

    //speed bullet
    public static final float SPEED_BULLET = 1f;
    public static final float TIME_DELAY_ATTACK = 1f;
    public static final float TIME_BOAT_MOVE = 15f;
    public static final float TIME_APPEAR_BOAT = .5f;
    public static final float RANGE_BULLET_ATTACK = 100;

    //font
    public static final BitmapFont BITMAP_WHITE_FONT = GAssetsManager.getBitmapFont("font_white.fnt");
    public static final BitmapFont BITMAP_YELLOW_FONT = GAssetsManager.getBitmapFont("font_yellow.fnt");

    //delta coin in order to buy weapon in shop
    public static final long DELTA_COIN = 3;
    public static final long COIN_START_GAME = 20;
    public static long coin = 200; //get remote config

    //max
    public static float MAX_ATTACK_BULLET = 130;
    public static float MAX_HITPOINT = 300;
    public static float MAX_SPEED = 55;


    //todo: save listPosOfWeapon(done!), coinCollection(done!), idBestPowerCannon(done!), listItemShop(idCannon, coin)(done!), wave(done!), target(done
}
