package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.ss.GMain;
import com.ss.core.util.GUI;
import com.ss.gameLogic.Game;
import com.ss.gameLogic.Interface.IMerge;

public class Weapon {

  private TextureAtlas weaponAtlas = GMain.weaponAtlas;
  private Image cannon, cannonFight, bullet;
  private int attackBullet;
  private int idCannon;
  private Vector2 pos;

  private Group gUI;
  private Game G;
  private IMerge iMerge;

  public Weapon(Game G, Group gUI, String name_cannon, String name_bullet, Vector2 pos, int idCannon) {
    this.gUI = gUI;
    this.idCannon = idCannon;
    this.pos = pos;
    this.G = G;
    this.iMerge = G;

    cannon = GUI.createImage(weaponAtlas, name_cannon);
    bullet = GUI.createImage(weaponAtlas, name_bullet);
    cannonFight = GUI.createImage(weaponAtlas, name_cannon+"_fight");

    assert cannon != null;
    cannon.setPosition(pos.x + cannon.getWidth()/2 - 25, pos.y - 20);
    cannon.setScale(2f);
    gUI.addActor(cannon);

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

        cannon.moveBy(x - cannon.getWidth() / 2, y - cannon.getHeight() / 2);

      }

      @Override
      public void dragStop(InputEvent event, float x, float y, int pointer) {
        super.dragStop(event, x, y, pointer);

        updatePosWeapon(cannon.getX(), cannon.getY());

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
          cannon.setPosition(pos.x + cannon.getWidth()/2 - 25, pos.y - 20);
          break;
        }
      }
      else {
        cannon.setPosition(pos.x + cannon.getWidth()/2 - 25, pos.y - 20);
      }
    }
  }

  public void setPosWeapon(Vector2 vTo) {
    pos = vTo;
    cannon.setPosition(vTo.x + cannon.getWidth()/2 - 25, vTo.y - 20);
  }

  public int getIdCannon() {
    return idCannon;
  }
}