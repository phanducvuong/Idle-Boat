package com.ss.gameLogic.effect;

import static com.badlogic.gdx.math.Interpolation.*;
import com.badlogic.gdx.math.Vector2;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.ss.core.action.exAction.GTween;
import com.ss.gameLogic.objects.Weapon;

public class EffectGame {

  private static EffectGame instance = null;

  public static EffectGame getInstance() {
    return instance == null ? instance = new EffectGame() : instance;
  }

  public void eftMergeWeapon(Vector2 pos, Weapon w1, Weapon w2, Runnable onComplete) {

    w1.getCannon().setPosition(pos.x - 40, pos.y);
    w2.getCannon().setPosition(pos.x + 40, pos.y);

    GTween.action(w1.getCannon(), moveTo(pos.x, pos.y, .3f, swingIn), onComplete);
    w2.getCannon().addAction(moveTo(pos.x, pos.y, .3f, swingIn));

  }

}
