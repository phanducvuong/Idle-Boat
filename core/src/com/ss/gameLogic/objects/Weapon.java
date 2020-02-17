package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import static com.badlogic.gdx.math.Interpolation.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.ss.gameLogic.config.Config.*;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.action.exAction.GTween;
import com.ss.core.util.GUI;
import com.ss.gameLogic.Game;
import com.ss.gameLogic.Interface.ICollision;
import com.ss.gameLogic.Interface.IMerge;
import com.ss.gameLogic.effect.EffectGame;
import com.ss.gameLogic.ui.GamePlayUI;

public class Weapon extends Image {

  private TextureAtlas weaponAtlas = GMain.weaponAtlas;
  private Image cannon, cannonFight, bullet, imgBurn, imgSmoke;
  private Rectangle bound;
  private float attackBullet, speed;
  private int idCannon;
  private Vector2 pos;
  public boolean isFight = false, isDrag = false, isOn = false; //isOn: chk weapon is have in screen or not
  public boolean isEftAttack = false; //flag to appear effect attack bullet

  private Group gUI;
  public Group gCannon;
  private Game G;
  private GamePlayUI gamePlayUI;
  private IMerge iMerge;
  private ICollision iCollision;

  private EffectGame effectGame = EffectGame.getInstance();

  public Weapon(Game G, GamePlayUI gamePlayUI, Group gUI, String name_cannon, String name_bullet, float attackBullet, float speed, int idCannon) {
    this.gUI = gUI;
    this.idCannon = idCannon;
    this.G = G;
    this.gamePlayUI = gamePlayUI;
    this.iMerge = G;
    this.iCollision = G;
    this.attackBullet = attackBullet;
    this.speed = speed;

    gCannon = new Group();

    cannon = GUI.createImage(weaponAtlas, name_cannon);
    bullet = GUI.createImage(weaponAtlas, name_bullet);

    if (weaponAtlas.findRegion(name_cannon+"_fight") != null) {
      cannonFight = GUI.createImage(weaponAtlas, name_cannon+"_fight");
      cannonFight.setScale(2f);
      cannonFight.setVisible(false);
      gCannon.addActor(cannonFight);
    }

    cannon.setScale(2f);
    gCannon.addActor(cannon);
    gCannon.setSize(cannon.getWidth(), cannon.getHeight());

    cannon.setOrigin(cannon.getWidth()/2, cannon.getHeight());
    if (cannonFight != null)
      cannonFight.setOrigin(cannonFight.getWidth()/2, cannonFight.getHeight());

    assert bullet != null;
    bound = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());

    initBurnAndSmoke();
    dragAndDrop();
  }

  private void dragAndDrop() {

    gCannon.addListener(new DragListener() {

      @Override
      public void dragStart(InputEvent event, float x, float y, int pointer) {
        super.dragStart(event, x, y, pointer);

        gCannon.setZIndex(1000);

      }

      @Override
      public void drag(InputEvent event, float x, float y, int pointer) {
        super.dragStart(event, x, y, pointer);

        isFight = true;
        isDrag = true;

        gCannon.moveBy(x - cannon.getWidth() / 2, y - cannon.getHeight() / 2);

      }

      @Override
      public void dragStop(InputEvent event, float x, float y, int pointer) {
        super.dragStop(event, x, y, pointer);

        float x1 = gamePlayUI.imgRecycle.getX() - 20;
        float x2 = gamePlayUI.imgRecycle.getX() + 40;
        float y1 = gamePlayUI.imgRecycle.getY() - 30;
        float y2 = gamePlayUI.imgRecycle.getY() + 30;

        if (gCannon.getX() >= x1 && gCannon.getX() <= x2 && gCannon.getY() >= y1 && gCannon.getY() <= y2)
          G.delWeapon(Weapon.this);
        else {
          updatePosWeapon(gCannon.getX(), gCannon.getY());
          isDrag = false;
          isFight = false;
        }

      }
    });

  }

  private void initBurnAndSmoke() {

    imgBurn = GUI.createImage(GMain.textureAtlas, "burn");
    imgBurn.setOrigin(imgBurn.getWidth()/2, imgBurn.getHeight()/2);

    imgSmoke = GUI.createImage(GMain.textureAtlas, "smoke");
    imgSmoke.setOrigin(Align.center);

    imgBurn.setVisible(false);
    imgSmoke.setVisible(false);

    gUI.addActor(imgBurn);
    gUI.addActor(imgSmoke);

  }

  private void updatePosWeapon(float x, float y) {
    for (int i=0; i<G.listPosOfWeapon.size(); i++) {

      Vector2 v = G.listPosOfWeapon.get(i).pos;

      if (x >= v.x - 44 && x <= v.x + 44 && y >= v.y - 88 && y <= v.y + 44) {

        if (pos != v) {
          iMerge.mergeWeapon(pos, v);
          break;
        }
        else {
          setPosWeapon(pos);
          break;
        }
      }
      else {
        setPosWeapon(pos);
      }
    }

  }

  public void setPosWeapon(Vector2 vTo) {

    pos = vTo;

    try {
      switch (idCannon) {
        case 0: case 1:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 5, vTo.y + 30);
          setPosBullet(vTo);

          break;
        case 2:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 5, vTo.y + 30);
          setPosBullet(vTo);

          cannonFight.setPosition(-1.5f, -6f);

          break;
        case 3:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 5, vTo.y + 32);
          setPosBullet(vTo);

          cannonFight.setPosition(0, -2);
          break;
        case 4:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 + 22, vTo.y);
          setPosBullet(vTo);
          cannonFight.setPosition(-2, 18);

          break;
        case 5:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 + 15, vTo.y + 10);
          setPosBullet(vTo);
          cannonFight.setPosition(0, 22);

          break;
        case 6:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 + 8, vTo.y + 20);
          setPosBullet(vTo);
          cannonFight.setPosition(1, 11);

          break;
        case 7:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2, vTo.y + 30);
          setPosBullet(vTo);
          cannonFight.setPosition(-1, 13);

          break;
        case 8:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2, vTo.y + 20);
          setPosBullet(vTo);
          cannonFight.setPosition(0,13);

          break;
        case 9:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 8, vTo.y + 25);
          setPosBullet(vTo);
          cannonFight.setPosition(-1,8);

          break;
        case 10:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 2, vTo.y + 30);
          setPosBullet(vTo);

          break;
        case 11:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2, vTo.y + 20);
          setPosBullet(vTo);
          cannonFight.setPosition(1,0);

          break;
        case 12:
          cannonFight.setVisible(true);
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 5, vTo.y + 25);
          setPosBullet(vTo);
          cannonFight.setPosition(2,0);

          break;
        case 13:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 5, vTo.y + 25);
          setPosBullet(vTo);
          cannonFight.setPosition(2,2);

          break;
        case 14:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 15, vTo.y + 40);
          setPosBullet(vTo);
          cannonFight.setPosition(0,15);

          break;
        case 15:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 15, vTo.y + 30);
          setPosBullet(vTo);
          cannonFight.setPosition(0,12);

          break;
        case 16:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 5, vTo.y + 10);
          setPosBullet(vTo);
          cannonFight.setPosition(1,8);

          break;
        case 17:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2, vTo.y + 20);
          setPosBullet(vTo);
          cannonFight.setPosition(-2,7);

          break;
        case 18:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2, vTo.y + 15);
          setPosBullet(vTo);
          cannonFight.setPosition(-1,0);

          break;
        case 19:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 2, vTo.y + 20);
          setPosBullet(vTo);
          cannonFight.setPosition(-1,2);

          break;
        case 20:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 5, vTo.y + 25);
          setPosBullet(vTo);
          cannonFight.setPosition(-1,9);

          break;
        case 21:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 - 5, vTo.y + 35);
          setPosBullet(vTo);
          cannonFight.setPosition(0.5f,7);

          break;
        case 22:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2 + 2, vTo.y + 40);
          setPosBullet(vTo);

          break;
        case 23:
          gCannon.setPosition(vTo.x + gCannon.getWidth()/2, vTo.y + 30);
          setPosBullet(vTo);
          cannonFight.setPosition(-1,5);

          break;
      }
    }
    catch (Exception ignored) {  }
  }

  private void setPosBullet(Vector2 vTo) {
    try {
      switch (idCannon) {
        case 0: case 1:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 18, gCannon.getY() + gCannon.getHeight()/2 - 35);
          break;
        case 2: case 3:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 16, gCannon.getY() + gCannon.getHeight()/2 - 20);
          break;
        case 4:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 15, gCannon.getY() + gCannon.getHeight()/2 - 120);
          break;
        case 5:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 15, gCannon.getY() + gCannon.getHeight()/2 - 105);
          break;
        case 6:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 18, gCannon.getY() + gCannon.getHeight()/2 - 95);
          break;
        case 7:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 30, gCannon.getY() + gCannon.getHeight()/2 - 100);
          break;
        case 8:
        case 23:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 20, gCannon.getY() + gCannon.getHeight()/2 - 90);
          break;
        case 9:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 28, gCannon.getY() + gCannon.getHeight()/2 - 80);
          break;
        case 10:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 15, gCannon.getY() + gCannon.getHeight()/2 - 50);
          break;
        case 11:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 20, gCannon.getY() + gCannon.getHeight()/2 - 85);
          break;
        case 12: case 13:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 5, gCannon.getY() + gCannon.getHeight()/2 - 50);
          break;
        case 14:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 8, gCannon.getY() + gCannon.getHeight()/2 - 55);
          break;
        case 15:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 10, gCannon.getY() + gCannon.getHeight()/2 - 65);
          break;
        case 16: case 17:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 30, gCannon.getY() + gCannon.getHeight()/2 - 110);
          break;
        case 18:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 20, gCannon.getY() + gCannon.getHeight()/2 - 80);
          break;
        case 19:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 45, gCannon.getY() + gCannon.getHeight()/2 - 70);
          break;
        case 20:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 40, gCannon.getY() + gCannon.getHeight()/2 - 80);
          break;
        case 21:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 35, gCannon.getY() + gCannon.getHeight()/2 - 85);
          break;
        case 22:
          bullet.setPosition(gCannon.getX() + gCannon.getWidth()/2 - 28, gCannon.getY() + gCannon.getHeight()/2 - 35);
          break;
      }
      bound.setPosition(bullet.getX(), bullet.getY());
    }
    catch (Exception ex) { System.out.println("ERR"); }
  }

  public void attack(float delay) {

    isFight = true;
    isEftAttack = true;
    GTween.action(bullet,

            sequence(
              delay(delay),
              parallel(
                      moveTo(bullet.getX(), -50, SPEED_BULLET, linear),
                      GSimpleAction.simpleAction((d, a) -> {

                        effectGame.eftWeaponAttack(this);
                        bound.setPosition(bullet.getX(), bullet.getY());
                        //check collision
                        iCollision.Collision(this);

                        return true;
                      })//simple action check collision
              )//parallel action
            ),
            () -> {
              if (!isDrag)
                isFight = false;
              resetWeapon();
            }//onComplete
    );
  }

  public void resetWeapon() {

    bullet.clearActions();
    bullet.setVisible(false);
    setPosBullet(pos);

  }

  public void clrActionWeapon() {

    gCannon.clearActions();
    bullet.clearActions();

  }

  public float getSpeed() {
    return speed;
  }

  public int getIdCannon() {
    return idCannon;
  }

  public float getAttackBullet() {
    return attackBullet;
  }

  public Image getBullet() {
    return bullet;
  }

  public Image getCannonFight() { return cannonFight; }

  public Image getCannon() { return cannon; }

  public void addCannonToScene() {

    gUI.addActor(gCannon);

  }

  public Image getImgBurn() { return imgBurn; }

  public Image getImgSmoke() { return imgSmoke; }

  public void addBulletToScene() {

    bullet.setScale(2f);
    bullet.setVisible(false);
    gUI.addActor(bullet);

  }

  public void removeWeapon() {

    isFight = false;
    isDrag = false;
    isOn = false;

    gCannon.remove();
    bullet.remove();
  }

  public Rectangle getBound() {
    return bound;
  }
}