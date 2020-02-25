package com.ss.gameLogic.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.effect.EffectGame;

public class PosOfWeapon {

  private Group gUI;
  public Group gPos;
  private Image boxContainer;
  public Vector2 pos;
  private Weapon weapon;
  private boolean isEmpty = true;
  public String name;
  private Image imgEftMerge;

  public int col;

  public PosOfWeapon(Group gUI, Group gPos) {
    this.gUI = gUI;
    this.gPos = gPos;

    boxContainer = GUI.createImage(GMain.textureAtlas, "pos_weapon");
    assert boxContainer != null;
    gPos.addActor(boxContainer);

    imgEftMerge = GUI.createImage(GMain.textureAtlas, "eft_merge_weapon");
    assert imgEftMerge != null;
    imgEftMerge.setOrigin(Align.center);
    imgEftMerge.setScale(0);
    imgEftMerge.setVisible(false);
    gPos.addActor(imgEftMerge);

  }

  public void setPosition(int i) {
    switch (i) {
      case 0:
        boxContainer.setPosition(Config.POS_0.x, Config.POS_0.y);
        imgEftMerge.setPosition(Config.POS_0.x - 40, Config.POS_0.y - 50);
        pos = Config.POS_0;
        col = 1;
        break;
      case 1:
        boxContainer.setPosition(Config.POS_1.x, Config.POS_1.y);
        imgEftMerge.setPosition(Config.POS_1.x - 40, Config.POS_1.y - 50);
        pos = Config.POS_1;
        col = 2;
        break;
      case 2:
        boxContainer.setPosition(Config.POS_2.x, Config.POS_2.y);
        imgEftMerge.setPosition(Config.POS_2.x - 40, Config.POS_2.y - 50);
        pos = Config.POS_2;
        col = 3;
        break;
      case 3:
        boxContainer.setPosition(Config.POS_3.x, Config.POS_3.y);
        imgEftMerge.setPosition(Config.POS_3.x - 40, Config.POS_3.y - 50);
        pos = Config.POS_3;
        col = 3;
        break;
      case 4:
        boxContainer.setPosition(Config.POS_4.x, Config.POS_4.y);
        imgEftMerge.setPosition(Config.POS_4.x - 40, Config.POS_4.y - 50);
        pos = Config.POS_4;
        col = 2;
        break;
      case 5:
        boxContainer.setPosition(Config.POS_5.x, Config.POS_5.y);
        imgEftMerge.setPosition(Config.POS_5.x - 40, Config.POS_5.y - 50);
        pos = Config.POS_5;
        col = 1;
        break;
      case 6:
        boxContainer.setPosition(Config.POS_6.x, Config.POS_6.y);
        imgEftMerge.setPosition(Config.POS_6.x - 40, Config.POS_6.y - 50);
        pos = Config.POS_6;
        col = 0;
        break;
      case 7:
        boxContainer.setPosition(Config.POS_7.x, Config.POS_7.y);
        imgEftMerge.setPosition(Config.POS_7.x - 40, Config.POS_7.y - 50);
        pos = Config.POS_7;
        col = 0;
      break;
      case 8:
        boxContainer.setPosition(Config.POS_8.x, Config.POS_8.y);
        imgEftMerge.setPosition(Config.POS_8.x - 40, Config.POS_8.y - 50);
        pos = Config.POS_8;
        col = 4;
      break;
      case 9:
        boxContainer.setPosition(Config.POS_9.x, Config.POS_9.y);
        imgEftMerge.setPosition(Config.POS_9.x - 40, Config.POS_9.y - 50);
        pos = Config.POS_9;
        col = 4;
        break;
    }
  }

  public void setWeapon(Weapon weapon) {
    this.weapon = weapon;
  }

  public Weapon getWeapon() {
    return weapon;
  }

  public boolean getIsEmpty() {
    return isEmpty;
  }

  public void setEmpty(boolean isEmpty) {
    this.isEmpty = isEmpty;
  }

  public Image getImgEftMerge() { return imgEftMerge; }

  public void resetImgMerge() {

    imgEftMerge.setVisible(false);
    imgEftMerge.setScale(0);

  }

  public void startEftMerge() {
    EffectGame.getInstance().eftMerge(this);
  }

}
