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

public class Weapon extends Image {

  private TextureAtlas weaponAtlas = GMain.weaponAtlas;
  private Image cannon, cannonFight, bullet;
  private Rectangle bound;
  private float attackBullet, speed;
  private int idCannon;
  private Vector2 pos;
  public boolean isFight = false, isDrag = false, isOn = false; //isOn: chk weapon is have in screen or not

  private Group gUI;
  private Game G;
  private IMerge iMerge;
  private ICollision iCollision;

  public Weapon(Game G, Group gUI, String name_cannon, String name_bullet, float attackBullet, float speed, int idCannon) {
    this.gUI = gUI;
    this.idCannon = idCannon;
    this.G = G;
    this.iMerge = G;
    this.iCollision = G;
    this.attackBullet = attackBullet;
    this.speed = speed;

    cannon = GUI.createImage(weaponAtlas, name_cannon);
    bullet = GUI.createImage(weaponAtlas, name_bullet);
    cannonFight = GUI.createImage(weaponAtlas, name_cannon+"_fight");

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

        updatePosWeapon(cannon.getX(), cannon.getY());
        isDrag = false;
        isFight = false;
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
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 20);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 20);

          setPosBullet(vTo);
          break;
        case 4:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 + 5, vTo.y - 60);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 + 5, vTo.y - 25);

          setPosBullet(vTo);
          break;
        case 5:
          cannon.setPosition(vTo.x + cannon.getWidth()/2, vTo.y - 60);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2, vTo.y - 20);

          setPosBullet(vTo);
          break;
        case 6:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 10, vTo.y - 40);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 10, vTo.y - 22);

          setPosBullet(vTo);
          break;
        case 7:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 40);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 15);

          setPosBullet(vTo);
          break;
        case 8:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 20, vTo.y - 40);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 20, vTo.y - 15);

          setPosBullet(vTo);
          break;
        case 9:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 35, vTo.y - 30);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 35, vTo.y - 20);

          setPosBullet(vTo);
          break;
        case 10:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 30);
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 + 8, cannon.getY() + cannon.getHeight()/2 - 28);

          setPosBullet(vTo);
          break;
        case 11:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 30);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 30);

          setPosBullet(vTo);
          break;
        case 12: case 13:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 30, vTo.y - 25);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 30, vTo.y - 25);

          setPosBullet(vTo);
          break;
        case 14:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 45, vTo.y);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 45, vTo.y + 35);

          setPosBullet(vTo);
          break;
        case 15:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 45, vTo.y - 15);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 45, vTo.y + 5);

          setPosBullet(vTo);
          break;
        case 16:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 50);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 35);

          setPosBullet(vTo);
          break;
        case 17:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 50);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 35);

          setPosBullet(vTo);
          break;
        case 18:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 50);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 50);

          setPosBullet(vTo);
          break;
        case 19:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 35);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 35);

          setPosBullet(vTo);
          break;
        case 20:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 20);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y);

          setPosBullet(vTo);
          break;
        case 21:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 28, vTo.y - 15);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 28, vTo.y);

          setPosBullet(vTo);
          break;
        case 22:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 20, vTo.y - 15);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 20, vTo.y - 15);

          setPosBullet(vTo);
          break;
        case 23:
          cannon.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 15);
          cannonFight.setPosition(vTo.x + cannon.getWidth()/2 - 23, vTo.y - 15);

          setPosBullet(vTo);
          break;
      }
    }
    catch (Exception ignored) {  }
  }

  private void setPosBullet(Vector2 vTo) {
    try {
      switch (idCannon) {
        case 0: case 1: case 2: case 3:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 + 5, cannon.getY() + cannon.getHeight()/2 + 15);
          break;
        case 4:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 5, cannon.getY() + cannon.getHeight()/2 - 40);
          break;
        case 5:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 5, cannon.getY() + cannon.getHeight()/2 - 35);
          break;
        case 6: case 16:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 2, cannon.getY() + cannon.getHeight()/2 - 35);
          break;
        case 7:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 8, cannon.getY() + cannon.getHeight()/2 - 35);
          break;
        case 8:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 + 2, cannon.getY() + cannon.getHeight()/2 - 28);
          break;
        case 9:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 - 4, cannon.getY() + cannon.getHeight()/2 - 28);
          break;
        case 10:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 + 8, cannon.getY() + cannon.getHeight()/2 - 28);
          break;
        case 11:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 + 3, cannon.getY() + cannon.getHeight()/2 - 20);
          break;
        case 12:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 + 20, cannon.getY() + cannon.getHeight()/2 - 5);
          break;
        case 13:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 + 10, cannon.getY() + cannon.getHeight()/2 - 5);
          break;
        case 14:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 + 22, cannon.getY() + cannon.getHeight()/2 - 10);
          break;
        case 15:
          bullet.setPosition(cannon.getX() + cannon.getWidth()/2 + 22, cannon.getY() + cannon.getHeight()/2 - 25);
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
    GTween.action(bullet,

            sequence(
              delay(delay),
              parallel(

                      moveTo(bullet.getX(), -50, SPEED_BULLET, linear),
                      GSimpleAction.simpleAction((d, a) -> {

                        bound.setPosition(bullet.getX(), bullet.getY());
                        //check collision
                        iCollision.Collision(this);

                        return true;
                      })//simple action
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

  public void addCannonToScene() {

    cannon.setScale(2f);
    gUI.addActor(cannon);

  }

  public void addCannonFightToScene() {

    try {
      cannonFight.setScale(2f);
      gUI.addActor(cannonFight);
    }
    catch (Exception ex) {  }

  }

  public void addBulletToScene() {

    bullet.setScale(2f);
    bullet.setVisible(false);
    gUI.addActor(bullet);

  }

  public void removeWeapon() {

    isFight = false;
    isDrag = false;
    isOn = false;

    cannon.remove();
    cannonFight.remove();
    bullet.remove();
  }

  public Rectangle getBound() {
    return bound;
  }
}