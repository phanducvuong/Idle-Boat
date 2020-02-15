package com.ss.gameLogic.effect;

import static com.badlogic.gdx.math.Interpolation.*;
import com.badlogic.gdx.math.Vector2;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.action.exAction.GTween;
import com.ss.gameLogic.objects.Boat;
import com.ss.gameLogic.objects.Weapon;

public class EffectGame {

  private static EffectGame instance = null;

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

  public void eftColiisionBoat(Image image, float x, float y) {

    image.setPosition(x, y);
    image.setScale(0);
    image.setZIndex(1000);
    image.setVisible(true);

    SequenceAction seq = sequence(
            scaleTo(1, 1, .25f, fastSlow),
            alpha(0, .25f, fastSlow),
            run(() -> {

              image.setScale(0);
              image.setVisible(false);
              image.getColor().a = 1;

            })
    );

    image.addAction(seq);

  }

}
