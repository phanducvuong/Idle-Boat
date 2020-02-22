package com.ss.gameLogic.effect;

import static com.badlogic.gdx.math.Interpolation.*;
import com.badlogic.gdx.math.Vector2;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ss.core.action.exAction.GTween;
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

  public void eftBurn(Image image, float x, float y) {

    image.setPosition(x, y);
    image.setScale(0);
    image.setZIndex(1000);
    image.setVisible(true);

    //setZindex for gShop when shop is show on screen
    G.gamePlayUI.setShowGShop();

    SequenceAction seq = sequence(
            scaleTo(1.2f, 1.2f, .25f, fastSlow),
            alpha(0, .25f, fastSlow),
            run(() -> {

              image.setScale(0);
              image.setVisible(false);
              image.getColor().a = 1;

            })
    );

    image.addAction(seq);

  }

  public void eftSmoke(Image image, float x, float y) {

    image.setPosition(x, y);
    image.setScale(0);
    image.setZIndex(1000);
    image.getColor().a = .8f;
    image.setVisible(true);

    G.gamePlayUI.setShowGShop();

    SequenceAction seq = sequence(
            parallel(
                    scaleTo(1, 1, .25f, fastSlow),
                    moveBy(0, -20, .25f, fastSlow),
                    alpha(0, 1f, linear)
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
            scaleTo(1f, 1f, .5f, fastSlow),
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

    lb.clear();
    lb.setZIndex(1000);
    lb.getColor().a = 0;

    SequenceAction seq = sequence(
            alpha(1f, 1f, linear),
            delay(1f),
            alpha(0f, 1f, linear),
            delay(1f),
            run(() -> eftShowLbNewWaveOrEndGame(G.gamePlayUI.lbNewWave, isWin))
    );

    lb.addAction(seq);

  }

  private void eftShowLbNewWaveOrEndGame(Label lb, boolean isWin) {

    if (isWin) {

      lb.setZIndex(1000);
      float x = GStage.getWorldWidth()/2 - lb.getWidth()/2;
      float y = GStage.getWorldHeight()/2 - lb.getHeight()/2 - 200;

      SequenceAction seq = sequence(
              moveTo(x, y, .75f, fastSlow),
              delay(1f),
              moveTo(GStage.getWorldWidth()/2 + lb.getWidth(), y, .5f, fastSlow),
              run(() -> {

                lb.setPosition(-GStage.getWorldWidth()/2 - lb.getWidth(), GStage.getWorldHeight()/2 - lb.getHeight()/2 - 200);
                G.resetWhenLevelUp();

              })
      );

      lb.addAction(seq);

    }//isWin = true => new wave else setVisible for imgBgEndGame = false
    else {

      G.gamePlayUI.imgBgEndGame.setVisible(false);
      G.gamePlayUI.imgPercentFinished.setScale(0);
      G.resetWhenEndGame();

      //todo: reset game
      //todo: show ads full screen when pass two level

    }

  }

  public void setGame(Game G) {
    this.G = G;
  }

}
