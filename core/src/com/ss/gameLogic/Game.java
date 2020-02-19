package com.ss.gameLogic;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.GMain;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.data.Data;
import com.ss.gameLogic.Interface.ICollision;
import com.ss.gameLogic.Interface.IDanger;
import com.ss.gameLogic.Interface.IMerge;
import static com.ss.gameLogic.config.Config.*;

import com.ss.gameLogic.effect.EffectGame;
import com.ss.gameLogic.objects.Boat;
import com.ss.gameLogic.objects.PosOfWeapon;
import com.ss.gameLogic.objects.Weapon;
import com.ss.gameLogic.ui.GamePlayUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements IMerge, ICollision, IDanger {

  private TextureAtlas textureAtlas = GMain.textureAtlas;
  private Group gUI;
  private Group gBoat;
  public LogicGame logicGame = LogicGame.getInstance(this);
  public GamePlayUI gamePlayUI;

  public List<PosOfWeapon> listPosOfWeapon;
  private List<Boat> listBoat;

  private EffectGame effectGame = EffectGame.getInstance();
  private Data data = Data.getInstance();

  private int countTarget = 0;
  private int target = 30; // target to finished level

  public Game() {
    gUI = new Group();
    gBoat = new Group();
    gUI.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());

    GStage.addToLayer(GLayer.ui, gUI);
    gUI.addActor(gBoat);

    data.initListWeapon(this, gamePlayUI, gUI);
    data.initListBoat(this, gUI);

    listBoat = new ArrayList<>();
    initLv(10, "boat_0", "boat_1", "boat_2");

    initAsset();
    initPosOfWeapon();
    initWeapon();

    //todo: setTextCoinCollection and coinBuyWeapon if first time play game else get it in share preference
    gamePlayUI = new GamePlayUI(this, gUI);
    gamePlayUI.setTextCoinCollection(0);
    gamePlayUI.setIconWeapon(0);
    gamePlayUI.setTextCoinBuyWeapon(0);

    nextBoat();
  }

  private void initLv(int numBoat, String ...boat) {

    for (int i=0; i<numBoat; i++)
      for (String b : boat)
        listBoat.add(data.HMBoat.get(b).get(i));

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

    Weapon weapon = data.HMWeapon.get("cannon_2").get(0);
    weapon.addCannonToScene();
    weapon.setPosWeapon(POS_0);
    weapon.addBulletToScene();
    weapon.isOn = true;

    listPosOfWeapon.get(0).setWeapon(weapon);
    listPosOfWeapon.get(0).setEmpty(false);

//    Weapon weapon1 = data.HMWeapon.get("cannon_0").get(1);
//    weapon1.addCannonToScene();
//    weapon1.setPosWeapon(POS_1);
//    weapon1.addBulletToScene();
//    weapon1.isOn = true;
//
//    listPosOfWeapon.get(1).setWeapon(weapon1);
//    listPosOfWeapon.get(1).setEmpty(false);
//
//    Weapon weapon2 = data.HMWeapon.get("cannon_0").get(2);
//    weapon2.addCannonToScene();
//    weapon2.setPosWeapon(POS_2);
//    weapon2.addBulletToScene();
//    weapon2.isOn = true;
//
//    listPosOfWeapon.get(2).setWeapon(weapon2);
//    listPosOfWeapon.get(2).setEmpty(false);
//
//    Weapon weapon6 = data.HMWeapon.get("cannon_0").get(6);
//    weapon6.addCannonToScene();
//    weapon6.setPosWeapon(POS_6);
//    weapon6.addBulletToScene();
//    weapon6.isOn = true;
//
//    listPosOfWeapon.get(6).setWeapon(weapon6);
//    listPosOfWeapon.get(6).setEmpty(false);
//
//    Weapon weapon8 = data.HMWeapon.get("cannon_0").get(8);
//    weapon8.addCannonToScene();
//    weapon8.setPosWeapon(POS_8);
//    weapon8.addBulletToScene();
//    weapon8.isOn = true;
//
//    listPosOfWeapon.get(8).setWeapon(weapon8);
//    listPosOfWeapon.get(8).setEmpty(false);
  }

  public void startBoat(Boat boat) {
    //todo move all boat in listBoat => boat is die reset boat and move boat again

    gamePlayUI.gTopUI.setZIndex(1000);

    try {
      gBoat.addActor(boat);
      boat.addAction(GSimpleAction.simpleAction(this::moveBoat));
    }
    catch (Exception ignored) {  }
  }

  private boolean moveBoat(float dt, Actor a) {

    Boat boat = (Boat) a;
    boat.addBoatToScene();
    boat.moveBoat();

    SequenceAction seq = new SequenceAction();
    seq.addAction(delay(TIME_APPEAR_BOAT));
    seq.addAction(run(this::nextBoat));

    gBoat.addAction(seq);

    return true;
  }

  private void nextBoat() {

    if (countTarget < target)
      startBoat(getRandomBoat());

  }

  private Boat getRandomBoat() {

    Collections.shuffle(listBoat);

    for (Boat boat : listBoat)
      if (!boat.isAlive) {
        boat.setPosBoat();
        return boat;
      }
    return null;
  }

  @Override
  public void mergeWeapon(Vector2 vFrom, Vector2 vTo) {
    logicGame.chkMergeWeapon(vFrom, vTo, listPosOfWeapon);
  }

  @Override
  public void Collision(Weapon weapon) {
    for (Boat boat : listBoat) {
      if (weapon.getBound().overlaps(boat.getBound())) {

        float blood = boat.getBlood() - weapon.getAttackBullet();
        boat.setBlood(blood);

        effectGame.eftBoat(boat);

        float x = boat.getImgBoat().getX() - 10;
        float y = boat.getImgBoat().getY();

        if (boat.getBlood() <= 0) {

          effectGame.eftBurn(weapon.getImgBurn(), x, y);
          gamePlayUI.setTextCoinCollection(boat.coin);

          boat.showCoin();
          boat.resetBoat(countTarget, target);
          countTarget++;

          effectGame.eftPercentFinished(gamePlayUI.imgPercentFinished, target);
        } //reset boat when boat is destroy
        else {

          effectGame.eftSmoke(weapon.getImgSmoke(), x-15, y);

        }

        if (!weapon.isDrag)
          weapon.isFight = false;
        weapon.resetWeapon();

        break;
      }
    }
  }

  @Override
  public void chkWin() {

    if (logicGame.chkWinGame(listBoat))
      System.out.println("WIN");

  }

  @Override
  public void endGame() {

    System.out.println("END GAME");
    for (Boat boat : listBoat)
      boat.getImgBoat().clear();

  }

  @Override
  public void fire(Boat boat) {

    try {

      for (PosOfWeapon pos : listPosOfWeapon) {
        if (pos.col == boat.col && !pos.getIsEmpty())
          if (!pos.getWeapon().isFight)
            pos.getWeapon().attack(TIME_DELAY_ATTACK);
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void delWeapon(Weapon weapon) {

    for (PosOfWeapon pos : listPosOfWeapon){
      if (pos.getWeapon() == weapon) {
        pos.setWeapon(null);
        pos.setEmpty(true);
        weapon.removeWeapon();
        break;
      }
    }

  }

  public void addWeapon(int idCannon, float x, float y) {

    for (Weapon w : data.HMWeapon.get("cannon_"+idCannon))
      if (!w.isOn) {
        w.clrActionWeapon();

        for (PosOfWeapon pos : listPosOfWeapon)
          if (pos.getIsEmpty()) {
            pos.setEmpty(false);
            pos.setWeapon(w);

            try {

              w.isOn = true;
              w.setPosWeapon(pos.pos);
              w.addBulletToScene();
              w.addCannonToScene();

            }
            catch (Exception ex) { ex.printStackTrace(); }

            break;
          }

        break;
      }

  }
}
