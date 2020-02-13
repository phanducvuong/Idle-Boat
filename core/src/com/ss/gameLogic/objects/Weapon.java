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
  private Image cannon, cannonFight, bullet;
  private Rectangle bound;
  private float attackBullet, speed;
  private int idCannon;
  private Vector2 pos;
  public boolean isFight = false, isDrag = false, isOn = false; //isOn: chk weapon is have in screen or not
  public boolean isEftAttack = false; //flag to appear effect attack bullet

  private Group gUI;
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

    cannon = GUI.createImage(weaponAtlas, name_cannon);
    bullet = GUI.createImage(weaponAtlas, name_bullet);

    if (weaponAtlas.findRegion(name_cannon+"_fight") != null)
      cannonFight = GUI.createImage(weaponAtlas, name_cannon+"_fight");

    cannon.setOrigin(cannon.getWidth()/2, cannon.getHeight());

    if (cannonFight != null)
      cannonFight.setOrigin(cannonFight.getWidth()/2, cannonFight.getHeight());

    assert bullet != null;
    bound = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());

    dragAndDrop();
  }

  private void dragAndDrop() {
    cannon.addListener(new DragListener() {

      @Override
      public void dragStart(InputEvent event, float x, float y, int pointer) {
        super.dragStart(event, x, y, pointer);

        cannon.setZIndex(1000);

      }

      @Override
      public void drag(InputEvent event, float x, float y, int pointer) {
        super.dragStart(event, x, y, pointer);

        isFight = true;
        isDrag = true;

        cannon.moveBy(x - cannon.getWidth() / 2, y - cannon.getHeight() / 2);

      }

      @Override
      public void dragStop(InputEvent event, float x, float y, int pointer) {
        super.dragStop(event, x, y, pointer);

        float x1 = gamePlayUI.imgRecycle.getX() - 50;
        float x2 = gamePlayUI.imgRecycle.getX() + 20;
        float y1 = gamePlayUI.imgRecycle.getY() - 70;
        float y2 = gamePlayUI.imgRecycle.getY() + 60;

        if (cannon.getX() >= x1 && cannon.getX() <= x2 && cannon.getY() >= y1 && cannon.getY() <= y2)
          G.delWeapon(Weapon.this);
        else {
          updatePosWeapon(cannon.getX(), cannon.getY());
          isDrag = false;
          isFight = false;
        }

      }
    });

    if (cannonFight != null)
      cannonFight.addListener(new DragListener() {

      @Override
      public void dragStart(InputEvent event, float x, float y, int pointer) {
        super.dragStart(event, x, y, pointer);

        cannonFight.setZIndex(1000);

      }

      @Override
      public void drag(InputEvent event, float x, float y, int pointer) {
        super.dragStart(event, x, y, pointer);

        isFight = true;
        isDrag = true;

        cannonFight.moveBy(x - cannonFight.getWidth() / 2, y - cannonFight.getHeight() / 2);

      }

      @Override
      public void dragStop(InputEvent event, float x, float y, int pointer) {
        super.dragStop(event, x, y, pointer);

        float x1 = gamePlayUI.imgRecycle.getX() - 50;
        float x2 = gamePlayUI.imgRecycle.getX() + 20;
        float y1 = gamePlayUI.imgRecycle.getY() - 70;
        float y2 = gamePlayUI.imgRecycle.getY() + 60;

        if (cannonFight.getX() >= x1 && cannonFight.getX() <= x2 && cannonFight.getY() >= y1 && cannonFight.getY() <= y2)
          G.delWeapon(Weapon.this);
        else {
          updatePosWeapon(cannonFight.getX(), cannonFight.getY());
          isDrag = false;
          isFight = false;
        }

      }
    });
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
        case 0: case 1: case 2: case 3:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 5, vTo.y + 30);
          setPosBullet(vTo);

          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 5, vTo.y + 30);
          break;
        case 4:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 + 25, vTo.y);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 + 24, vTo.y + 20);

          break;
        case 5:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 + 15, vTo.y + 10);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 + 15, vTo.y + 30);

          break;
        case 6:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 + 8, vTo.y + 20);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 + 8, vTo.y + 28);

          break;
        case 7:
          cannon.setPosition(vTo.x + cannon.getWidth()/2, vTo.y + 30);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2, vTo.y + 45);

          break;
        case 8:
          cannon.setPosition(vTo.x + cannon.getWidth()/2, vTo.y + 20);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2, vTo.y + 32);

          break;
        case 9:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 8, vTo.y + 25);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 8, vTo.y + 25);

          break;
        case 10:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 5, vTo.y + 30);
          setPosBullet(vTo);

          break;
        case 11:
          cannon.setPosition(vTo.x + cannon.getWidth()/2, vTo.y + 20);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2, vTo.y + 20);

          break;
        case 12: case 13:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 5, vTo.y + 25);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 5, vTo.y + 25);

          break;
        case 14:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 15, vTo.y + 40);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 15, vTo.y + 52);

          break;
        case 15:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 15, vTo.y + 30);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 15, vTo.y + 45);

          break;
        case 16:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 50);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 35);

          break;
        case 17:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 50);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 35);

          break;
        case 18:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 50);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 50);

          break;
        case 19:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 35);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 35);

          break;
        case 20:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 20);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y);

          break;
        case 21:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 28, vTo.y - 15);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 28, vTo.y);

          break;
        case 22:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 20, vTo.y - 15);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 20, vTo.y - 15);

          break;
        case 23:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 15);
          setPosBullet(vTo);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 15);

          break;
      }
    }
    catch (Exception ignored) {  }
  }

  private void setPosBullet(Vector2 vTo) {
    try {
      switch (idCannon) {
        case 0: case 1:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 18, cannon.getY() + cannon.getHeight()/2 - 35);
          break;
        case 2: case 3:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 16, cannon.getY() + cannon.getHeight()/2 - 20);
          break;
        case 4:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 15, cannon.getY() + cannon.getHeight()/2 - 120);
          break;
        case 5:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 15, cannon.getY() + cannon.getHeight()/2 - 105);
          break;
        case 6: case 16:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 18, cannon.getY() + cannon.getHeight()/2 - 95);
          break;
        case 7:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 30, cannon.getY() + cannon.getHeight()/2 - 100);
          break;
        case 8:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 20, cannon.getY() + cannon.getHeight()/2 - 90);
          break;
        case 9:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 28, cannon.getY() + cannon.getHeight()/2 - 80);
          break;
        case 10:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 15, cannon.getY() + cannon.getHeight()/2 - 50);
          break;
        case 11:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 20, cannon.getY() + cannon.getHeight()/2 - 85);
          break;
        case 12: case 13:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 5, cannon.getY() + cannon.getHeight()/2 - 50);
          break;
        case 14:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 8, cannon.getY() + cannon.getHeight()/2 - 55);
          break;
        case 15:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 10, cannon.getY() + cannon.getHeight()/2 - 65);
          break;
        case 17:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 7, cannon.getY() + cannon.getHeight()/2 - 35);
          break;
        case 18:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 + 2, cannon.getY() + cannon.getHeight()/2 - 25);
          break;
        case 19:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 20, cannon.getY() + cannon.getHeight()/2 - 10);
          break;
        case 20:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 20, cannon.getY() + cannon.getHeight()/2 - 25);
          break;
        case 21:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 10, cannon.getY() + cannon.getHeight()/2 - 30);
          break;
        case 22:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 5, cannon.getY() + cannon.getHeight()/2 + 15);
          break;
        case 23:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 + 3, cannon.getY() + cannon.getHeight()/2 - 30);
          break;
      }
      bound.setPosition(bullet.getX(), bullet.getY());
    }
    catch (Exception ex) { System.out.println("ERR"); }
  }

  public void attack(float delay) {

    bullet.setVisible(true);

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

    cannon.setScale(2f);
    gUI.addActor(cannon);

    try {
      cannonFight.setScale(2f);
//      cannonFight.setVisible(false);
      gUI.addActor(cannonFight);
      cannonFight.debug();
    }
    catch (Exception ex) {  }

  }

  public void addBulletToScene() {

    bullet.setScale(2f);
//    bullet.setVisible(false);
    gUI.addActor(bullet);

  }

  public void removeWeapon() {

    isFight = false;
    isDrag = false;
    isOn = false;

    cannon.remove();
    if (cannonFight != null)
      cannonFight.remove();
    bullet.remove();
  }

  public Rectangle getBound() {
    return bound;
  }
}