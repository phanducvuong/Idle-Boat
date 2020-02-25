package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import static com.badlogic.gdx.math.Interpolation.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
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
  public int coin;
  public boolean isAlive = false;
  public int col;
  public Label lbCoin;

  private Game G;
  private Group gBoat;
  private IDanger iDanger;

  public Boat(Game G, Group gBoat, String boat, float speed, float blood, int idBoat, int coin) {
    this.G = G;
    this.gBoat = gBoat;
    this.speed = speed;
    this.blood = blood;
    this.tempBlood = blood;
    this.idBoat = idBoat;
    this.iDanger = G;
    this.name = boat;
    this.coin = coin;

    imgBoat = GUI.createImage(boatAtlas, boat);
    imgBoat.setOrigin(Align.center);

    lbCoin = new Label("0", new Label.LabelStyle(BITMAP_WHITE_FONT, null));
    lbCoin.setVisible(false);
    lbCoin.setAlignment(Align.center);
    lbCoin.setFontScale(.6f);
    gBoat.addActor(lbCoin);

    lbCoin.setText("+" + G.logicGame.compressCoin(coin));

    assert imgBoat != null;
    bound = new Rectangle(imgBoat.getX(), imgBoat.getY(), imgBoat.getWidth(), imgBoat.getHeight() - 15);
  }

  public void setPosBoat() {

    int rand = (int) Math.round(Math.random() * 4);
//    int randY = (int) Math.round(Math.random() * 100);

    imgBoat.setPosition(0, 0);
    bound.setPosition(0, 0);
    float randY = imgBoat.getY() + 200;

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

    float y = imgBoat.getY() + 200 + GStage.getWorldHeight();
    isAlive = true;

    imgBoat.addAction(Actions.parallel(

            Actions.moveBy(0, y, speed, linear),

            GSimpleAction.simpleAction((d, a) -> {

              bound.setPosition(imgBoat.getX(), imgBoat.getY());

              if (imgBoat.getY() >= 50)
                iDanger.fire(this);

              if (imgBoat.getY() >= G.bulwark.getY()) //end game
                iDanger.endGame();

              return true;
            })
    ));
  }

  public void showCoin() {

    float x = imgBoat.getX();
    float y = imgBoat.getY();

    lbCoin.setZIndex(1000);
    lbCoin.setPosition(x - lbCoin.getWidth()/2 + 50, y);
    lbCoin.setVisible(true);

    SequenceAction seq = sequence(
            parallel(
                    Actions.moveBy(0, -20, .75f, fastSlow),
                    alpha(0, 1.5f, linear)
            ),
            run(() -> {
              lbCoin.setVisible(false);
              lbCoin.getColor().a = 1;
            })
    );

    lbCoin.addAction(seq);

  }

  public void resetBoat(int countTarget, int target) {

    reset();

    if (countTarget < target) {
      setPosBoat();
      G.startBoat(this);
    }

    iDanger.chkWin();

  }//prefabs boat

  public void reset() {

    isAlive = false;
    blood = tempBlood;
    bound.setPosition(0, 0);
    this.clearActions();
    imgBoat.clear();
    imgBoat.remove();
    imgBoat.setScale(1f);

  }//reset boat for new game

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
