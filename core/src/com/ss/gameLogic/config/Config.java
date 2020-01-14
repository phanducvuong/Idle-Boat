package com.ss.gameLogic.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.ss.core.util.GStage;

public class Config {
    public static float ratio = Gdx.graphics.getWidth() / 720;

    public static final Vector2 POS_1 = new Vector2(GStage.getWorldWidth()/2 - 44, GStage.getWorldHeight()/2 + 270);
    public static final Vector2 POS_0 = new Vector2(POS_1.x - 115, POS_1.y);
    public static final Vector2 POS_2 = new Vector2(POS_1.x + 115, POS_1.y);
    public static final Vector2 POS_4 = new Vector2(POS_1.x, POS_1.y + 150);
    public static final Vector2 POS_3 = new Vector2(POS_1.x + 115, POS_1.y + 150);
    public static final Vector2 POS_5 = new Vector2(POS_1.x - 115, POS_1.y + 150);
    public static final Vector2 POS_6 = new Vector2(POS_0.x - 115, POS_1.y + 50);
    public static final Vector2 POS_7 = new Vector2(POS_6.x, POS_6.y + 130);
    public static final Vector2 POS_8 = new Vector2(POS_2.x + 115, POS_6.y);
    public static final Vector2 POS_9 = new Vector2(POS_8.x, POS_7.y);

    public static final Vector2 POS_BOAT_0 = new Vector2(POS_6.x - 10, POS_6.y + 20);
    public static final Vector2 POS_BOAT_1 = new Vector2(POS_0.x - 10, POS_0.y + 20); // x: min, y: max
    public static final Vector2 POS_BOAT_2 = new Vector2(POS_1.x - 10, POS_1.y + 20);
    public static final Vector2 POS_BOAT_3 = new Vector2(POS_2.x - 10, POS_2.y + 20);
    public static final Vector2 POS_BOAT_4 = new Vector2(POS_8.x - 10, POS_8.y + 20);
}
