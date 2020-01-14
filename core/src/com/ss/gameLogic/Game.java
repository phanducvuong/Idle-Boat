package com.ss.gameLogic;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.GMain;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.data.Data;
import com.ss.gameLogic.Interface.ICollision;
import com.ss.gameLogic.Interface.IMerge;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.objects.Boat;
import com.ss.gameLogic.objects.PosOfWeapon;
import com.ss.gameLogic.objects.Weapon;

import java.util.ArrayList;
import java.util.List;

public class Game implements IMerge, ICollision {

  private TextureAtlas textureAtlas = GMain.textureAtlas;
  private Group gUI;
  private LogicGame logicGame = LogicGame.getInstance(this);

  public List<PosOfWeapon> listPosOfWeapon;
  public List<Boat> listBoat;

  private Data data = Data.getInstance();

  public Game() {
    gUI = new Group();
    gUI.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());

    GStage.addToLayer(GLayer.ui, gUI);

    data.initListWeapon(this, gUI);
    data.initListBoat(this, gUI);

    listBoat = new ArrayList<>();

    initAsset();
    initPosOfWeapon();
    initBoat();
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

  public Boat boat;
  private void initWeapon() {
    Weapon weapon = data.listWeapon.get(0);

    weapon.addCannonToScene();
//    weapon.addCannonFightToScene();
    weapon.setPosWeapon(Config.POS_0);

    weapon.addBulletToScene();
//    weapon.attack(0);

    listPosOfWeapon.get(0).setWeapon(weapon);
    listPosOfWeapon.get(0).setEmpty(false);
  }

  private void initBoat() {
    boat = data.listBoat.get(0);

    boat.setPosBoat();
    boat.addBoatToScene();

    //todo move all boat in listBoat => boat is die reset boat and move boat again

  }

  @Override
  public void mergeWeapon(Vector2 vFrom, Vector2 vTo) {
    logicGame.chkMergeWeapon(vFrom, vTo, listPosOfWeapon);
  }

  @Override
  public void Collision(Weapon weapon) {

  }
}
