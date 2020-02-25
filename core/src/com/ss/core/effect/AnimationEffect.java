package com.ss.core.effect;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ss.GMain;

import java.util.HashMap;

public class AnimationEffect {

//    private static TextureAtlas textureAtlas = GMain.textureAtlas;
    public static HashMap<String, TextureRegion[]> anims = new HashMap<>();
    public static TextureRegion[] listFrameGirlAnimation;

    public static void LoadAnimation() {
        listFrameGirlAnimation = new TextureRegion[3];
        for (int i = 0; i < 3; i++)
            listFrameGirlAnimation[i] = GMain.animAtlas.findRegion("flash_"+i);

        anims.put("anim_flash", listFrameGirlAnimation);

        System.out.println(anims.get("anim_flash").length);
    }
}
