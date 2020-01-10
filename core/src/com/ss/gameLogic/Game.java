package com.ss.gameLogic;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.GMain;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.data.Data;
import com.ss.gameLogic.Interface.IMerge;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.objects.PosOfWeapon;
import com.ss.gameLogic.objects.Weapon;

import java.util.ArrayList;
import java.util.List;

public class Game implements IMerge {

  private TextureAtlas textureAtlas = GMain.textureAtlas;
  private Group gUI;
  private LogicGame logicGame = LogicGame.getInstance(this);

  public List<PosOfWeapon> listPosOfWeapon;
  public List<Weapon> listWeapon;

  private Data data = Data.getInstance();

  public Game() {
    gUI = new Group();
    gUI.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());

    GStage.addToLayer(GLayer.ui, gUI);

    listWeapon = new ArrayList<>();

    initAsset();
    initPosOfWeapon();
    initWeapon();
  }

  private void initAsset() {
    Image bg = GUI.createImage(textureAtlas, "bg");
    assert bg != null;
    bg.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    gUI.addActor(bg);
  }

  private void initPosOfWeapon() {
    listPosOfWeapon = new ArrayList<>();

    for (int i=0; i<10; i++) {
      PosOfWeapon p = new PosOfWeapon(gUI);
      p.setPosition(i);
      p.name = i+"";
      listPosOfWeapon.add(p);
    }
  }

  private void initWeapon() {
    Weapon weapon1 = new Weapon(this, gUI, "cannon_0", "bullet_0", Config.POS_0, 0);
    Weapon weapon2 = new Weapon(this, gUI, "cannon_2", "bullet_0", Config.POS_1, 2);

    listPosOfWeapon.get(1).setWeapon(weapon2);
    listPosOfWeapon.get(1).setEmpty(false);
    listPosOfWeapon.get(0).setWeapon(weapon1);
    listPosOfWeapon.get(0).setEmpty(false);
  }

  @Override
  public void mergeWeapon(Vector2 vFrom, Vector2 vTo) {
    logicGame.chkMergeWeapon(vFrom, vTo, listPosOfWeapon);
  }
}
