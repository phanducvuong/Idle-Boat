package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import static com.badlogic.gdx.math.Interpolation.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.Game;
import com.ss.gameLogic.Interface.IDanger;
import static com.ss.gameLogic.config.Config.*;

public class Boat extends Image {

  private TextureAtlas boatAtlas = GMain.boatAtlas;
  private Image imgBoat;
  private Rectangle bound;
  private float blood, tempBlood;
  private float speed;
  private int idBoat;
  public String name;

  public boolean isAlive = false;
  public boolean isDanger = false;
  public int col;

  private Game G;
  private Group gBoat;
  private IDanger iDanger;

  public Boat(Game G, Group gBoat, String boat, float speed, float blood, int idBoat) {
    this.G = G;
    this.gBoat = gBoat;
    this.speed = speed;
    this.blood = blood;
    this.tempBlood = blood;
    this.idBoat = idBoat;
    this.iDanger = G;
    this.name = boat;

    imgBoat = GUI.createImage(boatAtlas, boat);

    assert imgBoat != null;
    bound = new Rectangle(imgBoat.getX(), imgBoat.getY(), imgBoat.getWidth(), imgBoat.getHeight() - 15);
  }

  public void setPosBoat() {

    int rand = (int) Math.round(Math.random() * 4);
//    int randY = (int) Math.round(Math.random() * 100);

    imgBoat.setPosition(0, 0);
    bound.setPosition(0, 0);
    float randY = imgBoat.getY() + 100;

    switch (rand) {
      case 0:

        col = 0;
        imgBoat.setPosition(POS_BOAT_0.x, -randY);
        bound.setPosition(POS_BOAT_0.x, -randY);
        break;

      case 1:

        col = 1;
        imgBoat.setPosition(POS_BOAT_1.x, -randY);
        bound.setPosition(POS_BOAT_1.x, -randY);
        break;

      case 2:

        col = 2;
        imgBoat.setPosition(POS_BOAT_2.x, -randY);
        bound.setPosition(POS_BOAT_2.x, -randY);
        break;

      case 3:

        col = 3;
        imgBoat.setPosition(POS_BOAT_3.x, -randY);
        bound.setPosition(POS_BOAT_3.x, -randY);
        break;

      case 4:

        col = 4;
        imgBoat.setPosition(POS_BOAT_4.x, -randY);
        bound.setPosition(POS_BOAT_4.x, -randY);
        break;
    }
  }

  public void moveBoat() {

    float y = imgBoat.getY() + 100 + GStage.getWorldHeight();
    isAlive = true;

    imgBoat.addAction(Actions.parallel(

            Actions.moveBy(0, y, speed, linear),

            GSimpleAction.simpleAction((d, a) -> {

              bound.setPosition(imgBoat.getX(), imgBoat.getY());
              iDanger.fire(this);

//              if (imgBoat.getY() > 500) //end game
//                iDanger.endGame();

              return true;
            })
    ));
  }

  public void resetBoat() {
    isAlive = false;
    blood = tempBlood;
    bound.setPosition(0, 0);
    this.clearActions();
    imgBoat.clear();
    imgBoat.remove();

    iDanger.chkWin();
  }

  public void addBoatToScene() {
    gBoat.addActor(imgBoat);
  }

  public Image getImgBoat() {
    return imgBoat;
  }

  public void setImgBoat(Image imgBoat) {
    this.imgBoat = imgBoat;
  }

  public float getBlood() {
    return blood;
  }

  public void setBlood(float blood) {
    this.blood = blood;
  }

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public Rectangle getBound() { return bound; }
}
