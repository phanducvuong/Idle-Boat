package com.ss.gameLogic.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.GMain;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.Config;

public class PosOfWeapon {

  private Group gUI;
  private Image boxContainer;
  public Vector2 pos;
  private Weapon weapon;
  private boolean isEmpty = true;
  public String name;

  public PosOfWeapon(Group gUI) {
    this.gUI = gUI;

    boxContainer = GUI.createImage(GMain.textureAtlas, "pos_weapon");
    assert boxContainer != null;
    gUI.addActor(boxContainer);
  }

  public void setPosition(Vector2 pos) {
    boxContainer.setPosition(pos.x - boxContainer.getWidth()/2, pos.y);
  }

  public void setPosition(int i) {
    switch (i) {
      case 0: boxContainer.setPosition(Config.POS_0.x, Config.POS_0.y); pos = Config.POS_0; break;
      case 1: boxContainer.setPosition(Config.POS_1.x, Config.POS_1.y); pos = Config.POS_1; break;
      case 2: boxContainer.setPosition(Config.POS_2.x, Config.POS_2.y); pos = Config.POS_2; break;
      case 3: boxContainer.setPosition(Config.POS_3.x, Config.POS_3.y); pos = Config.POS_3; break;
      case 4: boxContainer.setPosition(Config.POS_4.x, Config.POS_4.y); pos = Config.POS_4; break;
      case 5: boxContainer.setPosition(Config.POS_5.x, Config.POS_5.y); pos = Config.POS_5; break;
      case 6: boxContainer.setPosition(Config.POS_6.x, Config.POS_6.y); pos = Config.POS_6; break;
      case 7: boxContainer.setPosition(Config.POS_7.x, Config.POS_7.y); pos = Config.POS_7; break;
      case 8: boxContainer.setPosition(Config.POS_8.x, Config.POS_8.y); pos = Config.POS_8; break;
      case 9: boxContainer.setPosition(Config.POS_9.x, Config.POS_9.y); pos = Config.POS_9; break;
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

}
