package com.ss.core.action.exAction;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class GTemporalAction extends TemporalAction {
  private ActInterface at;
  private Actor actor;
  public static GTemporalAction add(float duration, ActInterface at) {
    GTemporalAction instance = Actions.action(GTemporalAction.class);
    instance.setDuration(duration);
    instance.setInterpolation(Interpolation.linear);
    instance.at = at;
    return  instance;
  }

  @Override
  protected void begin() {
    super.begin();
    actor = super.getActor();
  }

  @Override
  protected void update(float percent) {
    at.act(percent, actor);
  }

  @FunctionalInterface
  public interface ActInterface {
    void act(double percent, Actor actor);
  }
}
