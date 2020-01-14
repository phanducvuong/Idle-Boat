package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.GMain;
import com.ss.core.action.exAction.GTemporalAction;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.Game;

public class Boat {

  private TextureAtlas boatAtlas = GMain.boatAtlas;
  private Image imgBoat;
  public Rectangle bound;
  private int blood;
  private float speed;

  public boolean isAlive = false;
  public int col = -1;

  private Game G;
  private Group gUI;

  public Boat(Game G, Group gUI, String boat, float speed, int blood) {
    this.G = G;
    this.gUI = gUI;
    this.speed = speed;
    this.blood = blood;

    imgBoat = GUI.createImage(boatAtlas, boat);

    assert imgBoat != null;
    bound = new Rectangle(imgBoat.getX(), imgBoat.getY(), imgBoat.getWidth(), imgBoat.getHeight());
  }

  public void setPosBoat() {

    //todo: random pos in config => set position imagBoat and bound and set col by 1, 2, 3 or 4
    //todo: move boat

//    imgBoat.setPosition(pos.x, 0);
//    bound.setPosition(pos.x, 0);
  }

  public void moveBoat() {

    float x = imgBoat.getX();
    float y = imgBoat.getY();

    imgBoat.addAction(GTemporalAction.add(speed, (p,a) -> {

      float yy = (float) (y + GStage.getWorldHeight()*p);
      imgBoat.setPosition(x, yy);
      bound.setPosition(x, yy);

    }));
  }

  public void addBoatToScene() {
    gUI.addActor(imgBoat);
  }

  public Image getImgBoat() {
    return imgBoat;
  }

  public void setImgBoat(Image imgBoat) {
    this.imgBoat = imgBoat;
  }

  public int getBlood() {
    return blood;
  }

  public void setBlood(int blood) {
    this.blood = blood;
  }

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }
}
