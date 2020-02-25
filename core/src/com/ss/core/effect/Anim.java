package com.ss.core.effect;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

public class Anim extends Actor {

    private Group gEffect, gAnim = new Group();
    private Animation<TextureRegion> anim;
    float elapsedTime;

    public Anim(Group group, String ani, float x, float y) {
        this.anim = new Animation<>(1f/10f, AnimationEffect.anims.get(ani));
        this.gEffect = group;
        setX(x);
        setY(y);
        gAnim.addActor(this);
        gAnim.setVisible(false);

        gEffect.addActor(gAnim);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        setOrigin(Align.center);
        TextureRegion t = anim.getKeyFrame(elapsedTime, true);
        float x = getX() - t.getRegionWidth();
        float y = getY() - 180;
        float width = t.getRegionWidth();
        float height = t.getRegionHeight();

        batch.draw(t, x, y, 0, 0, width, height, 2f, 2f, 0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
    }

    public void start() {

      gAnim.setZIndex(1000);
      gAnim.setVisible(true);

    }

    public void stop() {

      gAnim.setVisible(false);

    }
}
