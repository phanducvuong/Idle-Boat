package com.ss.gameLogic;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.effect.Anim;
import com.ss.core.effect.AnimationEffect;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.data.Data;
import com.ss.gameLogic.Interface.ICollision;
import com.ss.gameLogic.Interface.IDanger;
import com.ss.gameLogic.Interface.IMerge;
import static com.ss.gameLogic.config.Config.*;

import com.ss.gameLogic.config.C;
import com.ss.gameLogic.effect.EffectGame;
import com.ss.gameLogic.objects.Boat;
import com.ss.gameLogic.objects.PosOfWeapon;
import com.ss.gameLogic.objects.Weapon;
import com.ss.gameLogic.ui.GamePlayUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements IMerge, ICollision, IDanger {

  public static float yBulwark;

  private TextureAtlas textureAtlas = GMain.textureAtlas;
  public Group gUI;
  public Group gPos, gTopUI, gEffect, gEndGame, gAnimMerWeapon;
  private Group gBoat;
  public LogicGame logicGame = LogicGame.getInstance(this);
  public GamePlayUI gamePlayUI;
  public Image bulwark, bgGame;

  public Anim animMergeWeapon;

  public List<PosOfWeapon> listPosOfWeapon;
  public List<Boat> listBoat;

  private EffectGame effectGame = EffectGame.getInstance();
  public Data data = Data.getInstance();

  private int countTarget = 0;
  public int wave = 1, boatPresent = 0; //boatPresent to check new boat, wave to update level
  public int target = 10; // target to finished level

  public Game() {
    gUI = new Group();
    gPos = new Group();
    gTopUI = new Group();
    gBoat = new Group();
    gEndGame = new Group();
    gEffect = new Group();
    gAnimMerWeapon = new Group();
    gUI.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());

    GStage.addToLayer(GLayer.ui, gUI);
    GStage.addToLayer(GLayer.ui, gPos);
    GStage.addToLayer(GLayer.ui, gTopUI);
    GStage.addToLayer(GLayer.ui, gAnimMerWeapon);
    GStage.addToLayer(GLayer.ui, gEffect);
    GStage.addToLayer(GLayer.ui, gEndGame);

    gUI.addActor(gBoat);

    effectGame.setGame(this);
    AnimationEffect.LoadAnimation();

    data.setG(this);

    initAsset();
    initPosOfWeapon();
    initAnim();

    gamePlayUI = new GamePlayUI(this, gUI);
    //todo: setTextCoinCollection and coinBuyWeapon if first time play game else get it in share preference
    gamePlayUI.setTextCoinCollection(0);
    gamePlayUI.setIconWeapon(0);
    gamePlayUI.setTextCoinBuyWeapon(0);

    data.initListWeapon(this, gamePlayUI, gUI);
    data.initListBoat(this, gUI);

    listBoat = new ArrayList<>();
    initWeapon();

    resetWhenLevelUp();

  }

  private void initAnim() {

    float x = GStage.getWorldWidth()/2;
    float y = GStage.getWorldHeight()/2;
    animMergeWeapon = new Anim(gAnimMerWeapon, "anim_flash", x, y);

  }

  public void initLv(int numBoat, String ...boat) {

    for (int i=0; i<numBoat; i++) {
      for (String b : boat)
        listBoat.add(data.HMBoat.get(b).get(i));
    }

  }

  private void initAsset() {

    Image bg = GUI.createImage(textureAtlas, "bg");
    assert bg != null;
    bg.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    gUI.addActor(bg);

    bgGame = GUI.createImage(GMain.textureAtlas, "bg_black");

    bulwark = GUI.createImage(textureAtlas, "bulwark");
    assert bulwark != null;
    bulwark.setWidth(GStage.getWorldWidth());
    bulwark.setPosition(0, GStage.getWorldHeight() - bulwark.getHeight());
    gPos.addActor(bulwark);

    yBulwark = bulwark.getY();

  }

  //create image weapon to show effect when merge weapon
  public Image initWeaponMerge(String name_cannon) {

    Image cannonMerge = GUI.createImage(GMain.weaponMerge, name_cannon);
    cannonMerge.setOrigin(Align.center);
    cannonMerge.setVisible(false);
    gAnimMerWeapon.addActor(cannonMerge);

    return cannonMerge;

  }

  private void initPosOfWeapon() {
    listPosOfWeapon = new ArrayList<>();

    for (int i=0; i<10; i++) {
      PosOfWeapon p = new PosOfWeapon(gUI, gPos);
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

    if (logicGame.chkWinGame(listBoat)) {

      //todo set wave for lbNewWave

      gamePlayUI.lbFinishedWave.setText(C.lang.winGame);
      effectGame.eftShowLbWinOrEndGame(gamePlayUI.lbFinishedWave, true);

    }

  }

  @Override
  public void endGame() {

    for (Boat boat : listBoat)
      boat.getImgBoat().clear();

    for (PosOfWeapon pos : listPosOfWeapon) {
      if (pos.getWeapon() != null)
        pos.getWeapon().clrActionWeapon();
    }

    gamePlayUI.bgEndGame.setVisible(true);
    effectGame.eftShowLbWinOrEndGame(gamePlayUI.lbEndGame, false);

    //todo: reset level present

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

        long tempCoin = weapon.getCoin() / 3;
        gamePlayUI.lbShowCoinDelWeapon.setText("+"+logicGame.compressCoin(tempCoin));
        effectGame.eftShowCoinDelWeapon(gamePlayUI.lbShowCoinDelWeapon);
        gamePlayUI.setTextCoinCollection(tempCoin);

        break;
      }
    }

  }

  public void addWeapon(int idCannon, float x, float y) {

    try {

      for (Weapon w : data.HMWeapon.get("cannon_"+idCannon))
        if (!w.isOn) {
          w.clrActionWeapon();

          for (PosOfWeapon pos : listPosOfWeapon)
            if (pos.getIsEmpty()) {
              pos.setEmpty(false);
              pos.setWeapon(w);

              try {

                w.isOn = true;
                w.isFight = true;
                w.moveWeaponToPos(pos, w);
                w.addBulletToScene();
                w.addCannonToScene();

                gamePlayUI.setTextCoinCollectionBuyWeapon(w.getCoin());
                gamePlayUI.imgBgBlack.setZIndex(1000);
                gamePlayUI.gShop.setZIndex(1000);

              }
              catch (Exception ex) { ex.printStackTrace(); }

              break;
            }

          break;
        }

    }
    catch (Exception ex) { ex.printStackTrace(); }

  }

  public void resetWhenEndGame() {

    countTarget = 0;
    logicGame.resetGame(listPosOfWeapon, listBoat);

    SequenceAction seq = sequence(
            delay(1.5f),
            run(this::nextBoat)
    );

    gUI.addAction(seq);

  }

  public void resetWhenLevelUp() {

    //todo: plus target

    countTarget = 0;
    gamePlayUI.imgPercentFinished.setScale(0);

    initLv(15, "boat_0");

    gamePlayUI.setNewWave(wave);

    Runnable run = () -> {
      logicGame.updateLevel(wave);

      //todo: save game when level up!

      gamePlayUI.lbNewWave.setPosition(-GStage.getWorldWidth()/2 - gamePlayUI.lbNewWave.getWidth(), GStage.getWorldHeight()/2 - gamePlayUI.lbNewWave.getHeight()/2 - 200);
      nextBoat();
    };

    effectGame.eftShowNewWave(gamePlayUI.lbNewWave, run);

  }

}
