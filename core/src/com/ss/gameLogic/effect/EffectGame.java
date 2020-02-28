package com.ss.gameLogic.effect;

import static com.badlogic.gdx.math.Interpolation.*;
import com.badlogic.gdx.math.Vector2;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.google.gson.Gson;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.action.exAction.GTween;
import com.ss.core.effect.SoundEffects;
import com.ss.core.util.GStage;
import com.ss.gameLogic.Game;
import com.ss.gameLogic.objects.Boat;
import com.ss.gameLogic.objects.PosOfWeapon;
import com.ss.gameLogic.objects.Weapon;

public class EffectGame {

  private static EffectGame instance = null;
  private Game G;

  public static EffectGame getInstance() {
    return instance == null ? instance = new EffectGame() : instance;
  }

  public void eftMergeWeapon(Vector2 pos, Weapon w1, Weapon w2, Runnable onComplete) {

    w1.gCannon.setPosition(pos.x - 40, pos.y);
    w2.gCannon.setPosition(pos.x + 40, pos.y);

    GTween.action(w1.gCannon, moveTo(pos.x, pos.y, .3f, swingIn), onComplete);
    w2.gCannon.addAction(moveTo(pos.x, pos.y, .3f, swingIn));

  }

  public void eftWeaponAttack(Weapon weapon) {

    if (weapon.isEftAttack) {

      if (weapon.getCannonFight() != null) {

        weapon.getBullet().setVisible(true);
        weapon.getCannon().setVisible(false);
        weapon.getCannonFight().setVisible(true);

        Runnable run = () -> {

          weapon.getCannonFight().setVisible(false);
          weapon.getCannon().setVisible(true);

        };

        actionAttack(weapon.getCannonFight(), run);

      }
      else {

        weapon.getBullet().setVisible(true);
        actionAttack(weapon.getCannon(), null);

      }

      weapon.isEftAttack = false;

    }

  }

  private void actionAttack(Image image, Runnable onComplete) {

    GTween.action(image,
            scaleBy(0, -.2f, .35f, fastSlow),
            () -> GTween.action(image,
                    scaleBy(0, .2f, .35f, linear), onComplete)
    );

  }

  public void eftBoat(Boat boat) {

    SequenceAction seq = sequence(
            scaleBy(0, 0.25f, .05f, fastSlow),
            scaleBy(0, -0.25f, .05f, fastSlow)
    );

    boat.getImgBoat().addAction(seq);

  }

  public void eftBurn(Image image, float x, float y, Boat boat) {

    image.setPosition(x, y);
    image.setScale(0);
    image.setZIndex(1000);
    image.setVisible(true);

    SequenceAction seq = sequence(
            parallel(
                    scaleTo(1.2f, 1.2f, .25f, fastSlow),
                    run(() -> {

                      SoundEffects.start("burn");

                    })
            ),
            alpha(0, .25f, fastSlow),
            run(() -> {

              image.setScale(0);
              image.setVisible(false);
              image.getColor().a = 1;

            })
    );

    image.addAction(seq);

  }

  public void eftImgShine(Image img) {

    SequenceAction seq = sequence(
            rotateBy(-10, .25f, linear),
            run(() -> eftImgShine(img))
    );

    img.addAction(seq);

  }

  public void eftSmoke(Image image, float x, float y, Boat boat) {

    image.setPosition(x, y);
    image.setScale(0);
    image.setZIndex(1000);
    image.getColor().a = .8f;
    image.setVisible(true);

    SequenceAction seq = sequence(
            parallel(
                    scaleTo(1, 1, .25f, fastSlow),
                    moveBy(0, -20, .25f, fastSlow),
                    alpha(0, 1f, linear),
                    run(() -> {

//                      SoundEffects.stop(boat.smoke);
                      SoundEffects.start("smoke");

                    })
            ),
            run(() -> {
              image.setScale(0);
              image.setVisible(false);
              image.getColor().a = .8f;
            })
    );

    image.addAction(seq);

  }

  public void eftClick(Group btn, Runnable onComplete) {

    SequenceAction seq = sequence(
      scaleBy(-.1f, -.1f, .05f, fastSlow),
      scaleBy(.1f, .1f, .05f, fastSlow),
      run(onComplete)
    );

    btn.addAction(seq);

  }

  public void eftNewWeapon(Group gNew, Runnable onComplete) {

    SequenceAction seq = sequence(
            scaleTo(1f, 1f, .75f, swingOut),
            delay(1f),
            run(onComplete)
    );

    gNew.addAction(seq);

  }

  public void eftClick(Image btn, Runnable onComplete) {

    SequenceAction seq = sequence(
            scaleBy(-.1f, -.1f, .05f, fastSlow),
            scaleBy(.1f, .1f, .05f, fastSlow),
            run(onComplete)
    );

    btn.addAction(seq);

  }

  public void eftPercentFinished(Image percent, int target) {

    float deltaScl = 1f / target;
    float sclX = percent.getScaleX() + deltaScl;

    if (sclX >= 1)
      percent.setScale(1f);
    else
      percent.setScale(sclX, 1f);

  }

  public void eftMerge(PosOfWeapon pos) {

    pos.getImgEftMerge().setVisible(true);
    pos.getImgEftMerge().setZIndex(1000);
    SequenceAction seq = sequence(
            parallel(
                    scaleTo(1f, 1f, .5f, fastSlow),
                    run(() -> SoundEffects.start("merge_cannon"))
            ),
            run(pos::resetImgMerge)
    );

    pos.getImgEftMerge().addAction(seq);

  }

  public void eftShowCoinDelWeapon(Label lbCoin) {

    SequenceAction seq = sequence(
            alpha(1, .75f, linear),
            alpha(0, .75f, linear)
    );

    lbCoin.addAction(seq);

  }

  public void eftWhenAddWeapon(Group gCannon, float x, float y, Runnable onComplete) {

    SequenceAction seq0 = sequence(
            parallel(
                    moveTo(x, y, .25f, fastSlow),
                    scaleTo(1f, 1f, .25f, fastSlow)
            ),
            run(onComplete)
    );

    gCannon.addAction(seq0);

  }

  public void eftShowLbNoEnough(Label lb) {

    lb.clear();
    lb.setPosition(GStage.getWorldWidth()/2 - lb.getWidth()/2, GStage.getHeight()/2 - lb.getHeight()/2);
    lb.setZIndex(1000);
    lb.getColor().a = 0;

    SequenceAction seq = sequence(
            parallel(
                    alpha(1f, .25f, linear),
                    moveBy(0, -150, .75f, linear)
            ),
            alpha(0f, .5f, linear),
            run(() -> lb.moveBy(0, 50))
    );

    lb.addAction(seq);

  }

  public void eftShowLbWinOrEndGame(Label lb, boolean isWin) {

    lb.getColor().a = 0;

    SequenceAction seq = sequence(
            alpha(1f, 1f, linear),
            delay(1f),
            alpha(0f, 1f, linear),
            delay(1f),
            run(() -> G.logicGame.chkWinOrEndGame(isWin))
    );

    if (isWin) {

      SoundEffects.stop("wave_finished");
      SoundEffects.start("wave_finished");

    }
    else {

      SoundEffects.stop("wave_failed");
      SoundEffects.start("wave_failed");

    }

    lb.addAction(seq);

  }

  public void eftShowNewWave(Label lb, Runnable onComplete) {

    float x = -GStage.getWorldWidth()/2 - lb.getWidth();
    float xTo = GStage.getWorldWidth()/2 - lb.getWidth()/2;
    float y = GStage.getWorldHeight()/2 - lb.getHeight()/2 - 200;

    SequenceAction seq = sequence(
            parallel(
                    moveTo(xTo, y, 1.25f, fastSlow),
                    run(() -> {
                      SoundEffects.stop("wave_start");
                      SoundEffects.start("wave_start");
                    })
            ),
            delay(.75f),
            moveBy(GStage.getWorldWidth(), 0, .75f, swingIn),
            run(onComplete)
    );

    lb.addAction(seq);

  }

  public void eftSliceTutorialL(Image t1, Image t2) {

    t1.setPosition(-t1.getWidth(), 0);
    t1.setVisible(true);

    t1.addAction(moveTo(0, 0, .25f, fastSlow));

    SequenceAction seq = sequence(
            moveTo(GStage.getWorldWidth(), 0, .25f, fastSlow),
            run(() -> t2.setVisible(false))
    );

    t2.addAction(seq);

  }

  public void eftSliceTutorialR(Image t1, Image t2, Runnable onComplete) {

    t2.setVisible(true);
    t2.setPosition(GStage.getWorldWidth(), 0);

    SequenceAction seq = sequence(
            moveTo(-t1.getWidth(), 0, .25f, fastSlow),
            run(() -> t1.setVisible(false)),
            run(onComplete)
    );

    t1.addAction(seq);
    t2.addAction(moveTo(0, 0, .25f, fastSlow));

  }

  public void setGame(Game G) {
    this.G = G;
  }

  public void eftAniMergeWeapon(Image w1, Image w2, Runnable onComplete) {

    float x = GStage.getWorldWidth() - w2.getWidth();
    float y = GStage.getWorldHeight()/2 - 100;

    float xTo = GStage.getWorldWidth()/2 - w1.getWidth()/2;
    float yTo = GStage.getWorldHeight()/2 - w1.getHeight()/2 - 100;

    w1.getColor().a = 1f;
    w2.getColor().a = 1f;

    G.logicGame.setPosWeaponMerge(w1, 0, y);
    G.logicGame.setPosWeaponMerge(w2, x, y);

    G.gAnimMerWeapon.addActor(G.bgGame);
    w1.setVisible(true);
    w2.setVisible(true);
    w1.setZIndex(1000);
    w2.setZIndex(1000);

    SequenceAction seq1 = sequence(
            rotateBy(-10, .1f, fastSlow),
            rotateBy(20, .1f, fastSlow),
            rotateBy(-20, .1f, fastSlow),
            rotateBy(20, .1f, fastSlow),
            rotateBy(-20, .1f, fastSlow),
            rotateBy(20, .1f, fastSlow),
            rotateTo(0, .1f, fastSlow),
            parallel(
                    moveTo(xTo, yTo, .5f, fastSlow),
                    alpha(0, .5f, linear)
            )
    );

    SequenceAction seq2 = sequence(
            rotateBy(10, .1f, fastSlow),
            rotateBy(-20, .1f, fastSlow),
            rotateBy(20, .1f, fastSlow),
            rotateBy(-20, .1f, fastSlow),
            rotateBy(20, .1f, fastSlow),
            rotateBy(-20, .1f, fastSlow),
            rotateTo(0, .1f, fastSlow),
            parallel(
                    moveTo(xTo, yTo, .5f, fastSlow),
                    alpha(0, .5f, linear),
                    run(() -> G.animMergeWeapon.stop())
            ),
            run(onComplete)
    );

    SoundEffects.stop("unlock_cannon_intro");
    SoundEffects.start("unlock_cannon_intro");

    G.animMergeWeapon.start();
    w1.addAction(seq1);
    w2.addAction(seq2);

  }

}
